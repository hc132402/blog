package com.hc.controller;


import com.hc.common.lang.Result;
import com.hc.entity.User;
import com.hc.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hc
 * @since 2022-07-17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication // 验证用户是否登录
    @GetMapping("/index")
    public Result index(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){

        return Result.success(user);
    }
}
