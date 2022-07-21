import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

import Login from '../views/Login'
import BlogDetail from '../views/BlogDetail'
import BlogEdit from '../views/BlogEdit'
//import Blogs from '../views/Blogs'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      redirect: { name: 'Blogs' }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/blogs',
      name: 'Blogs',
      // 懒加载
      component: () => import('../views/Blogs')
    },
    {
      path: '/blog/add',  // 注意放在 path: '/blog/:blogId'之前
      name: 'BlogAdd',
      meta: {
        requireAuth: true  // 登录权限
      },
      component: BlogEdit
    },
    {
      path: '/blog/:blogId',
      name: 'BlogDetail',
      component: BlogDetail
    },
    {
      path: '/blog/:blogId/edit',
      name: 'BlogEdit',
      meta: {
        requireAuth: true // 登录权限
      },
      component: BlogEdit
    }
  ],
  mode:"history"
})
