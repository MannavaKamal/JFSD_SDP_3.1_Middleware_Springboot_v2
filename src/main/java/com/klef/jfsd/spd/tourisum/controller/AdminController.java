package com.klef.jfsd.spd.tourisum.controller;


import java.time.LocalDateTime;



import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.klef.jfsd.spd.tourisum.model.Admin;
import com.klef.jfsd.spd.tourisum.model.HotelAdmin;
import com.klef.jfsd.spd.tourisum.model.RoomSchedule;
import com.klef.jfsd.spd.tourisum.model.Rooms;
import com.klef.jfsd.spd.tourisum.model.TravelPlanRequest;
import com.klef.jfsd.spd.tourisum.model.User;
import com.klef.jfsd.spd.tourisum.model.paymentdetails;
import com.klef.jfsd.spd.tourisum.model.touristSpot;
import com.klef.jfsd.spd.tourisum.repository.HotelAdminRepository;
import com.klef.jfsd.spd.tourisum.repository.PaymentRepository;
import com.klef.jfsd.spd.tourisum.repository.RoomScheduleRepository;
import com.klef.jfsd.spd.tourisum.repository.RoomsRepository;
import com.klef.jfsd.spd.tourisum.repository.TravelPlanRequestRepository;
import com.klef.jfsd.spd.tourisum.repository.UserRepository;
import com.klef.jfsd.spd.tourisum.service.AdminService;
import com.klef.jfsd.spd.tourisum.service.HotelAdminService;
import com.klef.jfsd.spd.tourisum.service.UserService;
// for JWT
import com.klef.jfsd.spd.tourisum.controller.JwtStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.HashMap;


import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@RestController
public class AdminController {
	
   
	@Autowired
	private AdminService adminservice;
	@Autowired
	private TravelPlanRequestRepository travelplanrequestrepository;
	@Autowired
	private HotelAdminService hoteladminservice;
	 @Autowired
	 private UserService userservice;
	 @Autowired
    private UserRepository userrepository; 
	 @Autowired
		private RoomScheduleRepository roomschedulerepository;
	 @Autowired
		private HotelAdminRepository hoteladminrepository;
	 @Autowired
		private RoomsRepository roomsrepository;
	 @Autowired
		private PaymentRepository paymentrepository;
	 @Autowired
	 private JavaMailSender mailSender;
	 
		@PostMapping("/login")
		public Map<String,String> login(@RequestBody Admin a1,HttpServletRequest request ) throws JsonProcessingException {
			
			 HttpSession session = request.getSession();
			 User user1 = userservice.checkUserLogin(a1.getEmail(), a1.getPassword());
				if(user1 != null) {
					
					// if user login is success status is 11
					Map<String,String> map1 = JwtStorage.storeObject(user1);
					map1.put("status", "11");
					return map1;
		        	
		    		
		    	}
				 HotelAdmin ha =  hoteladminservice.checkHotelAdminLogin(a1.getEmail(), a1.getPassword());	
				 if(ha!=null) {
					 if(ha.getStatus() == 0) {
					 //return 21;   // waiting for admin aprovel   that ststus is 21
						
						 Map<String,String> map1 = new HashMap<String,String>();
						 map1.put("status","21");
						 return map1;
						
					 }
				 }
					if(ha != null){
						
					
						Map<String,String> map1 = JwtStorage.storeObject(ha);
					
						map1.put("status", "12");
						return map1;
					}
			 Admin admin = adminservice.checkAdminLogin(a1.getEmail(), a1.getPassword());
			 if(admin != null){
				 
					
					
					Map<String,String> map1 = JwtStorage.storeObject(admin);
					// if admin login is success status is 13
					map1.put("status", "13");
					return map1;
				
					
				}
				//return 3;  // if user not exists
			 Map<String,String> map1 = new HashMap<String,String>();
			// 3 for invalid credentials
			 map1.put("status","3");
			 return map1;
		}	 
	 
	@GetMapping("/checkadminsession")
    public Admin checkadminsession(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    
    	Admin a1 = (Admin)session.getAttribute("admindetails");
    	if(a1!=null) {
    		session.setMaxInactiveInterval(3600);
    		return a1;
    	}else {
    		return null;
    	}
    }
	@PostMapping("/checkadminsession1")
    public Admin checkadminsession1(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException {
		Map<String, String> map = (Map<String, String>) obj; // jwtToken credeitails will get here
   	 ObjectMapper mapper = new ObjectMapper();
      Admin a1 =  (Admin)mapper.readValue((String)JwtStorage.getObject(map), Admin.class);
    	if(a1!=null) {
    		return a1;
    	}else {
    		return null;
    	}
    }
	
	 
	@PostMapping("/insertLocation")
	public Integer insertLocation(@RequestBody Object obj ,HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		
		Map<String,Object> map = (Map<String,Object>)obj;
		
		if(checkadminsession1(map.get("customerdata")) == null ) {
			return null;
		}
		touristSpot tp = new touristSpot();
		tp.setCity((String)map.get("city"));
		tp.setCountry((String)map.get("country"));
		tp.setSpotimageinbytes((String)map.get("spotimageinbytes"));
		tp.setSpotname((String)map.get("spotname"));
		tp.setState((String)map.get("state"));
		adminservice.insertLocation(tp);		
		return 1;
	}
	
	@PostMapping("/getLocations")
	public List<touristSpot> getLocations(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException{
		if(checkadminsession1(obj) == null ) {
			return null;
		}
		return adminservice.getLocations();
	}
	
	@PostMapping("/deletespot")
	public Integer deletespot(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException {
		Map<String,Object> map = (Map<String,Object>)obj;
		if(checkadminsession1((Map<String,String>)map.get("customerdata")) == null ) {
			return null;
		}
		return (Integer)adminservice.deleteSpotById((Integer)map.get("id"));
		
	}
	
	@PostMapping("/adminrequests")
	public List<TravelPlanRequest> myrequests(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException { 
		
		if(checkadminsession1(obj) == null ) {
			return null;
		}
		return travelplanrequestrepository.findAll();
		//return null;
	}
	
	@PostMapping("/responsefromadmin")
	public String responcefromadmin(@RequestBody Object obj,  HttpServletRequest request) throws JsonMappingException, JsonProcessingException { 		
		Map<String,Object> map = (Map<String,Object>)obj;
		if(checkadminsession1(map.get("customerdata")) == null ) {
			return null;
		}
		TravelPlanRequest t1 = new TravelPlanRequest();		
		t1.setAdminstatus((Integer)map.get("adminstatus"));
		t1.setRequestid((Integer)map.get("requestid"));
		if(t1.getAdminstatus() == 1) {			
			t1.setAmount((Integer)map.get("amount"));
			t1.setTgname((String)map.get("tgname"));
			t1.setTgcontact((Long)map.get("tgcontact"));
		travelplanrequestrepository.requestStatusBasedOnReqid(t1.getAdminstatus(),t1.getAmount(),t1.getTgname(),t1.getTgcontact(),t1.getRequestid());
		}
		else {
			t1.setReasonforreject((String)map.get("reasonforreject"));
			travelplanrequestrepository.requestStatusBasedOnReqid1(t1.getAdminstatus(),t1.getReasonforreject(),t1.getRequestid());
		}
		return null;
	}
	
	@PostMapping("/getuserbyid")
	public User getuserbyid(@RequestBody Object obj,HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		Map<String,Object> map = (Map<String,Object>)obj;
		if(checkadminsession1(map.get("customerdata")) == null ) {
			return null;
		}
	return userrepository.getUserById((Integer)map.get("id"));	
	}
	
	@PostMapping("/hoteladminapprovel")
	public Integer hoteladminapprovel(@RequestBody Object obj) throws Exception{
		Map<String,Object> map = (Map<String,Object>)obj;
		if(checkadminsession1(map.get("customerdata")) == null ) {
			return null;
		}
		HotelAdmin ha = new HotelAdmin();
		ha.setEmail((String)map.get("email"));
		ha.setContact((Long)map.get("contact"));
		ha.setCity((String)map.get("city"));
		ha.setHotelimageinbytes((String)map.get("hotelimageinbytes"));
		ha.setName((String)map.get("name"));
		ha.setId((Integer)map.get("id"));
		ha.setPassword((String)map.get("password"));
		ha.setSex((String)map.get("sex"));
		ha.setState((String)map.get("state"));
		ha.setStatus(1);
		ha.setCountry((String)map.get("country"));
		ha.setHotelname((String)map.get("hotelname"));
		hoteladminrepository.save(ha);
		String subject = "Notification: Account Approved for Tourisum.jfsd.sdp.com";
		String htmlcontent ="<p><strong>Admin Approved your Account you can login now with your details</strong> </p>" +
				"<p><strong>Use below details  for further communication</strong> </p>" +
		        "<p><strong>Email:</strong>" + "2200032973@kluniversity.in"+ " </p>"+
		        "<p><strong>Telegram id:</strong>" + "@Mannava_Kamal"+ " </p>"+
		        "<p><strong>Thankyou</strong></p>";		   
		sendmail(ha.getEmail(), subject, htmlcontent);
		return 1;
	}
	
	@PostMapping("/getallusers")
	public List<User> getallusers(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException{
		if(checkadminsession1(obj) == null ) {
			return null;
		}
		return userrepository.findAll();
	}
	
	@PostMapping("/deleteuserbyid")
	public Integer deleteuserbyid(@RequestBody Object obj)throws Exception {
		Map<String,Object> map = (Map<String,Object>)obj;
		if(checkadminsession1(map.get("customerdata")) == null ) {
			return null;
		}
		
		User user = new User();
		user.setContact((Long)map.get("contact"));
		user.setEmail((String)map.get("email"));
		user.setId((Integer)map.get("id"));
		user.setPassword((String)map.get("password"));
		user.setSex((String)map.get("sex"));
		user.setName((String)map.get("name"));
		user.setUserimageinbytes((String)map.get("userimageinbytes"));
		User user2 = userrepository.findById(user.getId()).get();
		if(user2 == null) {
			return 0; // account not available 
		}
		List<RoomSchedule> lrs = roomschedulerepository.findAll();
		for(RoomSchedule rs : lrs) {
			if(rs.getUserid() == user.getId()) {
				roomschedulerepository.deleteById(rs.getOrderid());
			}
		}
		List<TravelPlanRequest> ltr = travelplanrequestrepository.findAll();
		for(TravelPlanRequest tr : ltr) {
			if(tr.getUserid() == user.getId()) {
				travelplanrequestrepository.deleteById(tr.getRequestid());
			}
		}
		List<paymentdetails> lpd = paymentrepository.findAll();
		for(paymentdetails pd : lpd) {
			if(pd.getUserid() == user.getId()) {
				paymentrepository.deleteById(pd.getSno());
			}
		}
		userrepository.deleteById(user.getId());
		String subject = "Notification: Account Deletion for Tourisum.jfsd.sdp.com";
		String htmlcontent ="<p><strong>Admin removed your Account</strong> </p>" +
				"<p><strong>Use below details  for further communication</strong> </p>" +
		        "<p><strong>Email:</strong>" + "2200032973@kluniversity.in"+ " </p>"+
		        "<p><strong>Telegram id:</strong>" + "@Mannava_Kamal"+ " </p>";		   
		sendmail(user.getEmail(), subject, htmlcontent);
		return 1;//successfully deleated
	}
	@PostMapping("/getallhoteladmins")
	public List<HotelAdmin> getallhoteladmins(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException{
		if(checkadminsession1(obj) == null ) {
			return null;
		}
		return hoteladminrepository.findAll();
	}
	@PostMapping("/deletehoteladminbyid")
	public Integer deletehoteladminbyid(@RequestBody Object obj) throws Exception{
		//{id=4, name=Mannava Kamal, email=2200032973@kluniversity.in, password=Kamal@812004, sex=Male, contact=9393801998, country=India, state=Andhrapradesh, city=Guntur, hotelname=Mannava Kamal Hotels, hotelimageinbytes=data:i
		Map<String,Object> map = (Map<String,Object>)obj;
		if(checkadminsession1(map.get("customerdata")) == null ) {
			return null;
		}
		HotelAdmin ha = new HotelAdmin();
		ha.setEmail((String)map.get("email"));
		ha.setContact((Long)map.get("contact"));
		ha.setCity((String)map.get("city"));
		ha.setHotelimageinbytes((String)map.get("hotelimageinbytes"));
		ha.setName((String)map.get("name"));
		ha.setId((Integer)map.get("id"));
		ha.setPassword((String)map.get("password"));
		ha.setSex((String)map.get("sex"));
		ha.setStatus((Integer)map.get("status"));
		ha.setState((String)map.get("state"));
		HotelAdmin ha1 = hoteladminrepository.findById(ha.getId()).get();
		if(ha1 == null) {
			return 0; // account not available 
		}if(ha.getStatus() == 0) {
			String subject = "Notification:  Rejection for Tourisum.jfsd.sdp.com";
			String htmlcontent ="<p><strong>Admin Rejected your Account creation request</strong> </p>" +
					"<p><strong>Use below details  for further communication</strong> </p>" +
			        "<p><strong>Email:</strong>" + "2200032973@kluniversity.in"+ " </p>"+
			        "<p><strong>Telegram id:</strong>" + "@Mannava_Kamal"+ " </p>";		   
			sendmail(ha.getEmail(), subject, htmlcontent);
		}
		else {
			List<RoomSchedule> lrs = roomschedulerepository.findAll();
			for(RoomSchedule rs : lrs) {
				if(rs.getHoteladminid() == ha.getId()) {
					roomschedulerepository.deleteById(rs.getOrderid());
				}
			}
			List<Rooms> lr = roomsrepository.findAll();
			for(Rooms r : lr) {
				if(r.getId() == ha.getId()) {
					roomsrepository.deleteById(r.getSno());
				}
			}
			String subject = "Notification:  Account removal for Tourisum.jfsd.sdp.com";
			String htmlcontent ="<p><strong>Admin removed your Account </strong> </p>" +
					"<p><strong>Use below details  for further communication</strong> </p>" +
			        "<p><strong>Email:</strong>" + "2200032973@kluniversity.in"+ " </p>"+
			        "<p><strong>Telegram id:</strong>" + "@Mannava_Kamal"+ " </p>";		   
			sendmail(ha.getEmail(), subject, htmlcontent);
		}
		hoteladminrepository.deleteById(ha.getId());
		return 1;//successfully deleated
	}

	
//	@GetMapping("/adminlogout")
//	public void adminlogout(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		session.removeAttribute("admindetails");
//	}
//	
//	@Scheduled(fixedDelay = 600000)
//	public void deleteroom_shedule() {
//		LocalDateTime localDateTime = LocalDateTime.now();
//	      ZonedDateTime istZonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
//                  .withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//        String formattedDateTime = istZonedDateTime.format(formatter);
//        LocalDateTime dateTime1 = LocalDateTime.parse(formattedDateTime, formatter);// current IST time
//		List<RoomSchedule> l1 =  roomschedulerepository.findAll();
//		for(RoomSchedule rs : l1) {
//			LocalDateTime dateTime2 = LocalDateTime.parse(rs.getCheckouttime(), formatter); // checkouttime in database
//			if (dateTime2.isBefore(dateTime1)) {
//	          roomschedulerepository.deleteById(rs.getOrderid());
//	        }
//		}
//	}
	public void sendmail(String destinationemail,String subject,String htmlContent)throws Exception {
		try {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);        
        helper.setTo(destinationemail);
        helper.setSubject(subject);
        helper.setFrom("mannava.kamal@gmail.com");       
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage); 
		}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
	}
}


