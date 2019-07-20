package com.gk.university.controller;

import com.gk.university.dto.QuestionDTO;
import com.gk.university.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id,
                           Model model){
        QuestionDTO question = questionService.findQuestionById(id);
        model.addAttribute("question",question);
        return "question";
    }
}
