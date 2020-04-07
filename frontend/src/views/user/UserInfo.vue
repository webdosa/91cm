<template>
  <main class="mainwrapper" style="height: calc(100vh - 60px);">
    <div class="h-inherit" style="padding: 60px;">
      <div class="info-w verti-align">
        <h4 style="padding-top: 40px;">회원정보</h4>
      <div class="info-wrapper cetered-align">
        <div style="margin: 0px 0px 35px 0;">
          <img v-if="$store.state.currentUser.picture" class="icon-round" :src="$store.state.currentUser.picture" width="200" height="200">
          <img v-else class="icon-round" src="../../assets/images/default-user-picture.png" width="200" height="200">
        </div>
        <table style="margin-top: 50px;">
          <tbody>
          <tr>
            <th>
              <label for="name">이름</label>
            </th>
            <td>
              <span>{{$store.state.currentUser.name}}</span>
            </td>
          </tr>
          <tr>
            <th>
              <label for="email">이메일</label>
            </th>
            <td>
              <span>{{$store.state.currentUser.email}}</span>
            </td>
          </tr>
          <tr>
            <th>
              <label for="phone">전화번호</label>
            </th>
            <td>
              <span>{{$store.state.currentUser.phone}}</span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <b-button style="margin:15px;" variant="primary" @click="callComponent">수정</b-button>
    </div>
    </div>
  </main>
</template>
<script>
  export default {
    name: 'UserInfo',
    created() {
      console.log(this.$store.state.currentUser)
    },
    deactivated(){
      console.log('deactiveed user')
    },
    methods: {
      callComponent: function () {
        this.$store.commit('getSelectComponent','edit')
      }
    }
  }
</script>
