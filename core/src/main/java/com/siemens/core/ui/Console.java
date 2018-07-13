package com.siemens.core.ui;


import com.siemens.core.model.Student;
import com.siemens.core.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by radu.
 */
@Component
public class Console {
    @Autowired
    private StudentService studentService;

    public void runConsole() {
        //add student
        studentService.addStudent(new Student("test", 1));

        //get all students
        studentService.getAll().forEach(System.out::println);

        studentService.getAll().stream().findFirst()
                .ifPresent(student -> {
                    Long id = student.getId();

                    //update student
                    Student s = new Student("testupdate", 1);
                    s.setId(id);
                    studentService.updateStudent(s);

                    //delete Student
                    studentService.deleteStudent(id);
                });
    }
}
