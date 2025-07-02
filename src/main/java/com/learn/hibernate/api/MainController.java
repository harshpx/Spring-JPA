package com.learn.hibernate.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.parser.TE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.hibernate.dao.StudentDAO;
import com.learn.hibernate.dao.TeacherDAO;
import com.learn.hibernate.entity.Student;
import com.learn.hibernate.entity.Teacher;

@RestController
public class MainController {
  private StudentDAO studentDAO;
  private TeacherDAO teacherDAO;

  @Autowired
  public MainController(StudentDAO studentDAO, TeacherDAO teacherDAO) {
    this.studentDAO = studentDAO;
    this.teacherDAO = teacherDAO;
  }

  @GetMapping("/")
  public String home() {
    return "Welcome to the Hibernate Application!";
  }
  @GetMapping("/save")
  public String demoSave() {
    Student stud = new Student("Harsh", "Priye", "harsh.rzf@gmail.com");
    studentDAO.save(stud);
    return "Student saved! check DB.\n" + stud.toString();
  }
  @GetMapping("/findbyid")
  public String listAll(@RequestParam(required = false, value = "id", defaultValue = "0") String id) {
    try {
      int requiredId = Integer.parseInt(id);
      if (requiredId > 0) {
        Student std = studentDAO.findById(requiredId);
        if (std == null) {
          return "Not found";
        }
        return std.toString();
      } else {
        throw new NumberFormatException("Id Must be positive");
      }
    } catch (NumberFormatException e) {
      List<Student> arr = studentDAO.findAll();
      String str = "";
      for (int i = 0; i < arr.size(); i++) {
        str += arr.get(i).toString();
        str += "\n";
      }
      return str;
    }
  }
  @GetMapping("/findbyname")
  public String findByName(@RequestParam(required = false, value = "name", defaultValue = "") String name) {
    List<Student> arr;
    if(name != null) {
      arr = studentDAO.findByFirstName(name);
    } else {
      arr = studentDAO.findAll();
    }
    String str = "";
    for (int i = 0; i < arr.size(); i++) {
      str += arr.get(i).toString();
      str += "\n";
    }
    return str;
  }
  @GetMapping("/findall") 
  public String findAll() {
    List<Student> arr = studentDAO.findAll();
    String str = "";
    for (Student st : arr) {
      str += st.toString();
      str += "\n";
    }
    return str;
  }
  @GetMapping("/updatebyid")
  public String updateById(
    @RequestParam(value = "id", required = true, defaultValue = "0") int id, 
    @RequestParam(value = "attribute", required = true, defaultValue = "null") String attribute, 
    @RequestParam(value = "value", required = true, defaultValue = "null") String value
  ) {
    Student updatedStudent = studentDAO.findByIdAndUpdate(id, attribute, value);
    if (updatedStudent == null) {
      return "Failed to update, Invalid params";
    }
    return "Updated row: " + updatedStudent.toString();
  }

  @GetMapping("/updatebycondition")
  public String temp(
    @RequestParam(value = "targetAttribute", required = true) String targetAttribute,
    @RequestParam(value = "targetValue", required = true) String targetValue,
    @RequestParam(value = "conditionAttribute", required = true) String conditionAttribute,
    @RequestParam(value = "conditionValue", required = true) String conditionValue
  ) {
    int res = studentDAO.batchUpdateByCondition(targetAttribute, targetValue, conditionAttribute, conditionValue);
    if (res == 404) {
      return "Failed to update";
    }
    return "Updated!";
  }
  @GetMapping("/deletebyid")
  public String deleteById(@RequestParam(required = true, value = "id") int id) {
    Student deletedStudent = studentDAO.deleteById(id);
    if (deletedStudent == null) {
      return "Student not found";
    }
    return "Student deleted successfully, Deleted Student: " + deletedStudent.toString();
  }

  @GetMapping("/deletebycondition")
  public String deleteByCondition(
    @RequestParam(required = true, value = "attribute") String attribute,
    @RequestParam(required = true, value = "value") String value
  ) {
    int responseCode = studentDAO.batchDeleteByCondition(attribute, value);
    if (responseCode == 404) {
      return "Failed to execute deletion";
    }
    return "Batch deletion successful";
  }

  @GetMapping("/saveteachers")
  public String saveTeachers() {
    List<Teacher> arr = new ArrayList<>();
    Teacher t1 = new Teacher("Alpha", "Sharma", "alpha.sharma@gmail.com");
    arr.add(t1);
    Teacher t2 = new Teacher("Beta", "Singh", "beta.singh@gmail.com");
    arr.add(t2);
    Teacher t3 = new Teacher("Gamma", "Choudhary", "gamma.choudhary@gmail.com");
    arr.add(t3);
    teacherDAO.save(arr);
    return "Saved successfully!";
  }

  @GetMapping("/listteachers")
  public String listTeachers() {
    List<Teacher> arr = teacherDAO.findAll();
    String str = "";
    for (Teacher t : arr) {
      str += t.toString();
      str += "\n";
    }
    return str;
  }
}
