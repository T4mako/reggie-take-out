package com.t4mako.reggie.dto;


import com.t4mako.reggie.entity.Setmeal;
import com.t4mako.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
