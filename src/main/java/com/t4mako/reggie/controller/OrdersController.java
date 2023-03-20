package com.t4mako.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import com.t4mako.reggie.common.BaseContext;
import com.t4mako.reggie.common.R;
import com.t4mako.reggie.entity.Orders;
import com.t4mako.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author T4mako
 * @date 2023/3/12 13:39
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    public OrdersService ordersService;

    //用户下单
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    //用户退出
    @GetMapping("/userPage")
    public R<Page> userPage(int page,int pageSize){
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //执行查询
        ordersService.page(pageInfo,queryWrapper);//无需返回，在内部已封装给pageInfo
        return R.success(pageInfo);

    }

    //订单明细分页
    @GetMapping("/page")
    public R<Page> page( int page,
                         int pageSize,
                         @RequestParam(required = false) String number,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime){
        Page pageInfo = new Page(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //查询条件
        if(number != null){
            queryWrapper.like(Orders::getId,number);
        }
        if(beginTime != null){
            queryWrapper.ge(Orders::getOrderTime,beginTime);
        }
        if(endTime!=null){
            queryWrapper.le(Orders::getOrderTime,endTime);
        }
        ordersService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }


}
