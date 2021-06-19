import Vue from 'vue'
import Router from 'vue-router'
import DataInfoPage from '@/components/DataInfoPage'
import OnlineStatusPage from '@/components/OnlineStatusPage'
import TrackMapPage from '@/components/TrackMapPage'
import ScreenPage from '@/components/ScreenPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
        path: '/',
        redirect: '/screen'
    },
    {
        path: '/screen',
        component: ScreenPage
    },
    {
      path: '/datainfo',
      component: DataInfoPage
    },
    {
        path: '/onlinestatus',
        component: OnlineStatusPage
    },
    {
        path: '/trackmap',
        component: TrackMapPage
    }
  ]
})
