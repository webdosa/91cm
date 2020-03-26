import axios from 'axios'

class AboutChannel {
  // 임시로 제가 만든 채널리스트 가져오는 api입니다.
  // 추후에 코드 합치고나서 만들어주신 api 쓸 예정입니다.
  getChannelList() {
    return axios.get('http://localhost:9191/api/channel/list')
  }

  createChannel(channelTitle, email) {
    return axios.post('http://localhost:9191/api/channel/create', {
      name: channelTitle,
      member_email: email
    }, {
      headers: {
        'Content-Type': 'application/json'
      }
    })
  }

}

export default new AboutChannel()
