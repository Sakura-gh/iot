// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

// 引入全局样式文件
import './assets/css/global.less'
// 引入图表自定义主题
import chalk from '../static/chalk'

// 引入echart, 挂载到vue的原型对象上, 全局只需要用this.$echarts即可使用
import echarts from 'echarts'
Vue.prototype.$echarts = echarts

// 将axios挂载到vue的原型对象上, 全局只需要用this.$axios即可使用 
import axios from 'axios'
Vue.prototype.$http = axios.create({
  timeout: 20000, // 20s超时时间
  baseURL: '/api' // 后端地址      
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
