package com.jiggycode.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class S3Service {

    @Value("${aws.bucketName}")
    private String bucketName;
}
