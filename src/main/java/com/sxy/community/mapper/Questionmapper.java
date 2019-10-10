package com.sxy.community.mapper;

import com.sxy.community.DAO.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Questionmapper {
    @Insert("insert into question(title,describe,creater,tags,gmtcreat,gmtmodified) values(#{title},#{describe},#{creater},#{tag},#{gmtcreat},#{gmtmodified})")
    void insertquestion(Question question);
}
