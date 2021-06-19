<template>
 	<div class="com-container">
		<div class="com-chart" ref="datainfo_ref"></div>
	</div>
</template>

<script>
export default {
	data() {
		return {
            chartInstance: null, // 图表实例
            deviceIds: null, // 设备id
            deviceDataNums: null, // 每台设备对应的数据量
            currentPage: 1, // 当前显示的是第几页 
            totalPage: 0, // 一共有多少页
            numInPage: 3, // 一页显示的设备数
            timerId: null, // 访问服务器定时器
            sildeTimerId: null // 换页定时器id
        }
	},
    // vue生命周期在mounted之后dom结构加载完成
    mounted() {
        this.initChart()
        this.getDataInternal()
        window.addEventListener('resize', this.screenAdapter)
        // 在界面第一次加载完成后，主动进行屏幕大小适配
        this.screenAdapter()
    },
    // 当该组件被销毁时，取消定时器和屏幕大小监听
    destroyed() {
        // 取消定时器
        clearInterval(this.sildeTimerId)
        clearInterval(this.timerId)
        // 取消屏幕大小监听
        window.removeEventListener('resize', this.screenAdapter)
    },
	methods: {
        // 初始化echart instance对象，添加事件监听
        initChart() {
            this.chartInstance = this.$echarts.init(this.$refs.datainfo_ref, 'chalk')
            // 对图表进行初始化配置，包括除了大小和数据之外的配置项
            const initOption = {
                title: {
                    text: 'Ⅰ设备接收数据量统计',
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
                clearInterval(this.sildeTimerId)
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
        // 获取服务器数据
        async getData() {
            // 获取device ids
            var {data: ids} = await this.$http.get('device-ids')
            this.deviceIds = ids
            this.deviceIds.sort((a, b) => {
                return a < b;
            })
            // 获取每台device对应的数据总量
            this.deviceDataNums = new Array()
            for (var i = 0; i < this.deviceIds.length; i++) {
                var {data: num} = await this.$http.get('message-num-by-id', {
                    params: {
                        id: this.deviceIds[i]
                    }
                })
                this.deviceDataNums.push(num)
            }

            // 计算总页数
            this.totalPage = this.deviceIds.length % this.numInPage === 0 ? this.deviceIds.length / this.numInPage : this.deviceIds.length / this.numInPage + 1
            // 更新图表
            this.updateChart()
        },
        // 更新图表
        updateChart() {
            var start = (this.currentPage - 1) * this.numInPage
            var end = this.currentPage * this.numInPage
            var showIds = this.deviceIds.slice(start, end)
            var showDatas = this.deviceDataNums.slice(start, end)
            // 在update阶段，图表的option只需要更新data部分，包括数据的配置项
            const dataOption = {
                xAxis: {
                    data: showIds
                },
                series: [
                    {
                        data: showDatas // 数据
                    }
                ]
            }
            // option可以设置多次，不会覆盖，而是整合
            this.chartInstance.setOption(dataOption)
        },
        // 开启定时器
        sildeInterval() {
            // 如果之前的定时器还开着，就关闭
            if (this.sildeTimerId) {
                clearInterval(this.sildeTimerId)
            }
            // 每隔3s执行一次
            this.sildeTimerId = setInterval(() => {
                this.currentPage++
                if (this.currentPage > this.totalPage) {
                    this.currentPage = 1
                }
            }, 3000)
        },
        // 浏览器大小变化时，进行屏幕适配，包括大小的配置项
        screenAdapter() {
            // console.log(this.$refs.datainfo_ref.offsetWidth)
            // 标题大小
            const titleFontSize = this.$refs.datainfo_ref.offsetWidth / 100 * 3.6
            // 屏幕适配: 和分辨率大小相关的配置项option
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
            // 需要手动调用chart的resize才能对大小产生改变
            this.chartInstance.resize()
        }
    }
}	
</script>

<style>
	
</style>