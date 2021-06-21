<template>
  <div class="screen-container">
    <header class="screen-header">
      <div><MenuBar class="menu"></MenuBar></div>
      <div>
        <span class="title-left">author: gehao</span>
      </div>
      <div>
        <img src="@/assets/img/header_border_dark.png" alt="" />
      </div>
      <span class="title">IOT可视化系统</span>
      <div class="title-right">
        <span class="datetime" ref="time"></span>
      </div>
    </header>
    <div class="screen-body">
      <section class="screen-left">
        <div id="left-top" :class="[status.dataInfo ? 'fullScreen' : '']">
          <DataInfo ref="dataInfo"></DataInfo>
          <div class="resize">
            <i
              @click="changeSize('dataInfo')"
              class="el-icon-copy-document"
            ></i>
          </div>
        </div>
        <div
          id="left-bottom"
          :class="[status.totalDataInfo ? 'fullScreen' : '']"
        >
          <TotalDataInfo ref="totalDataInfo"></TotalDataInfo>
          <div class="resize">
            <i
              @click="changeSize('totalDataInfo')"
              class="el-icon-copy-document"
            >
            </i>
          </div>
        </div>
      </section>
      <section class="screen-middle">
        <div id="middle-top" :class="[status.trackMap ? 'fullScreen' : '']">
          <TrackMap ref="trackMap"></TrackMap>
          <div class="resize">
            <i
              @click="changeSize('trackMap')"
              class="el-icon-copy-document"
            ></i>
          </div>
        </div>
        <div
          id="middle-bottom"
          :class="[status.onlineStatus ? 'fullScreen' : '']"
        >
          <OnlineStatus ref="onlineStatus"></OnlineStatus>
          <div class="resize">
            <i
              @click="changeSize('onlineStatus')"
              class="el-icon-copy-document"
            ></i>
          </div>
        </div>
      </section>
      <section class="screen-right">
        <div id="right-top" :class="[status.valueInfo ? 'fullScreen' : '']">
          <ValueInfo ref="valueInfo"></ValueInfo>
          <div class="resize">
            <i @click="changeSize('valueInfo')" class="el-icon-copy-document">
            </i>
          </div>
        </div>
        <div id="right-bottom" :class="[status.valueInfo2 ? 'fullScreen' : '']">
          <ValueInfo2 ref="valueInfo2"></ValueInfo2>
          <div class="resize">
            <i
              @click="changeSize('valueInfo2')"
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
import TotalDataInfo from "@/components/TotalDataInfo";
import ValueInfo from "@/components/ValueInfo";
import ValueInfo2 from "@/components/ValueInfo2";
import OnlineStatus from "@/components/OnlineStatus";
import TrackMap from "@/components/TrackMap";
import MenuBar from "@/components/MenuBar";

export default {
  data() {
    return {
      // 每个图表全局状态的定义
      status: {
        dataInfo: false,
        totalDataInfo: false,
        valueInfo: false,
        valueInfo2: false,
        onlineStatus: false,
        trackMap: false,
      },
      timerId: null,
    };
  },
  mounted() {
    this.showTime();
  },
  destroyed() {
    clearInterval(this.timerId);
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
    showTime() {
      this.timerId = setInterval(this.updateTime, 1000);
    },
    updateTime() {
      var timeSpan = this.$refs.time;
      var date = new Date();
      timeSpan.innerHTML =
        date.getFullYear() +
        "/" +
        date.getMonth() +
        "/" +
        date.getDay() +
        " " +
        date.getHours() +
        ":" +
        (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) +
        ":" +
        (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds());
    },
  },
  components: {
    DataInfo,
    TotalDataInfo,
    ValueInfo,
    ValueInfo2,
    OnlineStatus,
    TrackMap,
    MenuBar,
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
  .menu {
    display: flex;
    position: absolute;
    left: 0%;
    top: 10%;
  }
  .title-left {
    display: flex;
    align-items: center;
    position: absolute;
    left: 5%;
    top: 50%;
    font-size: 15px;
    transform: translateY(-80%);
  }
  .title-right {
    display: flex;
    align-items: center;
    position: absolute;
    right: 0px;
    top: 50%;
    transform: translateY(-80%);
  }
  .datetime {
    font-size: 15px;
    margin-left: 10px;
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
      height: 34%;
      position: relative;
    }
    #left-bottom {
      height: 50%;
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
      height: 34%;
      position: relative;
    }
    #right-bottom {
      height: 50%;
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
// section > div {
//   border: 1px solid red;
// }
</style>