package com.example.testtask.controller;

import com.example.testtask.service.LoaderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/test-task")
@AllArgsConstructor
public class FileParserController {

    private final LoaderService loader;

    @PostMapping("/get-parsed-file")
    public ResponseEntity<?> getParsedFile(@RequestParam("file") MultipartFile file) throws IOException {
        return loader.getParsedFile(file);
    }

}
