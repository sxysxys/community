package com.sxy.community.mapper;

import com.sxy.community.DAO.Question;
import com.sxy.community.DTO.QuestionDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Questionmapper {
    @Insert("insert into question(title,describe,creater,tags,gmtcreat,gmtmodified) values(#{title},#{describe},#{creater},#{tag},#{gmtcreat},#{gmtmodified})")
    void insertquestion(Question question);

    @Select("SELECT * FROM question limit #{offset},#{size}")
    List<Question> getall(@Param(value = "offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("SELECT * FROM question where creater=#{id} limit #{offset},#{size}")
    List<Question> getallById(@Param(value="id") Long id, @Param(value = "offset") Integer offset, @Param("size")Integer size);
    @Select("select count(1) from question WHERE creater=#{id}")
    Integer countById(Long id);
    @Select("SELECT * FROM question where id=#{id}")
    Question getById(@Param(value="id")Integer id);
}
