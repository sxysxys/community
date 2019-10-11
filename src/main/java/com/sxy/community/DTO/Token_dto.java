package com.sxy.community.DTO;

import lombok.Data;

@Data
public class Token_dto {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


    @Override
    public String toString() {
        return "Token_dto{" +
                "client_id='" + client_id + '\'' +
                ", client_secret='" + client_secret + '\'' +
                ", code='" + code + '\'' +
                ", redirect_uri='" + redirect_uri + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
