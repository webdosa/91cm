<template>
<header>
        <b-navbar toggleable="lg" type="light" variant="white">
<!--          <button  > </button>-->
<!--            <svg-->
<!--              style="fill:white;"-->
<!--              xmlns="http://www.w3.org/2000/svg"-->
<!--              width="24"-->
<!--              height="24"-->
<!--              viewBox="0 0 24 24"-->
<!--            >-->
<!--              <path d="M24 6h-24v-4h24v4zm0 4h-24v4h24v-4zm0 8h-24v4h24v-4z"></path>-->
<!--            </svg>-->
<!--            <span>&nbsp;Toggle Sidebar</span>-->
            <i v-if="$store.state.isLActive" class="im im-angle-right-circle btn btn-info" @click="LSidebarToggle" ></i>
            <i v-else class="im im-angle-left-circle btn btn-info" @click="LSidebarToggle" ></i>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item-dropdown no-caret right toggle-class="nonoutline" class="verti-align">
              <template v-slot:button-content>
                <i class="im im-bell"></i>
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
<!--                이미지 가지고 오는 것 느림-->
                <img v-if="$store.state.currentUser.picture" class="icon-round" :src="$store.state.currentUser.picture" width="40" height="40">
                <img v-else class="icon-round" src="../../assets/images/default-user-picture.png" width="40" height="40">
              </template>
              <b-dropdown-item @click="callComponent">Profile</b-dropdown-item>
              <b-dropdown-item @click="SignOut">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-navbar>
      </header>
</template>

<script>
export default {
  name: 'MainHeader',
  data() {
    return {
    }
  },
  methods: {
    callComponent: function(){
      this.$store.commit('getSelectComponent','user')
    },
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
}
</script>
