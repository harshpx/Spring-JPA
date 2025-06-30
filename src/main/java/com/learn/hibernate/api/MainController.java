package com.learn.hibernate.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.hibernate.dao.StudentDAO;
import com.learn.hibernate.entity.Student;

@RestController
public class MainController {
  private StudentDAO studentDAO;

  @Autowired
  public MainController(StudentDAO studentDAO) {
    this.studentDAO = studentDAO;
  }

  @GetMapping("/")
  public String home() {
    return "Welcome to the Hibernate Application!";
  }
  @GetMapping("/save")
  public String demoSave() {
    Student stud = new Student("Bhumi", "Sharma", "bhumisharma1080@gmail.com");
    studentDAO.save(stud);
    return "Student saved! check DB.\n" + stud.toString();
  }
  @GetMapping("/findbyid")
  public String listAll(@RequestParam(required = false, value = "id", defaultValue = "0") String id) {
    try {
      int requiredId = Integer.parseInt(id);
      if (requiredId > 0) {
        Student std = studentDAO.findById(requiredId);
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
}
