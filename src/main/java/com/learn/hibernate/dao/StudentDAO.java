package com.learn.hibernate.dao;

import java.util.List;
import com.learn.hibernate.entity.Student;

public interface StudentDAO {
  void save(Student student);
  List<Student>findAll();
  List<Student>findByFirstName(String theFirstName);
  Student findById(int id);
  Student findByIdAndUpdate(int id, String attribute, String lastName);
}
