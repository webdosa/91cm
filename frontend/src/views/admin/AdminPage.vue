<template>
  <main class="mainwrapper" style="height: calc(100vh - 60px);">
    <div style="height: inherit; padding: 15px;">
      <div
        style="height: calc(100vh - 90px); padding: 15px; display: flex; flex-direction: column; justify-content: center;"
      >
        <v-tabs
          v-model="tab"
          background-color="cyan lighten-2"
          class="elevation-2"
          :centered="true"
          :grow="true"
        >
          <v-tab
            v-for="i in tabs"
            :key="i"
            :href="`#tab-${i}`"
          >
            {{i}}
          </v-tab>

          <v-tab-item
            :value="'tab-' + tabs[0]"
          >
            <AuthorityPage></AuthorityPage>
          </v-tab-item>
        </v-tabs>
      </div>
    </div>
  </main>
</template>
<script>
  import AuthorityPage from "./AuthorityPage";
  export default {
    name: 'AdminPage',
    components: {AuthorityPage},
    data() {
      return {
        tab: null,
        tabs: ['권한 설정','전체메시지','개발중'],
        // authUserList: [],
        // authorityList: ["ROLE_USER", "ROLE_ADMIN", "ROLE_ANON"],
        // editedIndex: -1,
        // editedItem: {
        //   number: 0,
        //   name: "",
        //   email: "",
        //   authority: 0,
        //   protein: 0
        // },
        // dialog: false,
        isLActive: false,
        isRActive: false,
        // headers: [
        //   {
        //     text: "번호",
        //     align: "start",
        //     sortable: false,
        //     value: "number"
        //   },
        //   {text: "이름", value: 'name'},
        //   {text: "이메일", value: 'email', sortable: false},
        //   {text: "권한", value: 'authority'},
        //   {text: "Actions", value: 'actions', sortable: false},
        // ],
      }
    },
    computed: {},
    beforeCreate() {
      // this.$http.post('/api/user/admin/userList')
      //   .then(res => {
      //     this.authUserList = res.data
      //     this.authUserList.forEach((user, index) => {
      //       user.number = index + 1;
      //     });
      //   })

    },
    methods: {
      // close() {
      //   this.dialog = false;
      // },
      // save() {
      //   this.$http.post('/api/user/admin/auth', {
      //       member_email: this.editedItem.email,
      //       roles_authority: this.editedItem.authority
      //     }
      //   )
      //     .then(res => {
      //       this.authUserList[this.editedIndex].authority = this.editedItem.authority
      //       this.dialog = false
      //       this.$alertModal("alert", "권한이 수정되었습니다.")
      //     }).catch(error => {
      //     this.$alertModal("error", "권한 수정에 실패했습니다." + "\n" + error)
      //     this.dialog = false
      //   })
      // },
      // editItem(item) {
      //   console.log(item)
      //   this.editedIndex = this.authUserList.indexOf(item);
      //   this.editedItem = Object.assign({}, item);
      //   this.dialog = true;
      // },
    }

  }

</script>
<style scoped>
  .moHeight {
    height: inherit;
    overflow: auto;
  }
</style>
