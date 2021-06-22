<template>
  <div class="login">
    <div style="height: 30%"></div>
    <el-card class="card" shadow="always">
      <h3 class="title">IoT系统登录</h3>
      <el-row>
        <el-input
          placeholder="用户名"
          style="margin: 10px auto; width: 80%"
        ></el-input>
        <el-input
          placeholder="密码"
          style="margin: 10px auto; width: 80%"
        ></el-input>
        <el-button type="danger" style="margin: 10px auto; width: 60%">
          登录
        </el-button>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {};
</script>

<style lang="css">
.login {
  height: 100%;
  width: 100%;
  background-image: url("../assets/img/bg.png");
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #505458;
}
.card {
  border-radius: 15px;
  background-clip: padding-box;
  /* padding: 35px 35px 15px 35px; */
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;

  width: 450px;
  height: 300px;
  margin: auto;
  text-align: center;
}
</style>