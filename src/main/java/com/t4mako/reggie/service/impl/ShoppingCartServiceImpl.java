package com.t4mako.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.reggie.entity.ShoppingCart;
import com.t4mako.reggie.mapper.ShoppingCartMapper;
import com.t4mako.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author T4mako
 * @date 2023/3/12 11:21
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
