package com.klef.jfsd.spd.tourisum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="hotel_admin_Rooms")
public class Rooms implements java.io.Serializable{
	 private static final long serialVersionUID = 1L;
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // you can take this manually also through form
	 @Column(name="s_no")
	 private int sno;
	 @Column(name="hoteladmin_id")
	 private int id;
	 @Column(name="roomno")
	 private int roomno;
	 @Column(name="roomtype",nullable=false,length = 50)
	 private String roomtype;
	 @Column(name="roomcost",nullable=false)
	 private int roomcost;
	 @Column(name="roomavailable")
	 private boolean roomavailable;
	 @Lob
	 @Column(name = "roomimageinbytes", nullable = false,columnDefinition = "MEDIUMTEXT")
	 private String roomimageinbytes;
	 
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoomno() {
		return roomno;
	}
	public void setRoomno(int roomno) {
		this.roomno = roomno;
	}
	public String getRoomtype() {
		return roomtype;
	}
	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}
	public int getRoomcost() {
		return roomcost;
	}
	public void setRoomcost(int roomcost) {
		this.roomcost = roomcost;
	}
	public boolean isRoomavailable() {
		return roomavailable;
	}
	public void setRoomavailable(boolean roomavailable) {
		this.roomavailable = roomavailable;
	}
	public String getRoomimageinbytes() {
		return roomimageinbytes;
	}
	public void setRoomimageinbytes(String roomimageinbytes) {
		this.roomimageinbytes = roomimageinbytes;
	}
	@Override
	public String toString() {
		return "Rooms [sno=" + sno + ", id=" + id + ", roomno=" + roomno + ", roomtype=" + roomtype + ", roomcost="
				+ roomcost + ", roomavailable=" + roomavailable + ", roomimageinbytes=" + roomimageinbytes + "]";
	}	 
}
