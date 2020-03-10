<template>
  <div class="wrapper">
    <!-- Sidebar  -->
    <LSidebar></LSidebar>
    <!-- Page Content  -->
    <div id="m-wrapper" v-bind:class="{active: $store.state.isLActive}">
      <MainHeader></MainHeader>
      <ContentWrapper></ContentWrapper>
    </div>
    <RSidebar></RSidebar>
  </div>
</template>
<script>
  import LSidebar from '../views/main/LSidebar'
  import RSidebar from '../views/main/RSidebar'
  import MainHeader from '../views/main/MainHeader'
  import ContentWrapper from '../views/main/ContentWrapper'
  import AboutChannel from '../service/aboutchannel'
  import axios from 'axios'
  import SockJS from 'sockjs-client'
  import Stomp from 'webstomp-client'

  export default {
    name: 'Main',
    components: {MainHeader, LSidebar,RSidebar,ContentWrapper},
    data() {
      return {
        channelList: [],
        isRActive: false,
        array: [],
        currentChannel: 0,
        msgCountObj: {}
      }
    },
    created () {

      AboutChannel.getChannelList().then(
        res => {
          this.channelList = res.data
          for(let i in this.channelList){
            this.msgCountObj[this.channelList[i]] = 0
          }
          console.log(this.channelList)
          console.log(this.msgCountObj)
          this.connect()
        }
      )
      //this.channelList = channelList
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

          for(let i in this.channelList){
            this.stompClient.subscribe("/sub/chat/room/"+this.channelList[i],(e)=>{
              let data = JSON.parse(e.body);

              if(data.channel_id == this.currentChannel){
                this.array.push(data)
              }else{
                this.msgCountObj[data.channel_id] += 1
              }

            })
          }
        })
      }
    },
    mounted() {
    }
  }
</script>
