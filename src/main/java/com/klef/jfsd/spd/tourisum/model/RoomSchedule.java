package com.klef.jfsd.spd.tourisum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="room_bookings")
public class RoomSchedule implements java.io.Serializable{	
	 private static final long serialVersionUID = 1L;
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // you can take this manually also through form
	 @Column(name="order_id")
	 private int orderid;
	 @Column(name="hoteladmin_id")
	 private int hoteladminid;
	 @Column(name="room_id")
	 private int roomid;
	 @Column(name="user_id")
	 private int userid;
	 @Column(name="Booked_date",nullable=false,length = 50)
	 private String date;
	 @Column(name="check_in_time",nullable=false,length = 50)
	 private String checkintime;
	 @Column(name="Booked_out_time",nullable=false,length = 50)
	 private String checkouttime;	
	 
	 
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getHoteladminid() {
		return hoteladminid;
	}
	public void setHoteladminid(int hoteladminid) {
		this.hoteladminid = hoteladminid;
	}
	public int getRoomid() {
		return roomid;
	}
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCheckintime() {
		return checkintime;
	}
	public void setCheckintime(String checkintime) {
		this.checkintime = checkintime;
	}
	public String getCheckouttime() {
		return checkouttime;
	}
	public void setCheckouttime(String checkouttime) {
		this.checkouttime = checkouttime;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "RoomSchedule [orderid=" + orderid + ", hoteladminid=" + hoteladminid + ", roomid=" + roomid
				+ ", userid=" + userid + ", date=" + date + ", checkintime=" + checkintime + ", checkouttime="
				+ checkouttime + "]";
	}
	
}

