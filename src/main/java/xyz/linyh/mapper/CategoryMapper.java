package xyz.linyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.linyh.entity.Category;

import java.util.Date;
import java.util.List;

/**
* @author lin
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2023-03-26 16:05:00
* @Entity xyz.linyh.entity.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {

    int getCategoryCount();

    List<Category> getAllCategoryByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    Boolean deleteCategoryById(@Param("ids") long ids, @Param("uId") Long uId);

    boolean updateCategoryById(@Param("category") Category category);

    Category getCategoryByName(@Param("name") String name);

    Boolean saveCategory(@Param("category") Category category);
}




