<template>
  <main class="mainwrapper" style="height: calc(100vh - 60px);">
    <div style="height: inherit; padding: 15px;">
      <div
        style="height: calc(100vh - 90px); padding: 15px; display: flex; flex-direction: column; justify-content: center;"
      >
        <v-card v-bind:class="{moHeight:  $store.state.isSmallWidth }">
          <v-card-title>사용자 리스트</v-card-title>
          <v-data-table
            :headers="headers"
            :items="authUserList"
            :items-per-page="5"
            :calculate-width="true"
            class="elevation-1"
          >
            <template v-slot:item.actions="{ item }">
              <svg
                @click="editItem(item)"
                style="cursor: pointer;"
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
              >
                <path
                  d="M7.127 22.564l-7.126 1.436 1.438-7.125 5.688 5.689zm-4.274-7.104l5.688 5.689 15.46-15.46-5.689-5.689-15.459 15.46z"
                ></path>
              </svg>
            </template>
          </v-data-table>
        </v-card>
      </div>
      <v-dialog v-model="dialog" max-width="500px">
        <v-card>
          <v-card-title>
            <span class="headline">회원 정보 수정</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field :disabled="true" v-model="editedIndex" label="Dessert name"></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.name" label="Calories"></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field :disabled="true" v-model="editedItem.email" label="Fat (g)"></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-autocomplete
                    v-model="editedItem.authority"
                    :items="authorityList"
                    label="Interests"
                    multiple
                  ></v-autocomplete>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
            <v-btn color="blue darken-1" text @click="save">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </main>

</template>
<script>
  export default {
    name: 'AdminPage',
    data() {
      return {
        authUserList: [],
        authorityList: ["ROLE_USER", "ROLE_ADMIN", "ROLE_ANON"],
        editedIndex: -1,
        editedItem: {
          number: 0,
          name: "",
          email: "",
          authority: 0,
          protein: 0
        },
        desserts: null,
        dialog: false,
        isLActive: false,
        isRActive: false,
        headers: [
          {
            text: "번호",
            align: "start",
            sortable: false,
            value: "number"
          },
          {text: "이름", value: 'name'},
          {text: "이메일", value: 'email', sortable: false},
          {text: "권한", value: 'authority', sortable: false},
          {text: "Actions", value: 'actions', sortable: false},
        ],
      }
    },
    computed:{

    },
    beforeCreate() {
      this.$http.post('/api/user/admin/userList')
        .then(res =>{
          this.authUserList = res.data
        })
    },
    methods: {
      close() {
        this.dialog = false;
      },
      save() {
        this.dialog = false;
      },
      editItem(item) {
        console.log(item)
        this.editedIndex = this.authUserList.indexOf(item);
        this.editedItem = Object.assign({}, item);
        this.dialog = true;
      },
    }

  }

</script>
<style scoped>
  .moHeight {
    height: inherit;
    overflow: auto;
  }
</style>
