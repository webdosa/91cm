import 'babel-polyfill'
import 'expose-loader?$!expose-loader?jQuery!jquery'
// import '../dist/css/theme.css'
// import '../plugins/bootstrap/dist/css/bootstrap.min.css'
import '../plugins/fontawesome-free/css/all.min.css'
import '../plugins/icon-kit/dist/css/iconkit.min.css'
import '../plugins/ionicons/dist/css/ionicons.min.css'
import '../plugins/perfect-scrollbar/css/perfect-scrollbar.css'
import '../plugins/datatables.net-bs4/css/dataTables.bootstrap4.min.css'
import '../plugins/jvectormap/jquery-jvectormap.css'
import '../plugins/tempusdominus-bootstrap-4/build/css/tempusdominus-bootstrap-4.min.css'
import '../plugins/weather-icons/css/weather-icons.min.css'
import '../plugins/c3/c3.min.css'
import '../plugins/owl.carousel/dist/assets/owl.carousel.min.css'
import '../plugins/owl.carousel/dist/assets/owl.theme.default.min.css'
// import '../src/assets/js/vendor/modernizr-2.8.3.min.js'

import '../plugins/bootstrap/dist/js/bootstrap.min.js'

 
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
import 'animate.css'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import './assets/css/main.css'
import '../dist/css/theme.css'
//




Date.prototype.addDays = function (days) {
  var date = new Date(this.valueOf());
  date.setDate(date.getDate() + days);
  return date;
}
Vue.use(AlertModal)


Vue.use(Vuetify)
Vue.use(VueSession, { persist: true})
Vue.prototype.$eventBus = new Vue();
Vue.prototype.$http = axios
Vue.use(BootstrapVue)
Vue.config.productionTip = false

new Vue({
  vuetify: new Vuetify({
    icons: {
      iconfont: "md"
    }
  }),
  router,
  store,
  render: h => h(App)
}).$mount('#app')
