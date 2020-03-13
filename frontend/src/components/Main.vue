<template>
  <div class="wrapper">
    <!-- Sidebar  -->
    <LSidebar 
      :modalObj="modalObj" 
      :channelList="channelList" 
      @channelUpdate="channelUpdate"
      @sendTitle="sendTitle"></LSidebar>
    <!-- Page Content  -->
    <div id="m-wrapper" v-bind:class="{active: $store.state.isLActive}">
      <MainHeader></MainHeader>
      <router-view name="ChannelHeader" :channelTitle="modalObj.currentChannel.name"></router-view>
      <router-view
        :currentChannel="modalObj.currentChannel"
        :stompClient="stompClient"
        :msgArray="msgArray"
        @msgArrayUnshift="msgArrayUnshift"
      ></router-view>
    </div>
    <RSidebar :modalObj="modalObj" @passData="passData"></RSidebar>
  </div>
</template>
<script>
  import LSidebar from '../views/main/LSidebar'
  import RSidebar from '../views/main/RSidebar'
  import MainHeader from '../views/main/MainHeader'
  import ContentWrapper from '../views/main/ContentWrapper'
  import AboutChannel from '../service/aboutchannel'
  import SockJS from 'sockjs-client'
  import Stomp from 'webstomp-client'


  
  export default {
    name: 'Main',
    components: {MainHeader, LSidebar, RSidebar, ContentWrapper},
    data() {
      return {
        channelTitle: '',
        stompClient: null,
        channelList: [],
        isRActive: false,
        msgArray: [],
        msgCountObj: {},
        modalObj: {modalTitle: '', currentChannel: null}
      }
    },
    created () {

      axios.get('http://localhost:9191/api/user/getsession').then(res=>{
        if(res.data.phone == null || res.data.phone == ''){
            this.$router.replace('/signup')    
        }else{
          AboutChannel.getChannelList().then(
          res => {            
            this.channelList = res.data
            for(let i in this.channelList){
              this.msgCountObj[this.channelList[i].id] = 0
            }
            console.log(this.channelList)
            console.log(this.msgCountObj)
            //사용자가 채널을 선택하지 않았다면.
            if (this.modalObj.currentChannel == null) {
              this.modalObj.currentChannel = this.channelList[0]
              this.channelTitle = this.modalObj.currentChannel.name
              console.log(this.currentChannel)
            }
            // 현재 채널에 저장되어있는 메시지 가져오기
            // AboutChannel.getMsgList(this.currentChannel).then(
            //   res=> {

            // })

            this.connect()
          }
        )
        }
      })
    },
    methods: {
      sendTitle(channel){
        this.channelTitle = channel.name
        this.modalObj.currentChannel = channel
      },
      passData(modalObj) {
        this.modalObj.modalTitle = modalObj.modalTitle
      },
      connect() {
        this.stompClient = Stomp.over(new SockJS('http://localhost:9191/endpoint/'))
        console.log('asd1')
        this.stompClient.connect({}, () => {
          console.log('asd2')
          
          for (let i in this.channelList) {
            this.stompClient.subscribe("/sub/chat/room/" + this.channelList[i].id, (e) => {
              let data = JSON.parse(e.body);
              console.log(data)
              if (data.message.channel_id == this.modalObj.currentChannel.id) {
                data.message.content = this.replacemsg(data.message.content)
                console.log(data);
                this.msgArray.push(data)
              } else {
                this.msgCountObj[data.message.channel_id] += 1
              }
            })
          }
        })
      },
      replacemsg(originContent) {
        let array = originContent.split("\n")
        let content = ''
        for (let i in array) {
          content += '<p>' + array[i] + '</p>'
        }
        return content.replace(/ /gi, '&nbsp;')
      },
      msgArrayUnshift() {
        //console.log('함수실행')
      },
      channelUpdate(newChannelList) {
          let num = newChannelList.length - this.channelList.length
        for(let i=num; i>0; i-- ){
          let idx = newChannelList.length-i
          this.msgCountObj[newChannelList[idx].id] = 0
            this.stompClient.subscribe("/sub/chat/room/" + newChannelList[idx].id,(e)=>{
              let data = JSON.parse(e.body);
              if(data.message.channel_id == this.currentChannel){
                data.message.content = this.replacemsg(data.message.content)
                this.msgArray.push(data)
              }else{
                this.msgCountObj[data.message.channel_id] += 1
              }
            })
        }
        this.channelList = newChannelList
      }
    }
  }
</script>
