<template>
    <div class="com-container">
        <div class="com-chart" ref="value_info2_ref"></div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            chartInstance: null,
            deviceIds: null,
            seriesArr: new Array(),
            colorArr1: null,
            colorArr2: null,
            showNums: 20,
            timerId: null
        }
    },
    mounted() {
        this.initChart()
        this.getDataInternal()
        window.addEventListener('resize', this.screenAdapter)
        this.screenAdapter()
    },
    destroyed() {
        clearInterval(this.timerId)
        window.removeEventListener('resize', this.screenAdapter)
    },
    methods: {
        initChart() {
            this.chartInstance = this.$echarts.init(this.$refs.value_info2_ref, 'chalk')
            const initOption = {
                title: {
                    text: 'Ⅰ各设备value值变化趋势',
                    left: 5,
                    top: 5                    
                },
                grid: {
                    top: '25%',
                    left: '3%',
                    right: '6%',
                    bottom: '3%',
                    containLabel: true
                },
                tooltip: {
                    trigger: 'axis' // 文字提示
                },
                legend: {
                    top: '10%',
                    icon: 'circle',
                    left: 20
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false
                },
                yAxis: {
                    type: 'value'
                }
            }
            this.chartInstance.setOption(initOption)            
            // 半透明的颜色值
            this.colorArr1 = [
                'rgba(11, 168, 44, 0.5)',
                'rgba(44, 110, 255, 0.5)',
                'rgba(22, 242, 217, 0.5)',
                'rgba(254, 33, 30, 0.5)',
                'rgba(250, 105, 0, 0.5)'
            ]
            // 全透明的颜色值 
            this.colorArr2 = [
                'rgba(11, 168, 44, 0)',
                'rgba(44, 110, 255, 0)',
                'rgba(22, 242, 217, 0)',
                'rgba(254, 33, 30, 0)',
                'rgba(250, 105, 0, 0)'
            ]    
        },
        getDataInternal() {
            this.timerId = setInterval(() => {
                this.getData()
            }, 1000)
        },
        async getData() {
            // 获取device id
            var {data: ids} = await this.$http.get('device-ids')
            this.deviceIds = ids
            this.deviceIds.sort((a, b) => {
                return a < b;
            })
            this.seriesArr.length = 0
            // 获取每台device对应的value
            for (var i = 0; i < this.deviceIds.length; i++) {
                var {data: msgs} = await this.$http.get('messages-by-id-num', {
                    params: {
                        id: this.deviceIds[i],
                        num: this.showNums
                    }
                })
                this.seriesArr.push({
                    type: 'line',
                    name: this.deviceIds[i],
                    data: msgs.map((item) => {
                        return item.value
                    }),
                    stack: 'map', // 堆叠图
                    areaStyle: { // 折线图下方区域的渐变色填充
                        color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            {
                                offset: 0,
                                color: this.colorArr1[i % this.colorArr1.length]
                            },
                            {
                                offset: 1,
                                color: this.colorArr2[i % this.colorArr2.length]
                            }
                        ])
                    }
                })  
            }
            // 一个bug：seriesArr的长度有可能会比deviceIds要长，导致显示多余的折线
            // 目前还没找到解决方案，单纯将这种case抛弃
            if (this.seriesArr.length != this.deviceIds.length) {
                return null
            }
            this.updateChart() 
        },
        updateChart() {
            // x轴
            var timeArr = []
            for (var i = 1; i <= this.showNums; i++) {
                timeArr.push('last ' + (this.showNums - i) + ' s')
            }
            const dataOption = {
                xAxis: {
                    data: timeArr
                }, 
                series: this.seriesArr,
                legend: {
                    data: this.deviceIds
                }
            }            
            this.chartInstance.setOption(dataOption)
        },
        screenAdapter() {
            const titleFontSize = this.$refs.value_info2_ref.offsetWidth / 100 * 3.6

            const adapterOption = {
                // 文字大小
                title: {
                    textStyle: {
                        fontSize: titleFontSize
                    }
                },                
            }
            this.chartInstance.setOption(adapterOption)
            this.chartInstance.resize();
        }
    }
}
</script>

<style>

</style>