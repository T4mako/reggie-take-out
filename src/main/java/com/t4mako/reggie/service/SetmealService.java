package com.t4mako.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t4mako.reggie.dto.DishDto;
import com.t4mako.reggie.dto.SetmealDto;
import com.t4mako.reggie.entity.Setmeal;
import com.t4mako.reggie.entity.SetmealDish;

import java.util.List;

/**
 * @author T4mako
 * @date 2023/3/6 15:51
 */
public interface SetmealService extends IService<Setmeal> {
    //新增套餐，同时需要保存套餐和菜品的关联关系
    public void saveWithDish(SetmealDto setmealDto);

    //删除套餐,同时删除套餐和菜品的关联数据
    public void removeWithDish(List<Long> ids);

    //获取套餐以及菜品
    public SetmealDto getByIdWithDish(Long id);

    //更新套餐信息，同时更新对应的菜品信息
    public void updateWithDish(SetmealDto setmealDto);
}
