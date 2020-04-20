<template>
  <div class="wrapper">

    <template v-if="connectionCheck">
      <!-- Sidebar  -->
      <LSidebar
        :msgCountObj="msgCountObj"
        @channelUpdate="channelUpdate"
        @sendTitle="sendTitle"></LSidebar>
      <!-- Page Content  -->
      <div id="m-wrapper" v-bind:class="{active: $store.state.isLActive}">
        <MainHeader></MainHeader>
        <!-- 채널 리스트가 없을 경우 알림 글로 대체 (디자인은 추후에....)-->
        <NoChannel v-if="$store.state.userChannelList[0]==null && $store.state.selectComponent=='main'"/>
        <!-- CjannelHeader -->
        <div v-else>
<!--          추후에 깔금한 방식으로 변경-->
          <ChannelHeader v-if="$store.state.selectComponent!='user' && $store.state.selectComponent!='edit'"></ChannelHeader>
          <keep-alive>
            <component :is="whichComponent"
                       :msgArray="msgArray"
                       @msgArrayUpdate="msgArrayUpdate"
            ></component>
          </keep-alive>
        </div>
      </div>
      <RSidebar v-if="$store.state.currentChannel!=null"></RSidebar>
    </template>
    <Loading v-else/>
  </div>
</template>
<script>
  import LSidebar from '../views/main/LSidebar'
  import RSidebar from '../views/main/RSidebar'
  import MainHeader from '../views/main/MainHeader'
  import ContentWrapper from '../views/main/ContentWrapper'
  import AboutChannel from '../service/aboutchannel'
  import NotificationClass from '../service/notification'
  import EventListener from '../service/eventlistener'
  import UserInfo from "../views/user/UserInfo"
  import EditProfile from "../views/user/EditProfile"
  import ChannelHeader from "../views/main/ChannelHeader"
  import CommonClass from '../service/common'
  import NoChannel from '../views/main/NoChannel'
  import Loading from '../views/main/Loading'
  import Stomp from "webstomp-client";
  import SockJS from "sockjs-client";
  import TodoList from '../views/todolist/TodoList'
  import Calendar from "../views/calendar/Calendar";

  export default {
    name: 'Main',
    components: {
      'MainHeader': MainHeader,
      'LSidebar': LSidebar,
      'RSidebar': RSidebar,
      'ChannelHeader': ChannelHeader,
      'ContentWrapper': ContentWrapper,
      'UserInfo': UserInfo,
      'EditProfile': EditProfile,
      'NoChannel': NoChannel,
      'Loading': Loading,
      'TodoList' : TodoList,
      'Calendar' : Calendar
    },
    data() {
      return {
        channelTitle: '',
        channelList: [],
        isRActive: false,
        msgArray: [],
        msgCountObj: {},
        modalObj: {modalTitle: '', currentChannel: null},
      }
    },
    computed: {
      whichComponent() {
        console.log(this.$store.state.oldComponent)
        AboutChannel.updateLastAccessStatus(this.$store.state.oldComponent, this.$store.state.selectComponent)
        switch (this.$store.state.selectComponent) {
          case 'main':
            return 'ContentWrapper'
          case 'user':
            return 'UserInfo'
          case 'edit':
            return 'EditProfile'
          case 'todoList':
            return 'TodoList'
          case 'calendar':
            return 'Calendar'
          default:
            return 'ContentWrapper'
        }
      },
      connectionCheck() {
        if (this.$store.state.stompClient != null) {
          return this.$store.state.stompClient.connected
        }
      }
    },
    deactivated() {
      console.log('deactiveed')
    },
    async created() {
      // 적용은 mounted 이후에 가능한 것으로 보임...
      await this.$store.dispatch('userListUpdate')
      await this.$store.dispatch('channelList') // 설정되는 값은 userChannelList
      await this.$store.commit('setCurrentChannel', this.$store.state.userChannelList[0])
      const currentChannel = this.$store.state.currentChannel
      console.log(currentChannel)
      if (currentChannel != null) {
        currentChannel.count = 0
        await AboutChannel.initCurrentChannel(currentChannel.id)
      }
      this.connect()
      EventListener.beforeunloadEvt()
      EventListener.focusEvt(this)
      EventListener.blurEvt()
      NotificationClass.requestPermission()
    },
    updated() {
    },
    methods: {
      sendTitle(channel) {
        if (this.$store.state.oldComponent == 'main') {
          let oldChannel = this.$store.state.currentChannel
          AboutChannel.updateLastAccessDate(channel.id, oldChannel.id)
          console.log(oldChannel.id)
        }
        this.$store.commit('setCurrentChannel', channel)
        this.$store.state.currentChannel.count = 0
        this.$store.state.isSearchMode = false
      },
      connect() {
        // 새로고침 했을때 Main의 로직이 실행되지 않는 환경에서는 문제가 생길 수 있음
        this.$store.state.stompClient = Stomp.over(new SockJS('/endpoint/'))
        this.$store.state.stompClient.connect({}, () => {

          this.$store.state.userChannelList.forEach(channel => {
            this.$store.state.stompClient.subscribe("/sub/chat/room/" + channel.id, (e) => {
              console.log(e.body);
              let data = JSON.parse(e.body)
              if (data.message == 'updateChannel') {
                this.$store.state.syncSignal.syncChannelUser = !this.$store.state.syncSignal.syncChannelUser;
                return;
              } else {
                this.channelSubscribeCallBack(e);
                return;
              }
            })
          })
          this.$store.state.stompClient.subscribe("/sub/sync/info", (res) => {
            if (res.body == 'true') {
              this.storeUpdate()
            } else if (res.body == 'userList') {
              this.$store.dispatch('userListUpdate')
            }
          })
          this.$store.state.stompClient.subscribe("/sub/" + this.$store.state.currentUser.email, (e) => {
            //메시지 전송 실패시
            this.channelSubscribeCallBack(e, true)
          })
        }, function () {
          window.location.href = "/"
        })
      },
      channelUpdate(newChannelList) {
        let num = newChannelList.length - this.$store.state.userChannelList.length
        for (let i = num; i > 0; i--) {
          let idx = newChannelList.length - i
          this.msgCountObj[newChannelList[idx].id] = 0
          this.$store.state.stompClient.subscribe("/sub/chat/room/" + newChannelList[idx].id, (e) => {
            console.log(e.body);
            let data = JSON.parse(e.body)
            if (data.message == 'updateChannel') {
              this.$store.state.syncSignal.syncChannelUser = !this.$store.state.syncSignal.syncChannelUser;
              return;
            } else {
              this.channelSubscribeCallBack(e);
              return;
            }
          })
        }
        this.$store.commit('setChannelList', newChannelList)


      },
      msgArrayUpdate(newmsgArray) {
        this.msgArray = newmsgArray
      },
      channelSubscribeCallBack(e, fail) {
        let data = JSON.parse(e.body)
        console.log(data)
        console.log(this.$store.state.isfocus)
        NotificationClass.sendNotification(this.$store.state.isfocus, data)
        if (data.channel_id == this.$store.state.currentChannel.id && this.$store.state.selectComponent == 'main') {
          data.content = CommonClass.replacemsg(data.content)
          if (fail) {
            data.content = '<p style="color:red;">메세지 전송에 실패하였습니다.</p>' + data.content
          }
          this.msgArray.push(data)
          if (!this.$store.state.isfocus) {
            this.msgCounting(data)
          }
        } else {
          this.msgCounting(data)
        }
      },
      msgCounting(data) {
        // commit 을 안해도 객체 내부의 내용은 변경이 되는지 확인 필요 확인 후 해당 주석 제거
        for (let i = 0; i < this.$store.state.userChannelList.length; i++) {
          if (data.channel_id == this.$store.state.userChannelList[i].id) {
            this.$store.state.userChannelList[i].count += 1
            break
          }
        }
      },

    }

  }

</script>
