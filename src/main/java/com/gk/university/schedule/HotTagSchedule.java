package com.gk.university.schedule;

import com.gk.university.dto.HotTagDTO;
import com.gk.university.service.QuestionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@Data
public class HotTagSchedule {
    List<HotTagDTO> hotTags;
    @Autowired
    private QuestionService questionService;

    @Scheduled(cron = "0 0 */8 * * *")
    public void hotTag() {
        hotTags = questionService.findHotTag();
    }
}
