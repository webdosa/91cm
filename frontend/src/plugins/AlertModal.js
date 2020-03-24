export default {
  install(Vue, options) {
    // 단순 알림용
    Vue.msgModalOk = function (instance, title, content, option) {
      instance.$bvModal.msgBoxOk(content, {
        okTitle: '확인',
        buttonSize: 'sm',
        title: title,
        headerBgVariant: 'info',
        headerTextVariant: 'light',
      })
        .then(value => {
          switch (option) {
            case 'redirect':
              instance.$router.go('/main')
              return value
            default:
              return value
          }
        })
    }
    //YES OR NO


    Vue.prototype.$alertModal = async function (types, content) {
      const msgModalConfirm = async function (instance, title, content, option) {
        await instance.$bvModal.msgBoxConfirm(content, {
          title: title,
          okTitle: '확인',
          okVariant: 'danger',
          buttonSize: 'sm',
          cancelTitle: '취소'
        })
          .then(value => {
            console.log(value)
            // 취소 선택시 바로 리턴
            if (!value) {
              return value
            }
            switch (option) {
              case 'redirect':
                // instance.$router.go('/main')
                instance.$set('test',value)
                return value
              default:
                return value
            }
          })
      }
      let type = types.split(' ')[0]
      const option = types.split(' ')[1]
      let title = ''
      if (type == 'error') {
        title = '에러'
        return Vue.msgModalOk(this, title, content, option)
      } else if (type == 'select') {
        title = '확인'
        const test = await msgModalConfirm(this, title, content, option)
        console.log(this.test)
        return test
      } else {
        title = '알림'
        return Vue.msgModalOk(this, title, content, option)
      }
    }

  }
}
