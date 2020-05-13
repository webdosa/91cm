import 'babel-polyfill'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import axios from 'axios'
import VueSession from 'vue-session'
import "@/assets/css/app.scss"
import AlertModal from "./plugins/AlertModal";
import './assets/font/iconmonstr/css/iconmonstr-iconic-font.min.css';
import vuetify from "vuetify";
import "vuetify/dist/vuetify.min.css"
import './assets/css/main.css'

Date.prototype.addDays = function (days) {
  var date = new Date(this.valueOf());
  date.setDate(date.getDate() + days);
  return date;
}
Vue.use(AlertModal)
Vue.use(VueSession, { persist: true})
Vue.prototype.$eventBus = new Vue();
Vue.prototype.$http = axios
Vue.use(BootstrapVue)
Vue.config.productionTip = false
Vue.use(vuetify);

new Vue({
  vuetify: new vuetify({
    icons: {
      iconfont: "md"
    }
  }),
  router,
  store,
  render: h => h(App)
}).$mount('#app')
