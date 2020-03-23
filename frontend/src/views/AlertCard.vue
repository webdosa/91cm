<template>
  <div>
    <b-modal id="alertModal" size="sm" :title="alertMessage.title" @show="tst">
      <p>{{alertMessage.content}}</p>
    </b-modal>
  </div>
</template>

<script>
  export default {
    name: "AlertCard",
    data() {
      return {
        alertMessage: {
          title: '',
          content: ''
        }
      }
    },
    methods:{
      tst : function(){
        this.alertMessage.title = this.alertModalTitle
        this.alertMessage.content = this.alertModalContent
      },
    }
  }
</script>

<style scoped>

</style>
