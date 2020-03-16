<template>
  <div class='about'>
    <span> {{$store.state.currentUser.name }}</span>
    <h1>This is an about page!!</h1>
    <button @click="send">전송</button>
    <button @click="check">확인</button>
    <a href="/" @click="check">확인</a>
  </div>
</template>
<script>
/* import axios from 'axios' */
// import WebSocket from '../service/websocket'
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
let channelList = [1, 2]
channelList.push(3)
export default {
  name: 'About',
  data () {
    return {
      channelList: null,
      message: {
        channel_id: 3,
        content: 'hihi',
        sender: 'userid'
      },
      array: [],
      currentChannel: 0,
      msgCountObj: {}
    }
  },

  created () {
    this.channelList = channelList
  },
  methods: {

    send: function() {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/pub/chat/message", JSON.stringify(this.message),{})
      }
     },
    connect() {
      this.stompClient = Stomp.over(new SockJS('http://localhost:9191/endpoint/'))
      this.stompClient.connect({},() => {
        console.log('연결')

        for(let i in channelList){
        this.stompClient.subscribe("/sub/chat/room/"+channelList[i],(e)=>{
          let data = JSON.parse(e.body);

          if(data.channel_id == this.currentChannel){
            this.array.push(data)
          }else{
            this.msgCountObj[data.channel_id] += 1
          }

        })
      }
      })
    },
    check() {
      this.$store.state.isLActive = true
    }

    },
    mounted () {
      /* axios.get('http://localhost:9191/api/test')
        .then(res => {
          this.info = res.data
        }).catch(error => {
        }) */
      this.currentChannel = 1
      for(let i in this.channelList){
        this.msgCountObj[this.channelList[i]] = 0
      }
      this.connect()
  }
}

</script>
