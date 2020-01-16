package com.sxy.community.Service;

import com.sxy.community.DAO.*;
import com.sxy.community.DTO.CommentDto;
import com.sxy.community.enums.CommentTypeEnum;
import com.sxy.community.exception.CustomizeErrorCode;
import com.sxy.community.exception.CustomizeException;
import com.sxy.community.mapper.CommentMapper;
import com.sxy.community.mapper.QuestionExtMapper;
import com.sxy.community.mapper.QuestionMapper;
import com.sxy.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;

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

    /**
     * 通过问题，将问题id对应的评论全部查出来
     * @param id
     * @param type
     * @return
     */
    public List<CommentDto> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());  //查询的条件、
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);  //拿到所有和这个问题相关的comments
        if (comments.size()==0){
            return new ArrayList<>();
        }
        //如果直接暴力查询复杂度太高，使用java8提供的流操作。

        //先将所有评论的userid无重复的拿到
        Set<Long> collects = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(collects);

        //得到user的map映射关系,大大降低时间复杂度
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> collectUsers = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //将评论和用户关系上,将每一个comment映射成一个commentDto
        List<CommentDto> collect = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUser(collectUsers.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());

        return collect;
    }
}
