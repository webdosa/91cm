<template>
  <b-navbar toggleable="lg" type="light" variant="white">
    <!-- <b-nav-text>Navbar text</b-nav-text> -->
    <li class="list-unstyled" style="font-weight:bold;">{{ channelTitle }}</li>
    <a class="nuj verti-align ml-auto" @click="RSidebarOpen">
      <i class="im im-menu-dot-h"></i>
    </a>
  </b-navbar>
</template>
<script>
  export default {
    props: ['channelTitle'],
    name: 'ChannelHeader',
    data() {
      return {}
    },
    methods: {
      RSidebarOpen: function () {
        this.$store.state.isRActive = true
      }
    }
  }
</script>
<style lang="scss">

</style>
