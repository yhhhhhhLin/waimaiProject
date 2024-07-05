package xyz.linyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.linyh.entity.DishFlavor;
import xyz.linyh.mapper.DishFlavorMapper;
import xyz.linyh.service.DishFlavorService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author lin
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2023-03-26 23:02:12
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

    @Resource
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public Boolean saveDishFlavor(DishFlavor flavor) {
        boolean b = dishFlavorMapper.saveDishFlavor(flavor);
        return b;
    }

    @Override
    public Boolean updateDishFlavor(DishFlavor flavor) {
        Boolean b = dishFlavorMapper.updateDishFlavor(flavor);
        return b;
    }

    @Override
    public boolean saveOrUpdateDishFlavor(List<DishFlavor> flavors, Long dishId) {
//        先获取这个菜品对应的原有的口味
        List<DishFlavor> dishFlavors = dishFlavorMapper.getDishFlavorByDishId(dishId);
        List<Long> dishFlavorIds = new ArrayList<Long>();
        for(int i = 0;i<dishFlavors.size();i++){
            dishFlavorIds.add(dishFlavors.get(i).getId());
        }
//        查看要修改的flavorsId是否是原先有的，如果是有的，那么就直接修改，如果是没有的，那么就添加，如果是原先有，但是修改后没有的，那么删除
        for(DishFlavor flavor:flavors) {
//            如果是原先没有的，那么添加
            if(flavor.getId()==null){
                boolean b = dishFlavorMapper.saveDishFlavor(flavor);
            }
//            如果是原先有的，那么修改
            if (dishFlavorIds.contains(flavor.getId())){
                flavor.setUpdateTime(new Date());
                dishFlavorMapper.updateDishFlavor(flavor);
                dishFlavorIds.remove(flavor.getId());
            }
        }
//        最后剩下的就删除
        for(int i = 0;i<dishFlavorIds.size();i++) {
            Boolean b = dishFlavorMapper.deleteDishFlavorById(dishFlavorIds.get(i));
        }
        return true;
    }

    @Override
    public List<DishFlavor> getDishFlavorByDishId(long id) {
        return dishFlavorMapper.getDishFlavorByDishId(id);
    }
}




