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

        String confirm = "Register New Teacher!";
        return new
                ModelAndView("teacherRegister", "message1", confirm);
    }

    @RequestMapping("studentRegister")
    public ModelAndView studentRegister() {

        String confirm = "Register New Student!";
        return new
                ModelAndView("studentRegister", "message", confirm);
    }

    //adding teacher after they register
    @RequestMapping("addTeacher")
    public ModelAndView addTeacher(@RequestParam("FirstName") String firstName,
                                   @RequestParam("LastName") String lastName,
                                   @RequestParam("userName") String userName,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password) {

        //opens hibernation configuration
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        //adding restrictions uses current username for teacher
        Criteria c = session.createCriteria(TeacherEntity.class).add(Restrictions.eq("userName", userName));
        ArrayList<TeacherEntity> tList = (ArrayList<TeacherEntity>)c.list();

        //validation to check if username already exists
        if (tList.size() == 0) {
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
        else {
            //return same page with error message
            return new ModelAndView("teacherRegister", "message1", "User already exists!");
        }
    }

    //adding student after registration
    @RequestMapping("addStudent")
    public ModelAndView addStudent(@RequestParam("FirstName") String firstName,
                                   @RequestParam("LastName") String lastName,
                                   @RequestParam("userName") String userName,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password) {

        //opens hibernation configuration
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        Criteria c = session.createCriteria(StudentEntity.class).add(Restrictions.eq("userName", userName));
        ArrayList<StudentEntity> cList = (ArrayList<StudentEntity>)c.list();

        //checks to see if student username already exists
        if (cList.size() == 0) {
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
        else{
             return new ModelAndView("studentRegister", "message", "User already exists!");
        }
    }

    @RequestMapping("valid")
    public ModelAndView testing(@RequestParam("UserName") String userName, @RequestParam("Password") String password) {
        // after login page, it reaches this method to see if the log in user is a teacher or a student
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectStudents = sessionFact.openSession();
        Session selectTeachers = sessionFact.openSession();
        Transaction tx = selectStudents.beginTransaction();
        Transaction tx2 = selectTeachers.beginTransaction();
        StudentEntity loginStudent = (StudentEntity) selectStudents.get(StudentEntity.class, userName);
        TeacherEntity loginTeacher = (TeacherEntity) selectTeachers.get(TeacherEntity.class, userName);
        Criteria cc = selectStudents.createCriteria(StudentClassesEntity.class).add(Restrictions.eq("studentId", userName));

        //below statements check if the login user is a teacher or a student
        if (loginStudent == null && loginTeacher != null) {
            // if teacher, login and view teacher's page

            //checks to see if username and password match
            if (loginTeacher.getPassword().equals(password)) {
                Criteria c = selectStudents.createCriteria(StudentEntity.class);
                ArrayList<StudentEntity> studentList = (ArrayList<StudentEntity>) c.list();
                ModelAndView model = new ModelAndView("teacherPage", "message", loginTeacher.getFirstName() + " TEACHER");
                model.addObject("theList", studentList);
                currentUser = userName;


                return model;
            }
            else {
                return new ModelAndView("welcome", "message", "Invalid Info!");
            }
        } else if (loginStudent != null && loginTeacher == null) {
            // if student, log in
            if (loginStudent.getPassword().equals(password)) {
                currentStudent = userName;
                //if theres nothing in the test result column or if it says "false"
                if (loginStudent.getTestResults() == null || loginStudent.getTestResults().equals("false")) {
                    //view test page if student hasn't taken test
                    //logged in page is where students take the personality test
                    return new ModelAndView("loggedIn");
                } else {
                    //go to list of classes page if the student has taken test
                    Criteria c = selectStudents.createCriteria(ClassesEntity.class);
                    ArrayList<ClassesEntity> classList = (ArrayList<ClassesEntity>) c.list();
                    ArrayList<StudentClassesEntity> theClasses = (ArrayList<StudentClassesEntity>) cc.list();
                    return new ModelAndView("studentPage", "message5", loginStudent.getFirstName() + " STUDENT" + "  has taken test  " + loginStudent.getAgreeableness() + "  " + loginStudent.getConscientiousness()).addObject("theList", classList).addObject("courseList", theClasses);
                }
            }
            else {
                return new ModelAndView("welcome", "message1", "Invalid Info!");
            }
        } else {
            // user does not exist here so reload the welcome page with new info
            return new ModelAndView("welcome", "message1", "Invalid Info!");
        }


    }

    @RequestMapping("createClass")
    public ModelAndView classCreate() throws ClassNotFoundException, SQLException {
        // page can either create a new class or select a class in which we can look/make groups
        String url = "jdbc:mysql://finalprojectapp.cl4dqbesxxdh.us-east-2.rds.amazonaws.com/finalprojectapp";
        String userName = "root";
        String password = "admin1212";
        //inner join for classes and teacher
        String query = "SELECT * FROM Classes WHERE teacherUser IN (SELECT userName  FROM Teacher INNER JOIN Classes ON Teacher.userName = Classes.teacherUser)";

        //uses jdbc, difficult to establish hibernate inner join
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<ClassesEntity> classNames = new ArrayList<ClassesEntity>();

        //System.out.println(classNames.size());

        while (rs.next()) {
            ClassesEntity temp = new ClassesEntity();
            temp.setName(rs.getString("name"));
            temp.setClassId(rs.getString("classID"));
            classNames.add(temp);
        }
        rs.close();
        st.close();
        con.close();

        //the jsp page 'createClass' below will redirect to make a new class if create class button is clicked
        return new ModelAndView("createClass", "message", "testing").addObject("theList", classNames);

/*
        ModelAndView model = new ModelAndView("createClass", "message", "testing");
        model.addObject("theList", usersList);


        selectClasses.close();
        selectTeachers.close();
        return model;
        */
    }

    //takes in parameters for the class information
    @RequestMapping("classCreated")
    public ModelAndView classCreated(@RequestParam("Classname") String className, @RequestParam("schoolName") String schoolName, @RequestParam("classID") String classID) throws ClassNotFoundException, SQLException {
        //redirects to this from createClass if new class is created, takes in paramets of class name, etc
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        ClassesEntity checkClass = (ClassesEntity) session.get(ClassesEntity.class, classID);

        //below makes sure that class doesn't already exist based off classID aka validation
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

            //System.out.println(classNames.size());

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
        //fron the dropdown, redirects to the page where group making settings can be made
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

        //System.out.println(classNames.size());

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
        //displays all of the students from the student_classes junction table where the studentID is associated w classID
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

    @RequestMapping("testTaken")
    public ModelAndView deleteCustomer(@RequestParam("studentAnswer") String theAnswer) {
        //validation to ensure user is being wordy enough
        //API doesn't function without at least 100 words
        if (theAnswer.length() < 100) {
            return new ModelAndView("loggedIn", "message", "Please enter at least 20 words per answer!");

        }
        else {
            //takes the answers of the personality test and uses the API to create profile results, goes to the student page once results are figured out
            PersonalityInsights service = new PersonalityInsights();
            service.setUsernameAndPassword("fd5414fb-1232-411b-9edf-40bfc878274c", "aeOAQtv30TuH");
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact = cfg.buildSessionFactory();
            Session session = sessionFact.openSession();
            Transaction tx = session.beginTransaction();
            StudentEntity theStudent = (StudentEntity) session.get(StudentEntity.class, currentStudent);
            Criteria c = session.createCriteria(ClassesEntity.class);
            ArrayList<ClassesEntity> classList = (ArrayList<ClassesEntity>) c.list();
            Criteria cc = session.createCriteria(ClassesEntity.class);
            Criteria ccc = session.createCriteria(StudentClassesEntity.class).add(Restrictions.eq("studentId", currentStudent));
            ArrayList<StudentClassesEntity> theClasses = new ArrayList<StudentClassesEntity>();
            //if the list is not empty, it will be able to be called once the student is logged in
            if (!ccc.list().isEmpty()) {
                theClasses = (ArrayList<StudentClassesEntity>) ccc.list();
            }


            //theAnswer is student answers
            String text = theAnswer;
            //API requires making a profile
            Profile profile = service.getProfile(text).execute();
            JSONObject json = new JSONObject(profile);
            //goes through the JSON tree
            JSONArray ar = json.getJSONObject("tree").getJSONArray("children");

            String testing;
            //once in the JSON tree, goes through each index until we get to the necessary index for big 5 personality traits
            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).get("percentage").toString();
            double anotherTest = Double.valueOf(testing);
            //System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).get("name"));
            //sets the value for the student with the current username
            theStudent.setOppenness(anotherTest);

            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(1).get("percentage").toString();
            anotherTest = Double.valueOf(testing);
            //System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(1).get("name"));
            theStudent.setConscientiousness(anotherTest);

            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(2).get("percentage").toString();
            anotherTest = Double.valueOf(testing);
            //System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(2).get("name"));
            theStudent.setIntroExtro(anotherTest);

            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(3).get("percentage").toString();
            anotherTest = Double.valueOf(testing);
            //System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(3).get("name"));
            theStudent.setAgreeableness(anotherTest);

            testing = ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(4).get("percentage").toString();
            anotherTest = Double.valueOf(testing);
            //System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(4).get("name"));
            theStudent.setEmotionalRange(anotherTest);

            //sets test results in students table to "true", so when a student logs in again they don't need to take the test over
            theStudent.setTestResults("true");
            ArrayList<StudentClassesEntity> emptyList = new ArrayList<StudentClassesEntity>();

            //save things to database
            session.save(theStudent);
            tx.commit();
            session.close();

            if (theClasses.size() == 0) {
                return new ModelAndView("studentPage", "message2", text).addObject("theList", classList).addObject("courseList", emptyList);

            }
            else {

                return new ModelAndView("studentPage", "message2", text).addObject("theList", classList).addObject("courseList", theClasses);
            }
        }


    }

    @RequestMapping("classJoined")
    public ModelAndView joinClass(@RequestParam("classToJoin") String classID) {
        //for a student when they click the dropdown and join a class, adds to the junction table
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

        //System.out.println(classNames.size());

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


    //GO THROUGH EVVERYTHING BELOW HERE AND MAKE SURE EVERYBODY KNOWS
    @RequestMapping("groupCreated")
    public ModelAndView createGroups(@RequestParam("groupNum") String groupNum, @RequestParam("selectCriteria") String criteria) throws ClassNotFoundException, SQLException {
        //validation to ensure teacher us entering a number and not leaving this part blank
        if (!groupNum.isEmpty()) {
            //group created using settings from previous teacher page where they choose number of people per group
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
            // following query grabs all the students in currentClass
            query = "SELECT * FROM `Student` WHERE `userName` IN (SELECT `studentID` FROM `Student_Classes` WHERE `classID` = '" + currentClass + "')";
            ResultSet rs = st.executeQuery(query);
            int counter = 0;
            //getting the count in currentClass / getting numStudents in class
            while (rs.next()) {
                counter += 1;
            }

            // go through calculations to figure out number of students per group and remainder students
            int numStudents = counter;
            int groupNumber = Integer.valueOf(groupNum); //changed to integer from string
            THEGROUPNUM = groupNumber; //save globally to access in later method
            int numGroups = numStudents / groupNumber;
            int remainingStudents = numStudents % groupNumber;
            THECRITERIA = criteria;
            //System.out.println("You have " + numStudents + " Number of students. You can create " + numGroups + " groups. And you will have " + remainingStudents + " number of studnets not in groups and put into ");

            //following query produces list of students by criteria in ascending order
            query = "SELECT * FROM `Student` WHERE `userName` IN (SELECT `studentID` FROM `Student_Classes` WHERE `classID` = '" + currentClass + "') ORDER BY '" + criteria + "' asc";
            rs = st.executeQuery(query);

            int counter2 = 0;
            ArrayList<StudentEntity> studentList = new ArrayList<StudentEntity>();
            //counter counts all the students again, temp student is created from query and added to studentList which is list of ascending
            while (rs.next()) {
                //constructor for temp StudentEntity
                //push temp student into studnetlist
                StudentEntity temp = new StudentEntity(rs.getString("firstName"), rs.getString("lastName"), rs.getString("userName"), rs.getString("password"),
                        rs.getString("email"), rs.getString("testResults"), rs.getString("className"), rs.getDouble("oppenness"),
                        rs.getDouble("emotionalRange"), rs.getDouble("agreeableness"), rs.getDouble("introExtro"), rs.getDouble("conscientiousness"));
                studentList.add(temp);
                counter2++;
            }

            // start of 2D array
            StudentEntity[][] groupList = new StudentEntity[numGroups][groupNumber];
            int studentCounter = 0;

            // Loops through array regularly by row then column and initializes all
            // by going through studentList and just pushing them in in order
            for (int i = 0; i < numGroups; i++) {
                for (int j = 0; j < groupNumber; j++) {
                    //System.out.println("IN BOX " + i + "_" + j + " " + studentList.get(studentCounter).getFirstName());
                    groupList[i][j] = studentList.get(studentCounter);
                    studentCounter++;
                }
            }

            //Goes down rows first then jumps over column in order to get the order of students not in ascneding order but
            //by our grouped student, so goes in a 2x2 matrix, [0,0] [1,0] [0,1] [1,1]
            for (int i = 0; i < groupNumber; i++) {
                for (int j = 0; j < numGroups; j++) {
                    String idStudent = groupList[j][i].getUserName();
                    //System.out.println(idStudent + " " + groupList[j][i].getFirstName());
                    String idClass = currentClass;
                    String groupName = "" + currentClass + "_" + j;
                    //System.out.println("PUSHING " + idStudent);

                    // SQL would not let us update things in junction table, so we have to get rid of that row
                    String query2 = "DELETE FROM `Student_Classes` WHERE `studentID` = '" + idStudent + "' AND `classID` = '" + idClass + "'";
                    st.executeUpdate(query2);

                    // once row is gotten rid of, we re add the row with the same elements and a new groupID which woudl have previously been null
                    query2 = "INSERT INTO `Student_Classes` (studentID, classID, groupID) VALUES ('" + idStudent + "', '" + idClass + "', '" + groupName + "')";
                    st.executeUpdate(query2);
                }
            }

            //setting studentCounter to last index of studentList to grab the highest value
            studentCounter = studentList.size() - 1;

            //loop through and down from the top of the list until remainingStudents is 0 to grab all the remainder students
            //push into remainingList now
            while (remainingStudents != 0) {
                //System.out.println("ADDING TO REMAINING " + studentCounter + " " + studentList.get(studentCounter).getFirstName());
                remainingList.add(studentList.get(studentCounter));
                studentCounter--;
                remainingStudents--;
            }

            String nullString = "";
            //grabs the groupIDs from junction table based on classid
            query = "SELECT DISTINCT groupID FROM `Student_Classes` WHERE `classID` = '" + currentClass + "' AND `groupID` != '" + nullString + "'";
            rs = st.executeQuery(query);
            ArrayList<String> groupNames = new ArrayList<String>();
            int theCounter = numGroups;
            //goes through and grabs group IDs and puts them in groupNames list
            while (rs.next()) {
                if (theCounter == 0) break;
                groupNames.add(rs.getString("groupID"));
                theCounter--;
            }

            st.close();
            con.close();
            return new ModelAndView("viewGroups", "leftover", remainingList).addObject("theGroup", groupList).addObject("groups", groupNames);
        }
        else {
            return editClasses(currentClass).addObject("errorMessage", "Please enter group size!");
        }
        }

        @RequestMapping("manageGroup")
        public ModelAndView manageGroup(@RequestParam("selectName") String removeName, @RequestParam("selectGroup") String addGroup) throws ClassNotFoundException, SQLException {
            //allows the teacher to add remaining students into the groups that are created
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
            //turning addGroup into an integer, used in line 744
            int addToGroup = Integer.parseInt(addGroup);
            //turns removeName into an int, used in line 744,752
            int index = Integer.parseInt(removeName);
            //nested for loop, does the same thing as the mapping from above
            for (int i = 0; i < numGroups; i++) {
                for (int j = 0; j < groupNumber; j++) {
                    //System.out.println("IN BOX " + i + "_" + j + " " + studentList.get(studentCounter).getFirstName());
                    groupList[i][j] = studentList.get(studentCounter);
                    studentCounter++;
                }
            }
            //groupList[addToGroup][groupNumber-1] = remainingList.get(index);


            //System.out.println("ORDERED GROUP SHOULD PRINT HERE");
            //don't need this again beacuse it is a repeat and students in groups are already saved in the database
      /*      for (int i = 0; i < groupNumber; i++) {
                for (int j = 0; j < numGroups; j++) {
                    String idStudent = groupList[j][i].getUserName();
                    //System.out.println(idStudent + " " + groupList[j][i].getFirstName());
                    String idClass = currentClass;
                    String groupName = ""+ currentClass + "_" + j;
                    //System.out.println("PUSHING " + idStudent);
                    String query2 = "DELETE FROM `Student_Classes` WHERE `studentID` = '"+idStudent+"' AND `classID` = '"+idClass+"'";
                    st.executeUpdate(query2);
                    query2 = "INSERT INTO `Student_Classes` (studentID, classID, groupID) VALUES ('"+idStudent+"', '"+idClass+"', '"+groupName+"')";
                    st.executeUpdate(query2);
                }
                //System.out.println("---");
            }
            */

            //remainglist is a global value that is saved until create groups is accessed, this way when someone goes
            //manage a group the program knows who was left over

            //deleting person from remainingList based off index based off selection in dropdown list value
            String query2 = "DELETE FROM `Student_Classes` WHERE `studentID` = '"+remainingList.get(index).getUserName()+"' AND `classID` = '"+currentClass+"'";
            st.executeUpdate(query2);
            String groupIndexed = currentClass + "_" + addToGroup;
            //Inserts row of same values of delted student/class/group
            query2 = "INSERT INTO `Student_Classes` (studentID, classID, groupID) VALUES ('"+remainingList.get(index).getUserName()+"', '"+currentClass+"', '"+groupIndexed+"')";
            st.executeUpdate(query2);
            //System.out.println("GROU INDEX   " + addToGroup);
            String output = "Your Student " + remainingList.get(index).getFirstName() + " " +  remainingList.get(index).getLastName() + " has been added to Group " + (addToGroup + 1);
            //removes from remainingList
            remainingList.remove(index);

            //needed a blank string so that the program didnt grab the students still within the class,
            //but not in a group

            String nullString = "";
            //grabs all group names in currentClass
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
        //sees groups in studentclasses entity
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
        //views groups by student ID in the group
        //System.out.println("TESTING " + chosenClass);
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory sessionFact = cfg.buildSessionFactory();

        Session selectStudents = sessionFact.openSession();

        selectStudents.beginTransaction();

        Criteria c = selectStudents.createCriteria(StudentClassesEntity.class).add(Restrictions.eq("classId", chosenClass));

        ArrayList<StudentClassesEntity> theList = (ArrayList<StudentClassesEntity>) c.list();
        return new ModelAndView("seeGroups", "listOfGroups", theList);
    }

}
