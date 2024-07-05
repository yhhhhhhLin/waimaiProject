package xyz.linyh.VO;

import lombok.Data;
import xyz.linyh.entity.Category;
import xyz.linyh.entity.Setmeal;

import java.util.List;

@Data
public class AllSetMealVO {
    private List<Setmeal> setmeals;
    private int total;
    private int size;
    private int current;
    private int maxLimit;
    private int pages;
}
