import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
export default new Vuex.Store({
  // 数据，可以共享访问
  state: {
    token: '',
    userInfo: JSON.parse(sessionStorage.getItem("userInfo")) // 初始化
  },
  // 方法，可以改变属性值 类似于set
  mutations: {
    // 给token赋值
    SET_TOKEN: (state, token) => {
      state.token = token;
      // 放入localStorage中
      localStorage.setItem("token", token);
    },
    // 给userInfo赋值
    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo;
      // 放入sessionStorage中   localStorage中不能存对象  转为json
      sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
    },
    // 删除信息
    REMOVE_INFO: (state) => {
      state.token = '';
      state.userInfo = {};
      localStorage.setItem("token", '');
      sessionStorage.setItem("userInfo", JSON.stringify(''));
    }
  },
  // getter
  getters: {
    // 获取用户信息
    getUser: state => {
      return state.userInfo
    }
  },
  // 异步操作
  actions: {
    // 调用mutations里面的方法来改变数据
  },
  // 模块
  modules: {}
})
