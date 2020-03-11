import axios from 'axios'
class AboutChannel{

  getChannelList () {
    return axios.get('http://localhost:9191/api/user')
  }

}
export default new AboutChannel()
