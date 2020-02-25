<template>
  <div class='about'>
    <h1>This is an about page {{info}}</h1>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name : 'About',
  data () {
    return {
      info: null
    }
  },
  mounted() {
    axios.get('http://localhost:9191/api/test')
      .then(res => {
        this.info = res.data
      }).catch(error => {
        console.log(error)
    })
  }
}
</script>
