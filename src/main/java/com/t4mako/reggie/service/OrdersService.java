package com.t4mako.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t4mako.reggie.entity.Orders;

/**
 * @author T4mako
 * @date 2023/3/12 13:34
 */
public interface OrdersService extends IService<Orders> {
    //用户下单
    public void submit(Orders orders);
}
