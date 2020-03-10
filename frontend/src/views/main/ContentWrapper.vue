<template>
    <main class="mainwrapper">
        <div class="h-inherit">
          <ul class="c-c-wrapper list-unstyled" @scroll="scrollEvt">
            <MsgBox v-for="msg in msgArray" :key="msg.message.id">
              <template #m-info>
                <!-- #으로 단축해서 사용 -->
                <strong>{{ msg.user.name }}</strong>
                <span style="font-size: 11px; margin-left:3px; ">{{ msg.message.send_date }}</span>
              </template>
              <template #m-content>
                <!-- #으로 단축해서 사용 -->
                <div v-html="msg.message.content" class="mychat-content"></div>
              </template>
            </MsgBox>
          </ul>
          <div class="c-i-wrapper">
            <!-- 더 뭔가 추가할 거 같아서 div로 감싸놓음 -->
            <div style="flex-grow:1;">
              <b-form-textarea
                id="textarea-no-resize"
                placeholder="Enter chat message"
                rows="3"
                no-resize
                v-model="message.content"
                @keyup.enter.exact="send"

              ></b-form-textarea>
            </div>
            <b-button @click="send" style="height: 57px; width: 70px; margin-left:20px;" variant="primary">전송</b-button>
          </div>
        </div>
      </main>
</template>
<script>
import MsgBox from './MsgBox'
export default {
    props: ['currentChannel','stompClient','msgArray'],
    name: 'ContentWrapper',
    components: {
        MsgBox
    },
    data() {
      return {
        message: {
        channel_id: 0,
        content: '',
        sender: 'userid'
        }
      }
    },
    methods: {
      send(){
        console.log(this.currentChannel)
        console.log(this.stompClient)
        this.message.channel_id = this.currentChannel
        if (this.stompClient && this.stompClient.connected) {
          this.stompClient.send("/pub/chat/message", JSON.stringify(this.message),{})
          this.message.content = ''
        }
      },
      scrollEvt (e){
        let element = e.target;
        if(element.scrollTop <= 0){
          //추후 아래에 저장되어있는 메시지 가져오는 코드 추가
          // 저 emit코드는 .then(res=>{}) 안에다가 넣을 코드 emit 옆에 받은 데이터 전달
          // this.$emit('msgArrayUnshift')
        }
      }

    }

}
</script>