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

          <v-tab-item v-for="i in tabs" :key="i" :value="'tab-' + i ">
            <AuthorityPage v-if="i==tabs[0]"></AuthorityPage>
            <NoticeMessage v-if="i==tabs[1]"></NoticeMessage>
            <div v-if="i==tabs[2]">
              <v-img src="src/assets/images/egg/egg01.gif" contain></v-img>
            </div>
          </v-tab-item>
        </v-tabs>
      </div>
    </div>
  </main>
</template>
<script>
  import AuthorityPage from "./AuthorityPage";
  import NoticeMessage from "./NoticeMessage";
  export default {
    name: 'AdminPage',
    components: {NoticeMessage, AuthorityPage},
    data() {
      return {
        tab: null,
        tabs: ['권한 설정','전체메시지','개발중'],
        isLActive: false,
        isRActive: false,
      }
    },
    computed: {},
    methods: {

    }

  }

</script>
<style scoped>
  .moHeight {
    height: inherit;
    overflow: auto;
  }
</style>
