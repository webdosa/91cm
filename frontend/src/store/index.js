import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userChannelList: [],
    stompClient: Stomp.over(new SockJS('http://localhost:9191/endpoint/')),
    selectComponent: 'main',
    oldComponent: '',
    currentUser: {},
    userList : [],
    currentChannelUser: [],
    isLActive: false,
    isRActive: false,
    isfocus: true,
    isLogout: false,
    isSearchMode: false,
    isInviteMode: false,
    searchText: ''
  },
  mutations: {
    setChannelList: function(state,payload){
      state.userChannelList = payload
    },
    getSelectComponent: function(state, payload){
      console.log(state.selectComponent)
      console.log('index.js')
      state.oldComponent = state.selectComponent
      console.log(state.oldComponent)
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
    },
    setFocus: function (state, payload)  {
      state.isfocus = payload
    },
    setIsLogout: function (state,payload) {
      state.isLogout = payload
    },
    setSearchText: function (state,paylod) {
      state.searchText = paylod
    }
  },
  actions: {
    userListUpdate: function (context) {
      axios.get('/api/user/list')
        .then(res => {
          context.commit('getUserList',res.data);
        }).catch(error => {
          console.log(error);
      })
    },
    channelList: function(context){
      axios.get('/api/channel/list')
        .then(res => {
          context.commit('setChannelList',res.data)
        }).catch(error =>{
      })
    },
    initCurrentUser: async function (context) {
      await axios.get('/api/user/info')
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
