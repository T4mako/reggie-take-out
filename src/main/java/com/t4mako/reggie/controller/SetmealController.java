package com.t4mako.reggie.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4mako.reggie.common.R;
import com.t4mako.reggie.dto.SetmealDto;
import com.t4mako.reggie.entity.Category;
import com.t4mako.reggie.entity.Dish;
import com.t4mako.reggie.entity.Setmeal;
import com.t4mako.reggie.entity.SetmealDish;
import com.t4mako.reggie.service.CategoryService;
import com.t4mako.reggie.service.DishService;
import com.t4mako.reggie.service.SetmealDishService;
import com.t4mako.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author T4mako
 * @date 2023/3/9 13:39
 */
@RequestMapping("/setmeal")
@RestController
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    //新增套餐
    @PostMapping
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("test");
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    //套餐分页查询
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page,int pageSize,String name){
        //构造分页构造器
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>(); //页面展示需要其他属性，使用dto返回
        //查询构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //查询条件
        queryWrapper.like(name != null,Setmeal::getName,name);
        //排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);
        //对pageInfo进行对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category= categoryService.getById(categoryId);
            if(category != null){
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    //删除套餐
    //allEntries = true,删除这个分类（value）下所有的缓存数据
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }

    //停售功能（修改setmeal的status为0）
    @PostMapping("/status/0")
    public R<String> statusTo0(@RequestParam List<Long> ids){
        //条件修改器
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        //SQL:update setmeal set status = 0 where id in (1,2,3);
        updateWrapper.in(Setmeal::getId,ids).set(Setmeal::getStatus,0);
        setmealService.update(updateWrapper);
        return R.success("status更新成功");
    }

    //启售功能（修改setmeal的status为1）
    @PostMapping("/status/1")
    public R<String> statusTo1(@RequestParam List<Long> ids){
        //条件修改器
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        //SQL:update setmeal set status = 1 where id in (1,2,3);
        updateWrapper.in(Setmeal::getId,ids).set(Setmeal::getStatus,1);
        setmealService.update(updateWrapper);
        return R.success("status更新成功");
    }

    //根据id查询套餐以及菜品信息
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);
        return R.success(setmealDto);
    }

    //更新套餐信息，同时更新对应的菜品信息
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return R.success("修改成功");
    }

    //根据条件查询套餐数据
    @GetMapping("/list")
    @Cacheable(value = "setmealCache" , key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,1);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }

    //点击套餐显示详情
    @GetMapping("/dish/{id}")
    public R<List<SetmealDish>> dish(@PathVariable Long id){
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        return R.success(list);
    }
}

