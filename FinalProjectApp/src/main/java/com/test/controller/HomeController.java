package com.test.controller;

import com.ibm.watson.developer_cloud.personality_insights.v2.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;
import com.sun.javafx.sg.prism.NGShape;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.test.models.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import static sun.security.krb5.Confounder.intValue;

@Controller
public class HomeController {
    //MVP
    String currentUser = "";
    String currentStudent = "";
    String currentClass = "";
    ArrayList<StudentEntity> remainingList = new ArrayList<StudentEntity>();
    int THEGROUPNUM = 0;
    String THECRITERIA;

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
        Criteria cc = selectStudents.createCriteria(StudentClassesEntity.class).add(Restrictions.eq("studentId", userName));
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
                ArrayList<StudentClassesEntity> theClasses = (ArrayList<StudentClassesEntity>) cc.list();
                return new ModelAndView("studentPage", "message5", loginStudent.getFirstName() + " STUDENT" + "  has taken test  " + loginStudent.getAgreeableness() + "  " + loginStudent.getConscientiousness()).addObject("theList", classList).addObject("courseList", theClasses);
            }
        } else {
            return new ModelAndView("welcome", "message1", "Invalid Info!");
        }


    }

    @RequestMapping("createClass")
    public ModelAndView classCreate() throws ClassNotFoundException, SQLException {
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
        currentClass = classSelected;

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

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory sessionFact = cfg.buildSessionFactory();

        Session selectStudents = sessionFact.openSession();

        selectStudents.beginTransaction();
        query = "SELECT * FROM `Student` WHERE `userName` IN (SELECT `studentID` FROM `Student_Classes` WHERE `classID` = '"+classSelected+"')";
        Criteria c = selectStudents.createCriteria(StudentEntity.class);
        Criteria cc = selectStudents.createCriteria(StudentClassesEntity.class);
        cc.add(Restrictions.like("classId", classSelected));
        rs = st.executeQuery(query);
        ArrayList<StudentEntity> studentList = new ArrayList<StudentEntity>();
        while (rs.next()) {
            StudentEntity temp = new StudentEntity();
            temp.setFirstName(rs.getString("firstName"));
            temp.setLastName(rs.getString("lastName"));
            studentList.add(temp);
        }

        //studentList = (ArrayList<StudentEntity>) cc.list();
        rs.close();
        st.close();
        con.close();
        return new ModelAndView("createGroup", "theList", classNames).addObject("studentList", studentList);
    }

    @RequestMapping("testTaken") //method for deleting parts of the list
    public ModelAndView deleteCustomer(@RequestParam("studentAnswer") String theAnswer) {
        PersonalityInsights service = new PersonalityInsights();
        service.setUsernameAndPassword("fd5414fb-1232-411b-9edf-40bfc878274c", "aeOAQtv30TuH");
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        StudentEntity theStudent = (StudentEntity) session.get(StudentEntity.class, currentStudent);
        Criteria c = session.createCriteria(ClassesEntity.class);
        ArrayList<ClassesEntity> classList = (ArrayList<ClassesEntity>)c.list();
        Criteria cc = session.createCriteria(ClassesEntity.class);
        Criteria ccc = session.createCriteria(StudentClassesEntity.class).add(Restrictions.eq("studentId", currentStudent));
        ArrayList<StudentClassesEntity> theClasses = (ArrayList<StudentClassesEntity>)ccc.list();

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

            theStudent.setTestResults("true");

        //save things to database
        session.save(theStudent);
        tx.commit();
        session.close();


        return new ModelAndView("studentPage", "message2", text).addObject("theList", classList).addObject("courseList", theClasses);


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

        return new ModelAndView("studentPage", "message5", theStudent.getFirstName() + " STUDENT" + "  has taken test  " + theStudent.getAgreeableness() + "  " + theStudent.getConscientiousness() + "  " + anotherList.size()).addObject("theList", classList).addObject("courseList", anotherList);
    }

    @RequestMapping("createGroup")
    public ModelAndView groupCreate() throws ClassNotFoundException, SQLException {
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
        return new ModelAndView("createGroup", "message", "gorup group").addObject("theList", classNames);
    }

    @RequestMapping("groupCreated")
    public ModelAndView createGroups(@RequestParam("groupNum") String groupNum, @RequestParam("selectCriteria") String criteria) throws ClassNotFoundException, SQLException {
        remainingList.clear();
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();

        String url = "jdbc:mysql://finalprojectapp.cl4dqbesxxdh.us-east-2.rds.amazonaws.com/finalprojectapp";
        String userName = "root";
        String password = "admin1212";
        //String query = "SELECT * FROM Classes WHERE teacherUser IN (SELECT userName  FROM Teacher INNER JOIN Classes ON Teacher.userName = Classes.teacherUser)";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);
        Statement st = con.createStatement();
        //ResultSet rs = st.executeQuery(query);
        String query = "";
        query = "SELECT * FROM `Student` WHERE `userName` IN (SELECT `studentID` FROM `Student_Classes` WHERE `classID` = '"+currentClass+"')";
        ResultSet rs = st.executeQuery(query);
        int counter = 0;
        while (rs.next()) {
            counter += 1;
        }

        // gotat set query where you count number of studnets in student_classes rather than just the student entity
        //int numStudents = ((Long)session.createCriteria(StudentEntity.class).add(Restrictions.eq("className", currentClass)).setProjection(Projections.rowCount()).uniqueResult()).intValue();
        int numStudents = counter;
        int groupNumber = Integer.valueOf(groupNum);
        THEGROUPNUM = groupNumber;
        int numGroups = numStudents / groupNumber;
        int remainingStudents = numStudents % groupNumber;
        THECRITERIA = criteria;
        System.out.println("You have " + numStudents + " Number of students. You can create " + numGroups + " groups. And you will have "  + remainingStudents   + " number of studnets not in groups and put into ");

        String output = "You have " + numStudents + " Number of students. You can create " + numGroups + " groups. And you will have "  + remainingStudents   + " number of studnets not in groups and put into ";

       // Criteria c = session.createCriteria(StudentEntity.class).add(Restrictions.eq("className", currentClass)).addOrder(Order.asc(criteria));

        query = "SELECT * FROM `Student` WHERE `userName` IN (SELECT `studentID` FROM `Student_Classes` WHERE `classID` = '"+currentClass+"') ORDER BY '"+criteria+"' asc";

        //query = "SELECT * FROM Student";

        //Query q = session.createSQLQuery(query);

        rs = st.executeQuery(query);

        int counter2 = 0;
        ArrayList<StudentEntity> studentList = new ArrayList<StudentEntity>();
        while (rs.next()) {
            //constructor for temp StudentEntity
            //push studneteklfjaf into studnetlist
            StudentEntity temp = new StudentEntity(rs.getString("firstName"), rs.getString("lastName"), rs.getString("userName"), rs.getString("password"),
                    rs.getString("email"), rs.getString("testResults"), rs.getString("className"), rs.getDouble("oppenness"),
                    rs.getDouble("emotionalRange"), rs.getDouble("agreeableness"), rs.getDouble("introExtro"), rs.getDouble("conscientiousness"));
            studentList.add(temp);
            counter2++;
        }

        //ArrayList<StudentEntity> studentList = (ArrayList<StudentEntity>) q.list();

        StudentEntity[][] groupList = new StudentEntity[numGroups][groupNumber];
        int studentCounter = 0;

            for (int i = 0; i < numGroups; i++) {
                for (int j = 0; j < groupNumber; j++) {
                    System.out.println("IN BOX " + i + "_" + j + " " + studentList.get(studentCounter).getFirstName());
                    groupList[i][j] = studentList.get(studentCounter);
                    studentCounter++;
                }
            }



        System.out.println("ORDERED GROUP SHOULD PRINT HERE");
        for (int i = 0; i < groupNumber; i++) {
                for (int j = 0; j < numGroups; j++) {
                    String idStudent = groupList[j][i].getUserName();
                    System.out.println(idStudent + " " + groupList[j][i].getFirstName());
                    String idClass = currentClass;
                    String groupName = ""+ currentClass + "_" + j;
                    System.out.println("PUSHING " + idStudent);
                    String query2 = "DELETE FROM `Student_Classes` WHERE `studentID` = '"+idStudent+"' AND `classID` = '"+idClass+"'";
                    st.executeUpdate(query2);
                    query2 = "INSERT INTO `Student_Classes` (studentID, classID, groupID) VALUES ('"+idStudent+"', '"+idClass+"', '"+groupName+"')";
                    st.executeUpdate(query2);


                }
                System.out.println("---");
            }

            studentCounter = studentList.size() - 1;

        while (remainingStudents != 0 ){
            System.out.println("ADDING TO REMAINING " + studentCounter + " " + studentList.get(studentCounter).getFirstName());
            remainingList.add(studentList.get(studentCounter));
            studentCounter--;
            remainingStudents--;
        }

        System.out.println("REMAINING STUDENTS ARE BELOW: " + remainingList.size());

        for (int q = 0; q < remainingList.size(); q++) {
            System.out.println(remainingList.get(q).getFirstName());
        }

        String nullString = "";
        query = "SELECT DISTINCT groupID FROM `Student_Classes` WHERE `classID` = '"+currentClass+"' AND `groupID` != '"+nullString+"'";
        rs = st.executeQuery(query);
        ArrayList<String> groupNames = new ArrayList<String>();
        int theCounter = numGroups;
        while (rs.next()) {
            if (theCounter == 0) break;
            groupNames.add(rs.getString("groupID"));
            theCounter--;
        }

        st.close();
        con.close();
        return new ModelAndView("viewGroups", "message", output + counter2).addObject("leftover", remainingList).addObject("theGroup", groupList).addObject("groups", groupNames);
    }

    @RequestMapping("manageGroup")
    public ModelAndView manageGroup(@RequestParam("selectName") String removeName, @RequestParam("selectGroup") String addGroup) throws ClassNotFoundException, SQLException {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();

        String url = "jdbc:mysql://finalprojectapp.cl4dqbesxxdh.us-east-2.rds.amazonaws.com/finalprojectapp";
        String userName = "root";
        String password = "admin1212";
        //String query = "SELECT * FROM Classes WHERE teacherUser IN (SELECT userName  FROM Teacher INNER JOIN Classes ON Teacher.userName = Classes.teacherUser)";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);
        Statement st = con.createStatement();
        //ResultSet rs = st.executeQuery(query);
        String query = "";
        query = "SELECT * FROM `Student` WHERE `userName` IN (SELECT `studentID` FROM `Student_Classes` WHERE `classID` = '"+currentClass+"')";
        ResultSet rs = st.executeQuery(query);
        int counter = 0;
        while (rs.next()) {
            counter += 1;
        }

        // gotat set query where you count number of studnets in student_classes rather than just the student entity
        //int numStudents = ((Long)session.createCriteria(StudentEntity.class).add(Restrictions.eq("className", currentClass)).setProjection(Projections.rowCount()).uniqueResult()).intValue();
        int numStudents = counter;
        int groupNumber = THEGROUPNUM;
        int numGroups = numStudents / groupNumber;
        int remainingStudents = numStudents % groupNumber;


        // Criteria c = session.createCriteria(StudentEntity.class).add(Restrictions.eq("className", currentClass)).addOrder(Order.asc(criteria));

        query = "SELECT * FROM `Student` WHERE `userName` IN (SELECT `studentID` FROM `Student_Classes` WHERE `classID` = '"+currentClass+"') ORDER BY '"+THECRITERIA+"' asc";

        //query = "SELECT * FROM Student";

        //Query q = session.createSQLQuery(query);

        rs = st.executeQuery(query);

        int counter2 = 0;
        ArrayList<StudentEntity> studentList = new ArrayList<StudentEntity>();
        while (rs.next()) {
            //constructor for temp StudentEntity
            //push studneteklfjaf into studnetlist
            StudentEntity temp = new StudentEntity(rs.getString("firstName"), rs.getString("lastName"), rs.getString("userName"), rs.getString("password"),
                    rs.getString("email"), rs.getString("testResults"), rs.getString("className"), rs.getDouble("oppenness"),
                    rs.getDouble("emotionalRange"), rs.getDouble("agreeableness"), rs.getDouble("introExtro"), rs.getDouble("conscientiousness"));
            studentList.add(temp);
            counter2++;
        }

        //ArrayList<StudentEntity> studentList = (ArrayList<StudentEntity>) q.list();

        StudentEntity[][] groupList = new StudentEntity[numGroups][groupNumber];
        int studentCounter = 0;
        int addToGroup = Integer.parseInt(addGroup);
        int index = Integer.parseInt(removeName);
        for (int i = 0; i < numGroups; i++) {
            for (int j = 0; j < groupNumber; j++) {
                System.out.println("IN BOX " + i + "_" + j + " " + studentList.get(studentCounter).getFirstName());
                groupList[i][j] = studentList.get(studentCounter);
                studentCounter++;
            }
        }
        //groupList[addToGroup][groupNumber-1] = remainingList.get(index);


        System.out.println("ORDERED GROUP SHOULD PRINT HERE");
        for (int i = 0; i < groupNumber; i++) {
            for (int j = 0; j < numGroups; j++) {
                String idStudent = groupList[j][i].getUserName();
                System.out.println(idStudent + " " + groupList[j][i].getFirstName());
                String idClass = currentClass;
                String groupName = ""+ currentClass + "_" + j;
                System.out.println("PUSHING " + idStudent);
                String query2 = "DELETE FROM `Student_Classes` WHERE `studentID` = '"+idStudent+"' AND `classID` = '"+idClass+"'";
                st.executeUpdate(query2);
                query2 = "INSERT INTO `Student_Classes` (studentID, classID, groupID) VALUES ('"+idStudent+"', '"+idClass+"', '"+groupName+"')";
                st.executeUpdate(query2);
            }
            System.out.println("---");
        }

        String query2 = "DELETE FROM `Student_Classes` WHERE `studentID` = '"+remainingList.get(index).getUserName()+"' AND `classID` = '"+currentClass+"'";
        st.executeUpdate(query2);
        String groupIndexed = currentClass + "_" + addToGroup;
        query2 = "INSERT INTO `Student_Classes` (studentID, classID, groupID) VALUES ('"+remainingList.get(index).getUserName()+"', '"+currentClass+"', '"+groupIndexed+"')";
        st.executeUpdate(query2);
        System.out.println("GROU INDEX   " + addToGroup);
        String output = "Your Student " + remainingList.get(index).getFirstName() + " has been added to Group " + (addToGroup + 1);
        remainingList.remove(index);

        String nullString = "";
        query = "SELECT DISTINCT groupID FROM `Student_Classes` WHERE `classID` = '"+currentClass+"' AND `groupID` != '"+nullString+"'";
        rs = st.executeQuery(query);
        ArrayList<String> groupNames = new ArrayList<String>();
        int theCounter = numGroups;
        while (rs.next()) {
            if (theCounter == 0) break;
            groupNames.add(rs.getString("groupID"));
            theCounter--;
        }


        st.close();
        con.close();
        return new ModelAndView("viewGroups", "message", output).addObject("leftover", remainingList).addObject("theGroup", groupList).addObject("groups", groupNames);
    }

    @RequestMapping("viewGroup")
    public ModelAndView seeGroups() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory sessionFact = cfg.buildSessionFactory();

        Session selectStudents = sessionFact.openSession();

        selectStudents.beginTransaction();

        Criteria c = selectStudents.createCriteria(StudentClassesEntity.class);

        ArrayList<StudentClassesEntity> theList = (ArrayList<StudentClassesEntity>) c.list();
        return new ModelAndView("seeGroups", "listOfGroups", theList);
    }

    @RequestMapping("viewStudentGroup")
    public ModelAndView seeStudentGroups(@RequestParam("groupToSee") String chosenClass) {
        System.out.println("TESTING " + chosenClass);
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory sessionFact = cfg.buildSessionFactory();

        Session selectStudents = sessionFact.openSession();

        selectStudents.beginTransaction();

        Criteria c = selectStudents.createCriteria(StudentClassesEntity.class).add(Restrictions.eq("classId", chosenClass));

        ArrayList<StudentClassesEntity> theList = (ArrayList<StudentClassesEntity>) c.list();
        return new ModelAndView("seeGroups", "listOfGroups", theList);
    }

}
