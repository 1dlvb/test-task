package com.example.testtask.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Section {

    @NonNull
    private Integer level;

    @NonNull
    private String title;
    private List<Section> sectionContent = new ArrayList<>();

    @Override
    public String toString(){
        return String.format("%s", title);
    }
}
