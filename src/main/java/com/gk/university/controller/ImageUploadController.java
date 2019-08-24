package com.gk.university.controller;

import com.gk.university.dto.ImageUploadDTO;
import com.gk.university.provider.AliyunProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageUploadController {
    @Autowired
    private AliyunProvider aliyunProvider;
    @RequestMapping("/file/image")
    @ResponseBody
    public ImageUploadDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        try {
            ImageUploadDTO imageUploadDTO = aliyunProvider.upload(file.getInputStream(),  file.getOriginalFilename());
            return imageUploadDTO;
        } catch (Exception e) {
            ImageUploadDTO imageUploadDTO = new ImageUploadDTO();
            imageUploadDTO.setSuccess(0);
            imageUploadDTO.setMessage("上传失败");
            return imageUploadDTO;
        }
    }
}
