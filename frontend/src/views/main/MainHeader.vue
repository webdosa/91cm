<template>
  <header>
    <b-navbar toggleable="lg" type="light" variant="white">
      <i v-if="$store.state.isLActive" class="im im-angle-right-circle btn btn-info" @click="LSidebarToggle"></i>
      <i v-else class="im im-angle-left-circle btn btn-info" @click="LSidebarToggle"></i>
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-dropdown no-caret right toggle-class="nonoutline" class="verti-align" variant="nonoutline" right>
          <template v-slot:button-content>
            <i class="im im-bell"></i>
          </template>
          <b-dropdown-text v-for="(alarm,index) in getAlarmList" style="width: 25vw;" class="border">
            <div>
              <div class="row float-right">
                <b-button class="float-right" id="esc" size="sm" variant="nonoutline"
                          @click="getAlarmList.splice(index,1)"><i
                  class="im im-x-mark"></i></b-button>
              </div>
              <div class="row">
                <p>{{getUserNameByEmail(alarm.sender)}} 님이 채널에 초대했습니다. 수락하시겠습니까?</p>
              </div>
              <div class="row float-right">
                <b-button size="sm" variant="nonoutline" @click="inviteAccept(alarm,index)"><i
                  class="im im-check-mark-circle"
                  style="color: #42b983;"></i>
                </b-button>
                <b-button size="sm" variant="nonoutline" @click="inviteRefuse(alarm,index)"><i
                  class="im im-x-mark-circle"
                  style="color: red;"></i>
                </b-button>
              </div>
            </div>
          </b-dropdown-text>
        </b-dropdown>
        <div class="verti-align useridsty">{{ $store.state.currentUser.name }}</div>
        <b-nav-item-dropdown no-caret right toggle-class="nonoutline">
          <!-- Using 'button-content' slot -->
          <template v-slot:button-content style="padding:0px;">
            <!--                이미지 가지고 오는 것 느림-->
            <img v-if="$store.state.currentUser.picture" class="icon-round" :src="$store.state.currentUser.picture"
                 width="40" height="40">
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
  export default {
    name: 'MainHeader',
    data() {
      return {
        alarmList: [],

      }
    },
    computed: {
      getAlarmList: function () {
        while (this.alarmList.length > 5) {
          this.alarmList.pop()
        }
        return this.alarmList
      }
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
      this.$store.state.stompClient.subscribe("/sub/alarm/" + this.$store.state.currentUser.email, (e) => {
        console.log("get callback")
        let invite = JSON.parse(e.body)
        console.log(invite.sender)
        this.alarmList.unshift(invite)
      })
    },
    methods: {
      inviteAccept: function (alarm, index) {
        this.$http.post('/api/invite/accept', alarm)
          .then(res => {
            console.log(res)
            // 현재 채널을 변경하는 로직을 구현해야할듯
            this.alarmList.splice(index, 1);
          })
          .catch(error => {
            console.log(error)
          })
      },
      // 거절 과 수락은 하나의 api로 해서 신호를 하나 줘서 분기 시키는게 더 좋을 듯
      inviteRefuse: function (alarm, index) {
        // 초대가 거절됐다는 메시지를 채널에 보내는 로직을 구현해야함
        this.$http.post('/api/invite/refuse',alarm)
          .then(res =>{
            this.alarmList.splice(index, 1);
          })
          .catch(error => {
            console.log(error)
          })
      },
      callComponent: function () {
        this.$store.commit('getSelectComponent', 'user')
      },
      LSidebarToggle: function () {
        this.$store.state.isLActive = !this.$store.state.isLActive
      },
      RSidebarOpen: function () {
        this.$store.state.isRActive = true
      },
      SignOut() {
        this.$store.commit('setIsLogout', true)
        window.location.href = "/logout"
      },
      getUserNameByEmail: function (email) {
        return this.$store.state.userList.find(element => {
          return element.email == email
        }).name
      }
    },
  }
</script>
