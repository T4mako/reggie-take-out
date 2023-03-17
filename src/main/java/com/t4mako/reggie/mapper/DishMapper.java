package com.t4mako.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4mako.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author T4mako
 * @date 2023/3/6 15:49
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
