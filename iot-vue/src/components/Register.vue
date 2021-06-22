<template>
  <div class="register">
    <div class="top"></div>
    <el-card class="card" shadow="always">
      <h1 class="title">用户注册</h1>
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
      >
        <el-form-item label="姓名" prop="username">
          <el-input
            v-model="ruleForm.username"
            autocomplete="off"
            class="input"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            type="password"
            v-model="ruleForm.password"
            autocomplete="off"
            class="input"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input
            type="password"
            v-model="ruleForm.checkPassword"
            autocomplete="off"
            class="input"
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="ruleForm.email"
            autocomplete="off"
            class="input"
          ></el-input>
        </el-form-item>
        <el-form-item class="button">
          <el-button type="primary" @click="submitForm('ruleForm')">
            提交
          </el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    var checkUsername = async (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入用户名"));
      } else if (value.length <= 3) {
        callback(new Error("用户名长度必须大于3"));
      } else {
        var { data: exist } = await this.$http.get("check-username", {
          params: {
            username: value,
          },
        });
        if (exist) {
          callback(new Error("用户名已存在"));
        } else {
          callback();
        }
      }
    };
    var validatePassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (value.length <= 6) {
        callback(new Error("密码长度必须大于6"));
      } else {
        if (this.ruleForm.checkPassword !== "") {
          this.$refs.ruleForm.validateField("checkPassword");
        }
        callback();
      }
    };
    var validatePassword2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    var checkEmail = async (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("邮箱不能为空"));
      } else {
        var pattern =
          /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        var isEmail = pattern.test(value);
        if (!isEmail) {
          callback(new Error("邮箱格式错误"));
        } else {
          var { data: exist } = await this.$http.get("check-email", {
            params: {
              email: value,
            },
          });
          if (exist) {
            callback(new Error("邮箱已存在"));
          } else {
            callback();
          }
        }
      }
    };
    return {
      ruleForm: {
        username: "",
        password: "",
        checkPassword: "",
        email: "",
      },
      rules: {
        username: [{ validator: checkUsername, trigger: "blur" }],
        password: [{ validator: validatePassword, trigger: "blur" }],
        checkPassword: [{ validator: validatePassword2, trigger: "blur" }],
        email: [{ validator: checkEmail, trigger: "blur" }],
      },
    };
  },
  methods: {
    async submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var { data: ret } = this.$http.get("register", {
            params: {
              username: this.ruleForm.username,
              password: this.ruleForm.password,
              email: this.ruleForm.email,
            },
          });
          alert("注册成功！");
          this.$router.replace("/login");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style>
.register {
  width: 100%;
  height: 100%;
  text-align: center;
  background-image: url("../assets/img/bg.png");
}
.top {
  height: 20%;
}
.card {
  opacity: 0.7;
  border-radius: 15px;
  background-clip: padding-box;
  /* padding: 35px 35px 15px 35px; */
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;

  width: 500px;
  height: 450px;
  margin: auto;
  text-align: center;
}
.title {
}
.input {
  width: 85%; /* 输入框宽度是卡片的85% */
}
.button {
  text-align: right;
  margin: 40px;
}
</style>