/*
 * Created on May 22, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fruits.nestle.cache.test;

import java.io.*;

public class Person implements Serializable {
	private String id;
	private String name;
	private String sex;
	private int age;
	private String address;

	public Person(String id, String name, String sex, int age, String address) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
