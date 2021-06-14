import Vue from 'vue'
import Router from 'vue-router'
import DataInfoPage from '@/components/DataInfoPage'
import OnlineStatus from '@/components/OnlineStatus'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/datainfo',
      component: DataInfoPage
    },
    {
        path: '/onlinestatus',
        component: OnlineStatus
    }
  ]
})
