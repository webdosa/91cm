import AboutChannel from './aboutchannel'
import store from '../store'
class EventListener{
    beforeunloadEvt () {
        store.commit('setIsLogout',false)
        //로그아웃함수가 실행되어 페이지이동시에 발생하지 않도록 조건문 처리
        window.addEventListener('beforeunload', function (event) {
            if(!store.state.isLogout){
                AboutChannel.updateSessionIsCW(false)
            }
        })
    }
    focusEvt(instance) {
        window.addEventListener('focus', function() {
                if(!store.state.isLogout){
                store.commit('setFocus',true)
                let isfocus = store.state.isfocus
                AboutChannel.updateFocus(isfocus)
                console.log('focus')
                console.log(store.state.currentChannel)
                if(store.state.currentChannel != null && store.state.selectComponent == 'main'){
                    instance.msgCountUpdate(store.state.currentChannel.id,false)
                }
            }
        })
    }
    blurEvt() {
        window.addEventListener('blur', function() {
            if(!store.state.isLogout){
                store.commit('setFocus',false)
                let isfocus = store.state.isfocus
                AboutChannel.updateFocus(isfocus)
        }
        })
    }

    resizeEvt(){
        window.addEventListener('resize', function(){
            store.commit('setSmallWidth',(window.innerWidth < 500) ? true : false)
        })
    }

}
export default new EventListener()
