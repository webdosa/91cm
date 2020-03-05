<template>
  <div class="wrapper">
    <!-- Sidebar  -->
    <LSidebar></LSidebar>
    <!-- Page Content  -->
    <div id="m-wrapper" v-bind:class="{active: $store.state.isLActive}">
      <MainHeader></MainHeader>
      <ContentWrapper></ContentWrapper>
    </div>
    <RSidebar></RSidebar>
  </div>
</template>
<script>
  import LSidebar from '../views/main/LSidebar'
  import RSidebar from '../views/main/RSidebar'
  import MainHeader from '../views/main/MainHeader'
  import ContentWrapper from '../views/main/ContentWrapper'

  import axios from 'axios'

  export default {
    name: 'Main',
    components: {MainHeader, LSidebar,RSidebar,ContentWrapper},
    data() {
      return {
        //isLActive: false,
        isRActive: false,
        //channelTitle: '',
        //nameState: null
      }
    },
    methods: {
      checkFormValidity: function () {
        const valid = this.$refs.channelCreateForm.checkValidity()
        this.nameState = valid
        return valid
      },
      // resetModal() {
      //   this.channelTitle = ''
      //   this.nameState = null
      // },
      // handleOk(bvModalEvt) {
      //   // Prevent modal from closing
      //   bvModalEvt.preventDefault()
      //   // Trigger submit handler
      //   this.channelForm()
      // },
      // channelForm: function () {
      //   if (!this.checkFormValidity()) {
      //     return
      //   }
      //   this.$refs['modal'].hide()


      //   this.$nextTick(() => {
      //     this.$bvModal.hide('channel-create')
      //   })
      //   axios.get('http://localhost:9191/api/user')

      // },
      
    }
  }
</script>
