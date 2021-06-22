<template>
  <div class="login">
    <div style="height: 30%"></div>
    <el-card class="card" shadow="always">
      <h3 class="title">IoT系统登录</h3>
      <el-row>
        <el-input
          placeholder="用户名"
          style="margin: 10px auto; width: 80%"
          v-model="username"
        ></el-input>
        <el-input
          placeholder="密码"
          style="margin: 10px auto; width: 80%"
          v-model="password"
        ></el-input>
        <el-button
          type="danger"
          style="margin: 10px auto; width: 60%"
          @click="login"
        >
          登录
        </el-button>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: null,
      password: null,
    };
  },
  methods: {
    async login() {
      console.log(this.$store.state);
      var { data: response } = await this.$http.get("login", {
        params: {
          username: this.username,
          password: this.password,
        },
      });
      // 后端验证成功，返回true
      if (response) {
        var user = {
          username: this.username,
          password: this.password,
        };
        // 触发store中的login()方法，把user对象传递给store中的user对象
        this.$store.commit("login", user);
        // 获取登录前的页面路径并跳转，如果该路径不存在，就跳转到首页
        var path = this.$route.query.redirect;
        this.$router.replace({
          path: path == "/" || path == undefined ? "/index" : path,
        });
      }
      // 后端验证失败
      else {
        alert("账号或密码错误，请重试！");
      }
    },
  },
};
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