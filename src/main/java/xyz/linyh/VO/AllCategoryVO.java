package xyz.linyh.VO;

import lombok.Data;
import xyz.linyh.entity.Category;
import xyz.linyh.entity.Dish;

import java.util.List;

@Data
public class AllCategoryVO {
    private List<Category> categories;
    private int total;
    private int size;
    private int current;
    private int maxLimit;
    private int pages;
}
