package com.example.testtask.service;

import java.util.List;

public interface FileParserService {
    StringBuilder parseOnLists(List<String> text, char delimiter, String carriage);
    StringBuilder parseOnSB(List<String> splitText, char delimiter, String carriage);
}
