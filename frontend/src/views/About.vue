<template>
  <div>
    <div><video autoplay playsinline class="videostyle"></video></div>
    <b-button variant="primary" @click="cameraOpen">open</b-button>
    <b-button variant="primary" @click="videoStart">start</b-button>
    
    <b-button variant="primary" @click="videoStop">stop</b-button>
  </div>
  
</template>

<script>
import Stomp from "webstomp-client";
  import SockJS from "sockjs-client";
  export default {
    name: 'About',
    created(){
      'use strict'
      this.$store.state.stompClient = Stomp.over(new SockJS('http://localhost:9191/endpoint/'))
      this.$store.state.stompClient.connect({}, () => {
        this.$store.state.stompClient.subscribe("/sub/test", async (e) => {
          console.log('created')
          let data = JSON.parse(e.body)
          if(data.type==='offer'){
            console.log('받음!!!')
            if(this.pc==null){
              await this.connectStart()
            }
            this.pc.setRemoteDescription(new RTCSessionDescription(data));
            console.log(data.sdp)
            console.log(this.localDescription.sdp)
            if(!this.answered && data.sdp!==this.localDescription.sdp){
              this.doAnswer()
            }
          }else if(data.type==='answer'  && this.started){
            console.log('받음!!!: answer')
            this.pc.setRemoteDescription(new RTCSessionDescription(data));
          }else if(data.type==='candidate'  && this.started){
            console.log('candidate?!?!')
          }
        })
      })
      // Video element where stream will be placed.
      this.$nextTick(() => {
        this.localVideo = document.querySelector('video')
        console.log(this.localVideo)
      })
      
    },
    components: {
   
    },
    computed:{
     
      
    },
    data() {
      return {
        // On this codelab, you will be streaming only video (video: true).
        mediaStreamConstraints: {
          video: {
            width: {
              min: 1280
            },
            height: {
              min: 720
            }
          }
        },
        localStream: null,
        localVideo: null,
        remoteVideo: null,
        pc: null,
        remoteStream: null,
        configuration: [
          {
            urls: 'stun.l.google.com:19302'
          },
          {
            urls: 'turn:numb.viagenie.ca',
            credential: '91cm',
            username: 'jny9708@nineonesoft.com'
          }
        ],
        started: false,
        answered: false,
        localDescription: null
      }
    },
    methods: {
      // Handles success by adding the MediaStream to the video element.
      gotLocalMediaStream(mediaStream) {
        this.localStream = mediaStream;
        this.localVideo.srcObject = mediaStream;
      },
      // Handles error by logging a message to the console with the error message.
      handleLocalMediaStreamError(error) {
        console.log('navigator.getUserMedia error: ', error);
      },
      cameraOpen(){
        // Local stream that will be reproduced on the video.
        // Initializes media stream.
        navigator.mediaDevices.getUserMedia(this.mediaStreamConstraints)
          .then(this.gotLocalMediaStream).catch(this.handleLocalMediaStreamError);
        this.connectStart()
        },
      videoStop(){
        
      //  let tracks = this.localVideo.srcObject.getTracks()
      //   tracks.forEach(function(track) {
      //     track.stop();
      //   })
      //   this.localVideo.srcObject = null
      // console.log('asdsad ')
      // console.log(this.localVideo)
      this.localVideo.pause()
      this.localVideo.srcObject=null
      console.log(this.localStream)
      },
      videoStart(){
        if(this.localVideo.paused){
          this.localVideo.srcObject = this.localStream
          this.localVideo.play()
        }
      },
      connectStart(){
        console.log('connectStart')
        this.createPeerConnection()
        this.doCall()
      },
      doCall(){
        this.pc.createOffer().then((offer)=>{
          this.setLocalAndSendMessage()
        }).catch((e)=>{
          this.handleCreateOfferError()
        })
      },
      doAnswer(){
         this.pc.createAnswer().then((sessionDescription)=>{
           console.log('doAnswer',sessionDescription)
           this.setLocalAndSendMessage(sessionDescription)
           this.answered = true
         })
      },
      handleCreateOfferError(e){
        console.log('handleCreateOfferError',e)
      },
      setLocalAndSendMessage(sessionDescription) {
        this.pc.setLocalDescription(sessionDescription)
        console.log('setLocalAndSendMessage',sessionDescription)
        this.localDescription = sessionDescription
        this.send(sessionDescription)
      },
      createPeerConnection(){
        try{
          console.log('createPeerConnection')
        this.pc = new RTCPeerConnection(this.configuration)
        this.pc.onicecandidate = this.handleIceCandidate
        this.pc.ontrack = this.handleRemoteStreamAdded
        this.pc.onremovestream = this.handleRemoteStreamRemoved
        this.started = true;
        }
        catch(e){
          console.log('createPeerConnection error',e)
        }
      },
      send(message){
       this.$store.state.stompClient.send("/sub/test", JSON.stringify(message), {}) 
      },
      handleIceCandidate(evt){
        console.log('handleIceCandidate')
        console.log(evt)
        if(evt.candidate){
            this.send({'candidate': evt.candidate,'type':'candidate'})
        }
        else{
          console.log('handleIceCandidate : evt.candidate nothing')
        }
      },
      handleRemoteStreamAdded(evt){
        console.log('handleRemoteStreamAdded')  
        this.remoteStream = evt.stream
        console.log(evt)  
        this.remoteVideo.srcObject = this.remoteStream
      },
      handleRemoteStreamRemoved(evt){
        console.log('handleRemoteStreamRemoved',evt)
      }

    }

  }

</script>
<style scoped>
 .videostyle{
   background-color: black;
 }
 
.btn::after {
  content: none;
}
</style>
