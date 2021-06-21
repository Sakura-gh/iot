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

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/screen'
        },
        {
            path: '/query',
            component: QueryView
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
