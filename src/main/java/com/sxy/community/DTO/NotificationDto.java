package com.sxy.community.DTO;

import com.sxy.community.DAO.User;
import lombok.Data;

@Data
public class NotificationDto {
    private Long id;

    private Long notifier;

    private Long gmtCreate;

    private Integer states;
    //通知对应你的内容
    private String outerTitle;

    private String notifierName;
    //这个type返回的是原整形type对应的字符串
    private String typeName;

    private Integer type;

    private Long outId;  //拿到问题，进行相应的跳转
}
