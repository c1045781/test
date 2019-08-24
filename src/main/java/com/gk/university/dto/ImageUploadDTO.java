package com.gk.university.dto;

import lombok.Data;

@Data
public class ImageUploadDTO {
    private int success;
    private String message;
    private String url;
}
