<template>
  <nav id="sidebar" class="myflex-column" v-bind:class="{active: $store.state.isLActive}">
    <div class="sidebar-header">
      <a href="/main">
        <img style="width: 100%;" src="../../assets/images/nineone.png">
      </a>
      <h3>91CM</h3>
    </div>
    <div class="myflex-column menulist">
      <ul class="list-unstyled components">
        <div class="menulist-header">
          <span>Channels</span>
          <div class="menulist-header-icon">
            <a data-mode="create" @click="prepareModal">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                style="fill: white;/* height: 100%; */"
              >
                <path
                  d="M12 2c5.514 0 10 4.486 10 10s-4.486 10-10 10-10-4.486-10-10 4.486-10 10-10zm0-2c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm6 13h-5v5h-2v-5h-5v-2h5v-5h2v5h5v2z"
                ></path>
              </svg>
            </a>
          </div>
        </div>
        <li>
          <a v-b-toggle.collapse-1 class="dropdown-toggle">
        <li>Channels</li>
        </a>
        <b-collapse id="collapse-1">
          <ul class="list-unstyled">
            <li v-for="(channel, index ) in channelList" :key="channel.id">
              <a @click="sendSelectChannel(index)">{{ channel.name }}</a>
            </li>
          </ul>
        </b-collapse>
        </li>
        <div class="menulist-header">
          <span>Users</span>
        </div>
        <ul class="list-unstyled">
          <li v-for="(user) in channelUsers" :key="user.email">
            <a href="#">{{ user.name }}</a>
          </li>
        </ul>
      </ul>
    </div>
    <b-modal id="channelCU" centered ref="modal" @show="prepareModal" @hidden="resetModal" @ok="handleOk">
      <template #modal-title>
        {{modalObj.modalTitle}}
      </template>
      <form ref="channelCreateForm" @submit.stop.prevent="channelForm">
        <b-form-group label="채널 이름" :state="nameState" label-for="channel-input" invalid-feedback="채널 이름이 필요합니다.">
          <b-form-input id="channel-input" :state="nameState" v-model="channelTitle" required autofocus>
          </b-form-input>
        </b-form-group>
      </form>
    </b-modal>
  </nav>
</template>

<script>
  import AboutChannel from '../../service/aboutchannel'

  export default {
    props: ['modalObj', 'channelList'],
    watch: {
      channelList: function (newVal) {
        this.channelList = newVal
        this.getUserList()
      },
    },
    computed: {},
    name: 'LSidebar',
    data() {
      return {
        channelIndex: 0,
        nameState: null,
        channelmode: '',
        channelTitle: '',
        channelUsers: []
      }
    },
    created() {
    },
    mounted() {
    },
    updated() {
    },
    methods: {
      getUserList: function () {
        this.$http.get('/api/user/channel/' + this.channelList[this.channelIndex].id)
          .then(res => {
            this.channelUsers = res.data
            this.$eventBus.$emit('channelUserSize',this.channelUsers.length)
          })
      },
      sendSelectChannel: function (index) {
        this.channelIndex = index
        this.$emit('sendTitle', this.channelList[this.channelIndex])
        this.getUserList()
      },
      prepareModal: function (e) {
        if (e.target.parentNode.dataset.mode == 'create') {
          this.channelmode = 'create'
          this.modalObj.modalTitle = '채널 생성'
          this.$bvModal.show('channelCU')
        } else if (this.modalObj.modalTitle === '채널 수정') {
          this.channelTitle = this.modalObj.currentChannel.name
        }
      },
      // 채널 생성 부분
      checkFormValidity: function () {
        const valid = this.$refs.channelCreateForm.checkValidity()
        this.nameState = valid
        return valid
      },
      RSidebarClose: function () {
        this.$store.state.isRActive = false
      },
      resetModal() {
        this.channelTitle = ''
        this.nameState = null
      },
      handleOk(bvModalEvt) {
        // Prevent modal from closing
        bvModalEvt.preventDefault()
        // Trigger submit handler
        this.channelForm()
      },
      channelForm: function () {
        if (!this.checkFormValidity()) {
          return
        }
        this.$refs['modal'].hide()

        this.$nextTick(() => {
          this.$bvModal.hide('channelCU')
        })
        if (this.modalObj.modalTitle === '채널 생성') {
          this.createChannel()
        } else if (this.modalObj.modalTitle === '채널 수정') {
          this.modalObj.currentChannel.name = this.channelTitle
          this.updateChannel()
        }
      },
      updateChannel: function () {
        this.$http.post('http://localhost:9191/api/channel/update', this.modalObj.currentChannel,
          {
            headers: {'Content-Type': 'application/json'}
          })
          .then(res => {
            console.log(res)
          }).catch(error => {
          console.log(error)
        })
      },
      createChannel: function () {
        // vuex에서 currentUser 객체 사용
        this.$http.get('http://localhost:9191/api/user/info')
          .then(res => {
            console.log(res)
            this.$http.post('http://localhost:9191/api/channel/create', {
              name: this.channelTitle,
              member_email: res.data.email
            }, {
              headers: {
                'Content-Type': 'application/json'
              }
            })
              .then(res => {
                console.log(res)
                // 채널 생성 후 리스트를 업데이트 하는 부분
                this.$http.get('http://localhost:9191/api/channel/list').then(res => {
                  this.$emit('channelUpdate', res.data)
                })
                //this.$router.go('/main')
              })
              .catch(error => {
                console.warn(error)
              })
          })
          .catch(error => {
            console.warn(error)
          })
      }
    }
  }
</script>
