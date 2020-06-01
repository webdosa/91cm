import axios from 'axios'

class InviteService {

  async invite(sender, channelId, recipients) {
    console.log(channelId)
    return await axios.post('/api/invite', {
      channel_id: channelId,
      sender: sender,
      recipients: recipients
    })
  }
  sendMail(){
    
  }


}

export default new InviteService()
