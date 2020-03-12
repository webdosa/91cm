import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../components/Home.vue'
import NotFound from '../views/NotFound.vue'
import Clock from '../views/Clock'
import Main from '../components/Main'
import ContentWrapper from '../views/main/ContentWrapper'
import ChannelHeader from '../views/main/ChannelHeader'
import test from '../views/user/test'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '*',
    component: NotFound
  },
  {
    path: '/clock',
    component: Clock
  },
  {
    path: '/main',
    component: Main,
    children:[
      { path:'', components: {default:ContentWrapper,ChannelHeader:ChannelHeader }},
    ]
  },
  {
    path: '/user',
    component: Main,
    children:[
      { path:'', component: test},
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
