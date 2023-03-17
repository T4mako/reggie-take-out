package com.t4mako.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4mako.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author T4mako
 * @date 2023/3/11 16:56
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
