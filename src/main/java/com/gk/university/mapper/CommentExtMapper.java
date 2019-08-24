package com.gk.university.mapper;

import com.gk.university.model.Comment;
import com.gk.university.model.CommentExample;
import com.gk.university.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}