package com.sxy.community.Controller;

import com.sxy.community.DAO.User;
import com.sxy.community.DTO.PageDto;
import com.sxy.community.Service.NotificationService;
import com.sxy.community.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "2") Integer size,
                          Model model){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageDto pagination = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",pagination);
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            PageDto pagination = notificationService.list(user.getId(), page, size);
            model.addAttribute("pagination",pagination);
        }
        Long unreadCount = notificationService.unreadCount(user.getId());
//        model.addAttribute("unreadCount",unreadCount);
        return "profile";
    }
}
