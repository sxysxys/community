package com.sxy.community.Controller;

import com.sxy.community.DAO.User;
import com.sxy.community.mapper.Usermapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private Usermapper usermapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
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
        return "index";
    }
}
