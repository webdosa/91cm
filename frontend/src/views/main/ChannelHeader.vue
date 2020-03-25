<template>
  <b-navbar toggleable="lg" type="light" variant="white">
    <!-- <b-nav-text>Navbar text</b-nav-text> -->
    <li class="list-unstyled" style="font-weight:bold;">{{ channelTitle }}</li>
    <a
      class="nuj verti-align ml-auto"
      @click="RSidebarOpen"
    >
      <svg style="margin-right: 15px;" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M6 12c0 1.657-1.343 3-3 3s-3-1.343-3-3 1.343-3 3-3 3 1.343 3 3zm9 0c0 1.657-1.343 3-3 3s-3-1.343-3-3 1.343-3 3-3 3 1.343 3 3zm9 0c0 1.657-1.343 3-3 3s-3-1.343-3-3 1.343-3 3-3 3 1.343 3 3z"/></svg>
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
