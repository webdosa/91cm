<template>
  <main class="mainwrapper">
    <div class="h-inherit">
      <ul class="c-c-wrapper list-unstyled" @scroll="scrollEvt">
        <MsgBox v-for="msg in msgArray" :key="msg.message.id">
          <template #m-info>
            <!-- #으로 단축해서 사용 -->
            <strong>{{ msg.user.name }}</strong>
            <span style="font-size: 11px; margin-left:3px; ">{{ msg.message.send_date }}</span>
          </template>
          <template #m-content>
            <!-- #으로 단축해서 사용 -->
            <div v-html="msg.message.content" class="mychat-content"></div>
          </template>
        </MsgBox>
      </ul>
      <div class="c-i-wrapper">
        <!-- 더 뭔가 추가할 거 같아서 div로 감싸놓음 -->
        <div style="flex-grow:1;">
          <b-form-textarea
            autofocus
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
              @keydown.enter.exact="send"
              @keydown.esc.exact="test"
              list="user-info-list"
              style="height: 80px;"
              v-model="message.content"
              ref="testinput"
              autofocus
            ></b-form-input>
            <datalist id="user-info-list">
              <option>userInfo</option>
              <option v-for="user in $store.state.userList">{{ user.name }} {{ user.email }}</option>
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
  import InviteService from '../../service/inviteService'

  export default {
    props: ['currentChannel', 'stompClient', 'msgArray'],
    name: 'ContentWrapper',
    components: {
      MsgBox
    },
    data() {
      return {
        show: false,
        message: {
          channel_id: this.currentChannel.channel_id,
          content: '',
          sender: this.$store.state.currentUser.email
        },
        cursorPoint: {
          first: true,
          cursorId: 0
        }
      }
    },
    methods: {
      invite: async function () {
        const userName = this.message.content.split(' ')[0]
        const userEmail = this.message.content.split(' ')[1]
        console.log(this.currentChannel.id)
       await InviteService.invite(this.$store.state.currentUser.email, this.currentChannel.id, userEmail)
          .then(res => {
            if (res.data){
              this.message.content = userName + '님을 초대했습니다.'
            }else{
              this.message.content = '초대에 실패하였습니다.'
            }
          }).catch(error => {
            this.message.content = '초대에 실패하였습니다.'
            console.log(error)
        })
        // if (InviteService.invite(this.$store.state.currentUser.email, this.currentChannel.id, userEmail)) {
        //   this.message.content = userName + '님을 초대했습니다.'
        // }else {
        //  this.message.content = '초대에 실패하였습니다.'
        // }
      },
      test: function (e) {
        this.message.content = ''
        this.show = !this.show
        this.$refs.testinput.focus()
      },
      send : async function () {
        if (this.show) {
          await this.invite()
          this.show = !this.show
        }
        console.log(this.currentChannel)
        console.log(this.stompClient)
        this.message.channel_id = this.currentChannel.id
        if (this.stompClient && this.stompClient.connected) {
          this.stompClient.send("/pub/chat/message", JSON.stringify(this.message), {})
          this.message.content = ''
        }
      },
      scrollEvt(e) {
        let element = e.target;
        if (element.scrollTop <= 0) {
          //추후 아래에 저장되어있는 메시지 가져오는 코드 추가
          // 저 emit코드는 .then(res=>{}) 안에다가 넣을 코드 emit 옆에 받은 데이터 전달
          // this.$emit('msgArrayUnshift')
          this.$http.get('http://localhost:9191/api/message/getmsg').then(res => {

          })

        }
      }
    }

  }
</script>
