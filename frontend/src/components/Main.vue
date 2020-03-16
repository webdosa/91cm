<template>
  <div class="wrapper">
    <!-- Sidebar  -->
    <LSidebar :modalObj="modalObj" :channelList="channelList" @sendTitle="sendTitle"></LSidebar>
    <!-- Page Content  -->
    <div id="m-wrapper" v-bind:class="{active: $store.state.isLActive}">
      <MainHeader></MainHeader>
      <!-- 채널 리스트가 있는지 없는지 확인  -->
      <div v-if="channelList[0]!=null">
        <router-view name="ChannelHeader" :channelTitle="modalObj.currentChannel.name"></router-view>
        <router-view
          :currentChannel="modalObj.currentChannel"
          :stompClient="stompClient"
          :msgArray="msgArray"
          @msgArrayUnshift="msgArrayUnshift"
        ></router-view>
      </div>
      <!-- 채널 리스트가 없을 경우 알림 글로 대체 (디자인은 추후에....)-->
      <div v-else>
        <p>채팅방을 만들거나 가입해주세요</p>
      </div>
    </div>
    <RSidebar :modalObj="modalObj" @passData="passData"></RSidebar>
  </div>
</template>
<script>
  import LSidebar from '../views/main/LSidebar'
  import RSidebar from '../views/main/RSidebar'
  import MainHeader from '../views/main/MainHeader'
  import ContentWrapper from '../views/main/ContentWrapper'
  import AboutChannel from '../service/aboutchannel'
  import SockJS from 'sockjs-client'
  import Stomp from 'webstomp-client'

  export default {
    name: 'Main',
    components: {MainHeader, LSidebar, RSidebar, ContentWrapper},
    data() {
      return {
        channelTitle: '',
        stompClient: null,
        channelList: [],
        isRActive: false,
        msgArray: [],
        msgCountObj: {},
        modalObj: {modalTitle: '', currentChannel: null}
      }
    },
    created() {
      // 적용은 mounted 이후에 가능한 것으로 보임...
      this.$store.dispatch('userListUpdate')
      this.$store.dispatch('initCurrentUser')
      AboutChannel.getChannelList().then(
        res => {
          this.channelList = res.data
          //channel이 하나도 없으면 아래의 작업이 실행 되지 않도록
          if (this.channelList[0] == null) {
            console.log("channelList is null")
            this.channelList[0] = null // 문제점을 더욱 자세히 확인후에 변경
            return
          }
          for (let channel in this.channelList) {
            this.msgCountObj[this.channelList[channel.id]] = 0
          }
          console.log(this.channelList)
          console.log(this.msgCountObj)
          //사용자가 채널을 선택하지 않았다면.

          if (this.modalObj.currentChannel == null) {
            console.log("currentChannel init")
            this.modalObj.currentChannel = this.channelList[0]
            this.channelTitle = this.modalObj.currentChannel.name
          }
          // 현재 채널에 저장되어있는 메시지 가져오기
          // AboutChannel.getMsgList(this.currentChannel).then(
          //   res=> {

          // })
          this.connect()
        }
      )

    },
    methods: {
      sendTitle(channel) {
        this.channelTitle = channel.name
        this.modalObj.currentChannel = channel
      },
      passData(modalObj) {
        this.modalObj.modalTitle = modalObj.modalTitle
      },
      connect() {
        this.stompClient = Stomp.over(new SockJS('http://localhost:9191/endpoint/'))
        console.log('asd1')
        this.stompClient.connect({}, () => {
          console.log('asd2')
          for (let i in this.channelList) {
            this.stompClient.subscribe("/sub/chat/room/" + this.channelList[i].id, (e) => {
              let data = JSON.parse(e.body);
              console.log(data)
              if (data.message.channel_id == this.modalObj.currentChannel.id) {
                data.message.content = this.replacemsg(data.message.content)
                console.log(data);
                this.msgArray.push(data)
              } else {
                this.msgCountObj[data.message.channel_id] += 1
              }
            })
          }
        })
      },
      replacemsg(originContent) {
        let array = originContent.split("\n")
        let content = ''
        for (let i in array) {
          content += '<p>' + array[i] + '</p>'
        }
        return content.replace(/ /gi, '&nbsp;')
      },
      msgArrayUnshift() {
        //console.log('함수실행')
      }
    }
  }
</script>
