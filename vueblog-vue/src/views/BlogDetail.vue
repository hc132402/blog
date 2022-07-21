<template>
  <div>
    <Header></Header>
    <div class="mblog">
      <h3>{{ blog.title }}</h3>

      <el-link icon="el-icon-edit" v-show="ownBlog">
        <router-link :to="{name:'BlogEdit',params:{blogId: blog.id}}">
          编辑
        </router-link>
      </el-link>

      <span  v-show="ownBlog">
        <el-divider direction="vertical"></el-divider>
      </span>


      <el-link icon="el-icon-circle-close" v-show="ownBlog" @click="deleteArticle(blog.id)">
        删除
      </el-link>

      <el-divider></el-divider>
      <div class="content markdown-body" v-html="blog.content">
      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header";
import 'github-markdown-css/github-markdown.css'

export default {
  name: "BlogDetail",
  components: {Header},
  data() {
    return {
      blog: {
        id: null,
        title: '标题',
        description: "摘要",
        content: "描述"
      },
      ownBlog: false
    }
  },
  created() {
    const blogId = this.$route.params.blogId;
    const _this = this;
    if (blogId) {
      this.$axios.get("/blog/" + blogId).then((res) => {
        _this.blog = res.data.data
        var MarkdownIt = require('markdown-it'),
          md = new MarkdownIt();
        var result = md.render(res.data.data.content);

        _this.blog.content = result
        _this.ownBlog = (_this.blog.userId === _this.$store.getters.getUser.id)
      })
    }
  },
  methods:{
    deleteArticle(id){
      // 弹出确认窗口
      const _this = this;
      this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete("/blog/"+id,{
          headers: {
            "Authorization": localStorage.getItem("token")
          }
        }).then(res=>{
          if(res.data.code === 200){
            this.$message.success(res.data.msg)
          }else {
            this.$message.error(res.data.msg)
          }
        }).finally(()=>{
          _this.$router.push("/blogs")
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  }
}
</script>

<style scoped>
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  min-height: 700px;
  padding: 20px 15px;
}
</style>
