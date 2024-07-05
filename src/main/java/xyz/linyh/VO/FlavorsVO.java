package xyz.linyh.VO;

import lombok.Data;
import xyz.linyh.entity.DishFlavor;

import java.util.List;

@Data
public class FlavorsVO {
    List<DishFlavor> flavors;
}
