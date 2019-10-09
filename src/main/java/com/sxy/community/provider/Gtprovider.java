package com.sxy.community.provider;

import com.alibaba.fastjson.JSON;
import com.sxy.community.DTO.GithubUser;
import com.sxy.community.DTO.Token_dto;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Gtprovider {
    public String callback(Token_dto token_dto){
        MediaType media= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(media,JSON.toJSONString(token_dto));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try {
                try (Response response = client.newCall(request).execute()) {
                    String tokenString =response.body().string();
                    String[] split = tokenString.split("&");
                    String[] split1 = split[0].split("=");
                    tokenString=split1[1];
                    System.out.println(tokenString);
                    return tokenString;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
        }
        public GithubUser Usercallback(String tokenString){
            OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://api.github.com/user?access_token="+tokenString)
                        .build();

            try {
                try (Response response = client.newCall(request).execute()) {
                    String backString = response.body().string();
                    GithubUser githubUser = JSON.parseObject(backString, GithubUser.class);
                    return githubUser;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
}
