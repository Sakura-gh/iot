import Vue from 'vue'
// 引入Vuex，用于存储各组件共享的全局变量(状态)和方法
import Vuex from 'vuex'
Vue.use(Vuex)

// localStorage: 本地存储, 登录时会先判断本地存储中是否有user这个对象存在，
// 如果存在就取出获得username的值，否则把username设置为空, 此时只要不清除缓存，登录的状态就会一直保存
export default new Vuex.Store({
    state: {
        user: {
            username: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
        }
    },
    mutations: {
        login(state, user) {
            state.user = user
            window.localStorage.setItem('user', JSON.stringify(user))
        }
    }
})