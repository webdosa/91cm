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
import './assets/css/main.css'
import AlertModal from "./plugins/AlertModal";
Vue.use(AlertModal)
Vue.use(VueSession, { persist: true})
Vue.prototype.$eventBus = new Vue();
Vue.prototype.$http = axios
Vue.use(BootstrapVue)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
