package com.gk.university.mapper;

import com.gk.university.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insertQuestion(Question question);

    @Select("select * from question limit #{index},#{size}")
    List<Question> list(@Param("index") Integer index, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{index},#{size}")
    List<Question> listByUser(@Param("userId") Integer userId, @Param("index") Integer index, @Param("size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUser(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question questionById(@Param("id") Integer id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void updateQuestion(Question question);
}
