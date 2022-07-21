package com.hc.service.impl;

import com.hc.entity.Blog;
import com.hc.mapper.BlogMapper;
import com.hc.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hc
 * @since 2022-07-17
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
