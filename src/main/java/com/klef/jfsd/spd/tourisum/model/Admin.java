package com.klef.jfsd.spd.tourisum.model;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// these dependencies are adding for changing object to String
import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
@Table(name="Admin")
public class Admin implements java.io.Serializable {
	  private static final long serialVersionUID = 1L;
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // you can take this manually also through form
	 @Column(name="id")
	 @JsonProperty
	 private int id;
	@Column(name="email",nullable=false,unique = true,length = 50)
	@JsonProperty
	 private String email;
	 @Column(name="password",nullable=false,length = 50)
	 @JsonProperty
	 private String password;
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Admin [email=" + email + ", password=" + password + "]";
	}
	
}
