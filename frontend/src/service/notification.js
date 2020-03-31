import CommonClass from './common'
class NotificationClass {

    requestPermission () {
        Notification.requestPermission().then(function (result){
            console.log(result)
            return
        })
    }

    sendNotification (isfocus,data) {
        console.log(isfocus)
        if(!isfocus){
            let options = {
                body: data.user.name + ' : '+ CommonClass.replacemsgForPreview(data.content)
            }
            console.log(options.body)
            let notification = new Notification("91CM 메시지 도착", options);

            notification.onclick = function(event) {
                // 잘 모르겠음...ㅠ
                // event.preventDefault() // prevent the browser from focusing the Notification's tab
                // window.focus()
              }
            
            //알림 후 5초 뒤,
            setTimeout(function () {
                //얼람 메시지 닫기
                notification.close();
            }, 4000);
        }
    }
}
export default new NotificationClass()