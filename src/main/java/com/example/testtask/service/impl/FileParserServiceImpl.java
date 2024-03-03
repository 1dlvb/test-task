package com.example.testtask.service.impl;

import com.example.testtask.model.Section;
import com.example.testtask.service.FileParserService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileParserServiceImpl implements FileParserService {

    /**
     * this algorithm is using Lists
     * @param splitText is a text, that has been split to a List of lines.
     * @param delimiter is a delimiter, that determines file sections.
     * @param carriage is a symbol for indentation.
     * @return the contents and a structure of the newly parsed file.
     */
    @SneakyThrows
    @Override
    public StringBuilder parseOnLists(List<String> splitText, char delimiter, String carriage) {
        List<Section> strings = new ArrayList<>();
        List<Section> content = new ArrayList<>();
        List<Section> sections = new ArrayList<>();

        int level = 0;
        for (String line : splitText) {
            if (line.startsWith(String.valueOf(delimiter))) {
                content = new ArrayList<>();
                level = getLevel(line, delimiter);
                sections.add(new Section(level, line, content));
            }
            else{
                    content.add(new Section(level, line));
                }
            strings.add(new Section(level, removeDelimiters(line, delimiter)));
        }

        List<Section> structure = new ArrayList<>();
        for (Section item : sections) {
            String sectionTitle = item.getTitle();
            int curLevel = item.getLevel()-1;
            List<Section> sectionChildren = item.getSectionContent();
            String sectionName = removeDelimiters(sectionTitle, delimiter);
            if (sectionChildren.isEmpty()) {
            structure.add(new Section(curLevel, sectionName));
            } else {
                structure.add(new Section(curLevel, sectionName, sectionChildren));
            }
        }

        StringBuilder parsedFileContent = new StringBuilder();
        parsedFileContent.append("----------TEXT---------- \n")
                .append(getParsedText(strings, carriage))
                .append("\n --------STRUCTURE-------- \n")
                .append(getTextStructure(structure, carriage));
        return parsedFileContent;
    }

    /**
     * this algorithm is using StringBuilders
     * @param splitText is a text, that has been split to a List of lines.
     * @param delimiter is a delimiter, that determines file sections.
     * @param carriage is a symbol for indentation.
     * @return the contents and a structure of the newly parsed file.
     */
    public StringBuilder parseOnSB(List<String> splitText, char delimiter, String carriage) {
        int prevLevel = 0;
        StringBuilder textSB = new StringBuilder();
        StringBuilder structureSB = new StringBuilder();
        for (String datum : splitText) {
            if(datum.contains(String.valueOf(delimiter))) {
                structureSB.append(datum.replaceAll(String.valueOf(delimiter), carriage)).append("\n");
            }
            int level = getLevel(datum, delimiter);
            if(level == 0 && prevLevel != 0) level = prevLevel;
            textSB.append(carriage.repeat(Math.max(0, level)));
            datum = datum.replaceAll(String.valueOf(delimiter), "");
            textSB.append(datum);
            textSB.append("\n");
            prevLevel = level;
        }
        StringBuilder parsedFileContent = new StringBuilder();
        parsedFileContent.append("----------TEXT---------- \n")
                .append(textSB)
                .append("\n --------STRUCTURE-------- \n")
                .append(structureSB);
        return parsedFileContent;
    }

    private String removeDelimiters (String string, char delimiter){
        int numberOfDelimiters = 0;
        if (!string.startsWith(String.valueOf(delimiter))) return string.trim();
        while ((numberOfDelimiters < string.length()) && (string.charAt(numberOfDelimiters) == delimiter))
            numberOfDelimiters++;
        return string.substring(numberOfDelimiters).trim();
    }

    private static int getLevel (String section,char delimiter){
        int level = 0;
        if (!section.startsWith(String.valueOf(delimiter))) return 0;

        char[] chars = section.toCharArray();
        for (char aChar : chars) {
            if (aChar == delimiter) level++;
            else break;
        }
        return level;
    }

    private StringBuilder getParsedText(List<Section> sections, String carriage){
        if (Objects.equals(carriage, "") || carriage == null) throw new IllegalArgumentException();
        StringBuilder builder = new StringBuilder();
        for(Section s: sections){
            int level = s.getLevel();
            String car = carriage.repeat(level);
            List<Section> ch = s.getSectionContent();
            builder.append(String.format("%s%s\n", car, s.getTitle()));
            if (ch != null) {
                for(Section section: ch){
                    builder.append(car).append(section.getTitle()).append("\n");
                }
            }
        }
        return builder;
    }
    private StringBuilder getTextStructure(List<Section> sections, String carriage){
        if (Objects.equals(carriage, "") || carriage == null) throw new IllegalArgumentException();
        StringBuilder builder = new StringBuilder();
        for(Section s: sections){
            int level = s.getLevel();
            String car = carriage.repeat(level);
            builder.append(String.format("%s%s\n", car, s.getTitle()));
        }
        return builder;
    }
}




