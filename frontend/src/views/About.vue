<template>
  <div>
    <div style="flex-grow:1;" class="myflex-column">
          <div style="position: relative;">
      <div class="mytextarea-wrapper" v-if="!$store.state.isInviteMode && !$store.state.isSearchMode">
            <v-checkbox v-model="checkbox" class="myfile-upload" style="right: 82px;height: 24px; margin: 0;padding: 0;"></v-checkbox>
                <i class="im im-users myfile-upload" style="right: 50px;"></i>
              <label for="file-input" style="display: block;margin-bottom: 0;">
                <i class="im im-cloud-upload myfile-upload"></i>
              </label>
              <input id="file-input" type="file" ref="fileInput" multiple hidden/>
              <b-form-textarea
                class="mytextarea"
                autofocus
                id="textarea-no-resize"
                placeholder="Enter chat message"
                rows="2"
                no-resize
              ></b-form-textarea>
            </div>
          </div>
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
          users:['user1','user2','user3','user4','user5'],
          checkbox:false
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

/* .btn::after {
  content: none;
} */
</style>
