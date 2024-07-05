package xyz.linyh.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import xyz.linyh.Result.R;
import xyz.linyh.VO.AllCategoryVO;
import xyz.linyh.entity.Category;
import xyz.linyh.service.CategoryService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/page")
    public R listCateGory(int page,int pageSize){
        log.info("开始查询所有分类");
        AllCategoryVO allCategoryVO = categoryService.getAllCategoryByPage(page,pageSize);
        return R.success(allCategoryVO);
    }

    /**
     * 删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping()
    public R deleteCategory(long ids,
                            HttpServletRequest request){
        Long uId = (Long) request.getSession().getAttribute("uId");
        boolean b = categoryService.deleteCategoryById(ids,uId);
        return R.success(null);
    }

//    增加缓存
    @GetMapping("/{id}")
    public R getCateGoryById(@PathVariable int id){
//        先拼接出一个缓存到redis的key
        String key = "CateGory"+id;
        Category o =(Category) redisTemplate.opsForValue().get(id);
        if(o!=null){
            return R.success(o);
        }else {
            Category byId = categoryService.getById(id);
            redisTemplate.opsForValue().set(key,byId,30, TimeUnit.MINUTES);
            System.out.println("存到了redis中");
            return R.success(byId);

        }
    }

    @PutMapping
    public R updateCateGory(@RequestBody Category category,
                            HttpServletRequest request){
        log.info("开始修改分类：{}",category);

        long updateUid = (Long)request.getSession().getAttribute("uId");
        category.setUpdateTime(new Date());
        category.setUpdateUser(updateUid);
        boolean b = categoryService.updateCategoryById(category);
        return R.success("修改成功");
    }

    @PostMapping
    public R addCateGory(@RequestBody Category category,
                         HttpServletRequest request){
        long saveUid = (Long)request.getSession().getAttribute("uId");

//        判断菜品是否已经存在
        Category one = categoryService.getCategoryByName(category.getName());
        if(one!=null && !one.getName().equals(category.getName())){
            return R.error("名称已经存在");
        }
        category.setUpdateUser(saveUid);
        category.setCreateUser(saveUid);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        boolean b = categoryService.saveCategory(category);
        return R.success("添加成功");
    }

    /**
     * 获取所有菜品
     * @param type
     * @return
     */
    @GetMapping("/list")
    public R listAllCategory(String type){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type!=null,Category::getType,type);
        List<Category> list = categoryService.list(wrapper);
        return R.success(list);
    }


}
