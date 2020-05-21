<template>
  <v-container>
    <v-text-field @keydown.enter.exact="send" label="전체 공지사항" :rules="rules" hide-details="auto" v-model="message"></v-text-field>
  </v-container>
</template>

<script>
  export default {
    name: "NoticeMessage",
    data(){
      return{
        message: "",
        rules: [
          value => !!value || '값이 없습니다.'
        ]
      }
    },
    methods:{
      send: function () {
        console.log(this.message)
      }
    }
  }
</script>

<style scoped>

</style>
