<template>
  <main class="mainwrapper">
    <div class="h-inherit cetered-align" style="flex-direction:column;">
      <div class="info-wrapper">
        <h4 class="center">회원정보 수정</h4>
        <br>
        <div class="hori-align" style="margin: 24px 0px 35px 0;">
          <b-button v-if="$store.state.currentUser.picture" variant="light" v-b-modal.modal-prevent-closing>
            <img class="icon-round" :src="$store.state.currentUser.picture" width="200" height="200">
          </b-button>
          <b-button v-else variant="light" v-b-modal.modal-prevent-closing>
            <img class="icon-round" src="../../assets/images/default-user-picture.png" width="200" height="200">
          </b-button>
        </div>
        <table>
          <tbody>
          <tr>
            <th>
              <label>이름</label>
            </th>
            <td>
              <b-input type="text" name="name" v-model="user.name"></b-input>
            </td>
          </tr>
          <tr>
            <th>
              <label>이메일</label>
            </th>
            <td>
              <b-input type="email" name="email" disabled="true" v-model="$store.state.currentUser.email"></b-input>
            </td>
          </tr>
          <tr>
            <th>
              <label>전화번호</label>
            </th>
            <td>
              <b-input type="text" @keyup="phoneFormatter" name="phone" v-model="user.phone"></b-input>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <b-button style="margin:15px;" variant="primary" @click="edit">수정</b-button>
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
        this.$http.post('/api/user/update', this.user)
          .then(res => {
            console.log(this.user)
            if (res.data) {
              console.log(res.data)
              this.$store.dispatch('initCurrentUser')
              this.$store.commit('getSelectComponent', 'user')
            }
          })

      },
      phoneFormatter: function () {
        this.user.phone = this.user.phone.replace(/[^0-9]/g, "") // 숫자만 추출 되도록하는 정규식
        this.user.phone = this.user.phone.replace(/(^02.{0}|^01.{1}|[0-9]{4})([0-9]+)([0-9]{4})/, "$1-$2-$3");// 휴대폰번호 자동 하이픈 넣어주는 정규식
      },
    }
  }
</script>
