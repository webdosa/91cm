<template>
<div>
    <span>email</span>
    <b-input type="text" name="name" v-model="user.name"></b-input>
    <b-input type="text" name="phone" v-model="user.phone"></b-input>
     <b-button style="margin:15px;" variant="primary">수정</b-button>
</div>
</template>
<script>
import axios from 'axios'
export default {
    name:'SignUp',
    data() {
        return {
            user:{}
        }
    },
    created () {
      this.getUser()  
    },
    methods: {
        getUser () {
            axios.get('http://localhost:9191/api/user/getsession').then(res=>{
                console.log(res.data)
                this.user = res.data
                console.log(this.user)
            })
        },
        insertUser(){
            axios.post().then(res => {

            })
        }

    }
}
</script>