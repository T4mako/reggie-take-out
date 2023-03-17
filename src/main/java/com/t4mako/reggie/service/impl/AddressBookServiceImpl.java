package com.t4mako.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.reggie.entity.AddressBook;
import com.t4mako.reggie.mapper.AddressBookMapper;
import com.t4mako.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author T4mako
 * @date 2023/3/12 10:06
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
