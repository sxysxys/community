package com.sxy.community.Controller;

import com.sxy.community.DAO.Question;
import com.sxy.community.DAO.User;
import com.sxy.community.DTO.QuestionDto;
import com.sxy.community.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String dopublish(@RequestParam(name = "title",required = false) String title,
                            @RequestParam(name = "description",required = false) String description,
                            @RequestParam(name = "tag",required = false) String tag,
                            @RequestParam(name = "id",required = false) Long id,
                            HttpServletRequest httpServletRequest,
                            Model model
                            ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
//        Cookie[] cookies = httpServletRequest.getCookies();
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        if(title==null || title==""){
            model.addAttribute("error","标题不能为空！");
            return "publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","描述不能为空！");
            return "publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空！");
            return "publish";
        }
        Question que = new Question();
        que.setTitle(title);
        que.setDescription(description);
        que.setTag(tag);
        que.setCreater(user.getId());
        que.setId(id);
        questionService.CreatOrUpdate(que);
        return "redirect:/";
    }
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Long id,
                       Model model){
        QuestionDto byId = questionService.getById(id);
        model.addAttribute("title",byId.getTitle());
        model.addAttribute("describe",byId.getDescription());
        model.addAttribute("tag",byId.getTag());
        model.addAttribute("id",id);
        return "publish";
    }
}
