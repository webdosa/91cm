import axios from 'axios'

class InviteService {

  invite(sender, channelId, recipient) {
    console.log(channelId)
    axios.post('/api/invite',{
      channel_id: channelId,
      sender: sender,
      recipient: recipient
    })
      .then(res => {
        return res.data
      }).catch(error => {
      console.error(error)
      alert("초대에 실패했습니다.")
    })
  }

}
export default new InviteService()
