<template>
  <div class="wrapper">
    <!-- Sidebar  -->
    <LSidebar
      :modalObj="modalObj"
      :channelList="channelList"
      @channelUpdate="channelUpdate"
      @sendTitle="sendTitle"></LSidebar>
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
          @msgArrayUpdate="msgArrayUpdate"
        ></router-view>
      </div>
      <!-- 채널 리스트가 없을 경우 알림 글로 대체 (디자인은 추후에....)-->
      <div v-else>
        <p>채팅방을 만들거나 가입해주세요</p>
      </div>
    </div>
    <RSidebar v-if="channelList[0]!=null"
    :modalObj="modalObj"
    @passData="passData"></RSidebar>
  </div>
</template>
<script>
  import LSidebar from '../views/main/LSidebar'
  import RSidebar from '../views/main/RSidebar'
  import MainHeader from '../views/main/MainHeader'
  import ContentWrapper from '../views/main/ContentWrapper'
  import AboutChannel from '../service/aboutchannel'
  import CommonClass from '../service/common'
  import SockJS from 'sockjs-client'
  import Stomp from 'webstomp-client'
  import axios from 'axios'


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
    async created() {
      await this.$store.dispatch('userListUpdate')
      await this.$store.dispatch('initCurrentUser')
      console.log("created...")

      console.log(this.$store.state.currentUser.name)
      // 상의 후 수정해야할 듯
      // main이 created 될 때마다 비동기통신을 해야하기 때문에 생각해봐야할 문제일듯
      axios.get('http://localhost:9191/api/user/getsession').then(res => {
        if (res.data.phone == null || res.data.phone == '') {
          this.$router.replace('/signup')
        } else {
          AboutChannel.getChannelList().then(
            res => {
              this.channelList = res.data
              for (let i in this.channelList) {
                this.msgCountObj[this.channelList[i].id] = 0
              }
              console.log(this.channelList)
              console.log(this.msgCountObj)
              //사용자가 채널을 선택하지 않았다면.
              if (this.modalObj.currentChannel == null && this.channelList[0] != null) {
                this.modalObj.currentChannel = this.channelList[0]
                this.channelTitle = this.modalObj.currentChannel.name
                console.log(this.currentChannel)
              }
              // 현재 채널에 저장되어있는 메시지 3가져오기
              // AboutChannel.getMsgList(this.currentChannel).then(
              //   res=> {

              // })

              this.connect()
            }
          )
        }
      })
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
        this.stompClient.connect({}, () => {
          for (let i in this.channelList) {
            this.stompClient.subscribe("/sub/chat/room/" + this.channelList[i].id, (e) => {
              let data = JSON.parse(e.body)
              if (data.channel_id == this.modalObj.currentChannel.id) {
                data.content = CommonClass.replacemsg(data.content)
                this.msgArray.push(data)
              } else {
                this.msgCountObj[data.channel_id] += 1
              }
            })
          }
          this.stompClient.subscribe("/sub/" + this.$store.state.currentUser.email, (e) => {
              let data = JSON.parse(e.body)
              if (data.channel_id == this.modalObj.currentChannel.id) {
                data.content = CommonClass.replacemsg(data.content)
                data.content = '<p style="color:red;">메세지 전송에 실패하였습니다.</p>' + data.content
                this.msgArray.push(data)
              }
            })

        })
      },
      channelUpdate(newChannelList) {
          let num = newChannelList.length - this.channelList.length
        for(let i=num; i>0; i-- ){
          let idx = newChannelList.length-i
          this.msgCountObj[newChannelList[idx].id] = 0
            this.stompClient.subscribe("/sub/chat/room/" + newChannelList[idx].id,(e)=>{
              let data = JSON.parse(e.body);
              if(data.channel_id == this.modalObj.currentChannel.id){
                data.content = this.replacemsg(data.content)
                this.msgArray.push(data)
              }else{
                this.msgCountObj[data.channel_id] += 1
              }
            })
        }
        this.channelList = newChannelList
        if(this.modalObj.currentChannel == null){
            this.modalObj.currentChannel = this.channelList[0]
            this.channelTitle = this.modalObj.currentChannel.name
        }
      },
      msgArrayUpdate(newmsgArray) {
        this.msgArray = newmsgArray
      }
    }

  }
</script>
