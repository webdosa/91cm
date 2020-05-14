import axios from 'axios'

class InviteService {

  async invite(sender, channelId, recipient) {
    console.log(channelId)
    return await axios.post('/api/invite', {
      channel_id: channelId,
      sender: sender,
      recipient: recipient
    })
  }
  sendMail(){
    re
  }


}

export default new InviteService()
