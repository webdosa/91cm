import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    selectComponent: 'main',
    oldComponent: 'main',
    currentUser: {},
    userList : [],
    currentChannelUser: [],
    isLActive: false,
    isRActive: false
  },
  mutations: {
    getSelectComponent: function(state, payload){
      state.oldComponent = state.selectComponent
      state.selectComponent = payload
    },
    getUserList: function(state,payload) {
      state.userList = payload
    },
    setCurrentUser: function (state, payload) {
      state.currentUser = payload
    },
    resetCurrentUser: function (state) {
      state.currentUser = {}
    }
  },
  actions: {
    userListUpdate: function (context) {
      axios.get('http://localhost:9191/api/user/list')
        .then(res => {
          context.commit('getUserList',res.data);
        }).catch(error => {
          console.log(error);
      })
    },
    initCurrentUser: async function (context) {
      await axios.get('http://localhost:9191/api/user/info')
        .then(res => {
          console.log(res.data)
          context.commit('setCurrentUser',res.data)
        })
    },
    resetCurrentUser: function (context) {
      context.commit('resetCurrentUser')
    }
  },
  modules: {
  }
})
