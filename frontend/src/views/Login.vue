<template>
  <div>
    <div class="float-left d-inline-block">
      <img src="../assets/images/laptop.jpg" class="img-fluid float-left list-inline" alt="Responsive image">
    </div>
    <div class="d-inline-block" id="login-box">
      <img src="../assets/images/nineonesoft_logo.png" class="d-inline-block img-thumbnail">
      <form id="login-form">
        <input v-model="userid" id="userId" name="userId" type="text" class="form-control" placeholder="아이디를 입력해주세요">
        <input v-model="password" id="pwd" name="pwd" type="password" class="form-control" placeholder="비밀번호를 입력해주세요">
        <div class="form-check">
          <input class="form-check-input" type="checkbox" value="" id="id_save">
          <label class="form-check-label text-left" for="id_save">
            Remember me
          </label>
        </div>
        <div class="btn btn-outline-primary btn-block" @click="login">로그인</div>
        <div class="btn btn-outline-secondary btn-block" onclick="window.location.href='/'">회원가입</div>
      </form>
      <br>
      <p class="h6">소셜로그인</p>
      <hr>
      <div>
        <a href="/oauth2/authorization/github"><img class="social-logo" src="../assets/images/github_logo.png"></a>
        <a href="/oauth2/authorization/google"><img class="social-logo" src="../assets/images/google_logo.png"></a>
        <a href="/oauth2/authorization/naver"><img class="social-logo" src="../assets/images/naver_logo.png"></a>
        <a href="/oauth2/authorization/kakao"><img class="social-logo" src="../assets/images/kakao_logo.png"></a>
      </div>
    </div>
  </div>
</template>
<script>
import router from '../router'
import { AxiosInstance as axios } from 'axios'

export default {
  name: 'Login',
  data () {
    return {
      userid: '',
      password: ''
    }
  },
  methods: {
    login: function () {
      const form = new FormData()
      form.append('userid', this.userid)
      form.append('password', this.password)
      axios.post('http://localhost:9191/', form)
        .then(res => {
          console.log(res)
        }).catch(error => {
          console.log(error)
        })
    }
  },
  beforeCreate () {
    axios.get('http://localhost:9191/api/user/login')
      .then(res => {
        if (res.data) {
          router.replace('/about')
        }
      }).catch(error => {
        console.log(error)
      })
  }
}
</script>
<style scoped>
  @import "~bootstrap/dist/css/bootstrap.min.css";
  @import '~bootstrap-vue/dist/bootstrap-vue.min.css';
  @import "../assets/font/iconmonstr/css/iconmonstr-iconic-font.min.css";

  .social-logo{
    width: 5vw;
    height: 10vh;
    margin: 1vh;
  }
  .h6{
    color: darkgray;
  }

  .img-fluid {
    height: 100vh;
    width: 70vw;
  }

  #login-box{
    width: 28vw;
    padding: 1.5vw;
    margin-top: 10%;
  }

  #login-form{
    margin-top: 20%;
  }
</style>
