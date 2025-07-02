package com.learn.hibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "teacher")
public class Teacher {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "firstName")
  private String firstName;

  @Column(name = "lastName")
  private String lastName;

  @Column(name = "email")
  private String email;

  // constructors
  public Teacher() {}
  public Teacher(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  // getters - setters
  public int getId() {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getFirstName() {
    return this.firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName =  firstName;
  }
  public String getLastName() {
    return this.lastName;
  }
  public void setLastName(String lastName) {
    this.lastName =  lastName;
  }
  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email =  email;
  }
  // toString method
  @Override
  public String toString() {
    return "( Name: " + this.firstName + " " + this.lastName + ", Email: " + this.email + " )";
  }
}
