<template>
  <div>
    <b-button @click="test">test</b-button>
    <br><br><br><br><br><br><br><br>
<!--    <b-dropdown variant="nonoutline" dropup no-flip ref="ott">-->
<!--      <b-dropdown-item-button v-for="item in users" @click="alertsBtn">{{item}}</b-dropdown-item-button>-->
<!--    </b-dropdown>-->
    <b-list-group>
      <b-list-group-item button></b-list-group-item>
    </b-list-group>
    <b-form-textarea @keydown.shift.50="test"
      placeholder="Enter chat message"
    ></b-form-textarea>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
  import SockJS from "sockjs-client";
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
