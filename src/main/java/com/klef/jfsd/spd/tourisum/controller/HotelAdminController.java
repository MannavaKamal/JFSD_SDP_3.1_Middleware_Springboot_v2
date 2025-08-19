package com.klef.jfsd.spd.tourisum.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klef.jfsd.spd.tourisum.model.Admin;
import com.klef.jfsd.spd.tourisum.model.HotelAdmin;
import com.klef.jfsd.spd.tourisum.model.RoomSchedule;
import com.klef.jfsd.spd.tourisum.model.Rooms;
import com.klef.jfsd.spd.tourisum.model.User;
import com.klef.jfsd.spd.tourisum.repository.AdminRepository;
import com.klef.jfsd.spd.tourisum.repository.HotelAdminRepository;
import com.klef.jfsd.spd.tourisum.repository.RoomScheduleRepository;
import com.klef.jfsd.spd.tourisum.repository.RoomsRepository;
import com.klef.jfsd.spd.tourisum.repository.UserRepository;
import com.klef.jfsd.spd.tourisum.service.HotelAdminService;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@RestController
public class HotelAdminController {
	@Autowired
	  private JavaMailSender mailSender;
	
	@Autowired
	private HotelAdminService hoteladminservice;
	@Autowired
	private AdminRepository adminrepo;
	@Autowired
	private HotelAdminRepository hoteladminrepo;
	@Autowired
	private UserRepository userrepo;
	@Autowired
	private RoomScheduleRepository roomschedulerepository;
	@Autowired
	private RoomsRepository roomsrepository;
	
	
	@GetMapping("/checkhoteladminsession")
	  public HotelAdmin checkhoteladminsession(HttpServletRequest request) {
		  HttpSession session = request.getSession();
	    	HotelAdmin ha = (HotelAdmin)session.getAttribute("hoteladmindetails");
	    	if(ha!=null) {
	    		session.setMaxInactiveInterval(3600);
	    		return ha;
	    	}else {
	    		return null;
	    	}
	    }
	@PostMapping("/checkhoteladminsession1")
	  public HotelAdmin checkhoteladminsession1(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException {
		Map<String, String> map = (Map<String, String>) obj; // jwtToken credeitails will get here
	   	 ObjectMapper mapper = new ObjectMapper();
	   	HotelAdmin a1 =  (HotelAdmin)mapper.readValue((String)JwtStorage.getObject(map), HotelAdmin.class);
	    	if(a1!=null) {
	    		return a1;
	    	}else {
	    		return null;
	    	}
	    }
	    @GetMapping("/checkhoteladminsignupsession")
	    public HotelAdmin checkhoteladminsignupsession(HttpServletRequest request) {
	    	HttpSession session = request.getSession();
	    	HotelAdmin ha = (HotelAdmin)session.getAttribute("hoteladminsignup");
	    	
	    	if(ha!=null) {
	    		return ha;
	    	}else {
	    		return null;
	    	}
	    }
	    
	    @GetMapping("/removehoteladminsignupsession")
	    public void removehoteladminsignupsession(HttpServletRequest request) {
	    	HttpSession session = request.getSession();    	
	    	session.removeAttribute("hoteladminsignup");
	    }
	
	@PostMapping("/insertHotelAdmin")
	public Map<String,String> insert(@RequestBody HotelAdmin h1) throws Exception  {	
		Map<String,String> map1 = new HashMap<>();
		if(userrepo.checkUserByEmail(h1.getEmail())!=null || hoteladminrepo.checkHotelAdminByEmail(h1.getEmail())!=null || adminrepo.checkAdminByEmail(h1.getEmail())!=null) {
   		 map1.put("status", "2");
			return map1;
		}	
    	Map<String,Object> map = new HashMap<>();
    	map.put("hoteladmin",h1);
    	MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);        
        int otp = (int)(Math.random() * 99999); // random number generation
        map.put("otp", otp);
        helper.setTo(h1.getEmail());
        helper.setSubject("OTP from Tourisum.jfsd.sdp.com");
        helper.setFrom("mannava.kamal@gmail.com");
        String htmlContent =         
        "<p><strong>OTP:</strong> " + otp + "</p>" +
        "<p><strong>Note:</strong>" + "this otp expires in 10 minutes"+ " </p>";
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);    	
        map1 = JwtStorage.storeObject(map);
        map1.put("status", "1");
 		return map1;
		
	}
	
	@PostMapping("/checkotp")
	public String checkotp(@RequestBody Object obj) throws Exception  {
		
		
		Map<String,Object> map = (Map<String,Object>)obj;
    	Object obj2 = JwtStorage.getObject((Map<String,String>)map.get("hoteladminsignup"));
    	 String var2 = (String)obj2;
    	 ObjectMapper mapper = new ObjectMapper();
    	 Map<String,Object> map1 = mapper.readValue(var2, Map.class);    	 
    
		HotelAdmin ha = mapper.convertValue(map1.get("hoteladmin"), HotelAdmin.class);
		if (ha ==null)
		{
			return null;
		}
		
		int otpfromjwt = (Integer)map1.get("otp");
    	int optfromfrontend = (Integer)map.get("id");
    	
    	if(optfromfrontend ==otpfromjwt) {
        hoteladminservice.inserthoteladmin(ha);    		
      return "1";
    	}
    	return "0";	
	}
	
	 @PostMapping("posthoteladminbyid")
		public HotelAdmin gethoteladminbyid(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException {
		// 
		 Map<String,Object> map = (Map<String,Object>)obj;
	    	
	    	 HotelAdmin ha1 = checkhoteladminsession1(map.get("customerdata"));
			if(ha1 == null ) {
				return null;
			}
			HotelAdmin ha = new HotelAdmin();
			ha.setId((Integer)map.get("id"));
			if(ha1.getId() != ha.getId()) {
				ha.setName("");
				return ha;
			}
			ha = hoteladminrepo.findById(ha.getId()).get();
			if(ha == null) {   // if user is in session and if account deleted 
				ha1.setName(" ");
				return ha1;
			}
		return  ha;
		}
	
	 @PostMapping("/hoteladminprofileupdate")
	    public Integer hoteladminprofileupdate(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException
	    {
		 Map<String,Object> map = (Map<String,Object>)obj;
	    	HotelAdmin ha = checkhoteladminsession1(map.get("customerdata"));
	    	if(ha==null) {
	    		return null;
	    	}
	    	ObjectMapper mapper = new ObjectMapper(); 	    
	    	HotelAdmin ha1 = mapper.convertValue(map.get("HotelAdmin"), HotelAdmin.class);	
	        HotelAdmin ha2 = hoteladminrepo.findById(ha1.getId()).get();
	        if(ha2 == null) {
	        	return 0;
	        }
	        ha2.setHotelname(ha1.getHotelname());
	        ha2.setName(ha1.getName());
	        ha2.setContact(ha1.getContact());
	        ha2.setSex(ha1.getSex());
	        ha2.setHotelimageinbytes(ha1.getHotelimageinbytes());
	        hoteladminrepo.save(ha2);
	    	return 1;
	    }
	
	@PostMapping("/HotelAdminAddRoom")
	public Integer AddRooms(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException 
	{
		Map<String,Object> map = (Map<String,Object>)obj;
		
	HotelAdmin	ha =checkhoteladminsession1(map.get("customerdata"));
	if(ha == null) 
	{	
		return null;
	}
	Rooms r1 = new Rooms();	
	r1.setRoomcost(Integer.parseInt((String)map.get("roomcost")));
	r1.setRoomavailable(true);
	r1.setRoomimageinbytes((String)map.get("roomimageinbytes"));
	r1.setRoomno(Integer.parseInt((String)map.get("roomno")));
	r1.setRoomtype((String)map.get("roomtype"));
	Rooms r2 = hoteladminservice.checkroom(ha.getId(), r1.getRoomno());
	if(r2 == null) {
	r1.setId(ha.getId());
	hoteladminservice.hoteladminaddrooms(r1);	
	return 1; //"Room Added SuccessFully"
	}
	else {
		return 0;  //"Room Already exists"
	}	
		
	}
	
	@PostMapping("/HotelAdminRooms")
	public List<Rooms> hoteladminrooms(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException
	{
		HotelAdmin	ha =checkhoteladminsession1(obj);
		if(ha == null) 
		{
		   return null;	
		}
			List<Rooms> lr = hoteladminservice.getAllRoomsById(ha.getId());
			return lr;
	}
	
	@PostMapping("/deleteroom")
	public Integer deleteroom(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException
	{
		Map<String,Object> map = (Map<String,Object>)obj;
		HotelAdmin ha = checkhoteladminsession1(map.get("customerdata"));
		if(ha == null) {
			return null;
		}
		Rooms r1 = new Rooms();
		r1.setSno((Integer)map.get("sno"));
		Rooms r2 =  hoteladminservice.getRoomBySno(r1.getSno());
		if(r2 == null) {
			return 0;
		}
		roomsrepository.deleteById(r1.getSno());
			return 1;
		
	}
	
	@PostMapping("/updateroom")
	public Integer updateroom(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException
	{
		Map<String,Object> map = (Map<String,Object>)obj;
		HotelAdmin ha = checkhoteladminsession1(map.get("customerdata"));
		if(ha == null) {
			return null;
		}
		Rooms r1 = new Rooms();
		r1.setRoomcost((Integer)map.get("roomcost"));
		r1.setRoomavailable(true);
		r1.setRoomimageinbytes((String)map.get("roomimageinbytes"));
		r1.setRoomno((Integer)map.get("roomno"));
		r1.setRoomtype((String)map.get("roomtype"));
		r1.setSno((Integer)map.get("sno"));
		Rooms r2 =  roomsrepository.findById(r1.getSno()).get();
		if(r2 == null) {
			return 0;
		}
		r2.setRoomtype(r1.getRoomtype());
		r2.setRoomcost(r1.getRoomcost());
		r2.setRoomimageinbytes(r1.getRoomimageinbytes());
		roomsrepository.save(r2);
		return 1;
	}
	 
	@PostMapping("/roombookingsbasedonhoteladminid")
	public List<RoomSchedule> roombookingsbasedonhoteladminid(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException{
		HotelAdmin ha = checkhoteladminsession1(obj);
		if(ha == null) {
			return null;
		}
		
		return roomschedulerepository.roomidbasedonhoteladminid(ha.getId()); // all booking rooms based on hoteladmin id
	}
	
	@PostMapping("senduserbyid")
	public User getuserbyid(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException {
		Map<String,Object> map = (Map<String,Object>)obj;
		
		if(checkhoteladminsession1(map.get("customerdata")) == null ) {
			return null;
		}
	return userrepo.getUserById((Integer)map.get("id"));	
	}
	
	@PostMapping("/sendroombyid")
	public Rooms getroombyid(@RequestBody Object obj) throws JsonMappingException, JsonProcessingException { 
		Map<String,Object> map = (Map<String,Object>)obj;
		HotelAdmin ha = checkhoteladminsession1(map.get("customerdata"));
		if(ha == null){
			return null;
		}	
	Optional<Rooms> r2 =	roomsrepository.findById((Integer)map.get("sno"));
		return r2.get();
	}
//	@GetMapping("/hoteladminlogout")// removing the session attribute
//	public void lhoteladminogout(HttpServletRequest request) 
//	{		
//		if (checkhoteladminsession1(request)!=null) {
//			HttpSession session = request.getSession();
//			session.removeAttribute("hoteladmindetails");
//		}	
//	}
	
	
}
