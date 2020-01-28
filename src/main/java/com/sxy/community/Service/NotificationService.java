package com.sxy.community.Service;

import com.sxy.community.DAO.*;
import com.sxy.community.DTO.NotificationDto;
import com.sxy.community.DTO.QuestionDto;
import com.sxy.community.DTO.PageDto;
import com.sxy.community.enums.NotificationEnum;
import com.sxy.community.enums.NotificationStatusEnum;
import com.sxy.community.exception.CustomizeErrorCode;
import com.sxy.community.exception.CustomizeException;
import com.sxy.community.mapper.CommentMapper;
import com.sxy.community.mapper.NotificationMapper;
import com.sxy.community.mapper.QuestionMapper;
import com.sxy.community.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;

    public PageDto list(Long id, Integer page, Integer size) {
        //拿到用户所用通知的数量
        PageDto<NotificationDto> pageDto = new PageDto<>();
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(id);
        Integer allcount = (int)notificationMapper.countByExample(example);
        //计算相应的分页参数
        pageDto.setPagination(allcount,page,size);
        if(page>pageDto.getAllpages()){
            page=pageDto.getAllpages();
        }
        if(page<1){
            page=1;
        }
        Integer offset=size*(page-1);
        //拿到对应页面的所有值
        NotificationExample example1 = new NotificationExample();
        example1.createCriteria().andReceiverEqualTo(id);
        example1.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
        if (notifications.size()==0){
            return pageDto;
        }
        List<NotificationDto> notificationDtos=new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            notificationDto.setTypeName(NotificationEnum.getTypeName(notification.getType()));//拿到相应的字符串
            notificationDtos.add(notificationDto);
        }
        pageDto.setData(notificationDtos);
        return pageDto;
    }

    /**
     * 拿到未阅读数量
     * @param userId
     * @return
     */
    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatesEqualTo(NotificationStatusEnum.NO_READ.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDto read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FILE);
        }
        notification.setStates(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification,notificationDto);
        notificationDto.setTypeName(NotificationEnum.getTypeName(notification.getType()));//拿到相应的字符串
        return notificationDto;
    }

    /**
     * 返回第一层问题的id
     * @param commentId  传入的第二层id
     * @return
     */
    public Long searchQuestionId(Long commentId) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        return comment.getParentId();
    }
}
