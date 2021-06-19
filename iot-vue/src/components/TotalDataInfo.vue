<template>
    <div class="com-container">
        <div class="com-chart" ref="total_datainfo_ref"></div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            chartInstance: null,
            totalDataNums: new Array(),
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
            this.chartInstance = this.$echarts.init(this.$refs.total_datainfo_ref, 'chalk')
            const initOption = {
                title: {
                    text: 'Ⅰ接收数据总量情况',
                    left: 5,
                    top: 5
                },
                grid: {
                    top: '15%',
                    left: '3%',
                    right: '6%',
                    bottom: '3%',
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
                        color: 'rgba(250, 105, 0, 1)'
                    },
                    // 折线下方的区域颜色，从上到下，透明度从50%到0%
                    areaStyle: {
                        color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            {
                                offset: 0,
                                color: 'rgba(250, 105, 0, 0.5)' 
                            },
                            {
                                offset: 1,
                                color: 'rgba(250, 105, 0, 0)' 
                            }
                        ])
                    }                
                }
            }
            this.chartInstance.setOption(initOption)
        },
        getDataInternal() {
            this.timerId = setInterval(() => {
                this.getData()
            }, 1000)
        },
        async getData() {
            // get data
            var {data: num} = await this.$http.get('message-total-num')
            this.totalDataNums.push(num)
            if (this.totalDataNums.length > this.showNums) {
                this.totalDataNums.shift()
            }
            this.updateChart()
        },
        updateChart() {
            // x轴
            var time = []
            for (var i = 1; i <= this.showNums; i++) {
                time.push('last ' + (this.showNums - i) + ' s')
            }
            const dataOption = {
                xAxis: {
                    data: time
                },
                series: [
                    {
                        data: this.totalDataNums
                    }
                ]
            }
            this.chartInstance.setOption(dataOption)
        },
        screenAdapter() {
            const titleFontSize = this.$refs.total_datainfo_ref.offsetWidth / 100 * 3.6
            const adapterOption = {
                // 文字大小
                title: {
                    textStyle: {
                        fontSize: titleFontSize
                    }
                },                  
            }
            this.chartInstance.setOption(adapterOption)
            this.chartInstance.resize()
        }
    }
}
</script>

<style>

</style>