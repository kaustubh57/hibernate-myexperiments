package com.hibernate.myexperiments.models;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Slf4j
@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@SequenceGenerator(name = "employee_seq", sequenceName = "employee_id_seq", allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="age")
	private Integer age;
	
	public Employee() {
	}

	public Employee(Integer id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public Employee(String name, int age) {
		this.name = name;
		this.age = age;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Employee: " + this.id + ", " + this.name + ", " + this.age; 
	}
	
}

/**
 CREATE TABLE `company`.`employee` (
 id INT NOT NULL AUTO_INCREMENT,
 name VARCHAR(20) default NULL,
 age INT  default NULL,
 PRIMARY KEY (id)
 );
 */