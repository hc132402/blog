<template>
  <div>
    <Header></Header>
    <div class="block">

      <el-timeline>
        <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs" v-bind:key="blog.id">

          <el-card>

            <router-link :to="{name:'BlogDetail',params:{blogId:blog.id}}">
              <h4>{{ blog.title }}</h4>
            </router-link>

            <p>{{ blog.description }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>

      <!--分页-->
      <el-pagination class="m-page"
                     background
                     layout="prev, pager, next"
                     :current-page=pagination.currentPage
                     :page-size=pagination.pageSize
                     @current-change=page
                     :total="pagination.total">
      </el-pagination>

    </div>
  </div>
</template>

<script>
import Header from "../components/Header";

export default {
  name: "Blogs",
  components: {Header},
  data() {
    return {
      blogs: {}, // 文章数据
      pagination :{ // 分页
        currentPage: 1,
        total: 0,
        pageSize: 5
      }
    }
  },
  methods: {
    page(currentPage) {
      const _this = this;
      this.$axios.get("/blogs?currentPage=" + currentPage).then((res) => {
        _this.blogs = res.data.data.records;
        _this.pagination.total = res.data.data.total;
        _this.pagination.currentPage = res.data.data.current;
        _this.pagination.pageSize = res.data.data.size;
      })
    }
  },
  mounted() {
    this.page(1)
  }
}
</script>

<style scoped>
.m-page {
  text-align: center;
  margin: 0 auto;
}
</style>
