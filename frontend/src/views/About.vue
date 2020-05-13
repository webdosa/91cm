<template>
  <div>
      <div class="mytextarea-wrapper" v-if="!$store.state.isInviteMode && !$store.state.isSearchMode">
              <label for="file-input" style="display: block;margin-bottom: 0;">
                <i class="im im-cloud-upload myfile-upload"></i>
              </label>
              <input id="file-input" type="file" ref="fileInput" multiple @change="attachFile" hidden/>
              <label for="invite" style="display: block;margin-bottom: 0;">
                <i class="im im-users myfile-upload" style="margin-right: 4.3vh;"></i>
              </label>
              <b-button id="invite" type="file" ref="fileInput" @click="inviteToggle" hidden/>
              <b-form-textarea
                class="mytextarea"
                autofocus
                id="textarea-no-resize"
                placeholder="Enter chat message"
                rows="2"
                no-resize
                v-model="message.content"
                @keydown.ctrl.shift.70="toggleSearchMode"
                @keydown.enter.exact="send"
                @keyup="byteCheck"
                @keydown.shift.alt.50='inviteToggle'
              ></b-form-textarea>
            </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
  import SockJS from "sockjs-client";
  import axios from "axios"
  export default {
    name: 'About',
    created(){
    },
    components: {

    },
    computed:{
      getUsers: function () {

      }
    },
    data() {
      return {
          users:['user1','user2','user3','user4','user5']
      }
    },
    methods: {
      alertsBtn: function () {
        alert("hello world")
      },
      test: function () {
        // this.$refs.ott.show(true)
      }
    }
  }

</script>
<style scoped>
 .videostyle{
   background-color: black;
 }

.btn::after {
  content: none;
}
</style>
