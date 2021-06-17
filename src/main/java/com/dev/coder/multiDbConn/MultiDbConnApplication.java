package com.dev.coder.multiDbConn;

import com.dev.coder.multiDbConn.model.Course;
import com.dev.coder.multiDbConn.model.Student;
import com.dev.coder.multiDbConn.repository.CourseJdbcRepository;
import com.dev.coder.multiDbConn.repository.StudentJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MultiDbConnApplication {

    @Autowired
    private StudentJdbcRepository studentRepository;
    @Autowired
    private CourseJdbcRepository courseRepository;

    public static void main(String[] args) {
        SpringApplication.run(MultiDbConnApplication.class, args);
    }

    @PostConstruct
    public void initDummyData() {
        List<Student> studentList = Arrays.asList(new Student("John", "Doe"), new Student("Jane", "Doe"));
		List<Course> courseList = Arrays.asList(new Course("Introduction to Programming", "DEV-101"), new Course("Introduction to Database Management", "DBM-101"));

		// purge records in database
        studentRepository.purgeRecords();
        courseRepository.purgeRecords();

        // insert records;
		studentList.stream().forEach(student -> studentRepository.create(student));
		courseList.stream().forEach(course -> courseRepository.create(course));
    }

}
