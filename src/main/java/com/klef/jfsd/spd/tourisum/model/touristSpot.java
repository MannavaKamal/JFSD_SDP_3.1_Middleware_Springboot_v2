package com.klef.jfsd.spd.tourisum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="tourist_spots")
public class touristSpot implements java.io.Serializable{
	 private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // you can take this manually also through form
	 @Column(name="Spot_id")
	 private int id;
	 @Column(name="country",nullable=false,length=50)
	 private String country;
	 @Column(name="state",nullable=false,length=50)
	 private String state;
	 @Column(name="city",nullable=false,length=50)
	 private String city;
	 @Column(name="spotname",nullable=false, length=50)
	 private String spotname;
	 @Lob
	 @Column(name = "spotimageinbytes", nullable = false,columnDefinition = "MEDIUMTEXT")
	private String spotimageinbytes;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSpotname() {
		return spotname;
	}
	public void setSpotname(String spotname) {
		this.spotname = spotname;
	}
	
	public String getSpotimageinbytes() {
		return spotimageinbytes;
	}
	public void setSpotimageinbytes(String spotimageinbytes) {
		this.spotimageinbytes = spotimageinbytes;
	}
	
	
}
