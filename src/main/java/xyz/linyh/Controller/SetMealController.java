package xyz.linyh.Controller;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import xyz.linyh.DTO.setMealAndDishDTO;
import xyz.linyh.Result.R;
import xyz.linyh.VO.AllSetMealVO;
import xyz.linyh.VO.SetMealAndDishesVO;
import xyz.linyh.entity.Employee;
import xyz.linyh.entity.Setmeal;
import xyz.linyh.entity.SetmealDish;
import xyz.linyh.service.SetmealDishService;
import xyz.linyh.service.SetmealService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("setmeal")
public class SetMealController {

    @Resource
    private SetmealService setmealService;

    @Resource
    private SetmealDishService setmealDishService;


    @GetMapping("/page")
    public R page(int page,int pageSize,String name){
        AllSetMealVO allSetMealVO = setmealService.getAllSetMealByPage(page,pageSize,name);
        return R.success(allSetMealVO);
    }

    /**
     * 添加套餐
     * @param setMealAndDishDTO
     * @return
     */
    @PostMapping
    @Transactional
    public R addMeal(@RequestBody setMealAndDishDTO setMealAndDishDTO,
                     HttpServletRequest request){
        log.info(setMealAndDishDTO.getName());
        Long uId = (Long) request.getSession().getAttribute("uId");
//        先将meal里面的内容存到数据库里面，然后获取id
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setMealAndDishDTO,setmeal);
        setmeal.setUpdateTime(new Date());
        setmeal.setUpdateUser(uId);
        setmeal.setCreateTime(new Date());
        setmeal.setCreateUser(uId);
        //        套餐名字唯一性校验
        Setmeal one = setmealService.getSetMealByName(setmeal.getName());
        if(one!=null ){
            return R.error("套餐已经存在");
        }
        long setMealId = setmealService.saveSetMeal(setmeal);
        for(SetmealDish dishes: setMealAndDishDTO.getSetmealDishes()){
            dishes.setSetmealId(setMealId);
            dishes.setUpdateTime(new Date());
            dishes.setUpdateUser(uId);
            dishes.setCreateTime(new Date());
            dishes.setCreateUser(uId);
            boolean save1 = setmealDishService.saveSetMealDish(dishes);
        }
        return R.success("添加成功");
    }

    @GetMapping("/{id}")
    public R getSetMealDetail(@PathVariable("id") long id){
        SetMealAndDishesVO setMealAndDishesVO = new SetMealAndDishesVO();
        Setmeal setmeal = setmealService.getSetMealById(id);
        List<SetmealDish> setmealDishes = setmealDishService.getSetMealAndDishBySetMealId(id);
        BeanUtils.copyProperties(setmeal,setMealAndDishesVO);
        setMealAndDishesVO.setSetmealDishes(setmealDishes);
        System.out.println(setMealAndDishesVO);
        return R.success(setMealAndDishesVO);
    }

    /**
     * 订单状态修改
     */
    @PostMapping("/status/{status}")
    public R setStatus(@PathVariable int status,String ids){
        log.info("套餐状态修改");

        return null;
    }
}
