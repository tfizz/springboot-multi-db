package com.dev.coder.multiDbConn.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@ToString
public class Course {
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String code;

    public Course(String title, String code) {
        this.title = title;
        this.code = code;
    }
}
