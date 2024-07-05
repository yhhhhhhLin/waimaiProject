package xyz.linyh.DTO;

import lombok.Data;
import xyz.linyh.entity.Dish;
import xyz.linyh.entity.DishFlavor;

import java.util.List;

@Data
public class DishAndFlavorDTO extends Dish {

    private List<DishFlavor>  flavors;
}
