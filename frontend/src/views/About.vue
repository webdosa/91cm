<template>
  <header>
    <b-navbar toggleable="lg" type="light" variant="white">
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-dropdown no-caret right toggle-class="nonoutline" class="verti-align" variant="nonoutline" right>
          <template v-slot:button-content>
            <i class="im im-bell"></i>
          </template>
          <b-dropdown-text v-for="(alarm,index) in alarmList" style="width: 25vw;" class="border">
            <div>
              <div class="row float-right">
                <b-button size="sm" id="esc" variant="nonoutline" @click="alarmList.splice(index,1)"><i
                  class="im im-x-mark" ></i></b-button>
              </div>
              <div class="row">
                <p>{{alarm.info}}</p>
              </div>
              <div class="row float-right">
                <div >
                  <b-button variant="nonoutline"><i class="im im-check-mark-circle"
                                                              style="color: #42b983;"></i>
                  </b-button>
                  <b-button variant="nonoutline"><i class="im im-x-mark-circle" style="color: red;"></i>
                  </b-button>
                </div>
              </div>
            </div>
          </b-dropdown-text>
        </b-dropdown>
        <div class="verti-align useridsty">{{ $store.state.currentUser.name }}</div>
        <b-nav-item-dropdown no-caret right toggle-class="nonoutline">
          <!-- Using 'button-content' slot -->
          <template v-slot:button-content style="padding:0px;">
          </template>
          <b-dropdown-item>Profile</b-dropdown-item>
          <b-dropdown-item>Sign Out</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-navbar>
  </header>
</template>
<script>
  export default {
    name: 'About',
    data() {
      return {
        alarmList: [],
        alarm: {
          info: null,
          state: 'wait',
        }
      }
    },
    created() {
      for (let i = 0; i < 10; i++) {
        this.alarm.info = '알림' + i
        this.alarmList.push(JSON.parse(JSON.stringify(this.alarm)))
      }
    },
    computed: {},
    updated() {
    },
    methods: {}

  }

</script>
<style scoped>
  #esc{
    color: #7f7f7f;
  }
  #esc:hover{
    color: black;
  }
</style>
