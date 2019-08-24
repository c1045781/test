package com.gk.university.mapper;

import com.gk.university.dto.QuestionQueryDTO;
import com.gk.university.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question question);
    int incCommentCount(Question question);
    List<Question> relatedQuestion(Question question);
    int countQuestionBySearch(QuestionQueryDTO questionQueryDTO);
    List<Question> selectQuestionBySearch(QuestionQueryDTO questionQueryDTO);
}