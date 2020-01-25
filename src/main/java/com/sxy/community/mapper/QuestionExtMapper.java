package com.sxy.community.mapper;

import com.sxy.community.DAO.Question;

import java.util.List;

//用来加数量的类
public interface QuestionExtMapper {
    //增加阅读数
    int incView(Question record);
    //增加评论数
    int incCommentCount(Question question);
    //选出相关问题
    List<Question> selectRelated(Question question);
}