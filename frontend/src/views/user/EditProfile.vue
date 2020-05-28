<template>
  <main class="mainwrapper" style="height: calc(100vh - 150px);overflow: auto;">
    <div class="container-fluid">
            <div class="page-header">
                <div class="row align-items-end">
                    <div class="col-lg-8">
                        <div class="page-header-title">
                            <i class="ik ik-file-text bg-blue"></i>
                            <div class="d-inline">
                                <h5>Profile</h5>
                                <span>개인 프로필 수정</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row no-gutters">
                <div class="col-lg-2 col-md-3" >

                </div>
                <div class="col-lg-8 col-md-7">
                    <div class="card">
                        <div class="info-w verti-align">
                            
                                <div class="info-wrapper cetered-align card-body">
                                    <div style="margin: 20px 0px 35px;">
                                        <img v-if="$store.state.currentUser.picture" class="icon-round" :src="$store.state.currentUser.picture" width="200" height="200">
                                        <img v-else class="icon-round" src="../../assets/images/default-user-picture.png" width="200" height="200">
                                    </div>
                                    <table>
                                        <tbody>
                                            <tr>
                                                <th>
                                                    <label for="name">이름</label>
                                                </th>
                                                <td>
                                                    <b-input type="text" @keyup="symbolsFormatter" name="name" v-model="user.name"></b-input>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>
                                                    <label for="email">이메일</label>
                                                </th>
                                                <td>
                                                    <b-input type="email" name="email" disabled="true" v-model="user.email"></b-input>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>
                                                    <label for="phone">전화번호</label>
                                                </th>
                                                <td>
                                                    <b-input type="text" @keyup="phoneFormatter" name="phone" v-model="user.phone"></b-input>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                     <b-button style="margin:15px;" variant="primary" @click="edit">수정</b-button>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
        </div>
        <b-modal
      id="modal-prevent-closing"
      ref="modal"
      title="프로필 사진"
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
    >
      <form ref="form" @submit.stop.prevent="">
        <b-form-group
          :state="nameState"
          label="이미지 url"
          label-for="name-input"
          invalid-feedback="url 주소가 필요합니다."
        >
          <b-form-input
            id="name-input"
            v-model="imageUrl"
            :state="nameState"
            required
          ></b-form-input>
        </b-form-group>
      </form>
    </b-modal>
  </main>
</template>
<script>
  // 이름 길이 제한 및 핸드폰 번호 정규식 추가 예정
  export default {
    name: 'EditUser',
    data() {
      return {
        imageUrl: '',
        nameState: null,
        user: Object.assign({}, this.$store.state.currentUser)
      }
    },
    methods: {
      handleOk: function (bvModalEvt) {
        bvModalEvt.preventDefault()
        this.handleSubmit()
      },
      handleSubmit: function () {
        if (!this.checkFormValidity) {
          // 추후 url 주소 정규식 검사 추가
          return
        }
        this.user.picture = this.imageUrl
        this.$nextTick(() => {
          this.$bvModal.hide('modal-prevent-closing')
        })
      },
      checkFormValidity: function () {
        const valid = this.$refs.form.checkValidity()
        this.nameState = valid
        return valid
      },
      resetModal: function () {
        this.imageUrl = ''
      },
      edit: function () {
        if (!this.valueCheck(this.user.email, this.user.name, this.user.phone)){
          return;
        }
        this.$http.post('/api/user/update', this.user)
          .then(res => {
            if (res.data) {
              this.$store.state.stompClient.send('/pub/sync/info',null,{})
              this.$store.dispatch('initCurrentUser')
              this.$store.commit('getSelectComponent', 'user')
            }
          })

      },
      phoneFormatter: function () {
        this.user.phone = this.user.phone.replace(/[^0-9]/g, "") // 숫자만 추출 되도록하는 정규식
        this.user.phone = this.user.phone.replace(/(^02.{0}|^01.{1}|[0-9]{4})([0-9]+)([0-9]{4})/, "$1-$2-$3");// 휴대폰번호 자동 하이픈 넣어주는 정규식
      },
      symbolsFormatter: function(){
        this.user.name = this.user.name.replace(/[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi, "")
      },

      valueCheck: function (email, name, phone) {
        const phoneRegex = '^01(?:0|1|[6-9])[-]?(\\d{3}|\\d{4})[-]?(\\d{4})$'
        // const symbolsRegex = '/[~!@#$%^&*()_+|<>?:{}]/'
        // if (!name.match(symbolsRegex)){
        //   this.$alertModal('error','이름에는 특수기호가 들어갈 수 없습니다')
        //   return false
        // }
        if (email == null || email == '') {
          this.$alertModal('error', '이메일을 입력해주세요')
          return false
        }
        if (name == null || name == '') {
          this.$alertModal('error', '이름을 입력해주세요')
          return false
        }
        if (name.length > 20){
          this.$alertModal('error', '이름이 너무 깁니다.')
          return false
        }
        if (phone == null || phone == '') {
          this.$alertModal('error', '핸드폰 번호를 입력해주세요')
          return false
        }
        if (!phone.match(phoneRegex)){
          this.$alertModal('error','핸드폰 번호가 형식에 맞지 않습니다')
          return false
        }
        return true
      }
    }
  }
</script>
