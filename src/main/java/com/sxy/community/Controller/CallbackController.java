package com.sxy.community.Controller;


import com.sxy.community.DAO.User;
import com.sxy.community.DTO.GithubUser;
import com.sxy.community.DTO.Token_dto;
import com.sxy.community.mapper.Usermapper;
import com.sxy.community.provider.Gtprovider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class CallbackController {
    @Autowired
    private Gtprovider gtprovider;

    @Autowired
    private Usermapper usermapper;

    @Value("${git.client_id}")
    private String client_id;

    @Value("${git.client_secret}")
    private String client_secret;

    @Value("${git.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest httpSession,
                           HttpServletResponse httpServletResponse){
        Token_dto token_dto=new Token_dto();
        token_dto.setClient_id(client_id);
        token_dto.setClient_secret(client_secret);
        token_dto.setCode(code);
        token_dto.setRedirect_uri(redirect_uri);
        token_dto.setState(state);
        String gtp=gtprovider.callback(token_dto);
        GithubUser usercallback = gtprovider.Usercallback(gtp);
//        System.out.println(usercallback);
        if(usercallback!=null){
            User user = new User();
            String token =UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(usercallback.getName());
            user.setAccountId(String.valueOf(usercallback.getId()));
            user.setId(usercallback.getId());
            user.setAvatarUrl(usercallback.getAvatarUrl());
            usermapper.insertUser(user);
            Cookie cookie=new Cookie("token",token);
            httpServletResponse.addCookie(cookie);
            //httpSession.getSession().setAttribute("user",usercallback);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
