`
<template>
  <main class="mainwrapper">
    <div class="h-inherit" v-cloak @drop.prevent="addFile" @dragover.prevent>
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

            <div v-if="msg.files == null || msg.content" v-html="msg.content" class="mychat-content"></div>
            <b-container fluid v-else-if="msg.files.length > 0" class="p-4 bg-white">
              <b-row>
                <b-col v-for="file in msg.files">
                  <a @click="fileDownload(file)">
                    <b-img thumbnail rounded fluid :src="selectImage(file)" alt="이미지를 찾을 수 없습니다." style="max-width: 200px"></b-img>
                    <p><b>{{file.original_name}}</b></p>
                    <p>file size : {{(file.file_size / 1024).toLocaleString(undefined,{minimumFractionDigits:2})}} kb</p>
                  </a>
                </b-col>
              </b-row>
            </b-container>
            <!--            <b-card-group v-else-if="msg.files[0]" v-for="file in msg.files">-->
            <!--              <b-card :img-src="file.path" img-alt="card image" img-left>-->
            <!--              </b-card>-->
            <!--            </b-card-group>-->
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
            v-if="!show"
            id="textarea-no-resize"
            placeholder="Enter chat message"
            rows="3"
            no-resize
            v-model="message.content"
            @keydown.enter.prevent
            @keydown.enter.exact="send"
            @keydown.shift.50='inviteToggle'
            @keyup="byteCheck"
          ></b-form-textarea>
          <div class="input-group" v-if="show">
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
          <div style="display: flex;">
            <span class="ml-auto"> {{ stringByteLength }} / 30000Byte</span>
          </div>
        </div>

        <b-button v-if="!show" @click="send" style="height: 57px; width: 70px; margin-left:20px;" variant="primary">전송
        </b-button>
        <b-button v-else @click="invite" style="height: 57px; width: 70px; margin-left:20px;" variant="primary">전송
        </b-button>
      </div>
    </div>
  </main>
</template>
<script>
  import MsgBox from './MsgBox'
  import InviteService from '../../service/inviteService'
  import CommonClass from '../../service/common'

  export default {
    props: ['currentChannel', 'stompClient', 'msgArray'],
    name: 'ContentWrapper',
    components: {
      MsgBox
    },
    data() {
      return {
        tempImg: '',
        stringByteLength: 0,
        previewObj: {
          content: '',
          username: ''
        },
        show: false,
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
        getmsgBool: false
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
      console.log(this.msgArray)
    },
    methods: {
      selectImage: function (file) {
        return CommonClass.checkFileType(file)
      },
      fileDownload: function(file){
        this.$http.get(file.path,{
          responseType: 'blob'
        })
          .then(res =>{
            const url = window.URL.createObjectURL(new Blob([res.data]))
            const link = document.createElement('a')
            link.href = url;
            link.setAttribute('download', file.original_name)
            document.body.appendChild(link)
            link.click()
            link.remove()
            window.URL.revokeObjectURL(url)
          })
      },
      addFile: function (e) {
        const maxUploadSize = 100 * 1024 * 1024;
        let droppedFiles = e.dataTransfer.files;
        let fileSize = 0;
        if (!droppedFiles) return;
        let formData = new FormData();
        // formData에 multi로 파일을 담는 방법에 대해 추후 확인
        let files = [];
        ([...droppedFiles]).forEach(f => {
          files.push(f)
        });
        files.forEach(file => {
          formData.append("files", file)
          fileSize += file.size
        });
        if(fileSize >= maxUploadSize){
          this.$alertModal('alert','한번에 보낼 수 있는 파일 용량은 100MB 입니다.')
          return;
        }
        /////////////////////////////////////
        formData.append('channel_id', this.currentChannel.id)
        formData.append('sender', this.message.sender)
        this.$http.post('/api/file/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => {
          console.log(res)
        }).catch(error => {
          console.log(error)
        })
      },

      invite: async function () {
        const userName = this.message.content.split(' ')[0]
        const userEmail = this.message.content.split(' ')[1]
        console.log(this.currentChannel.id)
        await InviteService.invite(this.$store.state.currentUser.email, this.currentChannel.id, userEmail)
          .then(res => {
            console.log(res)
            this.message.content = userName + '님을 초대했습니다.'
            this.$eventBus.$emit('getUserList', true)
            this.send()
            this.inviteToggle()
          }).catch(error => {
            this.$alertModal('error', error.response.data.message)
            console.error(error.response)
            this.message.content = ''
          })
      },
      inviteToggle: function (e) {
        this.message.content = ''
        this.show = !this.show
      },
      send: async function () {
        this.message.channel_id = this.currentChannel.id
        this.message.user = this.$store.state.currentUser
        if (CommonClass.byteLimit(this.stringByteLength)) {
          if (this.$store.state.stompClient && this.$store.state.stompClient.connected) {
            this.$store.state.stompClient.send("/pub/chat/message", JSON.stringify(this.message), {})
            this.message.content = ''
            this.scrollToEnd(true)
          } else {
            this.message.content = CommonClass.replacemsg(this.message.content)
            this.message.content = '<p style="color:red;">메세지 전송에 실패하였습니다.</p>' + this.message.content
            let errormsg = JSON.parse(JSON.stringify(this.message))
            this.msgArray.push(errormsg)
            this.message.content = ''
          }
        }
      },
      scrollEvt(e) {
        let element = e.target;
        //스크롤이 없을때에도 스크롤 위치가 최상단이기 때문에 스크롤이 있는지 없는지 판단해야한다.
        if (element.scrollTop <= 0 && element.scrollHeight != element.clientHeight) {
          if (this.cursorPoint.empty == false) {
            this.getMessage(element)
          }
        } else if (this.isScrollAtEnd(element)) {
          this.msgPreviewBool = false
        }
      },

      getMessage(wrapperEl) {
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
          console.log("oldScrollHeight : " + this.oldScrollHeight)
          console.log("this.wrapperEl.clientHeight : " + this.wrapperEl.clientHeight)
          console.log("this.wrapperEl.scrollHeight : " + this.wrapperEl.scrollHeight)
          if (this.isScrollAtEnd(this.wrapperEl) || this.firstLoad || bool ||
            ((this.oldScrollHeight == this.wrapperEl.clientHeight) && (this.wrapperEl.scrollHeight > this.wrapperEl.clientHeight))) {

            this.wrapperEl.scrollTop = this.wrapperEl.scrollHeight
            this.firstLoad = false
            this.oldScrollHeight = this.wrapperEl.scrollHeight
          }
        })
      },
      isScrollAtEnd(wrapperEl) {
        if (Math.floor(wrapperEl.scrollTop + wrapperEl.clientHeight) == this.oldScrollHeight) {
          return true
        } else {
          return false
        }
      },
      clickMsgPreview() {
        this.scrollToEnd(true)
        this.msgPreviewBool = false
      },
      initData() {
        this.cursorPoint.channel_id = this.currentChannel.id
        this.cursorPoint.first = true
        this.cursorPoint.cursorId = 0
        this.cursorPoint.empty = false
        this.msgArray = []
        this.firstLoad = true,
          this.scrollHeight = 0,
          this.$emit('msgArrayUpdate', this.msgArray)
      },
      byteCheck(e) {
        this.stringByteLength = CommonClass.byteCount(this.message.content)
        if ((47 < e.keyCode && e.keyCode < 112) || e.keyCode == 13 || e.keyCode == 32) {
          CommonClass.byteLimit(this.stringByteLength)
        }
      }

    },
    watch: {
      currentChannel: function (newv, oldv) {
        this.initData()
        this.getMessage()
        this.scrollToEnd()
      },
      msgArray: function () {
        // 스크롤을 최상단으로 올려 메시지를 가져올 때 실행되는 것을 막기 위한 if문
        if (this.getmsgBool) {
          this.getmsgBool = false
        } else {
          if (!this.isScrollAtEnd(this.wrapperEl)) {
            let copymsg = JSON.parse(JSON.stringify(this.msgArray[this.msgArray.length - 1]))
            this.previewObj.content = copymsg.content==null? "첨부파일" : CommonClass.replacemsgForPreview(copymsg.content)
            this.previewObj.username = this.msgArray[this.msgArray.length - 1].user.name
            this.msgPreviewBool = true
          }
        }
      }
    }

  }
</script>
