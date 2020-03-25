<template>
  <main class="mainwrapper">
    <div class="h-inherit cetered-align" style="flex-direction:column;">
      <div class="info-wrapper">
        <h4 class="center">회원정보</h4>
        <br>
        <div class="hori-align" style="margin: 24px 0px 35px 0;">
          <img v-if="$store.state.currentUser.picture" class="icon-round" :src="$store.state.currentUser.picture" width="200" height="200">
          <img v-else class="icon-round" src="../../assets/images/default-user-picture.png" width="200" height="200">
        </div>
        <table>
          <tbody>
          <tr>
            <th>
              <p>이름</p>
            </th>
            <td>
              <span>{{$store.state.currentUser.name}}</span>
            </td>
          </tr>
          <tr>
            <th>
              <p>이메일</p>
            </th>
            <td>
              <span>{{$store.state.currentUser.email}}</span>
            </td>
          </tr>
          <tr>
            <th>
              <p>전화번호</p>
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
  </main>
</template>
<script>
  export default {
    name: 'UserInfo',
    created() {
      console.log(this.$store.state.currentUser)
    },
    methods: {
      callComponent: function () {
        this.$store.commit('getSelectComponent','edit')
      }
    }
  }
</script>
