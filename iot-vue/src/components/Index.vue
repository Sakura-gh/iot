<template>
  <div class="index">
    <div class="top"></div>
    <h1 class="title">IoT物联网应用网站</h1>
    <div class="author">
      <span>葛浩</span>
      <span>计算机科学与技术</span>
    </div>
    <div class="icon">
      <span>
        <a href="https://github.com/Sakura-gh/iot">
          <img src="../assets/img/github.png" style="width: 20px" />
        </a>
      </span>
      <span>
        <a href="mailto:Sakura.gehao@gmail.com">
          <img src="../assets/img/email.png" style="width: 20px" />
        </a>
      </span>
    </div>
    <MenuBar class="menu"></MenuBar>
  </div>
</template>

<script>
import MenuBar from "@/components/MenuBar";
export default {
  data() {
    return {};
  },
  methods: {},
  components: {
    MenuBar,
  },
};
</script>

<style lang="css">
.index {
  width: 100%;
  height: 100%;
  text-align: center;
  background-image: url("../assets/img/bg.png");
}
.top {
  height: 25%;
}
.title {
  color: white;
  text-shadow: 3px 3px 2px #cac6c6; /* 文字阴影：水平偏移、垂直偏移、阴影模糊距离 */
}
.author {
  color: white;
  text-shadow: 1px 1px 1px #cac6c6;
  font-weight: bold;
}
.icon {
  width: 100px;
  margin: 10px auto;
}
.menu {
  /* width: 20px;
  margin: 20px auto; */
}
</style>