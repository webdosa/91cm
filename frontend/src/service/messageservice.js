import axios from 'axios'
class MessageService{
    getMessageCount (channelList) {
        return axios.post('/api/channel/list',channelList)
    }
}

export default new MessageService()
