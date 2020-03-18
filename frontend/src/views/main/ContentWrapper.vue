<template>
  <main class="mainwrapper">
    <div class="h-inherit">
      <ul class="c-c-wrapper list-unstyled" @scroll="scrollEvt" >
        <MsgBox v-for="msg in msgArray" :key="msg.id">
          <template #m-icon>
            <img class="icon-round" :src="msg.user.picture" width="40" height="40"/>
          </template>
          <template #m-info>
            <!-- #으로 단축해서 사용 -->
            <strong>{{ msg.user.name }}</strong>
            <span style="font-size: 11px; margin-left:3px; ">{{ msg.str_send_date }}</span>
          </template>
          <template #m-content>
            <!-- #으로 단축해서 사용 -->
            <div v-html="msg.content" class="mychat-content"></div>
          </template>
        </MsgBox>
      </ul>
      <div class="c-i-wrapper">
        <!-- 더 뭔가 추가할 거 같아서 div로 감싸놓음 -->
        <div style="flex-grow:1;">
          <b-form-textarea
            inlist=""
            v-if="!show"
            id="textarea-no-resize"
            placeholder="Enter chat message"
            rows="3"
            no-resize
            v-model="message.content"
            @keyup.enter.exact="send"
            @keydown.shift.50='test'
          ></b-form-textarea>
          <div class="input-group" v-if="show">
            <div class="input-group-prepend">
              <span class="input-group-text">@</span>
            </div>
            <b-form-input
              @keydown.enter.exact="invite(user)"
              list="user-info-list"
              style="height: 60px;"
              v-model="message.content"
              ref="testinput"
              autofocus
            ></b-form-input>
            <datalist id="user-info-list">
              <option>userInfo</option>
              <option v-for="user in $store.state.userList" :key="user.email">{{ user.name }} {{ user.email }}</option>
            </datalist>
          </div>

        </div>
        <b-button @click="send" style="height: 57px; width: 70px; margin-left:20px;" variant="primary">전송</b-button>
      </div>
    </div>
  </main>
</template>
<script>
  import MsgBox from './MsgBox'

  export default {
    props: ['currentChannel', 'stompClient', 'msgArray'],
    name: 'ContentWrapper',
    components: {
      MsgBox
    },
    data() {
      return {
        show: false,
        sizes: ['Small', 'Medium', 'Large', 'Extra Large'],
        message: {
          channel_id: this.currentChannel.channel_id,
          content: '',
          sender: this.$store.state.currentUser.email,
          user:{}
        },
        // 채널 옮길 때마다 초기화 되어야한다.
        cursorPoint:{
          channel_id: 0,
          first:true,
          cursorId:0,
          empty:false
        },
        firstLoad: true,
        scrollHeight: 0,
      }
    },
    created () {
      this.getMessage()
    },
    mounted (){
    },
    updated() {
      this.scrollToEnd()
    },
    methods: {

      invite : function(user){
        const userName = this.message.content.split(" ")[0]
        this.message.content = userName+"님을 초대했습니다."
        this.send()
        this.show = !this.show
      },
      test: function (e) {
        this.show = !this.show
        this.$refs.testinput.focus()
      },
      send() {
        console.log(this.currentChannel)
        console.log(this.stompClient)
        this.message.channel_id = this.currentChannel.id
        this.message.user = this.$store.state.currentUser
        if (this.stompClient && this.stompClient.connected) {
          this.stompClient.send("/pub/chat/message", JSON.stringify(this.message), {})
          this.message.content = ''
        }
      },
      scrollEvt(e) {
        let element = e.target;
        if (element.scrollTop <= 0) {
          if(this.cursorPoint.empty == false){
            let wrapperEl = document.querySelector('.c-c-wrapper')
            let height = wrapperEl.scrollHeight 
            this.getMessage(wrapperEl,height)
          }
        }
      },
      getMessage(wrapperEl,height){
        this.cursorPoint.channel_id = this.currentChannel.id 
        this.$http.post('http://localhost:9191/api/message/getmsg',JSON.stringify(this.cursorPoint),{
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(res=>{
          console.log(res.data);
          if(res.data.length == 0 ){
            this.cursorPoint.empty = true
          }else{
            this.cursorPoint.first = false
            this.cursorPoint.cursorId = res.data[res.data.length-1].id
          }
          this.msgArray = res.data.reverse().concat(this.msgArray)
          if(wrapperEl!=null){
            this.$nextTick(()=> {
              wrapperEl.scrollTop = wrapperEl.scrollHeight - height
              this.scrollHeight = wrapperEl.scrollHeight  
            })
          }
          this.$emit('msgArrayUpdate',this.msgArray)
        })
      },
      scrollToEnd(){
        this.$nextTick(()=> {
          let wrapperEl = document.querySelector('.c-c-wrapper')
          if(this.firstLoad){
            this.scrollHeight = wrapperEl.scrollHeight
          }
          if((wrapperEl.scrollTop+wrapperEl.clientHeight) == this.scrollHeight || this.firstLoad){
            wrapperEl.scrollTop = wrapperEl.scrollHeight
            this.firstLoad = false
            this.scrollHeight = wrapperEl.scrollHeight
          }
        })
      }

    }

  }
</script>
