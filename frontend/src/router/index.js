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
    name: 'main',
    component: Main,
    children:[
      { path:'', components: {default:ContentWrapper,ChannelHeader:ChannelHeader }},
      { path:'user', component: test},
      { path:'edit', component: EditProfile},
    ],
    beforeEnter: async function(to,from,next){
      await store.dispatch('initCurrentUser')
        if(store.state.currentUser.phone != null){
          next()
        }else{
          next('/signup')
        }
      }
  },
  {
    path: '/user',
    component: Main,
    children:[

    ]
  },
  {
    path: '/signup',
    component: SignUp

  }
  //,
  // {
  //   path: '/user/edit',
  //   component: Main,
  //   children:[
  //     { path:'', component: EditProfile},
  //   ]
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
