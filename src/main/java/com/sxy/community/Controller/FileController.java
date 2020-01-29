package com.sxy.community.Controller;

import com.sxy.community.DTO.FileDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 提问时的图片上传
 */
@Controller
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto uploadFile(){
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(1);
        fileDto.setUrl("/images/ucdn.png");
        return fileDto;
    }

}
