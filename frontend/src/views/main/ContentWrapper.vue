<template>
  <main class="mainwrapper">
    <div class="h-inherit">
      <ul class="c-c-wrapper list-unstyled" @scroll="scrollEvt">
        <MsgBox v-for="msg in msgArray" :key="msg.id">
          <template #m-icon>
            <img v-if="msg.user.picture" class="icon-round" :src="msg.user.picture" width="40" height="40"/>
            <img v-else class="icon-round" src="../../assets/images/default-user-picture.png" width="40" height="40">
          </template>
          <template #m-info>
            <!-- #으로 단축해서 사용 -->
            <strong>{{ msg.user.name }}</strong>
            <span style="font-size: 11px; margin-left:3px; ">{{ msg.str_send_date }}</span>
          </template>
          <template #m-content>
            <!-- #으로 단축해서 사용 -->
            <div v-html="TextbyFilter(msg.content)" class="mychat-content"></div>
          </template>
        </MsgBox>
      </ul>
      <a v-if="msgPreviewBool" @click="clickMsgPreview">
      <div id="c-c-preview" v-bind:class="{active: $store.state.isLActive}">
            <div class="p-wrapper">
              <div>{{ previewObj.username }} : &nbsp;</div>
              <div class="p-nowrap" v-html="previewObj.content"></div>
            </div>
          </div>
      </a>
      <div class="c-i-wrapper">
        <!-- 더 뭔가 추가할 거 같아서 div로 감싸놓음 -->
        <div style="flex-grow:1;">
          <b-form-textarea
            autofocus
            v-if="!$store.state.isInviteMode && !$store.state.isSearchMode"
            id="textarea-no-resize"
            placeholder="Enter chat message"
            rows="3"
            no-resize
            v-model="message.content"
            @keydown.enter.exact="send"
            @keyup="byteCheck"
            @keydown.shift.50='inviteToggle'
          ></b-form-textarea>
          <div class="input-group" v-if="$store.state.isInviteMode">
            <div class="input-group-prepend">
              <span class="input-group-text">@</span>
            </div>
            <b-form-input
              @keydown.enter.exact="invite"
              @keydown.esc.exact="inviteToggle"
              list="user-info-list"
              style="height: 80px;"
              v-model="message.content"
              ref="testinput"
              autofocus
            ></b-form-input>
            <datalist id="user-info-list">
              <option v-for="user in $store.state.userList" :key="user.email">{{ user.name }} {{ user.email }}</option>
            </datalist>
          </div>
          <SearchInput 
            :msgArray="msgArray"
            :cursorPoint="cursorPoint" 
            :wrapperEl="wrapperEl"
            @getMessage="getMessage"></SearchInput>
          <div style="display: flex;">
            <span class="ml-auto" > {{ stringByteLength }} / 30000Byte</span>
          </div>
        </div>

        <b-button v-if="!$store.state.isInviteMode && !$store.state.isSearchMode" @click="send" style="height: 57px; width: 70px; margin-left:20px;" variant="primary">전송
        </b-button>
        <b-button v-if="$store.state.isInviteMode" @click="invite" style="height: 57px; width: 70px; margin-left:20px;" variant="primary">전송
        </b-button>
      </div>
    </div>
  </main>
</template>
<script>
  import MsgBox from './MsgBox'
  import InviteService from '../../service/inviteService'
  import CommonClass from '../../service/common'
  import SearchInput from './SearchInput'

  export default {
    props: ['currentChannel', 'stompClient', 'msgArray'],
    name: 'ContentWrapper',
    components: {
      MsgBox,SearchInput
    },
    data() {
      return {
        stringByteLength:0,
        previewObj:{
          content:'',
          username: ''
        },
        message: {
          channel_id: this.currentChannel.channel_id,
          content: '',
          sender: this.$store.state.currentUser.email,
          user: {}
        },
        // 채널 옮길 때마다 초기화 되어야한다.
        cursorPoint: {
          channel_id: 0,
          first: true,
          cursorId: 0,
          empty: false
        },
        firstLoad: true,
        oldScrollHeight: 0,
        wrapperEl: null,
        msgPreviewBool: false,
        getmsgBool:false,
      }
    },
    created() {
      this.getMessage()
    },
    mounted() {
      this.$nextTick(() => {
          this.wrapperEl = document.querySelector('.c-c-wrapper')
      })
    },
    updated() {
      this.scrollToEnd()
    },
    activated(){
      if(this.$store.state.oldComponent != 'main' && this.$store.state.selectComponent == 'main' ){
        this.scrollToEnd(true)
      }
    },
    deactivated(){
      console.log('deactiveed contentwrapper')
    },
    methods: {
      invite: async function () {
        const userName = this.message.content.split(' ')[0]
        const userEmail = this.message.content.split(' ')[1]
        console.log(this.currentChannel.id)
        await InviteService.invite(this.$store.state.currentUser.email, this.currentChannel.id, userEmail)
          .then(res => {
            console.log(res)
            this.message.content = userName + '님을 초대했습니다.'
            this.$eventBus.$emit('getUserList',true)
            this.send()
            this.inviteToggle()
          }).catch(error => {
            this.$alertModal('error',error.response.data.message)
            console.error(error.response)
            this.message.content = ''
          })
      },
      inviteToggle: function (e) {
        this.message.content = ''
        this.$store.state.isInviteMode = !this.$store.state.isInviteMode
      },
      send: async function (e) {
        console.log(e)
        e.preventDefault()
        this.message.channel_id = this.currentChannel.id
        this.message.user = this.$store.state.currentUser
        if(CommonClass.byteLimit(this.stringByteLength)){
          if (this.stompClient && this.stompClient.connected) {
            this.stompClient.send("/pub/chat/message", JSON.stringify(this.message), {})
            this.message.content = ''
            this.scrollToEnd(true)
        }
          else{
            this.message.content = CommonClass.replaceErrorMsg(this.message.content)
            this.message.content = '<p style="color:red;">메세지 전송에 실패하였습니다.</p>' + this.message.content
            let errormsg =  JSON.parse(JSON.stringify(this.message))
            this.msgArray.push(errormsg)
            this.message.content = ''
          }
        }
      },
      scrollEvt(e) {
        let element = e.target;
        //스크롤이 없을때에도 스크롤 위치가 최상단이기 때문에 스크롤이 있는지 없는지 판단해야한다.
        if (element.scrollTop <= 0 && element.scrollHeight != element.clientHeight) {
          if(this.cursorPoint.empty == false){
            this.getMessage(element)
          }
        }else if(this.isScrollAtEnd(element)){
            this.msgPreviewBool = false
        }
      },

      getMessage: function (wrapperEl) {
        this.cursorPoint.channel_id = this.currentChannel.id
        this.$http.post('http://localhost:9191/api/message/getmsg', JSON.stringify(this.cursorPoint), {
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(res => {
          console.log(res.data);
          if (res.data.length == 0) {
            this.cursorPoint.empty = true
          } else {
            this.cursorPoint.first = false
            this.cursorPoint.cursorId = res.data[res.data.length - 1].id
          }
          console.log(res.data)
          for(let i =0; i < res.data.length; i++){
            res.data[i].content = CommonClass.replacemsg(res.data[i].content)
          } 
          this.msgArray = res.data.reverse().concat(this.msgArray)
          if (wrapperEl != null) {
            this.$nextTick(() => {
              wrapperEl.scrollTop = wrapperEl.scrollHeight - this.oldScrollHeight
              this.oldScrollHeight = wrapperEl.scrollHeight
            })
          }
          this.getmsgBool = true
          this.$emit('msgArrayUpdate', this.msgArray)
        })
      },
      scrollToEnd(bool) {
        this.$nextTick(() => {
          if (this.firstLoad) {
            this.oldScrollHeight = this.wrapperEl.scrollHeight
          }
          if (this.isScrollAtEnd(this.wrapperEl)|| this.firstLoad || bool ||
              ((this.oldScrollHeight == this.wrapperEl.clientHeight)&& (this.wrapperEl.scrollHeight > this.wrapperEl.clientHeight))) {

            this.wrapperEl.scrollTop = this.wrapperEl.scrollHeight
            this.firstLoad = false
            this.oldScrollHeight = this.wrapperEl.scrollHeight
          }
        })
      },
      isScrollAtEnd(wrapperEl){
         if(Math.floor(wrapperEl.scrollTop + wrapperEl.clientHeight) == this.oldScrollHeight){
           return true
         } else{
           return false
         }
      },
      clickMsgPreview(){
        this.scrollToEnd(true)
        this.msgPreviewBool = false
      },
      initData(){
        this.cursorPoint.channel_id = this.currentChannel.id
        this.cursorPoint.first = true
        this.cursorPoint.cursorId = 0
        this.cursorPoint.empty = false
        this.msgArray = []
        this.firstLoad = true,
        this.scrollHeight = 0,
        this.$emit('msgArrayUpdate',this.msgArray)
      },
      byteCheck(e){
        // v-model을 썼음에도 e.target.value를 사용하는 이유는 한글은 바로 바인딩이 안되기때문에 수동적으로 값들을 message.content에 넣기 위함이다.
        this.message.content = e.target.value
        this.stringByteLength = CommonClass.byteCount(this.message.content)
        if((47< e.keyCode && e.keyCode < 112 && e.ctrlKey == false) || (e.keyCode == 13 && e.shiftKey == true) || e.keyCode == 32 
        || e.keyCode == 229 ){
          CommonClass.byteLimit(this.stringByteLength)
        }
      },
      TextbyFilter (content) {
        return this.$options.filters.highlight(content, this.$store.state.searchText);
      }
    },
    watch: {
      currentChannel: function (newv, oldv) {
        this.initData()
        this.getMessage()
        this.scrollToEnd()
      },
      msgArray:function(){
        // 스크롤을 최상단으로 올려 메시지를 가져올 때 실행되는 것을 막기 위한 if문
        if(this.getmsgBool){
          this.getmsgBool = false
        }else{
          if(!this.isScrollAtEnd(this.wrapperEl) && this.msgArray.length > 0){
            let copymsg = JSON.parse(JSON.stringify(this.msgArray[this.msgArray.length-1]))
            this.previewObj.content = CommonClass.replacemsgForPreview(copymsg.content)
            this.previewObj.username = this.msgArray[this.msgArray.length-1].user.name
            this.msgPreviewBool = true
          }
        }
      }
    },
    filters: {
    highlight: function(stringToSearch, searchTerm) {
        if (searchTerm === "") return stringToSearch;
        var iQuery = new RegExp(searchTerm, "ig");
        return stringToSearch
          .toString()
          .replace(iQuery, function(matchedText, a, b) {
            return "<span class='highlight'>" + matchedText + "</span>";
          });
      }
    }

  }
</script>
