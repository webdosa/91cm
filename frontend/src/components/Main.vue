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
      <!-- CjannelHeader -->
      <div v-if="channelList[0]!=null">
        <ChannelHeader v-if="$store.state.selectComponent=='main'"
                       :channelTitle="modalObj.currentChannel.name"></ChannelHeader>
        <keep-alive>
          <component :is="whichComponent" :currentChannel="modalObj.currentChannel"
                     :stompClient="$store.state.stompClient"
                     :msgArray="msgArray"
                     @msgArrayUpdate="msgArrayUpdate"
          ></component>
        </keep-alive>
        <!-- ContentWrapper
        <router-view
          :currentChannel="modalObj.currentChannel"
          :stompClient="stompClient"
          :msgArray="msgArray"
          @msgArrayUpdate="msgArrayUpdate"
        ></router-view>
        -->
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
  import SockJS from 'sockjs-client'
  import Stomp from 'webstomp-client'
  import UserInfo from "../views/user/UserInfo";
  import EditProfile from "../views/user/EditProfile";
  import ChannelHeader from "../views/main/ChannelHeader";

  export default {
    name: 'Main',
    components: {
      'MainHeader': MainHeader,
      'LSidebar': LSidebar,
      'RSidebar': RSidebar,
      'ChannelHeader': ChannelHeader,
      'ContentWrapper': ContentWrapper,
      'UserInfo': UserInfo,
      'EditProfile': EditProfile
    },
    data() {
      return {
        channelTitle: '',
        channelList: [],
        isRActive: false,
        msgArray: [],
        msgCountObj: {},
        modalObj: {modalTitle: '', currentChannel: null}
      }
    },
    computed: {
      whichComponent() {
        switch (this.$store.state.selectComponent) {
          case 'main':
            return 'ContentWrapper'
          case 'user':
            return 'UserInfo'
          case 'edit':
            return 'EditProfile'
          default:
            return 'ContentWrapper'
        }
      },
    },
    created() {
      // 적용은 mounted 이후에 가능한 것으로 보임...
      this.$store.dispatch('userListUpdate')
      AboutChannel.getChannelList().then(
        res => {
          this.channelList = res.data
          for (let i in this.channelList) {
            this.msgCountObj[this.channelList[i].id] = 0
          }
          // 처음 로그인하자마자 제일 처음에 만든 채널로 현재 채널객체를 초기화한다.
          if (this.modalObj.currentChannel == null && this.channelList[0] != null) {
            this.modalObj.currentChannel = this.channelList[0]
            this.channelTitle = this.modalObj.currentChannel.name
          }
          this.connect()
        }
      )
    },
    updated() {
    },
    methods: {
      storeUpdate: function () {
        this.$store.dispatch('userListUpdate')
        this.$store.dispatch('initCurrentUser')
      },
      sendTitle(channel) {
        this.channelTitle = channel.name
        this.modalObj.currentChannel = channel
      },
      passData(modalObj) {
        this.modalObj.modalTitle = modalObj.modalTitle
      },
      connect() {
        this.$store.state.stompClient.connect({}, () => {
          for (let i in this.channelList) {
            this.$store.state.stompClient.subscribe("/sub/chat/room/" + this.channelList[i].id, (e) => {
              this.channelSubscribeCallBack(e)
            })
          }
          this.$store.state.stompClient.subscribe("/sub/sync/info", (res) => {
            if (res.body == 'true') {
              this.storeUpdate()
            }
          })
          this.$store.state.stompClient.subscribe("/sub/" + this.$store.state.currentUser.email, (e) => {
            //메시지 전송 실패시
            this.channelSubscribeCallBack(e, true)
          })
        })
      },
      channelUpdate(newChannelList) {
        let num = newChannelList.length - this.channelList.length
        for (let i = num; i > 0; i--) {
          let idx = newChannelList.length - i
          this.msgCountObj[newChannelList[idx].id] = 0
          this.$store.state.stompClient.subscribe("/sub/chat/room/" + newChannelList[idx].id, (e) => {
            this.channelSubscribeCallBack(e)
          })
        }
        this.channelList = newChannelList

        // 코드 확인 필요
        if (this.modalObj.currentChannel == null) {
          this.modalObj.currentChannel = this.channelList[0]
          this.channelTitle = this.modalObj.currentChannel.name
        }
      },
      msgArrayUpdate(newmsgArray) {
        this.msgArray = newmsgArray
      },
      channelSubscribeCallBack(e, fail) {
        let data = JSON.parse(e.body)
        if (data.channel_id == this.modalObj.currentChannel.id) {
          if (fail) {
            data.content = '<p style="color:red;">메세지 전송에 실패하였습니다.</p>' + data.content
          }
          this.msgArray.push(data)
        } else {
          this.msgCountObj[data.channel_id] += 1
        }
      }
    }

  }
</script>
