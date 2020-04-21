import Vue from 'vue'
import store from '../store'
import VueRouter from 'vue-router'
import Home from '../components/Home.vue'
import NotFound from '../views/NotFound.vue'
import Clock from '../views/Clock'
import Main from '../components/Main'
import ContentWrapper from '../views/main/ContentWrapper'
import ChannelHeader from '../views/main/ChannelHeader'
import test from '../views/user/UserInfo'
import EditProfile from '../views/user/EditProfile'
import SignUp from '../components/SignUp'
import FormSignUp from "../views/FormSignUp";
import CopyRight from "../views/util/CopyRight"
import Todolist from '../views/todolist/TodoList'
import About from "../views/About";
import Calendar from "../views/calendar/Calendar";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'main',
    component: About
  },
  {
    path: '*',
    component: NotFound
    // redirect: '/errorpage'
  },
  {
    path: '/clock',
    component: Clock
  },
  {
    path: '/main',
    name: 'main',
    component: Main,
    beforeEnter: async function (to, from, next) {
      await store.dispatch('initCurrentUser')
      if (store.state.currentUser.phone != null) {
        next()
      } else {
        next('/signup')
      }
    }
  },
  {
    path: '/signup',
    component: SignUp

  },
  {
    path: '/formSignUp',
    component: FormSignUp
  },
  // 테스트용 path 테스트 후에 삭제
  {
    path: '/test',
    component: Calendar
  },
  {
    path: '/todo',
    component: Todolist
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
