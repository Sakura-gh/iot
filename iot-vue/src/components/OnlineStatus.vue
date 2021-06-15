<template>
    <div class="com-container">
        <div class="com-chart" ref="online_status_ref"></div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            chartInstance: null,
            allData: [],
            showNums: 20, // 显示近20s的设备在线信息
            timerId: null
        }
    },
    mounted() {
        this.initChart()
        // this.getData()
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
            this.chartInstance = this.$echarts.init(this.$refs.online_status_ref, 'chalk')
            const initOption = {
                title: {
                    text: 'Ⅰ最近20s的设备在线数量情况'
                },
                grid: {
                    left: '3%', 
                    top: '20%',
                    right: '4%',
                    bottom: '5%',
                    containLabel: true
                },
                tooltip: {
                    trigger: 'axis' // 文字提示
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false
                },
                yAxis: {
                    type: 'value'
                },
                series: {
                    type: 'line',
                    // 折线颜色
                    itemStyle: {
                        color: 'rgba(22, 242, 217, 1)'
                    },
                    // 折线下方的区域颜色，从上到下，透明度从50%到0%
                    areaStyle: {
                        color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            {
                                offset: 0,
                                color: 'rgba(22, 242, 217, 0.5)' 
                            },
                            {
                                offset: 1,
                                color: 'rgba(22, 242, 217, 0)' 
                            }
                        ])
                    }                
                }
            }
            this.chartInstance.setOption(initOption)
        },
        // 每秒获取一次数据
        getDataInternal() {
            this.timerId = setInterval(() => {
                this.getData()
            }, 1000)
        },
        async getData() {
            const {data: ret} = await this.$http.get('online-status')
            this.allData.push(ret)
            if (this.allData.length > this.showNums) {
                this.allData.shift()
            }
            this.updateChart()
        },
        updateChart() {
            // x轴
            var time = []
            for (var i = 1; i <= this.showNums; i++) {
                time.push('last ' + (this.showNums - i) + ' s')
            }
            console.log(this.allData)
            const dataOption = {
                xAxis: {
                    data: time
                },
                series: [
                    {
                        data: this.allData
                    }
                ]
            }
            this.chartInstance.setOption(dataOption)
        },
        screenAdapter() {
            const titleFontSize = this.$refs.online_status_ref.offsetWidth / 100 * 3.6

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
    },

}
</script>

<style>

</style>