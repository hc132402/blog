<template>
  <div>
    <Header></Header>

    <div class="mcontent">

      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>

        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <mavon-editor v-model="ruleForm.content"/>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">发表</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header";

export default {
  name: "BlogEdit",
  components: {Header},
  data() {
    return {
      ruleForm: {
        id: null,
        title: '',
        description: '',
        content: ''
      },
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
          {min: 1, max: 25, message: '长度在 1 到 25 个字符', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入摘要', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入内容', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this;
          // 发送发表博客请求
          this.ruleForm.created = '';
          this.$axios.post("/blog/edit", this.ruleForm, {
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          }).then((res) => {
            if(res.data.code === 200){
              this.$message.success(res.data.msg);
              this.$router.push("/blogs")
            }else {
              this.$message.error(res.data.msg);
            }
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
  },
  created() {
    const blogId = this.$route.params.blogId;
    const _this = this;
    if(blogId){
      this.$axios.get("/blog/"+blogId).then((res)=>{
        // _this.ruleForm.id = res.data.data.id;
        _this.ruleForm = res.data.data;
      })
    }
  }
}
</script>

<style scoped>
.mcontent {
  text-align: center;
}
</style>
