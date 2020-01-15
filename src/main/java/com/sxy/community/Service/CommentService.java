package com.sxy.community.Service;

import com.sxy.community.DAO.Comment;
import com.sxy.community.DAO.Question;
import com.sxy.community.enums.CommentTypeEnum;
import com.sxy.community.exception.CustomizeErrorCode;
import com.sxy.community.exception.CustomizeException;
import com.sxy.community.mapper.CommentMapper;
import com.sxy.community.mapper.QuestionExtMapper;
import com.sxy.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;

    /**
     * 插进来的是json数据
     * parentid代表第几个问题。
     *
     * @param comment
     */
    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment)) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOTEXIST);
        }
        if (comment.getCommentator()==null){
            throw new CustomizeException(CustomizeErrorCode.NO_PEOPLE_EXIST);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {  //如果是给评论进行评论
            Comment comment1 = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (comment1 == null) {
                throw new CustomizeException(CustomizeErrorCode.PARENT_NOT_EXIST);
            }
            commentMapper.insert(comment);
        } else {  //如果是给问题进行评论
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }
}
