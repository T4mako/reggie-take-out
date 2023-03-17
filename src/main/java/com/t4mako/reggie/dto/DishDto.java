package com.t4mako.reggie.dto;

import com.t4mako.reggie.entity.Dish;
import com.t4mako.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

//给Dish类扩展属性
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
