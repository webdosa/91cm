<template>
  <div>
    <p>22</p>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
  import SockJS from "sockjs-client";
  import axios from "axios"
  export default {
    name: 'About',
    created(){
      console.log(this.sendMail)
    },
    components: {

    },
    computed:{
      getUsers: function () {

      }
    },
    data() {
      return {
        sendMail:false,
          users:['user1','user2','user3','user4','user5'],
          checkbox:false,

      }
    },
    methods: {
      alertsBtn: function () {
        alert("hello world")
      },
      test: function () {
        // this.$refs.ott.show(true)
      },
      sendMailToggle(){
        this.sendMail =!this.sendMail
        if(this.sendMail){
          alert('지금부터 보내는 메시지는 나인원소프트 전체 메일로 보내집니다.')
        }
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
