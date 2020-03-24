export default {
  install(Vue, options) {
    Vue.prototype.$alertModal = function (type, content) {
      let title = type.split(' ')[0]
      const option = type.split(' ')[1]
      if (type == 'error') {
        title = '에러'
      } else {
        title = '알림'
      }
      this.$bvModal.msgBoxOk(content, {
        okTitle: '확인',
        buttonSize: 'sm',
        title: title,
        headerBgVariant: 'info',
        headerTextVariant: 'light',
      })
        .then(value => {
          switch (option) {
            case 'redirect':
              this.$router.go('/main')
              return value
            default:
              return value

          }
        })
    }
  }
}
