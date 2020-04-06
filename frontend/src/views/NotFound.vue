<template>
  <div id="notfound">
    <div class="notfound">
      <div class="notfound-404">
        <h3>Page not found</h3>
        <h1><span>4</span><span>0</span><span>4</span></h1>
      </div>
      <h2>해당 페이지를 찾을 수 없습니다.</h2>
      <a href="/" class="btn btn-white btn-animation-1">GO HOME</a>
    </div>
  </div>
</template>
<script>
export default {
  name:'NotFound',
  props: ['type'],
  mounted () {
    console.log(this.type)
  }

}
</script>

<style scoped>
  @import "../assets/css/error.css";
</style>
