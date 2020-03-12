<template>
  <nav id="sidebar" class="myflex-column" v-bind:class="{active: $store.state.isLActive}">
    <div class="sidebar-header">
      <img style="width: 100%;" src="../../assets/images/nineone.png">
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
            <li v-for="item in items" :key="item.id">
              <a>{{ item.name }}</a>
            </li>
          </ul>
        </b-collapse>
        </li>
        <div class="menulist-header">
          <span>Users</span>
        </div>
        <li>
          <a>Users</a>
        </li>
      </ul>
    </div>
    <b-modal id="channelCU" centered  ref="modal" @show="prepareModal" @hidden="resetModal" @ok="handleOk">
      <template #modal-title>
        {{modalObj.modalTitle}}
      </template>
      <form ref="channelCreateForm" @submit.stop.prevent="channelForm">
        <b-form-group label="채널 이름" :state="nameState" label-for="channel-input" invalid-feedback="채널 이름이 필요합니다.">
          <b-form-input id="channel-input" :state="nameState" v-model="modalObj.channelTitle" required>
          </b-form-input>
        </b-form-group>
      </form>
    </b-modal>
  </nav>
</template>

<script>
import axios from 'axios'
export default {
  props: ['modalObj'],
  name: 'LSidebar',
    data() {
    return {
      nameState: null,
      channelmode: ''
    }
  },
  methods: {
    prepareModal: function (e){
      if(e.target.parentNode.dataset.mode=='create'){
        this.channelmode = 'create'
        this.modalObj.modalTitle = '채널 생성'
        this.$bvModal.show('channelCU')
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
      axios.get('http://localhost:9191/api/user/info')
        .then(res => {
          console.log(res)
          axios.post('http://localhost:9191/api/channel/create',{
            name: this.modalObj.channelTitle,
            member_email: res.data
          }, {
            headers: {
              'Content-Type' : 'application/json'
            }
          })
            .then(res => {
              console.log(res)
              this.testList()
            })
            .catch(error => {
              console.warn(error)
            })
        })
        .catch(error => {
          console.warn(error)
        })
    },
    // 채널 리스트 가져오기
    // 함수 이름 변경 필요
    testList: function () {
      this.$http.get('http://localhost:9191/api/channel/list')
        .then(res => {
          this.items = res.data
        })
    }
  },
  mounted() {
    this.testList()
  }
}
</script>
