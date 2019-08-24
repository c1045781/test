package com.gk.university.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String modifierName;
    private String outerTitle;
    private Long outerId;
    private String typeName;
    private Integer type;
}
