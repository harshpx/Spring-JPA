package com.learn.hibernate.dao;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import com.learn.hibernate.entity.Student;

@Repository // similar to @Component but tailored for DAO implementation
public class StudentDAOImpl implements StudentDAO {

  private EntityManager entityManager;

  @Autowired
  public StudentDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void save(Student theStudent) {
    entityManager.persist(theStudent);
  }

  @Override 
  public List<Student> findAll() {
    TypedQuery<Student> query = entityManager.createQuery("from Student order by id asc", Student.class);
    return query.getResultList();
    // // or we can do (if want to use native sql):
    // @SuppressWarnings("unchecked")
    // List<Student> arr = entityManager.createNativeQuery("select * from Student", Student.class).getResultList();
    // return arr;
  }
  @Override
  public List<Student> findByFirstName(String theFirstName) {
    String jqlString = "from Student where firstName = :param1";
    TypedQuery<Student> query = entityManager.createQuery(jqlString, Student.class);
    query.setParameter("param1", theFirstName);
    return query.getResultList();
  }
  @Override
  public Student findById(int id) {
    Student std = entityManager.find(Student.class, id);
    return std;
  }
  @Override
  @Transactional
  public Student findByIdAndUpdate(int id, String attribute, String updatedProperty) {
    Student std = entityManager.find(Student.class, id);
    if (std == null) {
      return null;
    }
    if ("id".equals(attribute)) {
      try {
        int passedId = Integer.parseInt(updatedProperty);
        std.setId(passedId);
      } catch (NumberFormatException e) {
        return null;
      }
    } else if ("firstName".equals(attribute)) {
      String regex = "^[A-Za-z]+$";
      if (Pattern.matches(regex, updatedProperty)) {
        std.setFirstName(updatedProperty);
      } else {
        return null;
      }
    } else if ("lastName".equals(attribute)) {
      String regex = "^[A-Za-z]+$";
      if (Pattern.matches(regex, updatedProperty)) {
        std.setLastName(updatedProperty);
      } else {
        return null;

      }
    } else if ("email".equals(attribute)) {
      String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
      if (Pattern.matches(emailRegex, updatedProperty)) {
        std.setEmail(updatedProperty);
      } else {
        return null;
      }
    } else {
      return null;
    }
    entityManager.merge(std);
    return std;
  }
  @Override
  @Transactional
  public int batchUpdateByCondition(String targetAttribute, String targetValue, String conditionAttribute, String conditionValue) {
    List<String> validAttributes = Arrays.asList("id", "firstName", "lastName", "email");
    if (!validAttributes.contains(targetAttribute) || !validAttributes.contains(conditionAttribute)) {
      return 404;
    }
    try {
      List<String> numericAttributes = Arrays.asList("id");
      String str = "update Student set " + targetAttribute + " = ";
      if (numericAttributes.contains(targetAttribute)) {
        str += targetValue;
      } else {
        str += "'" + targetValue +"'";
      }
      str += " where " + conditionAttribute + " = ";
      if (numericAttributes.contains(conditionAttribute)) {
        str += conditionValue;
      } else {
        str += "'" + conditionValue +"'";
      }
      int rowsUpdated = entityManager.createQuery(str).executeUpdate();
      return rowsUpdated;
    } catch (Exception e) {
      return 404;
    }
  }
  @Override
  @Transactional
  public Student deleteById(int id) {
    Student std = entityManager.find(Student.class, id);
    if (std == null) {
      return null;
    }
    entityManager.remove(std);
    return std;
  }
  @Override
  @Transactional
  public int batchDeleteByCondition(String attribute, String value) {
    List<String> validAttributes = Arrays.asList("id", "firstName", "lastName", "email");
    if (!validAttributes.contains(attribute)) {
      return 404;
    }

    try {
      List<String> numericAttributes = Arrays.asList("id");
      String queryString = "delete from Student where " + attribute + " = ";
      if (numericAttributes.contains(attribute)) {
        queryString += value;
      } else {
        queryString += "'" + value + "'";
      }

      int modifiedRows = entityManager.createQuery(queryString).executeUpdate();
      return modifiedRows;
    } catch (Exception e) {
      return 404;
    }
  }
}