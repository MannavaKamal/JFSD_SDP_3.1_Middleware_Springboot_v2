package com.klef.jfsd.spd.tourisum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="payment_table")
public class paymentdetails implements java.io.Serializable {
	 private static final long serialVersionUID = 1L;
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // you can take this manually also through form
	 @Column(name="sno")
	 private int sno;
	 @Column(name="userid")
	 private int userid;
	 @Column(name="orderid",nullable=false,unique = true,length = 50)
	 private String orderid;
	 @Column(name="Created_on",nullable=false,length = 50)
	 private String date;
	 
	 
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "paymentdetails [sno=" + sno + ", userid=" + userid + ", orderid=" + orderid + ", date=" + date + "]";
	}
	
	
	
	
	

}
