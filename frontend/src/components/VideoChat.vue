<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-4">
        <content-wrapper :msg-array="msgArray" @msgArrayUpdate="msgArrayUpdate">
        </content-wrapper>
      </div>
      <div class="col-md-8">
        <vue-webrtc
          ref="webrtc"
          :room-id="$store.state.currentChannel.id"
          :socket-u-r-l="'http://91cm.nineonesoft.com:9001/'"
        ></vue-webrtc>
      </div>
    </div>
  </div>
</template>

<script>
  import * as io from 'socket.io-client'
  import ContentWrapper from "../views/main/ContentWrapper";

  export default {
    name: "VideoChat",
    props: ['msgArray'],
    components: {ContentWrapper},
    data() {
      return {
        img: null,
        roomId: "public-room"
      };
    },
    created() {
      window.io = io
      this.$eventBus.$on(command => {

      })
    },
    mounted() {
      this.$refs.webrtc.join();
    },
    methods: {
      msgArrayUpdate: function(newMsgArray){
        this.msgArray = newMsgArray
        this.$emit('msgArrayUpdate', newMsgArray)
      },
      onCapture() {
        this.img = this.$refs.webrtc.capture();
      },
      onJoin() {
        this.$refs.webrtc.join();
      },
      onLeave() {
        this.$refs.webrtc.leave();
      },
      onShareScreen() {
        this.img = this.$refs.webrtc.shareScreen();
      },
      onError(error, stream) {
        console.log('On Error Event', error, stream);
      },
      logEvent(event) {
        console.log('Event : ', event);
      },
    }
  }
</script>

<style scoped>

</style>
