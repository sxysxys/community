package com.sxy.community.Service;

import com.sxy.community.DAO.User;
import com.sxy.community.DAO.UserExample;
import com.sxy.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper usermapper;
    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> user1=usermapper.selectByExample(example);
        if(user1.size()==0){
            //插入
//            Integer a=1;
//            user.setId(a.longValue());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            usermapper.insert(user);
        }else {
            //更新
            User dbuser = user1.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarurl(user.getAvatarurl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample userExample=new UserExample();
            userExample.createCriteria().andIdEqualTo(dbuser.getId());
            usermapper.updateByExampleSelective(updateUser,userExample);
        }
    }
}
