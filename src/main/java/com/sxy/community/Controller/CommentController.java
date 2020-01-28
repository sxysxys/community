package com.sxy.community.Controller;

import com.sxy.community.DAO.Comment;
import com.sxy.community.DAO.User;
import com.sxy.community.DTO.CommentDto;
import com.sxy.community.DTO.ResultDto;
import com.sxy.community.Service.CommentService;
import com.sxy.community.enums.CommentTypeEnum;
import com.sxy.community.exception.CustomizeErrorCode;
import com.sxy.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody Comment comment, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(System.currentTimeMillis());
        if (StringUtils.isEmpty(comment.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.NO_CONTENT);
        }
        commentService.insert(comment,user);
        return ResultDto.success(CustomizeErrorCode.SUCCESS_RETURN);
    }

    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDto comments(@PathVariable(name = "id") Long id){
        List<CommentDto> commentDtos = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);//取出评论对应的一系列二级评论
        return ResultDto.success(commentDtos);
    }
}

