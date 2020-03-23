import AlertCard from "../views/AlertCard";

export default {
  install(Vue, options) {
    Vue.alertModalContent = ''
    Vue.alertModalTitle = ''
    Vue.use(AlertCard)

    Vue.prototype.$alertModal = function (id, type, content) {
      if (type == 'error') {
        Vue.alertModalTitle = '에러'
        Vue.alertModalContent = content
      } else {
        Vue.alertModalTitle = '알림'
        Vue.alertModalContent = content
      }
      console.log(this.alertModalTitle)
      this.$bvModal.show(id)
    }

  }
}
