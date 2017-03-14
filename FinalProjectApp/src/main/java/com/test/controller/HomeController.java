package com.test.controller;

import com.sun.javafx.sg.prism.NGShape;
import com.test.models.TeacherEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.test.models.StudentEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class HomeController {

    @RequestMapping("/")

    public ModelAndView helloWorld() {
        return new
                ModelAndView("welcome", "message", "Welcome! Log In Here.");

    }

    @RequestMapping("welcome")

    public ModelAndView helloWorld2() {
        return new
                ModelAndView("welcome2", "message", "Hello World2");

    }

    @RequestMapping("listStudents")
    public ModelAndView listStudents() {

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory sessionFact = cfg.buildSessionFactory();

        Session selectStudents = sessionFact.openSession();

        selectStudents.beginTransaction();

        Criteria c = selectStudents.createCriteria(StudentEntity.class);

        ArrayList<StudentEntity> studentList = (ArrayList<StudentEntity>) c.list();

        return new
                ModelAndView("welcome2", "cList", studentList);
    }


@RequestMapping("teacherRegister")
public ModelAndView teacherRegister() {

    String confirm = "";
    return new
            ModelAndView("teacherRegister", "confirmMessage", confirm);
}

    @RequestMapping("studentRegister")
    public ModelAndView studentRegister() {

        String confirm = "";
        return new
                ModelAndView("studentRegister", "confirmMessage", confirm);
    }



    @RequestMapping("addTeacher")
    public ModelAndView addTeacher(@RequestParam("FirstName") String firstName,
                                   @RequestParam("LastName") String lastName,
                                   @RequestParam("userName") String userName,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password){

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = (Session) sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        TeacherEntity newTeacher = new TeacherEntity();
        newTeacher.setFirstName(firstName);
        newTeacher.setLastName(lastName);
        newTeacher.setUserName(userName);
        newTeacher.setEmail(email);
        newTeacher.setPassword(password);

        session.save(newTeacher);
        tx.commit();
        session.close();

        String confirm = "Hello " + firstName + "!";

        return new
                ModelAndView("welcome","confirmMessage",confirm);
    }





    @RequestMapping("addStudent")
    public ModelAndView addStudent(@RequestParam("FirstName") String firstName,
                                   @RequestParam("LastName") String lastName,
                                   @RequestParam("userName") String userName,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password){

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = (Session) sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        StudentEntity newStudent = new StudentEntity();
        newStudent.setFirstName(firstName);
        newStudent.setLastName(lastName);
        newStudent.setUserName(userName);
        newStudent.setEmail(email);
        newStudent.setPassword(password);

        session.save(newStudent);
        tx.commit();
        session.close();

        String confirm = "Hello " + firstName + "!";

        return new
                ModelAndView("welcome","message1",confirm);
    }
}
