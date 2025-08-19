package com.klef.jfsd.spd.tourisum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

//these dependencies are adding for changing object to String
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="user")
public class User implements java.io.Serializable {
	  private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // you can take this manually also through form
	 @Column(name="id")
	 @JsonProperty
	 private int id;
	 @Column(name="name",nullable=false,length = 50)
	 @JsonProperty
	 private String name;
	 @Column(name="email",nullable=false,unique = true,length = 50)
	 @JsonProperty
	 private String email;
	 @Column(name="password",nullable=false,length = 50)
	 @JsonProperty
	 private String password;
	 @Column(name="sex",nullable=false,length=50)
	 @JsonProperty
	 private String sex;
	 @Column(name="contact",nullable=false,length=50)
	 @JsonProperty
	 private long contact;
	 @Lob
	 @Column(name = "userimageinbytes", nullable = false,columnDefinition = "MEDIUMTEXT")
	 @JsonProperty
	private String userimageinbytes;
	 
	 // getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	
	public String getUserimageinbytes() {
		return userimageinbytes;
	}
	public void setUserimageinbytes(String userimageinbytes) {
		this.userimageinbytes = userimageinbytes;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", sex=" + sex
				+ ", contact=" + contact + ", userimageinbytes=" + userimageinbytes + "]";
	}
	
	 
	
}
