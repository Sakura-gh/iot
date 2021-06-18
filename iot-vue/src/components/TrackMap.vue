<template>
    <div class="com-container">
        <div class="com-chart" ref="track_map_ref">test</div>
    </div>
</template>

<script>
import 'echarts/map/js/province/zhejiang'
export default {
    data() {
        return {
            chartInstance: null,
            bmap: null,
            deviceIds: null,
            iotMessages: new Map(),
            messageNum: 20,
            timerId: null,
            isShowTack: false // 是否显示轨迹
        }
    },
    mounted() {
        this.initChart()
        this.getData()
        // this.getDataInternal()
        window.addEventListener('resize', this.screenAdapter)
        this.screenAdapter()
    },
    destroyed() {
        clearInterval(this.timerId)
        window.removeEventListener('resize', this.screenAdapter)
    },
    methods: {
        initChart() {
            this.chartInstance = this.$echarts.init(this.$refs.track_map_ref)
            
            const initOption = {
                bmap: {
                    zoom: 5, // 地图当前缩放比例
                    roam: true, // 开启鼠标缩放和平移漫游
                }
            }
            this.chartInstance.setOption(initOption)
            // 当点击设备时，会做切换
            this.chartInstance.on('click', async device => {
                console.log(device.name)
                // 取反
                this.isShowTack = !this.isShowTack
                // this.resetEcharts()
                if (this.isShowTack) {
                    this.drawTrackInternal(device.name)
                } else {
                    this.getDataInternal()
                }
            })

            this.bmap = this.chartInstance.getModel().getComponent('bmap').getBMap()
            // console.log(this.bmap)
            // this.bmap.setMinZoom(6) // 地图最小缩放比例
            // this.bmap.setMaxZoom(12) // 地图最大缩放比例       
            this.bmap.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT})) // 地图左下角显示比例尺
            // this.bmap.setMapStyle({style: 'midnight'}) // 旧版本的bmap风格设置
            this.bmap.setMapStyleV2({styleId: '8394c7f10d2cb459727e8c39ba0a3650'})
            // console.log(BMap)
        },
        // 显示各个设备最新的位置信息
        getDataInternal() {
            // 先清除之前的定时器
            clearInterval(this.timerId)
            // 每1秒刷新一次地图数据
            this.timerId = setInterval(() => {
                this.getData()
            }, 1000)
        },
        async getData() {
            // 获取device ids
            var {data: ret} = await this.$http.get('device-ids')
            this.deviceIds = ret
            // console.log(this.deviceIds)
            // 获取每个设备最新的信息(每秒更新)，此时num设置为1，用于在地图上显示该设备的最新位置
            for(var i = 0; i < this.deviceIds.length; i++) {
                var {data: msgs} = await this.$http.get('messages-by-id-num', {
                    params: {
                        id: this.deviceIds[i],
                        num: this.messageNum
                    }
                })
                this.iotMessages.set(this.deviceIds[i], msgs)
            }
            // console.log(this.iotMessages)
            this.updateChart()
        },
        updateChart() {
            // seriesArr的每个元素都是一个设备下的所有散点数据
            // 如果想要在地图中显示，就给图表增加使用地图的坐标系的配置
            var locations = new Array()
            for(var i = 0; i < this.deviceIds.length; i++) {
                var msg = this.iotMessages.get(this.deviceIds[i])[0]
                // console.log(msg)
                locations.push({
                    name: this.deviceIds[i],
                    value: [msg.lng, msg.lat]
                })
            }
            const dataOption = {
                geo: {
                    map: "bmap",
                },
                bmap: {
                    center: [this.iotMessages.get(this.deviceIds[0])[0].lng, this.iotMessages.get(this.deviceIds[0])[0].lat],
                    zoom: 10, 
                    roam: true
                },
                series: [
                    {
                        name: '设备分布',
                        type: "effectScatter",
                        // rippleEffect: { // 涟漪效果修改
                        //     scale: 3,
                        //     brushType: 'stroke'
                        // },
                        coordinateSystem: "bmap",  
                        animation: true, // 设置为false时，拖动地图散点的移动不会有延时         
                        symbol:'circle',
                        symbolSize: 15,
                        data: locations,
                        label: {
                            normal: {
                                formatter: '{b}',
                                position: 'top',
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        }
                    },
                    // 这一部分仅为了适配echarts本身的bug: chart.setOption(option, {replaceMerge: string})无法做到完全删除
                    // 详情可见我提的issue：https://github.com/apache/echarts/issues/15186
                    {
                        name: '设备分布',
                        type: "effectScatter",
                        // rippleEffect: { // 涟漪效果修改
                        //     scale: 3,
                        //     brushType: 'stroke'
                        // },
                        coordinateSystem: "bmap",  
                        animation: true, // 设置为false时，拖动地图散点的移动不会有延时         
                        symbol:'circle',
                        symbolSize: 1,
                        data: locations,
                        label: {
                            normal: {
                                formatter: '{b}',
                                position: 'top',
                                show: false
                            },
                            emphasis: {
                                show: false
                            }
                        }
                    },
                ],
            }
            console.log('locations:')
            console.log(this.chartInstance.getOption())
            this.chartInstance.setOption(dataOption, {replaceMerge: ['series', 'dataRange', 'geo']})
            // this.chartInstance.setOption(dataOption)
            console.log(this.chartInstance.getOption())
        },
        screenAdapter() {
            const adapterOption = {}
            this.chartInstance.setOption(adapterOption)
            this.chartInstance.resize()
        },
        drawTrackInternal(deviceId) {
            // 首先清除实时显示各设备坐标点的定时器
            clearInterval(this.timerId)
            // 每秒刷新deviceId对应设备的轨迹
            this.timerId = setInterval(() => {
                this.drawTrack(deviceId)
            }, 1000)
        },
        // 绘制该设备的轨迹图(最新messageNum个坐标点)
        async drawTrack(deviceId) {
            var {data: msg} = await this.$http.get('messages-by-id-num', {
                params: {
                    id: deviceId,
                    num: this.messageNum
                }
            })
            // console.log(msg)
            // 原先的msg数组是新数据到旧数据的排列，反转更新为旧数据到新数据的排列
            // 然后按照这个排列依次画出轨迹
            msg.reverse()
            // console.log(msg)
            // 获取用于显示的坐标点和连接线数据
            var lineData = new Array()
            var pointData = new Array()
            for (var i = 0; i < msg.length - 1; i++) {
                // 连接线，from->to
                lineData.push({
                    // fromName: msg[i].clientId,
                    // toName: msg[i + 1].clientId,
                    fromName: i,
                    toName: i + 1,
                    coords: [
                        [msg[i].lng, msg[i].lat],
                        [msg[i + 1].lng, msg[i + 1].lat]
                    ]
                })
                // 坐标点，起点要额外考虑
                if (i == 0) {
                    pointData.push({
                        name: i,
                        value: [msg[i].lng, msg[i].lat]
                    })
                }
                pointData.push({
                    // name是显示的标签
                    name: i + 1,
                    value: [msg[i + 1].lng, msg[i + 1].lat]
                })
            }

            const dataOption = {
                geo: {
                    map: "bmap",
                    polyline: true,
                    progressiveThreshold: 100,
                    progressive: 400,
                    label: {
                        "emphasis": {
                            "show": false
                        }
                    },
                    roam: true,
                    itemStyle: {
                        "normal": {
                            "areaColor": "#E7A060",   //连接线的颜色
                            "borderColor": "#ff9900"   //移动飞线的颜色
                        },
                        "emphasis": {
                            "areaColor": "#F300FF" 
                        }
                    }
                },
                dataRange: {
                    show:false,
                    min : 0,
                    max : 100,
                    calculable : true,
                    color: ['#ff3333', 'orange', 'yellow','lime','aqua'],
                    textStyle:{
                        color:'#fff'
                    }
                },
                series: [
                    {
                        //飞线样式
                        name: '飞线',
                        type: "lines",
                        coordinateSystem: "bmap",
                        symbol: ['none', 'arrow'],
                        effect: {
                            symbolSize: 5,
                            "show": true,
                            "scaleSize": 2,
                            "period": 20,
                            "color": '#FFF',
                            "shadowBlur": 10,
                            constantSpeed: 50
                        },
                        lineStyle: {
                            "normal": {
                                "color": new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                    offset: 0,
                                    color: '#58B3CC'
                                }, {
                                    offset: 1,
                                    color: '#ffbf31'
                                }], false),
                                "width": 2,  //飞线粗细
                                "opacity": 0.3,
                                "curveness": 0.3
                            },
                        },
                        data:lineData,
                    },
                    {
                        //终点点位样式
                        name: '终点',
                        type: "effectScatter",
                        coordinateSystem: "bmap",
                        rippleEffect: {
                            period: 5,
                            scale: 3,
                            "brushType": "stroke"
                        },
                        symbolSize : 10,
                        itemStyle:{
                            normal:{
                                "color": "#FF4040"
                            },
                        },
                        label: {
                            normal: {
                                formatter: '{b}',
                                position: 'top',
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        //出发点和终点坐标
                        data: pointData,
                    }
                ],
            }
            console.log('track:')
            console.log(this.chartInstance.getOption())
            this.chartInstance.setOption(dataOption, {replaceMerge: ['series', 'dataRange', 'geo']})
            // this.chartInstance.setOption(dataOption)
            console.log(this.chartInstance.getOption())
        },
        // // 重置echarts，性能低，但目前官方并没有提供更好的方案
        // resetEcharts() {
        //     // console.log(this.chartInstance.getOption())
        //     this.chartInstance.clear()
        //     this.chartInstance.setOption({bmap:{}})
            
        //     this.bmap = this.chartInstance.getModel().getComponent('bmap').getBMap()     
        //     this.bmap.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT})) // 地图左下角显示比例尺
        //     this.bmap.setMapStyleV2({styleId: '8394c7f10d2cb459727e8c39ba0a3650'})
        // }
    }
}
</script>

<style>

</style>