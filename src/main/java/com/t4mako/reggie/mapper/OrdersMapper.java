package com.t4mako.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4mako.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author T4mako
 * @date 2023/3/12 13:34
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
