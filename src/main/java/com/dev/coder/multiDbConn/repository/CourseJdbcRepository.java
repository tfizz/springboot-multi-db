package com.dev.coder.multiDbConn.repository;

import com.dev.coder.multiDbConn.dao.CourseDAO;
import com.dev.coder.multiDbConn.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository("CourseJdbcRepository")
public class CourseJdbcRepository implements CourseDAO {

    private static final Logger log = LoggerFactory.getLogger(CourseJdbcRepository.class);

    private JdbcTemplate jdbcTemplate;

    public CourseJdbcRepository(@Qualifier("courseJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Course> mapper = (rs, i) ->{
        Course course = new Course(rs.getString("title"), rs.getString("code"));
        course.setId(rs.getInt("id"));
        return course;
    };

    @Override
    public List<Course> list() {
        String query = "select * from courses";
        return jdbcTemplate.query(query, mapper);
    }

    @Override
    public Optional<Course> get(int id) {
        String query = "select * from courses where id = ?";
        Course course = null;
        try{
            course = jdbcTemplate.queryForObject(query, new Object[]{id}, new int[]{Types.INTEGER}, mapper);
        }
        catch (Exception ex){
            log.debug("Course not found: {}", id);
        }
        return Optional.ofNullable(course);
    }

    @Override
    public void create(Course data) {
        String query = "insert into courses(title,code) values(?,?)";
        int rowsAffected = jdbcTemplate.update(query, new Object[]{data.getTitle(), data.getCode()}, new int[]{Types.VARCHAR,Types.VARCHAR});
        log.debug("Inserting record {} into courses database. {} row affected",data,rowsAffected);
    }

    @Override
    public void update(int id, Course data) {
        String query = "update courses set title=?, code=? where id = ?";
        int rowsAffected = jdbcTemplate.update(query, new Object[]{data.getTitle(), data.getCode(), id}, new int[]{Types.VARCHAR,Types.VARCHAR,Types.INTEGER});
        log.debug("Updating course record {} in courses database. {} row affected",id,rowsAffected);
    }

    @Override
    public void delete(int id) {
        String query = "delete from courses where id = ?";
        int rowsAffected = jdbcTemplate.update(query, new Object[]{id}, new int[]{Types.INTEGER});
        log.debug("Deleting course record {} in courses database. {} row affected",id,rowsAffected);
    }

    public void purgeRecords(){
        String query = "truncate table courses";
        jdbcTemplate.update(query);
        log.debug("Purging records in courses table");
    }
}
