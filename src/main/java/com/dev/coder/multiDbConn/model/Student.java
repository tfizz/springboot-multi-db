package com.dev.coder.multiDbConn.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@ToString
public class Student {
    private int id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
