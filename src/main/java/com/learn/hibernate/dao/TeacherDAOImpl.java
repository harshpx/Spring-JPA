package com.learn.hibernate.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.learn.hibernate.entity.Teacher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class TeacherDAOImpl implements TeacherDAO {

  private EntityManager entityManager;

  @Autowired
  public TeacherDAOImpl(EntityManager theEntityManager) {
    entityManager = theEntityManager;
  }

  @Override
  @Transactional
  public void save(List<Teacher> teacherList) {
    for (Teacher t : teacherList) {
      entityManager.persist(t);
    }
  }
  @Override
  public List<Teacher> findAll() {
    TypedQuery<Teacher> query = entityManager.createQuery("from Teacher", Teacher.class);
    return query.getResultList();
  }
}
