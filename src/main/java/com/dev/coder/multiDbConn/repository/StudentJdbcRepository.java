package com.dev.coder.multiDbConn.repository;

import com.dev.coder.multiDbConn.dao.CrudDAO;
import com.dev.coder.multiDbConn.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository("StudentJdbcRepository")
public class StudentJdbcRepository implements CrudDAO<Student> {

    private static final Logger log = LoggerFactory.getLogger(StudentJdbcRepository.class);

    private JdbcTemplate jdbcTemplate;

    public StudentJdbcRepository(@Qualifier("studentJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Student> mapper = (rs, i) ->{
        Student student = new Student(rs.getString("firstName"), rs.getString("lastName"));
        student.setId(rs.getInt("id"));
        return student;
    };

    @Override
    public List<Student> list() {
        String query = "select * from students";
        return jdbcTemplate.query(query, mapper);
    }

    @Override
    public Optional<Student> get(int id) {
        String query = "select * from students where id = ?";
        Student student = null;
        try{
            student = jdbcTemplate.queryForObject(query, new Object[]{id}, new int[]{Types.INTEGER}, mapper);
        }
        catch (Exception ex){
            log.debug("Student not found: {}", id);
        }
        return Optional.ofNullable(student);
    }

    @Override
    public void create(Student data) {
        String query = "insert into students(firstName,lastName) values(?,?)";
        int rowsAffected = jdbcTemplate.update(query, new Object[]{data.getFirstName(), data.getLastName()}, new int[]{Types.VARCHAR, Types.VARCHAR});
        log.debug("Inserting record {} into students database. {} row affected",data,rowsAffected);
    }

    @Override
    public void update(int id, Student data) {
        String query = "update students set firstName=?, lastName=? where id = ?";
        int rowsAffected = jdbcTemplate.update(query, new Object[]{data.getFirstName(), data.getLastName(), id}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER});
        log.debug("Updating user record {} in students database. {} row affected",id,rowsAffected);
    }

    @Override
    public void delete(int id) {
        String query = "delete from students where id = ?";
        int rowsAffected = jdbcTemplate.update(query, new Object[]{id}, new int[]{Types.INTEGER});
        log.debug("Deleting user record {} in students database. {} row affected",id,rowsAffected);
    }

    public void purgeRecords(){
        String query = "truncate table students";
        jdbcTemplate.update(query);
        log.debug("Purging record in students table.");
    }


}
