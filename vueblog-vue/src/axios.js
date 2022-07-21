// 定义全局axios拦截器
import axios from 'axios'
import ElementUI from 'element-ui'
import router from './router'
import store from './store'

axios.defaults.baseURL = 'http://localhost:8081'

// 前置拦截
axios.interceptors.request.use(config => {
  // 可以统一设置请求头
  // console.log("前置拦截")
  return config;
})

// 后置拦截
axios.interceptors.response.use(response => {
    const res = response.data;
    if(res.code === 200){
      return response;
    }else {
      ElementUI.Message.error(res.msg)
      // 直接拒绝往下面返回结果信息
      return Promise.reject(res.msg)
    }
  },
  error => {
    console.log('err' + error)// for debug

    // 401 未登录 清空storage 跳转到登录页面
    if(error.response.status === 401){
      store.commit("REMOVE_INFO");
      router.push("/login")
    }

    ElementUI.Message.error(error.response.data.msg)
    return Promise.reject(error)
  })
