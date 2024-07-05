package xyz.linyh.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.linyh.Result.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${waimai.img.path}")
    private String imgSavePath;

    @PostMapping("/upload")
    public R upload(MultipartFile file){
        log.info(file.toString());

//        文件原先名称，但是这样子获取的文件名会，可能会重复，重复了就会覆盖前面的
        String originalFilename = file.getOriginalFilename();

//        利用UUID生成文件名，在拼接上原来的文件类型
        String fileName = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));


        try {
            file.transferTo(new File(imgSavePath+fileName));
            return R.success(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取服务器内的图片
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void downLoadImg(String name,
                         HttpServletResponse response){

        try {
//            利用输入流获取图片
            FileInputStream fileInputStream = new FileInputStream(new File(imgSavePath+name));
//            获取输出流
            ServletOutputStream outputStream = response.getOutputStream();
//            开始写出到浏览器

//            设置响应图片
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while((len = fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();



        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
