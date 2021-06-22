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
import Index from '@/components/Index'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/index'
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/index',
            component: Index,
            meta: { // 该元数据表示页面需要拦截
                requireAuth: true
            }
        },
        {
            path: '/query',
            component: QueryView,
            meta: { // 该元数据表示页面需要拦截
                requireAuth: true
            }
        },
        {
            path: '/config',
            component: ConfigView,
            meta: { // 该元数据表示页面需要拦截
                requireAuth: true
            }
        },
        {
            path: '/screen',
            component: ScreenPage,
            meta: { // 该元数据表示页面需要拦截
                requireAuth: true
            }
        },
        {
            path: '/datainfo',
            component: DataInfoPage,
            meta: { // 该元数据表示页面需要拦截
                requireAuth: true
            }
        },
        {
            path: '/totaldatainfo',
            component: TotalDataInfoPage,
            meta: { // 该元数据表示页面需要拦截
                requireAuth: true
            }
        },
        {
            path: '/valueinfo',
            component: ValueInfoPage,
            meta: { // 该元数据表示页面需要拦截
                requireAuth: true
            }
        },
        {
            path: '/valueinfo2',
            component: ValueInfo2Page,
            meta: { // 该元数据表示页面需要拦截
                requireAuth: true
            }
        },
        {
            path: '/onlinestatus',
            component: OnlineStatusPage,
            meta: { // 该元数据表示页面需要拦截
                requireAuth: true
            }
        },
        {
            path: '/trackmap',
            component: TrackMapPage,
            meta: { // 该元数据表示页面需要拦截
                requireAuth: true
            }
        }
    ]
})
