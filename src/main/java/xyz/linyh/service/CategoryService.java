package xyz.linyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.linyh.VO.AllCategoryVO;
import xyz.linyh.entity.Category;

/**
* @author lin
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2023-03-26 16:05:00
*/
public interface CategoryService extends IService<Category> {

    AllCategoryVO getAllCategoryByPage(int page, int pageSize);

    boolean deleteCategoryById(long ids, Long uId);

    boolean updateCategoryById(Category category);

    Category getCategoryByName(String name);

    boolean saveCategory(Category category);
}
