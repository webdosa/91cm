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
            <a data-mode="create" @click="prepareModal" style="margin-right: 5px;">
              <i class="im im-plus-circle"></i>
            </a>
          </div>
        </div>
        <li>

          <a v-b-toggle.collapse-1 class="dropdown-toggle">Channels</a>
        <b-collapse id="collapse-1" visible>
          <ul class="list-unstyled">
            <li v-for="(channel, index ) in channelList" :key="channel.id">
              <a @click="sendSelectChannel(index)" style="display: flex;">
                <div>{{ channel.name }}</div>
                <div class="menulist-header-icon">
                 <b-badge v-if="msgCountObj[channel.id]!=0" variant="danger" class="verti-align channel-nowrap">{{msgCountObj[channel.id]}}</b-badge>
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
    props: ['modalObj', 'channelList','msgCountObj'],
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
      console.log("LSidebar created")
    },
    mounted() {
      console.log("LSidebar mounted")
    },
    updated() {

      console.log("LSidebar updated")
      //once는 이벤트 버스를 한번만 연결해서 데이터를 가져오는 메소드
      // 해당 로직은 사용자를 초대해서 현재 채널의 유저 정보를 다시 가져오는 로직
      this.$eventBus.$once('getUserList', data => {
        if (data) {
          this.getUserList()
        }
      })
    },
    methods: {
      getUserList: function () {
        this.$http.get('/api/user/channel/' + this.channelList[this.channelIndex].id)
          .then(res => {
            this.channelUsers = res.data
            this.$eventBus.$emit('channelUserSize', this.channelUsers.length)
            this.$eventBus.$off('channelUserSize') // 이벤트 버스 연결 해제 코드
          })
      },
      sendSelectChannel: function (index) {
        this.$store.commit('getSelectComponent','main')
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
        AboutChannel.updateChannelAPI(this.modalObj.currentChannel)
          .then(res => {
            console.log(res)
          }).catch(error => {
            console.log(error)
        })
      },
      createChannel: function () {
        // vuex에서 currentUser 객체 사용
            AboutChannel.createChannel(this.channelTitle,this.$store.state.currentUser.email)
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
<style lang="scss">
  @import '../../assets/font/iconmonstr/css/iconmonstr-iconic-font.min.css';
</style>
