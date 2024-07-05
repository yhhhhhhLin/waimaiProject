package xyz.linyh.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import xyz.linyh.DTO.DishAndFlavorDTO;
import xyz.linyh.DTO.DishPageDTO;
import xyz.linyh.Result.R;
import xyz.linyh.VO.AllDishVO;
import xyz.linyh.entity.Dish;
import xyz.linyh.entity.DishFlavor;
import xyz.linyh.service.DishFlavorService;
import xyz.linyh.service.DishService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Resource
    private DishService dishService;

    @Resource
    private DishFlavorService dishFlavorService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CacheManager cacheManager;



    @GetMapping("/page")
    public R pageDish(int page,int pageSize,String name){

        log.info("page:{},pageSize:{},name:{}",page,pageSize,name);

        AllDishVO allDishVO = dishService.getDishByPage(page,pageSize,name);
        return R.success(allDishVO);

    }

    //    删除菜品，如果菜品在售，那么不可以删除
//    如果删除的是多个的话，其中如果有一个菜品为在售，那么就全部都删除不了
    @DeleteMapping
    public R delDish(String ids,
                     HttpServletRequest request){
        log.info(ids);
        long uId = (long)request.getSession().getAttribute("uId");
        String[] splitIds = ids.split(",");
        Long[] dishIds = new Long[splitIds.length];
        for(int i =0;i<splitIds.length;i++){
            dishIds[i]=Long.parseLong(splitIds[i]);
        }

        for(int i =0;i<dishIds.length;i++){
            Dish byId = dishService.getDishById(dishIds[i]);
            if(byId.getStatus()==1){
                return R.error("在售的不可以删除");
            }
        }
        for(int i =0;i<dishIds.length;i++){
            boolean b = dishService.deleteDishById(dishIds[i]);
            if(!b){
                return R.error("删除失败");
            }
            log.info("删除成功...");

        }
        return R.success(null);
    }

    /**
     * 因为添加菜单里面还有喜好口味，所以不可以直接用dish接收或dishFlavor接收，需要用一个DTO统一接收
     *
     * 添加了菜品后，需要把redis里面的缓存去掉
     * @param
     * @return
     */
    @PostMapping
    public R addDish(@RequestBody DishAndFlavorDTO dishAndFlavorDTO,
                     HttpServletRequest request){
        log.info("开始添加菜品。。。。。。。");

        Long uId = (Long) request.getSession().getAttribute("uId");
//        先将菜品添加到dish表中
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishAndFlavorDTO,dish);

        //判断是否已经存在，如果已经
        Dish one = dishService.getDishByName(dish.getName());
        if(one!=null){
            return R.error("名字已经存在");
        }
        dish.setCreateUser(uId);
        dish.setUpdateUser(uId);
        dish.setCreateTime(new Date());
        dish.setUpdateTime(new Date());
        dish.setSort(0);
        long dishId = dishService.saveDish(dish);
        System.out.println(dishId);

//        boolean save = dishService.save(dish);

//        开始添加口味
        for (DishFlavor flavor : dishAndFlavorDTO.getFlavors()) {
            flavor.setDishId(dishId);
            flavor.setCreateTime(new Date());
            flavor.setUpdateTime(new Date());
            flavor.setCreateUser(uId);
            flavor.setUpdateUser(uId);

            dishFlavorService.saveDishFlavor(flavor);

        }

        return R.success("添加成功");

    }

//    根据id查找菜品
    @GetMapping("/{id}")
    public R getDishById(@PathVariable long id){
        log.info("进入了根据id找菜品");
        DishAndFlavorDTO dishAndFlavorDTO = new DishAndFlavorDTO();
//        先根据id获取菜品
        Dish dish = dishService.getDishById(id);
        BeanUtils.copyProperties(dish,dishAndFlavorDTO);
//        根据id获取菜品的喜好
        List<DishFlavor> flavors = dishFlavorService.getDishFlavorByDishId(dish.getId());
        dishAndFlavorDTO.setFlavors(flavors);
        log.info(dishAndFlavorDTO.getFlavors().toString());
        return R.success(dishAndFlavorDTO);
    }

//    修改菜品信息
    @PutMapping
    @Transactional//如果出现问题会自动回滚
    public R editDishAndFlavor(@RequestBody DishAndFlavorDTO dishAndFlavorDTO,
                               HttpServletRequest request){
        long uId = (long)request.getSession().getAttribute("uId");

        Dish dish = new Dish();
        System.out.println(dish.getId());
        BeanUtils.copyProperties(dishAndFlavorDTO,dish);

////        如果是修改菜品状态直接修改就可以了
//        if(dishAndFlavorDTO.getName()==null){
//            dish.setUpdateUser(uId);
//            dish.setUpdateTime(new Date());
//            Boolean b = dishService.updateDishStatus(dish);
//            return R.success("修改成功");
//        }
//        1.查看菜品是否重名
        Dish one = dishService.getDishByName(dishAndFlavorDTO.getName());
        if(one!=null && !one.getName().equals(dishAndFlavorDTO.getName())){
            return R.error("菜品不能重名");
        }
//        2.修改菜品信息
        dish.setUpdateTime(new Date());
        dish.setUpdateUser(uId);
        boolean b = dishService.updateDish(dish);
//        3.修改口味信息
        Long dishId = dish.getId();
        for (DishFlavor flavor : dishAndFlavorDTO.getFlavors()) {

            if(flavor.getCreateTime()==null){
                flavor.setDishId(dishId);
                flavor.setCreateTime(new Date());
                flavor.setCreateUser(uId);
                flavor.setUpdateUser(uId);
                flavor.setUpdateTime(new Date());
            }
        }
        boolean b2 = dishFlavorService.saveOrUpdateDishFlavor(dishAndFlavorDTO.getFlavors(),dishId);
        if(!b2 || !b){
            return R.error("修改失败");
        }
        return R.success("修改成功");
        //《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《
////        修改菜品信息
//        boolean b2 = dishService.updateById(dish);
////        修改口味信息
//        Long dishId2 = dish.getId();
//        for (DishFlavor flavor : dishAndFlavorDTO.getFlavors()) {
//
//            if(flavor.getCreateTime()==null){
//                flavor.setDishId(dishId);
////                flavor.setCreateTime(new Date());
////                flavor.setCreateUser(uId);
//
//            }
//
////            flavor.setUpdateTime(new Date());
////            flavor.setUpdateUser(uId);
//
//        }
//        boolean b1 = dishFlavorService.saveOrUpdateBatch(dishAndFlavorDTO.getFlavors());
//        return R.success("修改成功");

    }

//    修改菜品状态
    @PostMapping("/status/{status}")
    public R editDishStatus(@PathVariable int status,
                            String ids,
                            HttpServletRequest request){
        long uId = (long)request.getSession().getAttribute("uId");
        Dish dish = new Dish();
//        根据id修改菜品的修改时间和修改user和修改状态
        log.info(ids);
//        将字符串转换为int，如果是多个id的话，那么就分割开
        String[] splitIds = ids.split(",");
        System.out.println(Arrays.toString(splitIds));
//        根据id修改对应信息
        for(int i =0;i<splitIds.length;i++){
            long id = Long.parseLong(splitIds[i]);
//            LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
//            updateWrapper.eq(Dish::getId,id)
//                    .set(Dish::getUpdateTime,new Date())
//                    .set(Dish::getUpdateUser,uId)
//                    .set(Dish::getStatus,status);
            dish.setId(id);
            dish.setUpdateTime(new Date());
            dish.setUpdateUser(uId);
            dish.setStatus(status);
            boolean update = dishService.updateDishStatus(dish);

        }

        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R listDish(long categoryId){
//        拼接一个key
        String key = "dish"+categoryId;
        List<Dish> list=null;
//        list = (List<Dish>) redisTemplate.opsForValue().get(key);
//        if(list!=null){
//            return R.success(list);
//        }
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId,categoryId);
        list = dishService.list(wrapper);
//        redisTemplate.opsForValue().set(key,list,30, TimeUnit.MINUTES);
        return R.success(list);
    }



}
