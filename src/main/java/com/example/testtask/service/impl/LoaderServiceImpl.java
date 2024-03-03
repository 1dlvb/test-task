package com.example.testtask.service.impl;

import com.example.testtask.service.FileParserService;
import com.example.testtask.service.LoaderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoaderServiceImpl implements LoaderService {

    private final FileParserService fileParser;
    @Override
    public ResponseEntity<?> getParsedFile(MultipartFile file) throws IOException {
        List<String> fileLines = splitFile(file.getBytes());
        String fileNameAndExtension = String.format("%s.txt", UUID.randomUUID());
        String fileText = fileParser.parseOnLists(fileLines, '#', "-").toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileNameAndExtension)
                .body(fileText.getBytes());
    }
    private List<String> splitFile(byte[] bytes){
        String inputText = new String(bytes, StandardCharsets.UTF_8);
        List<String> textList = new ArrayList<>(List.of(inputText.split("\r|\n|\r\n")));
        textList.removeIf(item -> item == null || item.isEmpty());
        return textList;
    }
}
