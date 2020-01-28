package com.sxy.community.Controller;

import com.sxy.community.DAO.User;
import com.sxy.community.DTO.NotificationDto;
import com.sxy.community.Service.NotificationService;
import com.sxy.community.enums.NotificationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable("id") Long id){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        NotificationDto notificationDto= notificationService.read(id,user);  //拿到相应的消息对象进行判断
        if (notificationDto.getType() == NotificationEnum.REPLY_QUESTION.getType()) {
            return "redirect:/question/" + notificationDto.getOutId();
        }else if (notificationDto.getType() == NotificationEnum.REPLY_COMMENT.getType()){
            //当通知的是评论时，此时需要拿到相应的问题需要再查一层
           Long questionId=notificationService.searchQuestionId(notificationDto.getOutId());
            return "redirect:/question/" + questionId;
        }else {
            return "redirect:/";
        }
    }
}
