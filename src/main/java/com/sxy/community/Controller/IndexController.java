package com.sxy.community.Controller;

import com.sxy.community.DAO.Question;
import com.sxy.community.DAO.User;
import com.sxy.community.DTO.QuestionDto;
import com.sxy.community.DTO.pageDto;
import com.sxy.community.Service.QuestionService;
import com.sxy.community.mapper.Questionmapper;
import com.sxy.community.mapper.Usermapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private Usermapper usermapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "1") Integer size){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0)
            for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user=usermapper.findbytoken(token);
                if(user!=null){
                    System.out.println(user);
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        pageDto pagination=questionService.getall(page,size);
/*        for (QuestionDto questionDto : questionDtos) {
            questionDto.setDescribe("fffff");
        }*/
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
