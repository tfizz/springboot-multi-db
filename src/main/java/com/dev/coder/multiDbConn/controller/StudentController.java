package com.dev.coder.multiDbConn.controller;

import com.dev.coder.multiDbConn.exception.NotFoundException;
import com.dev.coder.multiDbConn.model.Student;
import com.dev.coder.multiDbConn.repository.StudentJdbcRepository;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public List<Student> getStudents(){
        return repository.list();
    }

    @GetMapping(path = "/{student-id}")
    public Student getStudent(@PathVariable("student-id") int id) throws NotFoundException {
        Optional<Student> student = repository.get(id);
        if(!student.isPresent()){
            throw new NotFoundException("Student Not Found");
        }
        return student.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@Valid @RequestBody Student student){
        repository.create(student);
    }

    @PutMapping(path = "/{student-id}")
    public void updateStudent(@PathVariable("student-id") int id,@RequestBody @Valid Student student){
        repository.update(id, student);
    }

    @DeleteMapping(path = "/{student-id}")
    public Student deleteStudent(@PathVariable("student-id") int id) throws NotFoundException {
        Student student = getStudent(id);
        repository.delete(student.getId());
        return student;
    }


}
