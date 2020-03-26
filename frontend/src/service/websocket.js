import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'

class WebSocket {
  constructor() {
    this.array = []
    this.currentChannel = 0
    this.msgCountArr = []
  }

  connect(channelList) {
    this.socket = new SockJS('http://localhost:9191/endpoint/')
    this.stompClient = Stomp.over(this.socket)
    this.stompClient.connect({}, () => {
      console.log('연결')

      for (let i in channelList) {
        this.stompClient.subscribe("/sub/chat/room/" + channelList[i], (e) => {
          let data = JSON.parse(e.body);
          if (data.channel_id == this.currentChannel) {
            this.array.push(data)
          } else {
            this.msgCountArr.push()
          }

        })
      }
    })
  }

  send(message) {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send("/pub/chat/message", JSON.stringify(message), {})
    }
  }

}

export default new WebSocket()
