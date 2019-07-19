package com.gk.university.controller;

import com.gk.university.dto.PageInationDTO;
import com.gk.university.dto.QuestionDTO;
import com.gk.university.mapper.UserMapper;
import com.gk.university.model.User;
import com.gk.university.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String hello(HttpServletRequest request, Model model,
                        @RequestParam(value = "currentPage" ,defaultValue = "1")Integer currentPage,
                        @RequestParam(value = "size" ,defaultValue = "5")Integer size) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    request.getSession().setAttribute("user", user);
                    break;
                }
            }
        }

        PageInationDTO pageInationDTO = questionService.list(currentPage,size);
        model.addAttribute("pageInation",pageInationDTO);
        return "index";
    }
}
