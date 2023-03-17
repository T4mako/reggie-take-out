package com.t4mako.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.reggie.entity.User;
import com.t4mako.reggie.mapper.UserMapper;
import com.t4mako.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author T4mako
 * @date 2023/3/11 16:57
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
