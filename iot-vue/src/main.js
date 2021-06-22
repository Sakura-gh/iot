// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

// 引入element-ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

// 引入全局样式文件
import './assets/css/global.less'
// 引入图表自定义主题
import chalk from '../static/chalk'

// 引入echart, 挂载到vue的原型对象上, 全局只需要用this.$echarts即可使用
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
// 引入百度地图api
import 'echarts/extension/bmap/bmap'

// 将axios挂载到vue的原型对象上, 全局只需要用this.$axios即可使用 
import axios from 'axios'
import { exp } from 'prelude-ls'
Vue.prototype.$http = axios.create({
    timeout: 20000, // 20s超时时间
    baseURL: '/api' // 后端地址      
})

Vue.config.productionTip = false

// 添加对store的引用
import store from './store'
// 使用钩子函数判断是否拦截
router.beforeEach((to, from, next) => {
    // 如果访问的路径需要登录验证，先判断store是否存有user信息
    // 如果存在就放行，否则跳转到登录页面
    if (to.meta.requireAuth) {
        if (store.state.user.username) {
            next()
        } else {
            next({
                path: '/login',
                query: { redirect: to.fullPath }
            })
        }
    }
    // 如果访问的路径不需要登录验证，直接放行
    else {
        next()
    }
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store, // 添加store
    components: { App },
    template: '<App/>'
})
