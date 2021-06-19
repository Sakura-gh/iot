<template>
    <div class="com-container">
        <div class="com-chart" ref="valueinfo_ref"></div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            chartInstance: null,
            deviceIds: null,
            values: null,
            currentPage: 1, // 当前显示的是第几页 
            totalPage: 0, // 一共有多少页
            numInPage: 3, // 一页显示的设备数
            timerId: null,
            slideTimerId: null
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
        clearInterval(this.slideTimerId)
        window.removeEventListener('resize', this.screenAdapter)
    },
    methods: {
        initChart() {
            this.chartInstance = this.$echarts.init(this.$refs.valueinfo_ref, 'chalk')
            const initOption = {
                title: {
                    text: 'Ⅰ设备value值情况',
                    left: 5,
                    top: 5
                },
                grid: {
                    top: '20%',
                    left: '3%',
                    right: '6%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category'
                    // 初始化不赋值数据
                },
                yAxis: {
                    type: 'value'
                },
                // 状态栏
                tooltip: {
                    // 当鼠标移到坐标系中就触发
                    trigger: 'axis',
                    axisPointer: {
                        type: 'line',
                        z: 0, // 不覆盖原柱形
                        lineStyle: {
                            color: '#2D3443'
                        }
                    }
                },
                series: [
                    {
                        // 类型：柱形图
                        type: 'bar',
                        // 柱形上的标签设置
                        label: {
                            show: true,
                            position: 'top',
                            textStyle: {
                                color: 'white'
                            }
                        },
                        // 每条柱形的设置
                        itemStyle: {
                            // 设置颜色渐变：指明颜色渐变的方向+指明不同百分比之下颜色的值
                            // (0, 0)->(0, 1)从下往上渐变
                            color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                // 百分之0状态下的颜色值
                                {
                                    offset: 0,
                                    color: '#5052EE'
                                },
                                // 百分之100状态下的颜色值
                                {
                                    offset: 1,
                                    color: '#AB6EE5'
                                }
                            ])
                        }
                    }
                ]
            }
            this.chartInstance.setOption(initOption)

            // 对图表对象进行鼠标事件的监听
            // 鼠标移入图表时，关闭定时器，取消图表的自动换页
            this.chartInstance.on('mouseover', () => {
                clearInterval(this.slideTimerId)
            })
            // 鼠标移出图表时，重新开启定时器
            this.chartInstance.on('mouseout', () => {
                this.sildeInterval()
            })

            // 开启换页的定时器
            this.sildeInterval()
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
            // 获取每台device对应的value
            this.values = new Array()
            for (var i = 0; i < this.deviceIds.length; i++) {
                var {data: value} = await this.$http.get('value-by-id', {
                    params: {
                        id: this.deviceIds[i]
                    }
                })
                this.values.push(value)
            }
            // 计算总页数
            this.totalPage = this.deviceIds.length % this.numInPage === 0 ? this.deviceIds.length / this.numInPage : this.deviceIds.length / this.numInPage + 1
            this.updateChart()
        },
        updateChart() {
            var start = (this.currentPage - 1) * this.numInPage
            var end = this.currentPage * this.numInPage
            var showIds = this.deviceIds.slice(start, end)
            var showValues = this.values.slice(start, end)
            const dataOption = {
                xAxis: {
                    data: showIds
                },
                series: [
                    {
                        data: showValues
                    }
                ]
            }
            this.chartInstance.setOption(dataOption)
        },
        // 该定时器仅改变currentPage，用于换页
        sildeInterval() {
            if (this.slideTimerId) {
                clearInterval(this.slideTimerId)
            }
            // 每3秒换一次页
            this.slideTimerId = setInterval(() => {
                this.currentPage++
                if (this.currentPage > this.totalPage) {
                    this.currentPage = 1
                }
            }, 3000)
        },
        screenAdapter() {
            const titleFontSize = this.$refs.valueinfo_ref.offsetWidth / 100 * 3.6
            const adapterOption = {
                // 文字大小
                title: {
                    textStyle: {
                        fontSize: titleFontSize
                    }
                },
                // 阴影背景大小
                tooltip: {
                    axisPointer: {
                        lineStyle: {
                            width: titleFontSize
                        }
                    }
                },
                // 柱形的大小
                series: [
                    {
                        barWidth: titleFontSize,
                        // 圆角的大小
                        itemStyle: {
                            barBorderRadius: [titleFontSize / 2, titleFontSize / 2, 0, 0], // 左上角、右上角、左下角、右下角的圆角宽度设置
                        }
                    }
                ]                
            }
            this.chartInstance.setOption(adapterOption)
            this.chartInstance.resize()
        }
    }
}
</script>

<style>

</style>