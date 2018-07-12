package com.siemens.core.service;

import com.siemens.core.model.Student;

import java.util.List;

/**
 * Created by radu.
 */
public interface StudentService {

    List<Student> getAll();

    Student findStudent(Long id);

    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(Long id);
}
