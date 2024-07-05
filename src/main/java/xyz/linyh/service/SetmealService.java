package xyz.linyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.linyh.VO.AllSetMealVO;
import xyz.linyh.entity.Setmeal;

/**
* @author lin
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2023-03-28 19:16:09
*/
public interface SetmealService extends IService<Setmeal> {

    AllSetMealVO getAllSetMealByPage(int page, int pageSize, String name);

    long saveSetMeal(Setmeal setmeal);

    Setmeal getSetMealByName(String name);

    Setmeal getSetMealById(long id);
}
