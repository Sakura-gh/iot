import Vue from 'vue'
import Router from 'vue-router'
import DataInfoPage from '@/components/DataInfoPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/datainfo',
      component: DataInfoPage
    }
  ]
})
