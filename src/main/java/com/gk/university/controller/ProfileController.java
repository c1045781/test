package com.gk.university.controller;

import com.gk.university.dto.PaginationDTO;
import com.gk.university.mapper.UserMapper;
import com.gk.university.model.User;
import com.gk.university.service.NotificationService;
import com.gk.university.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "size", defaultValue = "5") Integer size,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "redirect:/";
        }
        if ("question".equals(action)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionService.findPaginationById(user.getId(), currentPage, size);
            model.addAttribute("pagination", paginationDTO);
        } /*else if ("reply".equals(action)) {
            PaginationDTO paginationDTO = notificationService.findPaginationById(user.getId(), currentPage, size);
            int unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("section", "reply");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("unreadCount",unreadCount);
        }*/
        return "profile";
    }
}
