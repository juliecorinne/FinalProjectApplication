package com.test.controller;

import com.ibm.watson.developer_cloud.personality_insights.v2.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;
import com.sun.javafx.sg.prism.NGShape;
import com.test.models.ClassesEntity;
import com.test.models.TeacherEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.test.models.StudentEntity;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    String currentUser = "";

    @RequestMapping("/")

    public ModelAndView helloWorld() {
        /*
        PersonalityInsights service = new PersonalityInsights();
        service.setUsernameAndPassword("fd5414fb-1232-411b-9edf-40bfc878274c", "aeOAQtv30TuH");
        */

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
        Session session = sessionFact.openSession();
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
        Session session =  sessionFact.openSession();
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

    @RequestMapping("valid")
    public ModelAndView testing(@RequestParam("UserName") String userName, @RequestParam("Password") String password) {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectStudents = sessionFact.openSession();
        Session selectTeachers = sessionFact.openSession();
        Transaction tx = selectStudents.beginTransaction();
        Transaction tx2 = selectTeachers.beginTransaction();
        StudentEntity loginStudent = (StudentEntity)selectStudents.get(StudentEntity.class, userName);
        TeacherEntity loginTeacher = (TeacherEntity)selectTeachers.get(TeacherEntity.class, userName);

        if (loginStudent== null && loginTeacher != null) {
            Criteria c = selectStudents.createCriteria(StudentEntity.class);

            ArrayList<StudentEntity> studentList = (ArrayList<StudentEntity>) c.list();
            ModelAndView model =  new ModelAndView("teacherPage", "message", loginTeacher.getFirstName() + " TEACHER");
            model.addObject("theList", studentList);
            currentUser = userName;


            return model;
        }
        else if (loginStudent != null && loginTeacher == null) {
            if (loginStudent.getTestResults() == null) {
                return new ModelAndView("loggedIn", "message", loginStudent.getFirstName() + " STUDENT EMPTY TEST");
            }
            else {
                return new ModelAndView("loggedIn", "message", loginStudent.getFirstName() + " STUDENT");
            }
        }
        else {
            return new ModelAndView("welcome", "message1", "Invalid Info!");
        }




    }

    @RequestMapping("createClass")
    public ModelAndView classCreate() throws ClassNotFoundException, SQLException {
        /*
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory session= cfg.buildSessionFactory();
        Session selectClasses = session.openSession();
        Session selectTeachers = session.openSession();
        selectClasses.beginTransaction();
        selectTeachers.beginTransaction();
        Criteria c = selectClasses.createCriteria(ClassesEntity.class);
        Query q = selectClasses.createSQLQuery("SELECT name FROM Classes WHERE teacherUser = (SELECT userName  FROM Teacher INNER JOIN Classes ON Teacher.userName = Classes.teacherUser)");
        List<ClassesEntity> usersList = q.list();


        ArrayList<ClassesEntity> classList = (ArrayList<ClassesEntity>) c.list();
        */


        String url = "jdbc:mysql://finalprojectapp.cl4dqbesxxdh.us-east-2.rds.amazonaws.com/finalprojectapp";
        String userName = "root";
        String password = "admin1212";
        String query = "SELECT * FROM Classes WHERE teacherUser IN (SELECT userName  FROM Teacher INNER JOIN Classes ON Teacher.userName = Classes.teacherUser)";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<ClassesEntity> classNames = new ArrayList<ClassesEntity>();

        System.out.println(classNames.size());

        while (rs.next()) {
            ClassesEntity temp = new ClassesEntity();
            temp.setName(rs.getString("name"));
            temp.setClassId(rs.getString("classID"));
            classNames.add(temp);
        }
        rs.close();
        st.close();
        con.close();

        return new ModelAndView("createClass", "message", "testing").addObject("theList", classNames);

/*
        ModelAndView model = new ModelAndView("createClass", "message", "testing");
        model.addObject("theList", usersList);


        selectClasses.close();
        selectTeachers.close();
        return model;
        */
    }

    @RequestMapping("classCreated")
    public ModelAndView classCreated(@RequestParam("Classname") String className, @RequestParam("schoolName") String schoolName, @RequestParam("classID") String classID) {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        ClassesEntity checkClass = (ClassesEntity)session.get(ClassesEntity.class, classID);

        if (checkClass == null) {
            ClassesEntity insertClass = new ClassesEntity();
            insertClass.setClassId(classID);
            insertClass.setSchoolName(schoolName);
            insertClass.setName(className);
            insertClass.setTeacherUser(currentUser);
            checkClass = insertClass;
            session.save(checkClass);
            tx.commit();
            session.close();
            return new ModelAndView("classCreated", "message", "SUCCESS " + insertClass.getTeacherUser() + "  " +currentUser);
        }
        else {
            return new ModelAndView("classCreated", "message", "CLASS EXISTS");

        }




    }
/*
    @RequestMapping("editClass")
    public ModelAndView editClasses() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        ClassesEntity checkClass = (ClassesEntity)session.get(ClassesEntity.class, classID);




    }

*/


}
