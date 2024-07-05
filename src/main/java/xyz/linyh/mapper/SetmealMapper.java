package xyz.linyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.linyh.entity.Setmeal;

import java.util.List;

/**
* @author lin
* @description 针对表【setmeal(套餐)】的数据库操作Mapper
* @createDate 2023-03-28 19:16:09
* @Entity xyz.linyh.entity.Setmeal
*/
public interface SetmealMapper extends BaseMapper<Setmeal> {

    int getSetMealCount(@Param("name") String name);

    List<Setmeal> getAllSetMealByPage(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("name") String name);

    Boolean saveSetMeal(@Param("setmeal") Setmeal setmeal);

    Setmeal getSetMealByName(@Param("name") String name);

    Setmeal getSetMealById(@Param("id") long id);
}




