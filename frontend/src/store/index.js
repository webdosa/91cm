import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import CommonClass from '../service/common'
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

Vue.use(Vuex)

// state, mutations, action, getter 등을 파일로 나눌 필요 있음
export default new Vuex.Store({
  state: {
    channelUsers: [],
    taskBoard: [],
    syncSignal: {
      syncChannel: false,
      syncChannelUser: false,
    },
    currentChannel: {},
    userChannelList: [],
    stompClient: null,
    selectComponent: 'main',
    oldComponent: '',
    currentUser: {},
    userList: [],
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
    setChannelUsers: function(state,payload){
      state.channelUsers = payload
    },
    setCurrentChannel: function (state, payload) {
      state.currentChannel = payload
    },
    setChannelList: function (state, payload) {
      state.userChannelList = payload
    },
    getSelectComponent: function (state, payload) {
      console.log(state.selectComponent)
      console.log('index.js')
      state.oldComponent = state.selectComponent
      console.log(state.oldComponent)
      state.selectComponent = payload
    },
    getUserList: function (state, payload) {
      state.userList = payload
    },
    setCurrentUser: function (state, payload) {
      state.currentUser = payload
    },
    resetCurrentUser: function (state) {
      state.currentUser = {}
    },
    setFocus: function (state, payload) {
      state.isfocus = payload
    },
    setIsLogout: function (state, payload) {
      state.isLogout = payload
    },
    setSearchText: function (state, paylod) {
      state.searchText = paylod
    },
    setTaskBoard: function (state,payload) {
      state.taskBoard = payload
    }
  },
  actions: {
    updateTaskBoard: function(context){
      axios.get('/api/tasklist/get/'+context.state.currentChannel.id)
        .then(res => {
          // CommonClass.replaceText(res.data)
          console.log(res.data)
          context.commit('setTaskBoard',res.data);
        }).catch(error=>{
          console.log(error)
      })
    },
    userListUpdate: function (context) {
      axios.get('/api/user/list')
        .then(res => {
          context.commit('getUserList', res.data);
        }).catch(error => {
        console.log(error);
      })
    },
    channelList: async function (context) {
      await axios.get('/api/channel/list')
        .then(res => {
          context.commit('setChannelList', res.data)
        }).catch(error => {
        })
    },
    initCurrentUser: async function (context) {
      await axios.get('/api/user/info')
        .then(res => {
          console.log(res.data)
          context.commit('setCurrentUser', res.data)
        })
    },
    resetCurrentUser: function (context) {
      context.commit('resetCurrentUser')
    }
  },
  modules: {
  },
  getters: {
    getSearchMode: state => {
      return state.isSearchMode
    },
    getStompClient: state => {
        if (state.stompClient.connected != null) {
      if (state.stompClient != null) {
          return state.stompClient
        }
      }
    },
    getUserChannelList: state => state.userChannelList,
    getTaskBoard: state=> state.taskBoard,
    getCurrentChannel: state => state.currentChannel,
    getSyncChannelUser: state => state.syncSignal.syncChannelUser,
    getChannelUsers: state => state.channelUsers,
    getSelectComponent: state => state.selectComponent,
    getUserList: state => state.userList
  }
})
