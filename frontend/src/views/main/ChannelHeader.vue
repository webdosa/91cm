<template>
  <b-navbar toggleable="lg" type="light" variant="white">
    <!-- <b-nav-text>Navbar text</b-nav-text> -->
    <li class="list-unstyled" style="font-weight:bold;">{{ channelTitle }}</li>
    <div class="ml-auto" style="display: flex;">
    <b-nav-item-dropdown no-caret right toggle-class="nonoutline" class="verti-align">
      <template v-slot:button-content>
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
          <path
            d="M23.809 21.646l-6.205-6.205c1.167-1.605 1.857-3.579 1.857-5.711 0-5.365-4.365-9.73-9.731-9.73-5.365 0-9.73 4.365-9.73 9.73 0 5.366 4.365 9.73 9.73 9.73 2.034 0 3.923-.627 5.487-1.698l6.238 6.238 2.354-2.354zm-20.955-11.916c0-3.792 3.085-6.877 6.877-6.877s6.877 3.085 6.877 6.877-3.085 6.877-6.877 6.877c-3.793 0-6.877-3.085-6.877-6.877z"
          ></path>
        </svg>
      </template>
      <b-dropdown-text>
        <b-form inline style="position: relative;">
          <svg style="position:absolute; left:10px;" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
            <path
              d="M23.809 21.646l-6.205-6.205c1.167-1.605 1.857-3.579 1.857-5.711 0-5.365-4.365-9.73-9.731-9.73-5.365 0-9.73 4.365-9.73 9.73 0 5.366 4.365 9.73 9.73 9.73 2.034 0 3.923-.627 5.487-1.698l6.238 6.238 2.354-2.354zm-20.955-11.916c0-3.792 3.085-6.877 6.877-6.877s6.877 3.085 6.877 6.877-3.085 6.877-6.877 6.877c-3.793 0-6.877-3.085-6.877-6.877z"
            ></path>
            </svg>
          <b-input  placeholder="Search..." class="searchinput mb-2 mr-sm-2 mb-sm-0"></b-input>
        </b-form>
      </b-dropdown-text>
    </b-nav-item-dropdown>
    <a class="nuj verti-align" @click="RSidebarOpen">
      <i class="im im-menu-dot-h"></i>
    </a>
    </div>
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
