package xyz.linyh.DTO;

import lombok.Data;
import xyz.linyh.entity.Setmeal;
import xyz.linyh.entity.SetmealDish;

import java.util.List;

@Data
public class setMealAndDishDTO extends Setmeal {

    private List<SetmealDish> setmealDishes;
}
