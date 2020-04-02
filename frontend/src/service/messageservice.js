import axios from 'axios'
class MessageService{
    getMessageCount (channelList) {
        return axios.post('http://localhost:9191/api/channel/list',channelList)
    }
}

export default new MessageService()