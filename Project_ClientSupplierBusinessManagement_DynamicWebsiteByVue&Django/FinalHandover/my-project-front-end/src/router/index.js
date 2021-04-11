import Vue from 'vue'
import Router from 'vue-router'


import Login from '../views/Login'
import SupplierHome from '../views/SupplierHome.vue'
import LoginSuccess from '../views/LoginSuccess'
import IndexPage from '../views/IndexPage.vue'
import Message from '../views/Message.vue'
import Dashboard from '../views/Dashboard.vue'
import JobStatus from '../views/JobStatus.vue'
import JobStatusCard from '../components/JobStatusCard.vue'
import SignUp from '../views/Signup.vue'
import UpdateCard from '../components/updateCard.vue'
import JobDescription from '../views/jobdescription.vue'
import BrowseTask from '../views/BrowseTask.vue'
import JobDetail from '../views/JobDetail.vue'


Vue.use(Router)


const router = new Router({
  routes: [
    {
      path: '/',
      name: 'IndexPage',
      component: IndexPage
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/browsetask',
      name: 'BrowseTask',
      component: BrowseTask
    },
    {
      path: '/supplierhome',
      name: 'SupplierHome',
      component: SupplierHome,
      redirect:'/dashboard',
      children:[
        {
          path: '/updateprofile',
          name: 'UpdateCard',
          component: UpdateCard
        },
        {
          path: '/dashboard',
          name: 'Dashboard',
          component: Dashboard
        },
        {
          path: '/status',
          name: 'Status',
          component: JobStatus
        },
        {
          path: '/message',
          name: 'Message',
          component: Message
        },
        {
          path: '/jobdescription',
          name: 'JobDescription',
          component: JobDescription
        }
      ]
    },
    {
      path: '/loginsuccess',
      name: 'LoginSuccess',
      component: LoginSuccess
    },
    {
      path: '/jobstatuscard',
      name: 'JobStatusCard',
      component: JobStatusCard
    },
    {
      path: '/signup',
      name: 'SignUp',
      component: SignUp
    },
   
    {
      path: '/jobdetail',
      name: 'JobDetail',
      component: JobDetail
    }
    
  ]
})

// Configure navigation guards
router.beforeEach((to, from, next) => {
  // cases where visit page doesn't require logged in status
  if(to.path === '/login') return next();  
  if(to.path === '/signup') return next();  
  // get token
  const tokenStr = window.sessionStorage.getItem('token');

  if(tokenStr) {
    if(to.path === '/') return next('/dashboard'); 
  } else {
    // without token, if redirect to index page, next()
    if(to.path === '/') return next(); 
    // otherwise, all pages that require logged in status to access will be stopped and redirect to login page
    return next('/login');
  }

  // in cases where token doesn't exist
  // if(!tokenStr) 
  next();

});

export default router