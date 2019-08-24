package com.gk.university.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {
    private String search;
    private Integer size;
    private Integer index;
}
