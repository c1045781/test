package com.gk.university.runner;

import com.gk.university.schedule.HotTagSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    HotTagSchedule hotTagSchedule;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        hotTagSchedule.hotTag();
    }
}
