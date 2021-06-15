<template>
    <div class="com-container">
        <div class="com-chart" ref="track_map_ref">test</div>
    </div>
</template>

<script>
import 'echarts/dist/extension/bmap.min.js'
export default {
    data() {
        return {
            chartInstance: null
        }
    },
    mounted() {
        this.initChart()
        this.getData()
        window.addEventListener(this.screenAdapter)
        this.screenAdapter()
    },
    destroyed() {
        window.removeEventListener(this.screenAdapter)
    },
    methods: {
        initChart() {
            this.chartInstance = this.$echarts.init(this.$refs.track_map_ref)
            
            const initOption = {
                bmap: {
                    center: [113.844038, 22.907044],//默认中心点;
                    zoom: 12,//缩放级别；
                    roam: false,//是否缩放
                    mapStyle:{style:'hardedge'},
                    mapOptions: {
                        enableMapClick: true//能都是地图可以点击
                    },     
                },
                geo: {                
                    map: 'bmap',                    
                    type: 'map'
                },
                series: []    
            }
            this.chartInstance.setOption(initOption)
        },
        getData() {

        },
        updateChart() {

        },
        screenAdapter() {

        }
    }
}
</script>

<style>

</style>