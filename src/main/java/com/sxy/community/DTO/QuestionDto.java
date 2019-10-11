package com.sxy.community.DTO;


import com.sxy.community.DAO.User;
import lombok.Data;


@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String describe;
    private String tag;
    private Long gmtcreat;
    private Long gmtmodified;
    private Long creater;
    private Integer viewcount;
    private Integer commentcount;
    private Integer likecount;
    private User user;
}
