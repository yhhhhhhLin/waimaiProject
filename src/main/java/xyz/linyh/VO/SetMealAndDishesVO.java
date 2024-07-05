package xyz.linyh.VO;

import lombok.Data;
import xyz.linyh.entity.Dish;
import xyz.linyh.entity.SetmealDish;

import java.util.List;

@Data
public class SetMealAndDishesVO {

    private int price;
    private int status;
    private String image;

    private Long categoryId;

    private List<SetmealDish> setmealDishes;
}
