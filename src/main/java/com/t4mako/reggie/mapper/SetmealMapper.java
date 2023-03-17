package com.t4mako.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.t4mako.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author T4mako
 * @date 2023/3/6 15:50
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
}
