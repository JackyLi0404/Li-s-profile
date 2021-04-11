// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui';
import locale from 'element-ui/lib/locale/lang/en';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App'
import router from './router'

// import global css style
import './assets/css/global.css'

// import axios
import axios from 'axios' 
// configure root path of http request
// axios.defaults.baseURL = 'http://127.0.0.1:8888/api/private/v1'
axios.defaults.baseURL = 'http://3.135.198.81:8081/'
// axios.defaults.baseURL = 'https://www.liulongbin.top:8888/api/private/v1/'



axios.interceptors.request.use(config => {
  config.headers.Authorization = window.sessionStorage.getItem('token')
  return config
})

Vue.prototype.$http = axios



Vue.use(ElementUI, { locale });

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  data() {
    return {
      me:null
    }
  },
  render: h => h(App),
  router,
  components: { App },
  template: '<App/>'
})
