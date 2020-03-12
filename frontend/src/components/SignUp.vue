<template>
<div>
    <span>email</span>
    <b-input type="text" name="name" v-model="user.name"></b-input>
    <b-input type="text" name="phone" v-model="user.phone"></b-input>
     <b-button style="margin:15px;" variant="primary" @click="insertUser">등록</b-button>
</div>
</template>
<script>
import axios from 'axios'
export default {
    name:'SignUp',
    data() {
        return {
            user:{
                name:'',
                phone:'',
                email:'',
                picture:''
            }
        }
    },
    created () {
      this.getUser()  
    },
    methods: {
        getUser () {
            axios.get('http://localhost:9191/api/user/getsession').then(res=>{
                this.user.name = res.data.name
                this.user.phone = res.data.phone
                this.user.email = res.data.email
                this.user.picture = res.data.picture
            })
        },
        insertUser(){
            let csrfToken = document.cookie.match('(^|;) ?' + 'XSRF-TOKEN' + '=([^;]*)(;|$)')
            //axios.defaults.headers.common['X-CSRF-TOKEN'] = csrfToken[2];
            axios.post('http://localhost:9191/api/user/signup',JSON.stringify(this.user),{headers: { 'X-CSRF-TOKEN': csrfToken[2],'Content-Type': 'application/json'}}).then(res => {
                if(res.data){
                    this.$router.replace('main')    
                }else{
                    alert('회원가입 실패')
                }
            })
        }

    }
}
</script>