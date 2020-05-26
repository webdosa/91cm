<template>
  <div >
    <router-view/>
  </div>
  
</template>

<script>

</script>


<style lang="scss">
#app {
  // font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

#app{
  height:100vh;
  background-color:#fafafa;
}
// #app a{
//   color:black;
// }

#app ul{
  padding-left:0;
  list-style: none;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
