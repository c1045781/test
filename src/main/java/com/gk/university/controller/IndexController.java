package com.gk.university.controller;

import com.gk.university.cache.HotTagCache;
import com.gk.university.dto.HotTagDTO;
import com.gk.university.dto.PaginationDTO;
import com.gk.university.schedule.HotTagSchedule;
import com.gk.university.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagSchedule hotTagSchedule;

    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                        @RequestParam(value = "size", defaultValue = "5") Integer size,
                        @RequestParam(value = "search" ,required = false)String search ,
                        @RequestParam(value = "tag" ,required = false)String tag) {

        PaginationDTO pagInationDTO = questionService.findPagination(tag,search, currentPage, size);
        List<HotTagDTO> hotTags = hotTagSchedule.getHotTags();
        model.addAttribute("hotTags",hotTags);
        model.addAttribute("pagination", pagInationDTO);
        model.addAttribute("search",search);
        model.addAttribute("tag",tag);
        return "index";
    }
}
