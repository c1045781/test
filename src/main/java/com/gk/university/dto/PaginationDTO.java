package com.gk.university.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOList;
    private Integer currentPage;
    private Integer size;
    private Integer totalCount;
    private Integer totalPage;
}