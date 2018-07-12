package com.siemens.core.service;

import com.siemens.core.model.Student;
import com.siemens.core.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

/**
 * Created by radu.
 */
@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);


    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAll() {
        LOG.trace("getAll --- method entered");

        List<Student> result = studentRepository.findAll();

        LOG.trace("getAll: result={}", result);

        return result;
    }

    @Override
    public Student findStudent(Long id) {
        LOG.trace("findStudent: id={}", id);

        Student student = studentRepository.findById(id).get();

        LOG.trace("findStudent: student={}", student);

        return student;
    }

    @Override
    public void addStudent(Student student) {
        LOG.trace("addStudent: student={}", student);

        studentRepository.save(student);

        LOG.trace("addStudent --- method finished");
    }

    @Override
    @Transactional
    public void updateStudent(Student student) {
        LOG.trace("updateStudent: student={}", student);

        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        if (studentOptional.isPresent()) {
            Student st = studentOptional.get();
            st.setName(student.getName());
            st.setGrade(student.getGrade());
        }

        LOG.trace("updateStudent: studentOptional={}", studentOptional);
    }

    @Override
    public void deleteStudent(Long id) {
        LOG.trace("deleteStudent: id={}", id);

        studentRepository.deleteById(id);

        LOG.trace("deleteStudent --- method finished");
    }
}
