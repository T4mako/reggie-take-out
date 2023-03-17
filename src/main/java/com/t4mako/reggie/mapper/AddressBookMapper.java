package com.t4mako.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4mako.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author T4mako
 * @date 2023/3/12 10:05
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
