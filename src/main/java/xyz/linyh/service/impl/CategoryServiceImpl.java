package xyz.linyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.linyh.VO.AllCategoryVO;
import xyz.linyh.VO.AllEmployeeVO;
import xyz.linyh.entity.Category;
import xyz.linyh.entity.Employee;
import xyz.linyh.mapper.CategoryMapper;
import xyz.linyh.service.CategoryService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author lin
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2023-03-26 16:05:00
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public AllCategoryVO getAllCategoryByPage(int page, int pageSize) {
        AllCategoryVO allCategoryVO = new AllCategoryVO();
//        查询所有符合条件的所有员工数量
        int count = categoryMapper.getCategoryCount();
        allCategoryVO.setTotal(count);
//        查询在这一页中的所有员工
        int offset = (page-1)*pageSize;
        List<Category> categories= categoryMapper.getAllCategoryByPage(offset,pageSize);
        allCategoryVO.setCategories(categories);
        allCategoryVO.setPages(page);
        allCategoryVO.setSize(pageSize);
        return allCategoryVO;
    }

    @Override
    public boolean deleteCategoryById(long ids, Long uId) {
        Boolean b = categoryMapper.deleteCategoryById(ids,uId);
        return b;
    }

    @Override
    public boolean updateCategoryById(Category category) {
        boolean b = categoryMapper.updateCategoryById(category);
        return b;
    }

    @Override
    public Category getCategoryByName(String name) {
        Category category = categoryMapper.getCategoryByName(name);
        return category;
    }

    @Override
    public boolean saveCategory(Category category) {
        Boolean b = categoryMapper.saveCategory(category);
        return b;
    }
}




