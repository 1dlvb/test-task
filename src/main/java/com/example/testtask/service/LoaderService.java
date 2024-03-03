package com.example.testtask.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface LoaderService {
    ResponseEntity<?> getParsedFile(MultipartFile file) throws IOException;
}
