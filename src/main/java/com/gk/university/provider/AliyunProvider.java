package com.gk.university.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.gk.university.dto.ImageUploadDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Component
public class AliyunProvider {
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.expires}")
    private Integer expires;

    public ImageUploadDTO upload(InputStream inputStream, String fileName) throws IOException {

        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        URL url = null;

        try {
            // 上传Object.
            PutObjectResult putObjectResult = client.putObject(bucketName, fileName, inputStream);
            // 设置URL过期时间为1小时。
            Date expiration = new Date(new Date().getTime() + expires*1000);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            url = client.generatePresignedUrl(bucketName, fileName, expiration);
            System.out.println(putObjectResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        ImageUploadDTO imageUploadDTO = new ImageUploadDTO();
        imageUploadDTO.setUrl(url.toString());
        imageUploadDTO.setMessage("上传成功");
        imageUploadDTO.setSuccess(1);

        return imageUploadDTO;
    }

}
