package com.klef.jfsd.spd.tourisum.model;

@jakarta.persistence.Entity
@jakarta.persistence.Table(name="TravelPlanRequest_Table")
public class TravelPlanRequest implements java.io.Serializable {
	 private static final long serialVersionUID = 1L;
	@jakarta.persistence.Id
	@jakarta.persistence.GeneratedValue(strategy=jakarta.persistence.GenerationType.IDENTITY)
	@jakarta.persistence.Column(name="req_id")
	private int requestid;

	// Field descriptor #17 Ljava/lang/String;
	@jakarta.persistence.Column(name="list_of_locations",
	 nullable=false,
	 length=(int) 50)
	private java.lang.String listoflocations;

	// Field descriptor #17 Ljava/lang/String;
	@jakarta.persistence.Column(name="start_date",
	 nullable=false,
	 length=(int) 50)
	private java.lang.String startdate;

	// Field descriptor #26 J
	@jakarta.persistence.Column(name="no_of_persons",
	 nullable=false)
	private long noofpersons;

	// Field descriptor #6 I
	@jakarta.persistence.Column(name="admin_status")
	private int adminstatus;

	// Field descriptor #17 Ljava/lang/String;
	@jakarta.persistence.Column(name="reason_for_rejection",
	 length=(int) 50)
	private java.lang.String reasonforreject;

	// Field descriptor #26 J
	@jakarta.persistence.Column(name="travel_amount")
	private long amount;

	// Field descriptor #6 I
	@jakarta.persistence.Column(name="rezorpaymentstatus")
	private int rezorpaymentstatus;

	// Field descriptor #6 I
	@jakarta.persistence.Column(name="paymentstatus")
	private int paymentstatus;

	// Field descriptor #17 Ljava/lang/String;
	@jakarta.persistence.Column(name="tourist_guide_name",
	 length=(int) 50)
	private java.lang.String tgname;

	// Field descriptor #26 J
	@jakarta.persistence.Column(name="tourist_guide_contact")
	private long tgcontact;

	// Field descriptor #6 I
	@jakarta.persistence.Column(name="user_id",
	 nullable=false)
	private int userid;

	public int getRequestid() {
		return requestid;
	}

	public void setRequestid(int requestid) {
		this.requestid = requestid;
	}

	public java.lang.String getListoflocations() {
		return listoflocations;
	}

	public void setListoflocations(java.lang.String listoflocations) {
		this.listoflocations = listoflocations;
	}

	public java.lang.String getStartdate() {
		return startdate;
	}

	public void setStartdate(java.lang.String startdate) {
		this.startdate = startdate;
	}

	public long getNoofpersons() {
		return noofpersons;
	}

	public void setNoofpersons(long noofpersons) {
		this.noofpersons = noofpersons;
	}

	public int getAdminstatus() {
		return adminstatus;
	}

	public void setAdminstatus(int adminstatus) {
		this.adminstatus = adminstatus;
	}

	public java.lang.String getReasonforreject() {
		return reasonforreject;
	}

	public void setReasonforreject(java.lang.String reasonforreject) {
		this.reasonforreject = reasonforreject;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getRezorpaymentstatus() {
		return rezorpaymentstatus;
	}

	public void setRezorpaymentstatus(int rezorpaymentstatus) {
		this.rezorpaymentstatus = rezorpaymentstatus;
	}

	public int getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(int paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public java.lang.String getTgname() {
		return tgname;
	}

	public void setTgname(java.lang.String tgname) {
		this.tgname = tgname;
	}

	public long getTgcontact() {
		return tgcontact;
	}

	public void setTgcontact(long tgcontact) {
		this.tgcontact = tgcontact;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "TravelPlanRequest [requestid=" + requestid + ", listoflocations=" + listoflocations + ", startdate="
				+ startdate + ", noofpersons=" + noofpersons + ", adminstatus=" + adminstatus + ", reasonforreject="
				+ reasonforreject + ", amount=" + amount + ", rezorpaymentstatus=" + rezorpaymentstatus
				+ ", paymentstatus=" + paymentstatus + ", tgname=" + tgname + ", tgcontact=" + tgcontact + ", userid="
				+ userid + "]";
	}
	
	
	
	
}
