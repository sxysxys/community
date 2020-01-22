package com.sxy.community.DTO;

import com.sxy.community.DAO.User;
import lombok.Data;

/**
 * 问题界面的评论对象
 */
@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModify;
    private Long likeCount;
    private String content;
    private User user;
}
