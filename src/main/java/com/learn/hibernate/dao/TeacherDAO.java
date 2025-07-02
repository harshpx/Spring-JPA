package com.learn.hibernate.dao;

import java.util.List;

import com.learn.hibernate.entity.Teacher;

public interface TeacherDAO {
  void save(List<Teacher> teacherList);
  List<Teacher> findAll();
}
