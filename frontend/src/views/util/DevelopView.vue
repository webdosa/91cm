<template>
  <div style="text-align: center">
    <br>
    <p style="text-align: center" class="h4 animate__animated animate__fadeIn animate__delay-1s">개발자가 카페인을 분해하여 코드로 배출중입니다. 개발을 기다려주세요.</p>
    <b-img class="animate__animated animate__backInLeft" src="https://image.freepik.com/free-vector/programmers-concept-with-flat-design_23-2147862433.jpg"
           fluid center></b-img>
    <b-button size="lg" @mouseover="addAnimation">GO MAIN PAGE</b-button>
  </div>
</template>

<script>
  export default {
    name: "DevelopView",
    methods:{
      addAnimation:function (e) {
        console.log(e.target.)
        // e.target.toggle('animate__animated')
        // e.target.toggle('animate__bounceInDown')
      }
    }
  }
</script>

<style scoped>

</style>
