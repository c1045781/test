package com.gk.university.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private String content;
    private Integer type;
    private Long parentId;
}
