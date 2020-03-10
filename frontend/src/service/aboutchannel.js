import axios from 'axios'
class AboutChannel{
    // 임시로 제가 만든 채널리스트 가져오는 api입니다.
    // 추후에 코드 합치고나서 만들어주신 api 쓸 예정입니다.
    getChannelList () {
        return axios.get('http://localhost:9191/user')        
    }

    getMsgList (){
        return axios.get('')
    }
}
export default new AboutChannel()
