package com.dev.coder.multiDbConn.controller;

import com.dev.coder.multiDbConn.exception.NotFoundException;
import com.dev.coder.multiDbConn.model.Course;
import com.dev.coder.multiDbConn.repository.CourseJdbcRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/courses")
public class CourseController {

    CourseJdbcRepository repository;

    public CourseController(CourseJdbcRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Course> getCourses(){
        return repository.list();
    }

    @GetMapping(path = "/{course-id}")
    public Course getCourse(@PathVariable("course-id") int id) throws NotFoundException {
        Optional<Course> course = repository.get(id);
        if(!course.isPresent()){
            throw new NotFoundException("Course Not Found");
        }
        return course.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestBody @Valid Course course){
        repository.create(course);
    }

    @PutMapping(path = "/{course-id}")
    public void updateCourse(@PathVariable("course-id") int id,@RequestBody @Valid Course course){
        repository.update(id, course);
    }

    @DeleteMapping(path = "/{course-id}")
    public Course deleteCourse(@PathVariable("course-id") int id) throws NotFoundException {
        Course course = getCourse(id);
        repository.delete(course.getId());
        return course;
    }
}
