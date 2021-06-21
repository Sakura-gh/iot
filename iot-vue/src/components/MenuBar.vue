<template>
  <div>
    <el-button
      type="menu"
      icon="el-icon-s-unfold"
      @click="drawer = true"
      circle
    ></el-button>
    <el-drawer :visible.sync="drawer" direction="ltr" class="drawer">
      <el-menu
        :default-active="activeIndex"
        mode="vertical"
        router
        @select="handleSelect"
        @open="handleOpen"
        @close="handleClose"
      >
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-s-data"></i>
            <span>IOT可视化界面</span>
          </template>
          <!-- <el-submenu index="1-0">
            <template slot="title">
              <i class="el-icon-bell"></i>
              <span>主界面</span>
            </template>
          </el-submenu> -->
          <el-menu-item index="/screen">
            <i class="el-icon-s-platform"></i>
            <span>可视化主界面</span>
          </el-menu-item>
          <el-submenu index="1-1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>地图信息可视化模块</span>
            </template>
            <el-menu-item index="/trackmap">各设备位置信息</el-menu-item>
            <el-menu-item index="/trackmap">设备位置轨迹</el-menu-item>
          </el-submenu>
          <el-submenu index="1-2">
            <template slot="title">
              <i class="el-icon-s-promotion"></i>
              <span>接收数据可视化模块</span>
            </template>
            <el-menu-item index="/datainfo">各设备接收的数据量</el-menu-item>
            <el-menu-item index="/totaldatainfo">接收的数据总量</el-menu-item>
          </el-submenu>
          <el-submenu index="1-3">
            <template slot="title">
              <i class="el-icon-s-marketing"></i>
              <span>设备value可视化模块</span>
            </template>
            <el-menu-item index="/valueinfo">各设备最新value值</el-menu-item>
            <el-menu-item index="/valueinfo2">各设备value变化趋势</el-menu-item>
          </el-submenu>
          <el-submenu index="1-4">
            <template slot="title">
              <i class="el-icon-bell"></i>
              <span>设备在线情况可视化模块</span>
            </template>
            <el-menu-item index="/onlinestatus">设备在线数量</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-menu-item index="/query">
          <i class="el-icon-search"></i>
          <span slot="title">设备数据查询界面</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-setting"></i>
          <span slot="title">设备信息配置界面</span>
        </el-menu-item>
        <el-menu-item index="4">
          <i class="el-icon-user"></i>
          <span slot="title">登录注册界面</span>
        </el-menu-item>
      </el-menu>
    </el-drawer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      drawer: false,
      activeIndex: "1",
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleSelect(key, keyPath) {
      console.log(key);
      console.log(keyPath);
    },
  },
};
</script>

<style lang="css">
.drawer {
  /* 这个背景图的效果是：当点击按钮弹出drawer时，整个页面都会显示该图片，
  原先的表格啥的都隐藏，只有drawer再次被隐藏时，其余内容才会被正常显示*/
  background-image: url("../assets/img/bg.png");
}
.el-button--menu.is-active,
.el-button--menu:active {
  background: #20b2aa;
  border-color: #20b2aa;
  color: #fff;
}

.el-button--menu:focus,
.el-button--menu:hover {
  background: #48d1cc;
  border-color: #48d1cc;
  color: #fff;
}

.el-button--menu {
  color: #fff;
  background-color: #81e0dc;
  border-color: #81e0dc;
}
.group {
  font-size: 15px;
}
</style>