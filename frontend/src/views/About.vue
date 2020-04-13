<template>
  <div>
    <MainHeader></MainHeader>
    <b-button @click="test">Opensource</b-button>
  </div>
</template>
<script>
  import MainHeader from './main/MainHeader'
  import CopyRight from './util/CopyRight'
  import LSidebar from './main/LSidebar'
  export default {
    name: 'About',
    data() {
      return {
      }
    },
    methods: {
      test: function (){
         this.$bvModal.show('channelCU');
      }
    }

  }

</script>
<style scoped>
  #esc{
    color: #7f7f7f;
  }
  #esc:hover{
    color: black;
  }
</style>
