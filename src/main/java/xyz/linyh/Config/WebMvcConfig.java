package xyz.linyh.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import xyz.linyh.Interceptor.LoginInterceptor;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {


    @Resource
    private LoginInterceptor loginInterceptor;


    @Value("#{'${my-interceptor.interceptWhitelist}'.split(',')}")
    private List<String> interceptWhitelist;


//    /**
//     * 设置静态资源映射,不然就需要把静态资源默认放在static里面
//     * @param registry
//     */
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        log.info("开始静态资源映射");
////        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
//        registry.addResourceHandler("backend/**").addResourceLocations("classpath:/backend/");
////
//        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
//
//    }

    /**
     * 添加请求拦截器
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("拦截白名单为={}", interceptWhitelist);
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/backend/**", "/front/**")
                .excludePathPatterns(interceptWhitelist);
    }
}
