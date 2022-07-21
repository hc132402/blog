<template>
  <div class="m-content">
    <h3>欢迎来到小火车的博客</h3>
    <div class="block">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
    </div>
    <div>{{ user.username }}</div>
    <div class="m-action">
      <span><el-link href="/blogs">主页</el-link></span>

      <el-divider direction="vertical"></el-divider>
      <span><el-link type="success" href="/blog/add">发表博客</el-link></span>

      <el-divider direction="vertical"></el-divider>
      <span v-show="!hasLogin" ><el-link type="primary" href="/login">登录</el-link></span>

      <span v-show="hasLogin" ><el-link type="danger" @click.prevent="logout">退出</el-link></span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: {
        username: '请登录',
        avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
      },
      hasLogin:false
    }
  },
  methods: {
    // 退出
    logout() {
      const _this = this;

      this.$axios.get("/logout", {
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        // 清除storage数据即可
        _this.$store.commit("REMOVE_INFO")
        _this.$router.push("/login")
      })
    }
  },
  created() {
    // 数据回显
    if (this.$store.getters.getUser.username) {
      this.user.username = this.$store.getters.getUser.username;
      this.user.avatar = this.$store.getters.getUser.avatar;

      this.hasLogin = true;
    }
  }
}
</script>

<style scoped>
.m-content {
  text-align: center;
  max-width: 900px;
  margin: 0 auto;
}

.m-action {
  margin: 10px 0;
}
</style>
