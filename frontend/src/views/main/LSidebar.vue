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
            <a @click="prepareModal('create')" style="margin-right: 5px;">
              <i class="im im-plus-circle"></i>
            </a>
          </div>
        </div>
        <li>

          <a v-b-toggle.collapse-1 class="dropdown-toggle">Channels</a>
          <b-collapse id="collapse-1" visible>
            <ul class="list-unstyled">
              <li v-for="(channel, index ) in $store.state.userChannelList" :key="channel.id">
                <a @click="sendSelectChannel(index)" style="display: flex;">
                  <div>{{ channel.name }}</div>
                  <div class="menulist-header-icon">
                    <b-badge v-if="channel.count!=0" variant="danger" class="verti-align channel-nowrap">{{
                      channel.count }}
                    </b-badge>
                  </div>
                </a>
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
    <b-modal id="channelCU" centered ref="modal" @hidden="resetModal" @ok="handleOk">
      <template #modal-title>
        {{ channelmode }}
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
    props: ['modalObj', 'msgCountObj'],
    watch: {
      getCurrentChannel(newCurrentChannel, oldCurrentChannle) {
        this.$http.get('/api/user/channel/' + newCurrentChannel.id)
          .then(res => {
            this.channelUsers = res.data
            this.$eventBus.$emit('channelUserSize', this.channelUsers.length)
          })
      }
    },
    computed: {
      getCurrentChannel: function () {
        return this.$store.state.currentChannel
      }
    },
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
      console.log("LSidebar created")
    },
    mounted() {
      console.log("LSidebar mounted")
      this.$eventBus.$on('useModal', res =>{
        this.prepareModal(res)
      })
      // this.getUserList()
    },
    updated() {
      console.log("LSidebar updated")
    },
    methods: {

      sendSelectChannel: function (index) {
        this.$store.commit('getSelectComponent', 'main')
        this.$store.commit('setCurrentChannel',this.$store.state.userChannelList[index])
        this.$emit('sendTitle', this.$store.state.userChannelList[index])   // 나중에 변경
      },
      prepareModal: function (mode) {
        console.log(mode)
        if (mode == 'create') {
          this.channelmode = '채널 생성'
        } else if(mode == 'edit'){
          this.channelmode = '채널 수정'
        }
        this.$bvModal.show('channelCU')
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
        if (this.channelmode === '채널 생성') {
          this.createChannel()
        } else if (this.channelmode === '채널 수정') {
          this.$store.state.currentChannel.name = this.channelTitle
          this.updateChannel()
        }
      },
      updateChannel: function () {
        console.log(this.$store.state.currentChannel)
        AboutChannel.updateChannelAPI(this.$store.state.currentChannel)
          .then(res => {
            console.log(res)
          }).catch(error => {
          console.log(error)
        })
      },
      createChannel: function () {
        // vuex에서 currentUser 객체 사용
        AboutChannel.createChannel(this.channelTitle, this.$store.state.currentUser.email)
          .then(res => {
            // 채널 생성 후 리스트를 업데이트 하는 부분
            AboutChannel.getChannelList().then(res => {
              this.$emit('channelUpdate', res.data)
            })
          }).catch(error => {
          console.warn(error)
        })
      }
    }
  }
</script>
