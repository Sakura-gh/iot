import Vue from 'vue'
import Router from 'vue-router'
import DataInfoPage from '@/components/DataInfoPage'
import TotalDataInfoPage from '@/components/TotalDataInfoPage'
import ValueInfoPage from '@/components/ValueInfoPage'
import ValueInfo2Page from '@/components/ValueInfo2Page'
import OnlineStatusPage from '@/components/OnlineStatusPage'
import TrackMapPage from '@/components/TrackMapPage'
import ScreenPage from '@/components/ScreenPage'
import QueryView from '@/components/QueryView'
import ConfigView from '@/components/ConfigView'
import Login from '@/components/Login'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/query',
            component: QueryView
        },
        {
            path: '/config',
            component: ConfigView
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
            path: '/totaldatainfo',
            component: TotalDataInfoPage
        },
        {
            path: '/valueinfo',
            component: ValueInfoPage
        },
        {
            path: '/valueinfo2',
            component: ValueInfo2Page
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
