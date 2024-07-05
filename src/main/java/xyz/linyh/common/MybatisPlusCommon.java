package xyz.linyh.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import xyz.linyh.Utils.ThreadLocalUtils;

import java.util.Date;

@Component
@Slf4j
public class MybatisPlusCommon implements MetaObjectHandler {

    /**
     * 在mybatisPlus执行添加操作的时候会执行到这个方法
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("执行到了添加");
        metaObject.setValue("createTime",new Date());
        metaObject.setValue("updateTime",new Date());
        long uid = ThreadLocalUtils.getUid();
        metaObject.setValue("createUser",uid);
        metaObject.setValue("updateUser",uid);

    }

    /**
     * 在mybatisPlus执行更新操作的时候会执行到这个方法
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        metaObject.setValue("updateTime",new Date());
        long uid = ThreadLocalUtils.getUid();
        metaObject.setValue("updateUser",uid);
    }
}
