package com.test.controller;

import com.ibm.watson.developer_cloud.personality_insights.v2.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;
import com.sun.javafx.sg.prism.NGShape;
import com.test.models.ClassesEntity;
import com.test.models.StudentClassesEntity;
import com.test.models.TeacherEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.test.models.StudentEntity;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    //MVP
    String currentUser = "";
    String currentStudent = "";

    @RequestMapping("/")

    public ModelAndView helloWorld() {
        PersonalityInsights service = new PersonalityInsights();
        service.setUsernameAndPassword("fd5414fb-1232-411b-9edf-40bfc878274c", "aeOAQtv30TuH");
        String text = "My name is Jeff and I like to code and I like music and etc etc"
                + "I woke up at 6 am yesterday  and I came to grand circus bootcamp and started workign on "
                + "out project. we continued working and it was a good time and we got a lot of work done"
                + "we have to show our project tomorrow so it is a little nerve racking but we should"
                + "be good. Hopefuly we can have a good project ready for them to show and we can do well."
                + "I have a dog named Jessie and she is doing well. She is a mutt and weighs 60 pounds."
                + "Blah blah "
                + "I woke up at 6 am yesterday  and I came to grand circus bootcamp and started workign on "
                + "out project. we continued working and it was a good time and we got a lot of work done"
                + "we have to show our project tomorrow so it is a little nerve racking but we should"
                + "be good. Hopefuly we can have a good project ready for them to show and we can do well."
                + "I have a dog named Jessie and she is doing well. She is a mutt and weighs 60 pounds."
                + "Blah blah ";


        String text2 = "Call me Ishmael. Some years ago-never mind how long precisely-having "
                + "little or no money in my purse, and nothing particular to interest me on shore, "
                + "I thought I would sail about a little and see the watery part of the world. "
                + "It is a way I have of driving off the spleen and regulating the circulation. "
                + "Whenever I find myself growing grim about the mouth; whenever it is a damp, "
                + "drizzly November in my soul; whenever I find myself involuntarily pausing before "
                + "coffin warehouses, and bringing up the rear of every funeral I meet; and especially "
                + "whenever my hypos get such an upper hand of me, that it requires a strong moral "
                + "principle to prevent me from deliberately stepping into the street, and methodically "
                + "knocking people's hats off-then, I account it high time to get to sea as soon as I can. "
                + "This is my substitute for pistol and ball. With a philosophical flourish Cato throws himself "
                + "upon his sword; I quietly take to the ship. There is nothing surprising in this. "
                + "If they but knew it, almost all men in their degree, some time or other, cherish "
                + "very nearly the same feelings towards the ocean with me. There now is your insular "
                + "city of the Manhattoes, belted round by wharves as Indian isles by coral reefs-commerce surrounds ";


        Profile profile = service.getProfile(text).execute();
        Profile profile2 = service.getProfile(text2).execute();
        System.out.println("---");
        JSONObject json = new JSONObject(profile);
        JSONArray ar = json.getJSONObject("tree").getJSONArray("children");
        JSONObject json2 = new JSONObject(profile2);
        JSONArray ar2 = json2.getJSONObject("tree").getJSONArray("children");

        System.out.println("profile");
        String testing;
        for (int i = 0; i < 5; i++) {
            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(i).get("percentage").toString();
            float anotherTest = Float.valueOf(testing);
            System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(i).get("name"));
            System.out.println("FLOAT PERCENTAGE " + testing);
        }

        System.out.println("---");

        for (int i = 0; i < 5; i++) {
            System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(i).get("percentage"));
            System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(i).get("name"));
            System.out.println(i);
        }


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
                                   @RequestParam("password") String password) {

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
                ModelAndView("welcome", "confirmMessage", confirm);
    }


    @RequestMapping("addStudent")
    public ModelAndView addStudent(@RequestParam("FirstName") String firstName,
                                   @RequestParam("LastName") String lastName,
                                   @RequestParam("userName") String userName,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password) {

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
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
                ModelAndView("welcome", "message1", confirm);
    }

    @RequestMapping("valid")
    public ModelAndView testing(@RequestParam("UserName") String userName, @RequestParam("Password") String password) {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectStudents = sessionFact.openSession();
        Session selectTeachers = sessionFact.openSession();
        Transaction tx = selectStudents.beginTransaction();
        Transaction tx2 = selectTeachers.beginTransaction();
        StudentEntity loginStudent = (StudentEntity) selectStudents.get(StudentEntity.class, userName);
        TeacherEntity loginTeacher = (TeacherEntity) selectTeachers.get(TeacherEntity.class, userName);

        if (loginStudent == null && loginTeacher != null) {
            Criteria c = selectStudents.createCriteria(StudentEntity.class);

            ArrayList<StudentEntity> studentList = (ArrayList<StudentEntity>) c.list();
            ModelAndView model = new ModelAndView("teacherPage", "message", loginTeacher.getFirstName() + " TEACHER");
            model.addObject("theList", studentList);
            currentUser = userName;


            return model;
        } else if (loginStudent != null && loginTeacher == null) {
            currentStudent = userName;
            if (loginStudent.getTestResults() == null || loginStudent.getTestResults().equals("false")) {
                return new ModelAndView("loggedIn", "message", loginStudent.getFirstName() + " STUDENT EMPTY TEST");
            } else {
                //it is true
                Criteria c = selectStudents.createCriteria(ClassesEntity.class);
                ArrayList<ClassesEntity> classList = (ArrayList<ClassesEntity>) c.list();
                return new ModelAndView("studentPage", "message5", loginStudent.getFirstName() + " STUDENT" + "  has taken test  " + loginStudent.getAgreeableness() + "  " + loginStudent.getConscientiousness()).addObject("theList", classList);
            }
        } else {
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
    public ModelAndView classCreated(@RequestParam("Classname") String className, @RequestParam("schoolName") String schoolName, @RequestParam("classID") String classID) throws ClassNotFoundException, SQLException {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        ClassesEntity checkClass = (ClassesEntity) session.get(ClassesEntity.class, classID);

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
            return new ModelAndView("createClass", "message", "SUCCESS " + currentUser).addObject("theList", classNames);
        } else {
            return new ModelAndView("createClass", "message", "CLASS EXISTS");

        }


    }

    @RequestMapping("classSelected")
    public ModelAndView editClasses(@RequestParam("selectClass") String classSelected) throws SQLException, ClassNotFoundException {

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

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory sessionFact = cfg.buildSessionFactory();

        Session selectStudents = sessionFact.openSession();

        selectStudents.beginTransaction();

        Criteria c = selectStudents.createCriteria(StudentEntity.class);
        c.add(Restrictions.like("className", classSelected));

        ArrayList<StudentEntity> studentList = new ArrayList<StudentEntity>();
        studentList = (ArrayList<StudentEntity>) c.list();


        return new ModelAndView("createClass", "theList", classNames).addObject("studentList", studentList);
    }
/*
    @RequestMapping("delete") //method for deleting parts of the list
    public ModelAndView deleteCustomer(@RequestParam("id") String id) {
        // temp will store info for the object that we want to delete
        StudentEntity temp = new StudentEntity();
        temp.setCustomerID(id);
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory fact = cfg.buildSessionFactory();
        Session student = fact.openSession();
        student.beginTransaction();
        student.delete(temp);// delete the object from the list
        student.getTransaction().commit();//delete row from the datebase
        ArrayList<CustomersEntity> customerList = getAllCustomers();
        return new
                ModelAndView("welcome2", "cList", customerList);
    }
    */

    @RequestMapping("testTaken") //method for deleting parts of the list
    public ModelAndView deleteCustomer(@RequestParam("studentAnswer") String theAnswer) {
        PersonalityInsights service = new PersonalityInsights();
        service.setUsernameAndPassword("fd5414fb-1232-411b-9edf-40bfc878274c", "aeOAQtv30TuH");
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        StudentEntity theStudent = (StudentEntity) session.get(StudentEntity.class, currentStudent);

        String text = theAnswer;
        Profile profile = service.getProfile(text).execute();
        JSONObject json = new JSONObject(profile);
        JSONArray ar = json.getJSONObject("tree").getJSONArray("children");

        String testing;
            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).get("percentage").toString();
            double anotherTest = Double.valueOf(testing);
            System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).get("name"));
            theStudent.setOppenness(anotherTest);

            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(1).get("percentage").toString();
            anotherTest = Double.valueOf(testing);
            System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(1).get("name"));
            theStudent.setConscientiousness(anotherTest);

            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(2).get("percentage").toString();
            anotherTest = Double.valueOf(testing);
            System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(2).get("name"));
            theStudent.setIntroExtro(anotherTest);

            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(3).get("percentage").toString();
            anotherTest = Double.valueOf(testing);
            System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(3).get("name"));
            theStudent.setAgreeableness(anotherTest);

            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(4).get("percentage").toString();
            anotherTest = Double.valueOf(testing);
            System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(4).get("name"));
            theStudent.setEmotionalRange(anotherTest);

            //theStudent.setTestResults(1);
        //above grabs student in database

        //save things to database
        session.save(theStudent);
        tx.commit();
        session.close();


        return new ModelAndView("loggedIn", "message2", text);


    }

    @RequestMapping("classJoined")
    public ModelAndView joinClass(@RequestParam("classToJoin") String classID) {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        StudentEntity theStudent = (StudentEntity) session.get(StudentEntity.class, currentStudent);
        theStudent.setClassName(classID);

        StudentClassesEntity temp = new StudentClassesEntity();
        temp.setClassId(classID);
        temp.setStudentId(currentStudent);
        session.save(temp);

         Criteria c = session.createCriteria(ClassesEntity.class);
        ArrayList<ClassesEntity> classList = (ArrayList<ClassesEntity>) c.list();

        /*
        Criteria cc = session.createCriteria(StudentClassesEntity.class).add(Restrictions.eq("studentID", currentStudent));
         ArrayList<StudentClassesEntity> anotherList = (ArrayList<StudentClassesEntity>) cc.list();
*/

            Criteria cc = session.createCriteria(StudentClassesEntity.class);
            cc.add(Restrictions.eq("studentId", currentStudent));
          ArrayList<StudentClassesEntity> anotherList = (ArrayList<StudentClassesEntity>) cc.list();

//        ArrayList<String> anotherList= (ArrayList<String>)q.list();

        session.save(theStudent);
        tx.commit();
        session.close();

        return new ModelAndView("studentPage", "message5", theStudent.getFirstName() + " STUDENT" + "  has taken test  " + theStudent.getAgreeableness() + "  " + theStudent.getConscientiousness() + "  " + anotherList.size()).addObject("theList", classList);
    }

    @RequestMapping("createGroup")
    public ModelAndView groupCreate() {
        return new ModelAndView("createGroup", "message", "gorup group");
    }
}
