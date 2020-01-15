package com.sxy.community.Controller;

import com.sxy.community.DAO.Comment;
import com.sxy.community.DAO.User;
import com.sxy.community.DTO.ResultDto;
import com.sxy.community.Service.CommentService;
import com.sxy.community.exception.CustomizeErrorCode;
import com.sxy.community.exception.CustomizeException;
import com.sxy.community.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody Comment comment, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
//            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(System.currentTimeMillis());
        commentService.insert(comment);
        return ResultDto.success(CustomizeErrorCode.SUCCESS_RETURN);
    }
}

