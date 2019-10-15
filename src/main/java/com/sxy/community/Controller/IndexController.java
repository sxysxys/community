package com.sxy.community.Controller;

import com.sxy.community.DTO.pageDto;
import com.sxy.community.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "2") Integer size){
        pageDto pagination=questionService.getall(page,size);
/*        for (QuestionDto questionDto : questionDtos) {
            questionDto.setDescribe("fffff");
        }*/
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
