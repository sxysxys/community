package com.sxy.community.mapper;

import com.sxy.community.DAO.Question;

//用来加数量的类
public interface QuestionExtMapper {
    int incView(Question record);

    int incCommentCount(Question question);
}