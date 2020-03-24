<template>
<header>
        <b-navbar toggleable="lg" type="light" variant="white">
          <button type="button" @click="LSidebarToggle" id="sidebarCollapse" class="btn btn-info">
            <svg
              style="fill:white;"
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
            >
              <path d="M24 6h-24v-4h24v4zm0 4h-24v4h24v-4zm0 8h-24v4h24v-4z"></path>
            </svg>
            <span>&nbsp;Toggle Sidebar</span>
          </button>
          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item-dropdown no-caret right toggle-class="nonoutline" class="verti-align">
              <template v-slot:button-content>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                  <path
                    d="M15 21c0 1.598-1.392 3-2.971 3s-3.029-1.402-3.029-3h6zm.137-17.055c-.644-.374-1.042-1.07-1.041-1.82v-.003c.001-1.172-.938-2.122-2.096-2.122s-2.097.95-2.097 2.122v.003c.001.751-.396 1.446-1.041 1.82-4.668 2.709-1.985 11.715-6.862 13.306v1.749h20v-1.749c-4.877-1.591-2.193-10.598-6.863-13.306zm-3.137-2.945c.552 0 1 .449 1 1 0 .552-.448 1-1 1s-1-.448-1-1c0-.551.448-1 1-1zm-6.451 16c1.189-1.667 1.605-3.891 1.964-5.815.447-2.39.869-4.648 2.354-5.509 1.38-.801 2.956-.76 4.267 0 1.485.861 1.907 3.119 2.354 5.509.359 1.924.775 4.148 1.964 5.815h-12.903z"
                  ></path>
                </svg>
              </template>
              <b-dropdown-item href="#">EN</b-dropdown-item>
              <b-dropdown-item href="#">ES</b-dropdown-item>
              <b-dropdown-item href="#">RU</b-dropdown-item>
              <b-dropdown-item href="#">FA</b-dropdown-item>
            </b-nav-item-dropdown>
            <div class="verti-align useridsty">{{ $store.state.currentUser.name }}</div>
            <b-nav-item-dropdown no-caret right toggle-class="nonoutline">
              <!-- Using 'button-content' slot -->
              <template v-slot:button-content style="padding:0px;">
                //이미지 가지고 오는 것 느림
                <img v-if="$store.state.currentUser.picture" class="icon-round" :src="$store.state.currentUser.picture" width="40" height="40">
                <img v-else class="icon-round" src="../../assets/images/default-user-picture.png" width="40" height="40">
              </template>
              <b-dropdown-item href="#">Profile</b-dropdown-item>
              <b-dropdown-item @click="SignOut">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-navbar>
      </header>
</template>

<script>

import { AxiosInstance as axios } from 'axios'

export default {
  name: 'MainHeader',
  data() {
    return {
      userInfo: null
    }
  },
  methods: {
    LSidebarToggle: function () {
      this.$store.state.isLActive = !this.$store.state.isLActive
    },
    RSidebarOpen: function () {
      this.$store.state.isRActive = true
    },
    SignOut() {
      window.location.href="/logout"
    }
  },
  created() {
    // 리팩토링 해
    this.userInfo = this.$http.get('http://localhost:9191/api/user/info')
      .then(res => {
        this.userInfo = res.data
      })
      .catch(error => {
        console.log(error)
      })
  }
}
</script>
