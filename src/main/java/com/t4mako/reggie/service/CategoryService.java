package com.t4mako.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t4mako.reggie.entity.Category;

/**
 * @author T4mako
 * @date 2023/3/6 14:56
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
