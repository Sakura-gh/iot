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
            allData: null,
        }
    },
    mounted() {
        this.initChart()
        this.getData()
        window.addEventListener('resize', this.screenAdapter)
        this.screenAdapter()
    },
    destroyed() {
        window.removeEventListener('resize', this.screenAdapter)
    },
    methods: {
        initChart() {
            this.chartInstance = this.$echarts.init(this.$refs.online_status_ref)
            const initOption = {}
            this.chartInstance.setOption(initOption)
        },
        async getData() {
            // this.allData 
            this.updateChart()
        },
        updateChart() {
            // 处理数据
            const dataOption = {}
            this.chartInstance.setOption(dataOption)
        },
        screenAdapter() {
            const adapterOption = {}
            this.chartInstance.setOption(adapterOption)
            this.chartInstance.resize();
        }
    },

}
</script>

<style>

</style>