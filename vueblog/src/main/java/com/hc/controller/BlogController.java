package com.hc.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.common.lang.Result;
import com.hc.entity.Blog;
import com.hc.service.BlogService;
import com.hc.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hc
 * @since 2022-07-17
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    /**
     * 分页查询博客
     */
    @GetMapping("/blogs")
    public Result findAllBlogs(@RequestParam Integer currentPage){
        // currentPage默认为1
        if(currentPage == null || currentPage < 1 ) currentPage = 1;

        Page<Blog> page = new Page<>(currentPage, 5);

        IPage<Blog> pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.success(pageData);
    }

    /**
     * 查询单个博客
     */
    @GetMapping("/blog/{id}")
    public Result findBlogById(@PathVariable(name = "id") Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog.getId(), "该博客已删除");
        return Result.success(blog);
    }

    /**
     * 编辑博客 & 新增博客
     */
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result editBolg(@Validated @RequestBody Blog blog){
        // 判断参数中是否有id   有id是编辑  无id是新增
        Blog temp = null;
        if(blog.getId() != null){
            // 判断是否有权限编辑
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtils.getProfile().getId().longValue(), "没有权限编辑");

        }else {
            // 新增
            temp = new Blog();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        // 复制属性值
        BeanUtil.copyProperties(blog, temp,"id","userId","created","status");

        blogService.saveOrUpdate(temp);
        return Result.success(null);
    }

    @RequiresAuthentication
    @DeleteMapping("/blog/{id}")
    public Result delete(@PathVariable(name = "id")Long id){
        // 只有自己能删除
        Blog blog = blogService.getById(id);
        Assert.isTrue(blog.getUserId().longValue() == ShiroUtils.getProfile().getId().longValue(),"没有权限删除");

        blogService.removeById(id);
        return Result.success(null);
    }

}
