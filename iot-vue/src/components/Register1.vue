<template>
  <div class="register">
    <div class="top"></div>
    <el-card class="card" shadow="always">
      <el-col>
        <el-row>
          <span class="title">用户名:</span>
          <el-input class="input"></el-input>
        </el-row>
        <el-row>
          <span class="title">密码:</span>
          <el-input class="input"></el-input>
        </el-row>
        <el-row>
          <span class="title">邮箱:</span>
          <el-input class="input"></el-input>
        </el-row>
      </el-col>
    </el-card>
  </div>
</template>

<script>
export default {};
</script>

<style>
.register {
  width: 100%;
  height: 100%;
}
.top {
  height: 25%;
}
.card {
  border-radius: 15px;
  background-clip: padding-box;
  /* padding: 35px 35px 15px 35px; */
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;

  width: 600px;
  height: 300px;
  margin: auto;
  /* text-align: center; */
}
/* .title {
  width: 100px;
  text-align: right;
  border: 10px red;
} */
.input {
  width: 300px;
  text-align: right;
  margin: 10px auto;
}
</style>