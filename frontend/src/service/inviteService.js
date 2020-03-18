import axios from 'axios'

class InviteService {

  invite(sender, channelId, recipient) {
    console.log(channelId)
    return axios.post('/api/invite',{
      channel_id: channelId,
      sender: sender,
      recipient: recipient
    })
  }
}
export default new InviteService()
