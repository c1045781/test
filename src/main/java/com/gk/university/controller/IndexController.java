package com.gk.university.controller;

import com.gk.university.dto.PaginationDTO;
import com.gk.university.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(value = "currentPage" ,defaultValue = "1")Integer currentPage,
                        @RequestParam(value = "size" ,defaultValue = "5")Integer size) {

        PaginationDTO pagInationDTO = questionService.findPagination(currentPage,size);
        model.addAttribute("pagination",pagInationDTO);
        return "index";
    }
}
