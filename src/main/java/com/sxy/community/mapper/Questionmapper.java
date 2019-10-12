package com.sxy.community.mapper;

import com.sxy.community.DAO.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Questionmapper {
    @Insert("insert into question(title,discribe,creater,tags,gmtcreat,gmtmodified) values(#{title},#{describe},#{creater},#{tag},#{gmtcreat},#{gmtmodified})")
    void insertquestion(Question question);

    @Select("SELECT * FROM question limit #{offset},#{size}")
    List<Question> getall(@Param(value = "offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();
}
