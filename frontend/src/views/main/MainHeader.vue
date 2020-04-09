<template>
<header>
        <b-navbar toggleable="lg" type="light" variant="white">
            <i v-if="$store.state.isLActive" class="im im-angle-right-circle btn btn-info"@click="LSidebarToggle" ></i>
            <i v-else class="im im-angle-left-circle btn btn-info" @click="LSidebarToggle" ></i>
          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item-dropdown no-caret right toggle-class="nonoutline" class="verti-align">
              <template v-slot:button-content>
                <div style="position: relative;">
                  <b-badge style="position: absolute;bottom: -2px;right: -5px;font-size: 10px;" variant="danger">1</b-badge>
                <i class="im im-bell"></i>
                </div>
              </template>
             
            </b-nav-item-dropdown>
            <div class="verti-align useridsty">{{ $store.state.currentUser.name }}</div>
            <b-nav-item-dropdown no-caret right toggle-class="nonoutline">
              <!-- Using 'button-content' slot -->
              <template v-slot:button-content style="padding:0px;">
<!--                이미지 가지고 오는 것 느림-->
                <img v-if="$store.state.currentUser.picture" class="icon-round" :src="$store.state.currentUser.picture" width="40" height="40">
                <img v-else class="icon-round" src="../../assets/images/default-user-picture.png" width="40" height="40">
              </template>
              <b-dropdown-item @click="callComponent">Profile</b-dropdown-item>
              <b-dropdown-item @click="SignOut">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-navbar>
      </header>
</template>

<script>
  import {mapGetters} from "vuex";

  export default {
    name: 'MainHeader',
    data() {
      return {
        alarmList: [],
      }
    },

    watch: {
      StompClient: function (newVal, oldVal) {
        if(newVal.connected){
          newVal.subscribe("/sub/alarm/" + this.$store.state.currentUser.email, (e) => {
            console.log("get callback")
            let invite = JSON.parse(e.body)
            console.log(invite.sender)
            this.alarmList.unshift(invite)
          })
        }
      }
    },
    computed: {
      ...mapGetters({
        StompClient: 'getStompClient'
      }),
      getAlarmList: function () {
        while (this.alarmList.length > 5) {
          this.alarmList.pop()
        }
        return this.alarmList
      },
    },
    created() {
      this.$http.get('/api/invite/list')
        .then(res => {
          this.alarmList = res.data.reverse()
          console.log(this.alarmList)
        })
        .catch(error => {
          console.log(error)
        })
    },
    mounted() {

    },
    methods: {
      inviteAccept: function (alarm, index) {
        console.log(alarm)
        this.$http.post('/api/invite/accept', alarm)
          .then(res => {
            console.log(res)
            // 현재 채널을 변경하는 로직을 구현해야할듯
            this.alarmList.splice(index, 1);
            this.$store.state.stompClient.send('/pub/chat/room/'+alarm.channel_id,
              JSON.stringify({'message':'updateChannel', 'error':"null"}))
            this.$store.dispatch('channelList')
          })
          .catch(error => {
            console.log(error)
          })
      }
      ,
      // 거절 과 수락은 하나의 api로 해서 신호를 하나 줘서 분기 시키는게 더 좋을 듯
      inviteRefuse: function (alarm, index) {
        // 초대가 거절됐다는 메시지를 채널에 보내는 로직을 구현해야함
        this.$http.post('/api/invite/refuse', alarm)
          .then(res => {
            this.alarmList.splice(index, 1);
          })
          .catch(error => {
            console.log(error)
          })
      }
      ,
      callComponent: function () {
        this.$store.commit('getSelectComponent', 'user')
      }
      ,
      LSidebarToggle: function () {
        this.$store.state.isLActive = !this.$store.state.isLActive
      }
      ,
      RSidebarOpen: function () {
        this.$store.state.isRActive = true
      }
      ,
      SignOut() {
        this.$store.commit('setIsLogout', true)
        window.location.href = "/logout"
      }
      ,
      getUserNameByEmail: function (email) {
        return this.$store.state.userList.find(element => {
          return element.email == email
        }).name
      }
    }
    ,
  }
</script>
