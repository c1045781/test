package com.gk.university.mapper;

import com.gk.university.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insertQuestion(Question question);

    @Select("select * from question limit #{index},#{size}")
    List<Question> list(@Param("index") Integer index, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();
}
