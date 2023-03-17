package com.t4mako.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.reggie.common.CustomException;
import com.t4mako.reggie.dto.DishDto;
import com.t4mako.reggie.dto.SetmealDto;
import com.t4mako.reggie.entity.Dish;
import com.t4mako.reggie.entity.Setmeal;
import com.t4mako.reggie.entity.SetmealDish;
import com.t4mako.reggie.mapper.SetmealMapper;
import com.t4mako.reggie.service.SetmealDishService;
import com.t4mako.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author T4mako
 * @date 2023/3/6 15:52
 */
@Service
@Slf4j
public class SetMealSericeImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    //新增套餐，同时需要保存套餐和菜品的关联关系
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关联信息，操作setmeal_dish,执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }

    //删除套餐,同时删除套餐和菜品的关联数据
    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态，确定是否可用删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //SQL:select * from setmeal where id in (1,2,3...) and status = 1;
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        //如果不能删除，抛出一个业务异常信息
        if(count > 0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        //如果可以删除，先删除套餐表中的数据---setmeal
        this.removeByIds(ids);
        //删除关系表中的数据---setmeal_dish
        //SQL:delete from setmeal_dish where setmeal_id in (1,2,3... ids);
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(lambdaQueryWrapper);
    }

    //获取套餐以及菜品
    @Override
    public SetmealDto getByIdWithDish(Long id) {
        //查询套餐基本信息
        Setmeal setmeal = this.getById(id);
        //对象拷贝
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);

        //查询当前套餐的所有菜品,从setmeal_dish中查询
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        //SQL:select * from setmeal_dish where setmeal_id = id;
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        //查询
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        //添加属性
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    //更新套餐信息，同时更新对应的菜品信息
    @Transactional
    @Override
    public void updateWithDish(SetmealDto setmealDto){
        //更新基础信息
        this.updateById(setmealDto);

        //清理当前套餐的菜品信息
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        //删除setmeal_dish表中setmealid与setmealDto的id相同的菜品
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(queryWrapper);
        //设置新的菜品信息，插入新的菜品到setmeal_dish表中
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //setmeal_id无值，将setmealDto的id赋给setmealId
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        /*setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());*/
        setmealDishService.saveBatch(setmealDishes);
    }


}
