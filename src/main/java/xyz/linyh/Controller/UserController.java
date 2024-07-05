package xyz.linyh.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.linyh.Result.R;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @PostMapping("/login")
    public R login(@RequestBody Map<String,String> map,
                   HttpServletRequest request){
        long phone = Long.parseLong(map.get("phone"));
        String code = map.get("code");
        log.info("{}:{}",phone,code);
//        没有验证码登录，全都是成功
        request.getSession().setAttribute("uId",phone);
        return R.success("登录成功");
    }
}
