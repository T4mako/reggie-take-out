package com.t4mako.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4mako.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author T4mako
 * @date 2023/2/26 15:57
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> { //继承baseMapper，继承常见的增删改查
}
