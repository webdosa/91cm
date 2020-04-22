<template>
  <nav id="r-sidebar" class="myflex-column" v-bind:class="{barRtoggle: $store.state.isRActive}">
    <div class="r-header r-style">
      <span>About this Channel</span>
      <div style="flex-grow: 1;justify-content: flex-end;display: flex;">
        <a @click="RSidebarClose">
          <i class="im im-x-mark"></i>
        </a>
      </div>
    </div>
    <!-- content wrapper -->
    <div style="overflow:auto;">
      <!-- 채널 메뉴 시작 -->
      <a class="r-style" v-b-toggle.channel-info>
        <i class="im im-info"></i>
        <span style="margin-left:20px;">Channel Details</span>
        <div style="display: flex; flex-grow: 1; justify-content: flex-end;">
          <i class="im im-care-down" style="font-size: 15px;"></i>
        </div>
      </a>
      <!-- 채널 메뉴 끝 -->
      <!-- 채널 메뉴 콜랩스 -->
      <b-collapse id="channel-info">
        <div class="s-coll-style">
          <div>
            <div style="display:flex;">
              <p>Channel Name</p>
                <a class="verti-align" data-mode="edit" @click="useModal('edit')">Edit</a>
            </div>
            <li class="list-unstyled">{{ $store.state.currentChannel.name }}</li>
          </div>
          <div style="display:flex; justify-content:flex-start;">
            <b-button variant="primary" @click="leaveChannle">나가기</b-button>
          </div>
        </div>
      </b-collapse>

      <!-- Users 메뉴 시작 -->
      <a class="r-style" v-b-toggle.user-info>
        <i class="im im-users"></i>
        <span style="margin-left:20px;">{{channelUserSize}} Users</span>
      </a>
      <!-- Users 메뉴 끝 -->

      <!-- to do list 메뉴 시작 -->
      <a class="r-style" @click="callComponent('todoList')">
        <i class="im im-task-o"></i>
        <span style="margin-left:20px;">Todo List</span>
      </a>
      <!-- to do list 메뉴 끝 -->
      <!-- calender 메뉴 시작 -->
      <a class="r-style"  @click="callComponent('calendar')">
        <i class="im im-calendar"></i>
        <span style="margin-left:20px;">Calendar</span>
      </a>
      <!-- calender 메뉴 끝 -->
    </div>
  </nav>
</template>
<script>
  export default {
    props: ['modalObj'],
    name: 'RSidebar',
    data() {
      return {
        channelUserSize: 0,
        userSelect: null
      }
    },
    mounted() {
      this.$eventBus.$on('channelUserSize', data => {
        this.channelUserSize = data
      })
    },
    methods: {
      callComponent: function (componentName) {
        this.$store.commit('getSelectComponent',componentName)
      },
      leaveChannle: function () {
        this.$http.post('/api/channel/leave', {
          // 모두가 나가면 채널 삭제
          email: this.$store.state.currentUser.email,
          channel_id: this.$store.state.currentChannel.id
        }).then(res => {
          // 유저가 나갔음으로 채널 유저 업데이트
          this.$store.state.stompClient.send('/pub/chat/room/'+this.$store.state.currentChannel.id,
          JSON.stringify({'message':'updateChannel', 'error':"null"}))
          this.$eventBus.$emit('leaveChannelMsg')
          this.$alertModal('alert redirect', this.$store.state.currentChannel.name + ' 채널에서 나갔습니다.')
        }).catch(error => {
          this.$alertModal('error', '나가기에 실패했습니다.')
        })
      },
      RSidebarClose: function () {
        this.$store.state.isRActive = false
      },
      useModal: function (mode) {
        if (mode == 'edit') {
          this.$eventBus.$emit('useModal',mode)
        }
      },
      msgBox: async function (content) {
        await this.$bvModal.msgBoxConfirm(content, {
          title: '확인',
          okTitle: '확인',
          okVariant: 'danger',
          buttonSize: 'sm',
          cancelTitle: '취소'
        })
          .then(value => {
            this.userSelect = value
            return value
          })
      }
    }
  }

</script>
