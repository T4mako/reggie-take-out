package com.t4mako.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.reggie.entity.DishFlavor;
import com.t4mako.reggie.mapper.DishFlavorMapper;
import com.t4mako.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author T4mako
 * @date 2023/3/7 19:33
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper,DishFlavor> implements DishFlavorService {
}
