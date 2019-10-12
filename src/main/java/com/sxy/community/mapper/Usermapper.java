package com.sxy.community.mapper;

import com.sxy.community.DAO.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Usermapper {

    @Insert("INSERT INTO user(account_id,name,token,avatarUrl) values(#{accountId},#{name},#{token},#{avatarUrl})")
    public void insertUser(User user);

    @Select("SELECT * from user where token=#{token}")
    User findbytoken(@Param("token") String token);

    @Select("SELECT * from user where id=#{id}")
    User findbyID(@Param("id") Long id);
}