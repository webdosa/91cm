<template>
  <div class='about'>
    <h1>This is an about page</h1>
    <button @submit="testbtn">test</button>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  mounted () {
    axios.get('http://localhost:9191/api/test')
      .then(res => {
        alert(JSON.stringify(res.data))
      })
  }
}
</script>
