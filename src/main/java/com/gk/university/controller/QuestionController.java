package com.gk.university.controller;

import com.gk.university.dto.CommentDTO;
import com.gk.university.dto.QuestionDTO;
import com.gk.university.enums.CommentTypeEnum;
import com.gk.university.model.Question;
import com.gk.university.service.CommentService;
import com.gk.university.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model) {
        QuestionDTO question = questionService.findQuestionById(id);
        List<Question> relatedQuestion = questionService.relatedQuestion(question);
//        List<CommentDTO> comments = commentService.findCommentById(id, CommentTypeEnum.QUESTION);
        questionService.invcView(id);
        model.addAttribute("question", question);
//        model.addAttribute("comments", comments);
        model.addAttribute("questionList",relatedQuestion);
        return "question";
    }
}
