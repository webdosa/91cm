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
    axios.get('http://localhost:9191/api')
      .then(res => {
        console.log(res)
        console.log(res.status)
        console.log(JSON.stringify(res.data))
        console.log(res.data)
        console.log(res.data.toString())
        console.log(res.config)
        console.log(res.headers)
        console.log(res.statusText)
        console.log(res.data.value())
      })
  }
}
</script>
