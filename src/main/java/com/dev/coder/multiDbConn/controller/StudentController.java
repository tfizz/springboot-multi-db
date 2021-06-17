package com.dev.coder.multiDbConn.controller;

import com.dev.coder.multiDbConn.exception.NotFoundException;
import com.dev.coder.multiDbConn.model.Student;
import com.dev.coder.multiDbConn.repository.StudentJdbcRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/students")
public class StudentController {

    private StudentJdbcRepository repository;

    public StudentController(StudentJdbcRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all students")
    public List<Student> getStudents(){
        return repository.list();
    }

    @GetMapping(path = "/{student-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get single user specified by id")
    public Student getStudent(@PathVariable("student-id") int id) throws NotFoundException {
        Optional<Student> student = repository.get(id);
        if(!student.isPresent()){
            throw new NotFoundException("Student Not Found");
        }
        return student.get();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create new student")
    public void createStudent(@Valid @RequestBody Student student){
        repository.create(student);
    }

    @PutMapping(path = "/{student-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "modify student specified by id")
    public void updateStudent(@PathVariable("student-id") int id,@RequestBody @Valid Student student){
        repository.update(id, student);
    }

    @DeleteMapping(path = "/{student-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete student specified by id")
    public Student deleteStudent(@PathVariable("student-id") int id) throws NotFoundException {
        Student student = getStudent(id);
        repository.delete(student.getId());
        return student;
    }


}
