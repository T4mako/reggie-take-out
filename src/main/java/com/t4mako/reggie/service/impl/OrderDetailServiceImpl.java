package com.t4mako.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.reggie.entity.OrderDetail;
import com.t4mako.reggie.mapper.OrderDetailMapper;
import com.t4mako.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author T4mako
 * @date 2023/3/12 13:44
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper,OrderDetail> implements OrderDetailService {
}
