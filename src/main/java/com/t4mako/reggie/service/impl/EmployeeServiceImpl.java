package com.t4mako.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.reggie.entity.Employee;
import com.t4mako.reggie.mapper.EmployeeMapper;
import com.t4mako.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author T4mako
 * @date 2023/2/26 16:05
 */
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
