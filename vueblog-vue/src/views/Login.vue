<template>
  <div>
    <el-container>

      <el-header>
        <img class="mlogo" src="https://s1.aigei.com/src/img/png/a0/a049219b3f034edf86954db66aa433bf.png?imageMogr2/auto-orient/thumbnail/!237x237r/gravity/Center/crop/237x237/quality/85/&e=1735488000&token=P7S2Xpzfz11vAkASLTkfHN7Fw-oOZBecqeJaxypL:fGaKzsedwLnffPyoiTZnexLXt1Q=" alt="">
        <div class="login_span">登 录</div>
      </el-header>

      <el-main>

        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>

        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      ruleForm: {
        username: 'hc',
        password: '111111'
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 保存vue的全局  axios中的this代表axios的请求
          const _this = this;
          this.$axios.post("/login",this.ruleForm).then(res =>{
            const jwt = res.headers["authorization"];
            const userInfo = res.data.data;

            // 赋值到全局Vuex中 数据共享出去
            _this.$store.commit("SET_TOKEN",jwt);
            _this.$store.commit("SET_USERINFO",userInfo);

            // 获取
            // console.log(_this.$store.getters.getUser)

            // 跳转
            this.$router.push("/blogs")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  display:flex;
  justify-content:center;
  align-items:center;
  /*background-color: #B3C0D1;*/
  margin: 50px auto;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  /*background-color: #E9EEF3;*/
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.mlogo{
  height: 75%;
}
.demo-ruleForm{
  max-width: 500px;
  margin: 0 auto;
}
.login_span{
  color: #646e7d;
  font-size: 30px;
  margin-left: 10px;
  font-weight: lighter;
}
</style>
