<template>
  <header>
    <b-navbar toggleable="lg" type="light" variant="white">
      <i v-if="$store.state.isLActive" class="im im-angle-right-circle btn btn-info" @click="LSidebarToggle"></i>
      <i v-else class="im im-angle-left-circle btn btn-info" @click="LSidebarToggle"></i>
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-dropdown style="button:position: relative;" no-caret right toggle-class="nonoutline" class="verti-align" variant="nonoutline" :disabled="getAlarmList.length <= 0">
          <template v-slot:button-content>
            <div style="position: relative;" ref="bell">
              <b-badge style="position: absolute; right: -5px;font-size: 10px;" variant="danger" v-show="getAlarmList.length > 0">
                {{alarmList.length}}
              </b-badge>
              <i class="im im-bell"></i>
            </div>
          </template>
          <b-dropdown-text v-for="(alarm,index) in getAlarmList" style="width: 25vw;" :key="alarm"
                           class="border">
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
                <b-button size="sm" variant="nonoutline" @click="inviteRefuse(alarm,index)" style="padding-bottom: 0;"><i
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
          <b-dropdown-item @click="showModal('copyRight-modal')">Opensource license</b-dropdown-item>
          <b-dropdown-item @click="SignOut">Sign Out</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-navbar>
    <!-- 아래 modal은 임시코드 추후에 필히 삭제할것 -->
    <b-modal id="copyRight-modal" size="lg" scrollable ok-only title="오픈소스 라이센스">
                <b-button class="button-margin" v-b-toggle.collapse-open variant="outline-dark" block>Animate.css</b-button>
                <b-collapse id="collapse-open" class="mt-2">
                    <b-card>
                        <span>link :</span>
                        <b-link href="https://github.com/daneden/animate.css" style="color: blue;">https://github.com/daneden/animate.css</b-link>
                        <p class="card-text" >
                            The MIT License (MIT)
                            <br>
                            Copyright (c) 2019 Daniel Eden
                            <br>
                            Permission is hereby granted, free of charge, to any person obtaining a copy
                            of this software and associated documentation files (the "Software"), to deal
                            in the Software without restriction, including without limitation the rights
                            to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
                            copies of the Software, and to permit persons to whom the Software is
                            furnished to do so, subject to the following conditions:
                            <br>
                            The above copyright notice and this permission notice shall be included in all
                            copies or substantial portions of the Software.
                            <br>
                            THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
                            IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
                            FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
                            AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
                            LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
                            OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
                            SOFTWARE.
                        </p>
                    </b-card>
                </b-collapse>
                 <b-button class="button-margin" v-b-toggle.collapse-open2 variant="outline-dark" block>SortableJS/Vue.Draggable</b-button>
                <b-collapse id="collapse-open2" class="mt-2">
                    <b-card>
                        <span>link :</span>
                        <b-link href="https://github.com/SortableJS/Vue.Draggable" style="color: blue;">https://github.com/SortableJS/Vue.Draggable</b-link>
                        <p class="card-text" >
                            The MIT License (MIT)
                            <br>
                            Copyright (c) 2016-2019 David Desmaisons
                            <br>
                            Permission is hereby granted, free of charge, to any person obtaining a copy
                            of this software and associated documentation files (the "Software"), to deal
                            in the Software without restriction, including without limitation the rights
                            to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
                            copies of the Software, and to permit persons to whom the Software is
                            furnished to do so, subject to the following conditions:
                            <br>
                            The above copyright notice and this permission notice shall be included in all
                            copies or substantial portions of the Software.
                            <br>
                            THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
                            IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
                            FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
                            AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
                            LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
                            OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
                            SOFTWARE.
                        </p>
                    </b-card>
                </b-collapse>
        </b-modal>
  </header>
</template>

<script>
  import {mapGetters} from "vuex";
  import CopyRight from '../util/CopyRight'

  export default {
    name: 'MainHeader',
    data() {
      return {
        alarmList: [],
      }
    },

    // watch: {
    //   // stomp 클라이언트가 null 일때가 없음으로 다시 구현
    //   StompClient: function (newVal, oldVal) {
    //     if(newVal.connected){
    //       newVal.subscribe("/sub/alarm/" + this.$store.state.currentUser.email, (e) => {
    //         console.log("get callback")
    //         let invite = JSON.parse(e.body)
    //         console.log(invite.sender)
    //         this.alarmList.unshift(invite)
    //       })
    //     }
    //   }
    // },
    computed: {
      // ...mapGetters({
      //   StompClient: 'getStompClient'
      // }),
      getAlarmList: function () {
        while (this.alarmList.length > 5) {
          this.alarmList.pop()
        }
        return this.alarmList
      },
    },
    created() {
      this.$store.state.stompClient.subscribe("/sub/alarm/" + this.$store.state.currentUser.email, (e) => {
        console.log("get callback")
        let invite = JSON.parse(e.body)
        console.log(invite.sender)
        this.alarmList.unshift(invite)
        this.shakeAnimation()
      }),
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
      showModal: function(modalId){
        this.$bvModal.show(modalId)
      },
      shakeAnimation: function(){
        const element = this.$refs.bell;
        element.classList.add('animated','shake');
        element.addEventListener('animationend',()=>{
          console.log("animationend")
          element.classList.remove('animated','shake')
        });
      },
      inviteAccept: function (alarm, index) {
        console.log(alarm)
        const message = {
          channel_id: alarm.channel_id,
          sender: this.$store.state.currentUser.email,
          content: this.$store.state.currentUser.name+'님이 채널에 초대되었습니다.',
          user: this.$store.state.currentUser
        }
        this.$http.post('/api/invite/accept', alarm)
          .then(res => {
            console.log(res)
            // 현재 채널을 변경하는 로직을 구현해야할듯
            this.alarmList.splice(index, 1);
            this.$store.state.stompClient.send('/pub/chat/room/' + alarm.channel_id,
              JSON.stringify({"message": "updateChannel", "error": "null"}))
            this.$store.state.stompClient.send('/pub/chat/message',JSON.stringify(message))
            this.$store.dispatch('channelList')
              .then(() => {
                const joinChannel = this.$store.state.userChannelList.find(channel => channel.id == alarm.channel_id)
                this.$store.commit('setCurrentChannel', joinChannel)
              })

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

