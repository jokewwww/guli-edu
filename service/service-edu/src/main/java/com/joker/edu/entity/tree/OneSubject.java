package com.joker.edu.entity.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {

    private String id;
    private String title;
    private List<TwoSubject> children=new ArrayList<>();
}
