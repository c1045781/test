package com.gk.university.controller;

import com.gk.university.cache.TagCache;
import com.gk.university.dto.QuestionDTO;
import com.gk.university.dto.TagDTO;
import com.gk.university.exception.CustomizeErrorCode;
import com.gk.university.exception.CustomizeException;
import com.gk.university.mapper.QuestionMapper;
import com.gk.university.model.Question;
import com.gk.university.model.User;
import com.gk.university.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id, Model model,HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        QuestionDTO questionDTO = questionService.findQuestionById(id);
        if(questionDTO.getCreator() != user.getId()){
            throw new CustomizeException(CustomizeErrorCode.CAN_NOT_EDIT_QUESTION);
        }
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag, Model model,
                            @RequestParam("id") Long id,
                            HttpServletRequest request) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        if (title == null || title.equals("")) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        String filterInvalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(filterInvalid)) {
            model.addAttribute("error", "标签格式错误" + filterInvalid);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setId(id);
        question.setDescription(description);
        question.setCreator(user.getId());
        questionService.insertOrUpdateQuestion(question);
        return "redirect:/";
    }
}
