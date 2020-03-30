import axios from 'axios'
class AboutChannel{
    // 임시로 제가 만든 채널리스트 가져오는 api입니다.
    // 추후에 코드 합치고나서 만들어주신 api 쓸 예정입니다.
    getChannelList () {
        return axios.get('http://localhost:9191/api/channel/list')
    }
   
    createChannel (channelTitle,email) {
        return axios.post('http://localhost:9191/api/channel/create', {
            name: channelTitle,
            member_email: email
          }, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
    }
    updateChannelAPI (currentChannel) {
      return axios.post('http://localhost:9191/api/channel/update', currentChannel,
      {
        headers: {'Content-Type': 'application/json'}
      })
    }

    updateLastAccessDate (currentId,oldId) {
      console.log(oldId)
      return axios.put('http://localhost:9191/api/channel/update/lastaccessdate',
      {
        oldChannelId: oldId,
        currentChannelId: currentId
      }
      )
    }

    updateSessionIsCW (bool) {
      console.log(bool)
      return axios.put("http://localhost:9191/api/channel/update/sessioniscw",
      {
        isContentWrapper: bool
      }
      )
    }

    // insertSessionLA(channel_id){
    //   return axios.post("http://localhost:9191/api/channel/insert/sessionLA",
    //   {
    //     isFocus: true,
    //     currentChannelId: channel_id,
    //     isContentWrapper: true
    //   })
    // }
    
}

export default new AboutChannel()
