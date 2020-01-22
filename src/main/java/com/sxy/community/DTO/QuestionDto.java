package com.sxy.community.DTO;


import com.sxy.community.DAO.User;
import lombok.Data;


@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtcreat;
    private Long gmtmodified;
    private Long creater;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likecount;
    private User user;
}
