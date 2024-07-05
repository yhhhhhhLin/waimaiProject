package xyz.linyh.VO;

import lombok.Data;
import xyz.linyh.entity.Dish;

import java.util.List;

@Data
public class AllDishVO {
    private List<Dish> dishes;
    private int total;
    private int size;
    private int current;
    private int maxLimit;
    private int pages;
}
