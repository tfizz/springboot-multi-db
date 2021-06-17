package com.dev.coder.multiDbConn.dao;

import com.dev.coder.multiDbConn.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDAO extends CrudDAO<Student> {
}
