package com.sxy.community.Controller;


import com.sxy.community.DTO.GithubUser;
import com.sxy.community.DTO.Token_dto;
import com.sxy.community.provider.Gtprovider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CallbackController {
    @Autowired
    private Gtprovider gtprovider;
    @GetMapping("/callback")
    public void callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state")String state){
        Token_dto token_dto=new Token_dto();
        token_dto.setClient_id("fa5640b107cf8fc78bb9");
        token_dto.setClient_secret("ff7c86bebdeb5264b71930f694aa47857f9869ca");
        token_dto.setCode(code);
        token_dto.setRedirect_uri("http://localhost:8882/callback");
        token_dto.setState(state);
        String gtp=gtprovider.callback(token_dto);
        GithubUser usercallback = gtprovider.Usercallback(gtp);
        System.out.println(usercallback);
    }
}
