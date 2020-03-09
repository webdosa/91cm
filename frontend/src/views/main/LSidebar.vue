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
            <a v-b-modal.channel-create>
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
              <li>
                <a>cha1</a>
              </li>
              <li>
                <a>cha2</a>
              </li>
              <li>
                <a>cha3</a>
              </li>
              <li>
                <a>cha1</a>
              </li>
              <li>
                <a>cha1</a>
              </li>
              <li>
                <a>cha1</a>
              </li>
              <li>
                <a>cha1</a>
              </li>
              <li>
                <a>cha1</a>
              </li>
              <li>
                <a>cha1</a>
              </li>
              <li>
                <a>cha1</a>
              </li>
              <li>
                <a>cha1</a>
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
    <b-modal id="channel-create" centered title="채널 생성" ref="modal" @show="resetModal" @hidden="resetModal" @ok="handleOk">
      <form ref="channelCreateForm" @submit.stop.prevent="channelForm">
        <b-form-group label="채널 이름" :state="nameState" label-for="channel-input" invalid-feedback="채널 이름이 필요합니다.">
          <b-form-input id="channel-input" :state="nameState" v-model="channelTitle" required>
          </b-form-input>
        </b-form-group>
      </form>
    </b-modal>
    <b-modal id="channel-edit" centered title="채널 이름 수정">
        <p class="my-4">Vertically centered modal!</p>
      </b-modal>
  </nav>  
</template>

<script>
import axios from 'axios'
export default {
  name: 'LSidebar',
    data() {
    return {
      channelTitle: '',
      nameState: null
    }
  },
  methods: {
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
          this.$bvModal.hide('channel-create')
        })
         axios.get('http://localhost:9191/api/user')
    },
    checkFormValidity: function () {
        const valid = this.$refs.channelCreateForm.checkValidity()
        this.nameState = valid
        return valid
      }
  }
}
</script>
