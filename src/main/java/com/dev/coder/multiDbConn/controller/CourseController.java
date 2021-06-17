package com.dev.coder.multiDbConn.controller;

import com.dev.coder.multiDbConn.exception.NotFoundException;
import com.dev.coder.multiDbConn.model.Course;
import com.dev.coder.multiDbConn.repository.CourseJdbcRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all courses")
    public List<Course> getCourses(){
        return repository.list();
    }

    @GetMapping(path = "/{course-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get single course by id")
    public Course getCourse(@PathVariable("course-id") int id) throws NotFoundException {
        Optional<Course> course = repository.get(id);
        if(!course.isPresent()){
            throw new NotFoundException("Course Not Found");
        }
        return course.get();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create a new course")
    public void createCourse(@RequestBody @Valid Course course){
        repository.create(course);
    }

    @PutMapping(path = "/{course-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "modify course specified by id")
    public void updateCourse(@PathVariable("course-id") int id,@RequestBody @Valid Course course) throws NotFoundException {
        getCourse(id);
        repository.update(id, course);
    }

    @DeleteMapping(path = "/{course-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete course specified by id")
    public Course deleteCourse(@PathVariable("course-id") int id) throws NotFoundException {
        Course course = getCourse(id);
        repository.delete(course.getId());
        return course;
    }
}
