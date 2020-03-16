<template>
  <div class="bg">
    <div class="container">
      <div class="row">

        <div class="col-md-12 min-vh-100 d-flex flex-column justify-content-center">
          <div class="row">
            <div class="col-lg-6 col-md-8 mx-auto">
              <div class="card rounded shadow shadow-sm mainbox">
                <div class="card-header">
                  <h5>91cm를 사용하기 위해 추가적으로 필요한 정보를 입력해주세요</h5>
                </div>
                <div class="card-body">
                  <label>가입할 이메일을 입력해주세요</label>
                  <b-input type="text" name="email" v-model="user.email" placeholder="이메일"></b-input>
                  <label>91cm에서 사용할 이름을 입력해주세요</label>
                  <b-input type="text" name="name" v-model="user.name" placeholder="이름"></b-input>
                  <label>업무에 사용하는 핸드폰 번호를 입력해주세요</label>
                  <b-input type="text" name="phone" v-model="user.phone" placeholder="핸드폰 번호"
                           @keydown="phoneFormatter"></b-input>
                  <b-button class="float-right" style="margin:15px;" variant="primary" @click="insertUser">등록</b-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import axios from 'axios'

  export default {
    name: 'SignUp',
    data() {
      return {
        user: {
          name: '',
          phone: '',
          email: '',
          picture: ''
        }
      }
    },
    created() {
      this.getUser()
    },
    methods: {
      phoneFormatter: function () {
        if (this.user.phone.length >= 10)
          this.user.phone = this.user.phone.replace(/(^02.{0}|^01.{1}|[0-9]{4})([0-9]+)([0-9]{4})/, "$1-$2-$3");
      },
      getUser() {
        axios.get('http://localhost:9191/api/user/getsession').then(res => {
          this.user.name = res.data.name
          this.user.phone = res.data.phone
          this.user.email = res.data.email
          this.user.picture = res.data.picture
        })
      },
      insertUser() {
        let csrfToken = document.cookie.match('(^|;) ?' + 'XSRF-TOKEN' + '=([^;]*)(;|$)')
        axios.post('http://localhost:9191/api/user/signup', JSON.stringify(this.user), {
          headers: {
            'X-CSRF-TOKEN': csrfToken[2],
            'Content-Type': 'application/json'
          }
        }).then(res => {
          if (res.data) {
            this.$router.replace('main')
          } else {
            alert('회원가입 실패')
          }
        })
      }

    }
  }
</script>
<style>
  .bg {
    background-image: url("../assets/images/laptop.jpg");
  }
</style>
