package com.gk.university.controller;

import com.gk.university.dto.NotificationDTO;
import com.gk.university.enums.NotificationTypeEnum;
import com.gk.university.model.User;
import com.gk.university.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class notificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable("id") Long id,
                          Model model,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (notificationDTO.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()
                || notificationDTO.getType().equals(NotificationTypeEnum.REPLY_QUESTION.getType())) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}
