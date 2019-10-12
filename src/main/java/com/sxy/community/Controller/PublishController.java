package com.sxy.community.Controller;

import com.sxy.community.DAO.Question;
import com.sxy.community.DAO.User;
import com.sxy.community.mapper.Questionmapper;
import com.sxy.community.mapper.Usermapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private Questionmapper questionmapper;
    @Autowired
    private Usermapper usermapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String dopublish(@RequestParam("title") String title,
                            @RequestParam("describe") String describe,
                            @RequestParam("tag") String tag,
                            HttpServletRequest httpServletRequest,
                            Model model
                            ){
        model.addAttribute("title",title);
        model.addAttribute("describe",describe);
        model.addAttribute("tag",tag);
        Cookie[] cookies = httpServletRequest.getCookies();
        User user=null;
        if(cookies!=null&&cookies.length!=0)
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user=usermapper.findbytoken(token);
                    if(user!=null){
                        System.out.println(user);
                        // httpServletRequest.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        if(title==null || title==""){
            model.addAttribute("error","标题不能为空！");
            return "publish";
        }
        if(describe==null || describe==""){
            model.addAttribute("error","描述不能为空！");
            return "publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空！");
            return "publish";
        }
        Question que = new Question();
        que.setTitle(title);
        que.setDescribe(describe);
        que.setTag(tag);
        que.setCreater(user.getId());
        que.setGmtcreat(System.currentTimeMillis());
        que.setGmtmodified(que.getGmtcreat());
        questionmapper.insertquestion(que);
        return "redirect:/";
    }
}