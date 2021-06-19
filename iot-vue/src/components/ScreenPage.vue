<template>
  <div class="screen-container">
    <header class="screen-header">
      <div>
        <!-- <img src="/static/img/header_border_dark.png" alt="" /> -->
      </div>
      <span class="logo">
        <!-- <img src="/static/img/logo_dark.png" alt="" /> -->
      </span>
      <span class="title">IOT可视化系统</span>
      <div class="title-right">
        <!-- <img src="/static/img/qiehuan_dark.png" class="qiehuan" /> -->
        <span class="datetime">2049-01-01 00:00:00</span>
      </div>
    </header>
    <div class="screen-body">
      <section class="screen-left">
        <div id="left-top" :class="[status.dataInfo ? 'fullScreen' : '']">
          <!-- 销量趋势图表 -->
          <DataInfo ref="dataInfo"></DataInfo>
          <div class="resize">
            <i
              @click="changeSize('dataInfo')"
              class="el-icon-copy-document"
            ></i>
          </div>
        </div>
        <div id="left-bottom">
          <!-- 商家销售金额图表 -->
          <div class="resize">
            <i class="el-icon-copy-document"></i>
          </div>
        </div>
      </section>
      <section class="screen-middle">
        <div id="middle-top" :class="[status.trackMap ? 'fullScreen' : '']">
          <!-- 商家分布图表 -->
          <TrackMap ref="trackMap"></TrackMap>
          <div class="resize">
            <i
              @click="changeSize('trackMap')"
              class="el-icon-copy-document"
            ></i>
          </div>
        </div>
        <div id="middle-bottom">
          <!-- 地区销量排行图表 -->
          <div class="resize">
            <i class="el-icon-copy-document"></i>
          </div>
        </div>
      </section>
      <section class="screen-right">
        <div id="right-top">
          <!-- 热销商品占比图表 -->
          <div class="resize">
            <i class="el-icon-copy-document"></i>
          </div>
        </div>
        <div
          id="right-bottom"
          :class="[status.onlineStatus ? 'fullScreen' : '']"
        >
          <!-- 库存销量分析图表 -->
          <OnlineStatus ref="onlineStatus"></OnlineStatus>
          <div class="resize">
            <i
              @click="changeSize('onlineStatus')"
              class="el-icon-copy-document"
            ></i>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import DataInfo from "@/components/DataInfo";
import OnlineStatus from "@/components/OnlineStatus";
import TrackMap from "@/components/TrackMap";
export default {
  data() {
    return {
      // 每个图表全局状态的定义
      status: {
        dataInfo: false,
        onlineStatus: false,
        trackMap: false,
      },
    };
  },
  methods: {
    changeSize(chartName) {
      // 1. 改变status
      this.status[chartName] = !this.status[chartName];
      // 2. 调用每个图表组件的screenAdapter，使大小正常化
      // this.$refs[chartName].screenAdapter()
      // 当数据变化后，只有等到组件更新完成后，再去调用屏幕自适应方法
      this.$nextTick(() => {
        this.$refs[chartName].screenAdapter();
      });
    },
  },
  components: {
    DataInfo,
    OnlineStatus,
    TrackMap,
  },
};
</script>

<style lang="less" scoped>
.fullScreen {
  position: fixed !important;
  top: 0 !important;
  left: 0 !important;
  width: 100% !important;
  height: 100% !important;
  margin: 0 !important;
  z-index: 100;
}
.screen-container {
  width: 100%;
  height: 100%;
  padding: 0 20px;
  background-color: #161522;
  color: #fff;
  box-sizing: border-box;
}
.screen-header {
  width: 100%;
  height: 64px;
  font-size: 20px;
  position: relative;
  > div {
    img {
      width: 100%;
    }
  }
  .title {
    position: absolute;
    left: 50%;
    top: 50%;
    font-size: 20px;
    transform: translate(-50%, -50%);
  }
  .title-right {
    display: flex;
    align-items: center;
    position: absolute;
    right: 0px;
    top: 50%;
    transform: translateY(-80%);
  }
  .qiehuan {
    width: 28px;
    height: 21px;
    cursor: pointer;
  }
  .datetime {
    font-size: 15px;
    margin-left: 10px;
  }
  .logo {
    position: absolute;
    left: 0px;
    top: 50%;
    transform: translateY(-80%);
    img {
      height: 35px;
      width: 128px;
    }
  }
}
.screen-body {
  width: 100%;
  height: 100%;
  display: flex;
  margin-top: 10px;
  .screen-left {
    height: 100%;
    width: 27.6%;
    #left-top {
      height: 53%;
      position: relative;
    }
    #left-bottom {
      height: 31%;
      margin-top: 25px;
      position: relative;
    }
  }
  .screen-middle {
    height: 100%;
    width: 41.5%;
    margin-left: 1.6%;
    margin-right: 1.6%;
    #middle-top {
      width: 100%;
      height: 56%;
      position: relative;
    }
    #middle-bottom {
      margin-top: 25px;
      width: 100%;
      height: 28%;
      position: relative;
    }
  }
  .screen-right {
    height: 100%;
    width: 27.6%;
    #right-top {
      height: 46%;
      position: relative;
    }
    #right-bottom {
      height: 38%;
      margin-top: 25px;
      position: relative;
    }
  }
}
.resize {
  position: absolute;
  right: 20px;
  top: 20px;
  cursor: pointer;
}
section > div {
  border: 1px solid red;
}
</style>