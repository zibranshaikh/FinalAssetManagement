package com.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import beans.AdminLogin;
import beans.AllotedAsset;
import beans.Asset;
import beans.Employee;
import beans.Request;
import beans.UserAssetRequest;
import dao.EmpDao;
import dao.LoginDao;
import dao.RequestDao;

@Controller
@SessionAttributes({"user","mid"})
public class AssetController
{ 
	//  ---------------------------  Workdone by Zibran Shaikh-------------------------------------------- //

	public org.hibernate.Session session()
	{
	SessionFactory sf=new AnnotationConfiguration().configure().buildSessionFactory();//Class.forName
	org.hibernate.Session ss=sf.openSession(); //Connection
	
	return ss;
	}

//	@Override
//	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
	@RequestMapping("/")
  	public ModelAndView home()//Index Page
  {
          ModelAndView mv=new ModelAndView("Home");//view name
          mv.addObject("msg","Welcome");
          
  		return mv;
  	}
	@RequestMapping("/example")
  	public ModelAndView example()
  {
          ModelAndView mv=new ModelAndView("example");//view name
          
  		return mv;
  	}
	
	
	@RequestMapping("/adminlogin")//Admin Login
  	public ModelAndView Adminlogin()
  {
		
		ModelAndView mv=new ModelAndView("Hello");//view name
          mv.addObject("msg","Admin");
          
  		return mv;
  	}
	   @ModelAttribute
	   public void addCommonObject(Model m)
	   {
		   m.addAttribute("head","Asset Management System");
	   }
	   
		@RequestMapping("/check")//Check Admin Login
		public ModelAndView welcome(@RequestParam String uid,@RequestParam String pwd)
	{      ModelAndView mv=null;
	      
	int y=0;
	        LoginDao ld=new LoginDao();
	         y=ld.check(uid,pwd);
	        if(y!=0)
	        { 
	          String user=uid;
	         if(user!=null)
	         {
	        mv=new ModelAndView("Admin");//view name
	        mv.addObject("user",uid);
	         }
	         }
	        else
	        {
	        mv=new ModelAndView("Hello");	
	        mv.addObject("msg","Login failed, please try again");
	        }
	        return mv;
		}

		@RequestMapping("/changeAdpwd")
		public ModelAndView  changeAdpwd()
		{  ModelAndView mv=null; 
		   mv=new ModelAndView("changeAdminPwd");//view name
	 	     return mv;

		}
			
		@RequestMapping("/AdpwdChange")//Change Password For Admin
		public ModelAndView  AdpwdChange(@RequestParam String user,@RequestParam String npass2,@RequestParam String cpass)
		{  
			ModelAndView mv=null; 
		 
		   LoginDao l=new LoginDao();
	       int y=l.changeAdPwd(npass2,cpass,user);
	       if(y!=0)
	       {
	    	   mv=new ModelAndView("Admin");//view name
	 	       mv.addObject("msg","Password Succesfully Changed");
	       }
	       else
	       {
	    	   mv=new ModelAndView("Admin");//view name
	 	       mv.addObject("msg","Password Not Changed because current password not matched");
	 	         
	 	            
	       }
	       
	     return mv;

		}
		

	@RequestMapping("/Emplogin")
  	public ModelAndView Emplogin()
  {
          ModelAndView mv=new ModelAndView("EmpLogin");//view name
          
  		return mv;
  	}
   
	@RequestMapping("/empcheck")//Check Employee Login
	public ModelAndView checkemp(@RequestParam String eid,@RequestParam String pwd)
{      ModelAndView mv=null;
      
      System.out.println(eid+" "+pwd);
      int y=0;
        LoginDao ld=new LoginDao();
         y=ld.checkEmp(eid,pwd);//Method in logindao to check employee login
         
         System.out.println("y in empcheckdao"+y);
        if(y!=0)
        {
        if(eid.contains("S"))//If employee id contains 'S' redirect it to Support Home 
         {
        mv=new ModelAndView("Support");//view name 
        mv.addObject("user",eid);
        }
         if(eid.contains("E"))//If employee id contains 'E' redirect it to Employee Home
         {
        	
        mv=new ModelAndView("EmployeeHome");//view name
        //String z=ld.checkManager(eid);
	   // mv.addObject("mid",z);
        mv.addObject("user",eid);
        }
         if(eid.contains("M"))//If employee id contains 'M' redirect it to Manager Home
         {
        mv=new ModelAndView("managerhome");//view name
        mv.addObject("user",eid);
        }
         }
        else
        {
        mv=new ModelAndView("EmpLogin");
        mv.addObject("msg","Login failed, please try again");
        }
        return mv;
	}
	@RequestMapping("/changeSPwdjs")
	public ModelAndView  changepwdjs()
	{  ModelAndView mv=null; 
	 
	       mv=new ModelAndView("changeSPwd");//view name
 	     return mv;

	}
		
	@RequestMapping("/spwdChange")//Password Change for Support 
	public ModelAndView  pwdChange(@RequestParam String eid,@RequestParam String npass2,@RequestParam String cpass)
	{  ModelAndView mv=null; 
	 
	   LoginDao l=new LoginDao();
       int y=l.changeSPwd(eid,npass2,cpass);//method in logindao to change password for support
       if(y!=0)
       {
    	   mv=new ModelAndView("changeSPwd");//view name//redirect it to jsp
 	       mv.addObject("msg","Password Succesfully Changed");
       }
       else
       {
    	   mv=new ModelAndView("changeSPwd");//view name
 	       mv.addObject("msg","Password Not Changed because current password not matched");
         
       }
       
     return mv;

	}
	
	@RequestMapping("/createEmp")
	public ModelAndView  createEmp()
	{  ModelAndView mv=null; 
	 
	       mv=new ModelAndView("createEmp");//view name
 	     return mv;

	}

   @RequestMapping("/insertEmp")//Insert a new Employee
    //public(@RequestParam int id,@RequestParam String name,@RequestParam String address,@RequestParam String mobile)
  	public ModelAndView insertEmp(@ModelAttribute("Employee") Employee c)
  	{
        ModelAndView mv=null;
        //Customer c=new Customer();
        //c.setEid(id);
        //c.setName(name);
        //c.setAddress(salary);
        //c.setMobile(mobile);
        //c.setEmail(email);
        
        EmpDao l=new EmpDao();
        
        if(c.getDesignation().equalsIgnoreCase("Manager"))
        {
        c.setMid1("0");
        c.setEid1("0");
        c.setEid2("0");
        }
        if(c.getDesignation().equalsIgnoreCase("Support Team"))
        {
        c.setEid1("0");	
        c.setMid1("0");
        c.setSid("0");
        c.setMid2("0");
        }
        if(c.getDesignation().equalsIgnoreCase("Employee"))
        {
        c.setMid2("0");
        c.setEid2("0");
        }
   
        int y=l.insertEmp(c);//By calling this function employee has been inserted 
        if(y==1)
        { 
         if(c.getDesignation().equalsIgnoreCase("Employee"))
         {
        	 String url="http://localhost:8080/Asset_Management/Emplogin";
        	 String email=c.getEmail();
        	 String subject="Asset Management Employee Account Creation";
        	 String message="Welcome to Asset Management Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
         sendMail(email,subject,message);
         }
         else if(c.getDesignation().equalsIgnoreCase("Manager"))
         {
        	 String url="http://localhost:8080/Asset_Management/Emplogin";
        	 String email=c.getEmail();
        	 String subject="Asset Management Employee Account Creation";
        	 String message="Welcome to Asset Management Your Employee Id is : "+c.getMid2()+" Your password is :  "+c.getPassword()+" Your Support id is : "+c.getSid()+"   Url For login : "+url;       	 
         sendMail(email,subject,message);
         }
         else if(c.getDesignation().equalsIgnoreCase("Support Team"))
         {
        	 String url="http://localhost:8080/Asset_Management/Emplogin";
             String email=c.getEmail();
        	 String subject="Asset Management Employee Account Creation";
        	 String message="Welcome to Asset Management Your Employee Id is : "+c.getEid2()+" Your password is :  "+c.getPassword()+"   Url For login : "+url; 	 
         sendMail(email,subject,message);
         }
         mv=new ModelAndView("createEmp");//view name
       mv.addObject("msg","Employee Succesfully created");
        }
		return mv;

  	}
   
   public void sendMail(String email,String subject,String message1)//Sending Mail to employee on account creation 
   {
		  // Recipient's email ID needs to be mentioned.
	      String to =email;//request.getParameter("id");//change accordingly
	      String sub=subject;
//	      long p=System.currentTimeMillis();//439807598430759083
//	      String pwd=(p+"").substring(7);
	   //   String pwd=pwd1;
	      String msg=message1;
	      // Sender's email ID needs to be mentioned
	      String from = "zshaikh1990s@gmail.com";
	      final String username = "zshaikh1990s@gmail.com";//change accordingly
	      final String password = "93009300";//change accordingly

	      // Assuming you are sending email through relay.jangosmtp.net
	      String host = "smtp.gmail.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	         }
	      });

	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	         InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject(sub);

	         // Now set the actual message
	         message.setText(msg);

	         // Send message
	         
	         Transport.send(message);
//	         PrintWriter out=response.getWriter();
//	        out.println("Sent message successfully....");
	         } catch (MessagingException e) {
	    	  e.printStackTrace();
	    	     }	
	      }
   
      
    @RequestMapping("/UpdateAEmp")//Admin can update Employee details
    //public(@RequestParam int id,@RequestParam String name,@RequestParam String address,@RequestParam String mobile)
 	public ModelAndView updateEmp(@ModelAttribute("Employee") Employee c)
 	{
       ModelAndView mv=null;
       //Customer c=new Customer();
       //c.setEid(id);
       //c.setName(name);
       //c.setAddress(salary);
       //c.setMobile(mobile);
       //c.setEmail(email);
       
       System.out.println("sid in updateemp "+c.getEid2());
       EmpDao l=new EmpDao();
       int y=l.updateEmp(c);//Calling function to update employee by admin
       if(y==1)
       {
    	  EmpDao ed=new EmpDao();
 	      ArrayList<Employee>list=ed.viewEmp();
 	      mv=new ModelAndView("viewEmp");//view name
 	      mv.addObject("LIST",list);
 	      mv.addObject("msg","Employee Designaton Successfully Updated");
 	      }
		return mv;

 	}

    @RequestMapping("/EUpdateEmp")//Employee can update his own profile 
    public ModelAndView eupdateEmp(@ModelAttribute("Employee")Employee c,HttpServletRequest request)
 	{
       ModelAndView mv=null;
       EmpDao l=new EmpDao();
       int z=l.eupdateEmp(c);//calling function to update Employee's own profile
       if(z==1)
       {
    	   
    	   HttpSession ss=request.getSession();
     	  String y=(String)ss.getAttribute("user");
     	  //System.out.println(y);
   	EmpDao ed=new EmpDao();
   	ArrayList<Employee>list=ed.viewProfile(y);
   	mv=new ModelAndView("ViewProfile");//redirecting to view profile
   	mv.addObject("LIST", list);
   	mv.addObject("msg","Profile Successsfully Updated");
   	
   	   }
		return mv;

 	}

   @RequestMapping("/viewEmp")//Admin can view his all employee
 	public ModelAndView  viewEmp()
 	{
	      EmpDao ed=new EmpDao();
	      ArrayList<Employee>list=ed.viewEmp();
	      ModelAndView mv=new ModelAndView("viewEmp");//view name
	      mv.addObject("LIST",list);
	       
      return mv;

 	}

   @RequestMapping(value="/empAD",method = RequestMethod.POST)//Admin Can activate and deactivate employee 
	public ModelAndView  empAD(@RequestParam String eid,@RequestParam String op)
	{  
	   ModelAndView mv=null; 
	   if(op.equals("Activate"))
    {
	   
	   EmpDao l=new EmpDao();
       int y=l.activateEmp(eid);//calling this function activate employee
       if(y!=0)
       {
//      	 String url="http://localhost:8080/Asset_Management/Emplogin";
//         String email=c.getEmail();
//      	 String subject="Asset Management Employee Account Creation";
//      	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//         sendMail(email,subject,message);
//      
    	   EmpDao ed=new EmpDao();
 	      List<Employee>list=ed.viewEmp();
 	       mv=new ModelAndView("viewEmp");//view name
 	       mv.addObject("LIST",list);
 	       mv.addObject("msg","Employee Succesfully Activated");
       }
    }  else if(op.equals("Deactivate"))
          {
	   
	   EmpDao l=new EmpDao();
       int y=l.deactivateEmp(eid);//Calling this function admin can deactivate employee
       if(y!=0)
       {
    	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//         String email=c.getEmail();
//      	 String subject="Asset Management Employee Account Creation";
//      	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//         sendMail(email,subject,message);
 
    	   EmpDao ed=new EmpDao();
 	      List<Employee>list=ed.viewEmp();
 	       mv=new ModelAndView("viewEmp");//view name
 	       mv.addObject("LIST",list);
 	       mv.addObject("msg1","Employee Succesfully Deactivated");
       
       }
       }
	   if(op.equalsIgnoreCase("Promote&Demote"))
	   {     
		   System.out.println("eid in update "+eid);
		   EmpDao ed=new EmpDao();
		      Employee e=ed.viewEmpUpdate(eid);//Calling this admin can update employee
		      mv=new ModelAndView("updateEmp");//view name
		       mv.addObject("E",e);
	   }
     return mv;

	}
   @RequestMapping("/viewRequest")//Support team can view all asset request
 	public ModelAndView  viewRequest()
 	{
	   EmpDao rd=new EmpDao();
	      ArrayList<Request>list=rd.viewRequest();
	      ModelAndView mv=new ModelAndView("viewReq");//view name
	       mv.addObject("LIST",list);
      
      return mv;

 	}
	
   @RequestMapping("/viewSProfile")//Support can view his profile
   protected ModelAndView viewSProfile(HttpServletRequest request)
   {
   	ModelAndView mv=null;
   	 HttpSession ss=request.getSession();
     	  String y=(String)ss.getAttribute("user");
     	  //System.out.println(y);
   	EmpDao ed=new EmpDao();
   	ArrayList<Employee>list=ed.viewSProfile(y);//calling function to view support profile 
   	mv=new ModelAndView("viewSupProfile");
   	mv.addObject("LIST", list);
   	
   	
   	return mv;
   }
    
    @RequestMapping("/approveReq")//Support can approve or reject the request coming from manager
	public ModelAndView  approveReq(@ModelAttribute("Request")Request r,@RequestParam String op)
	{
	   ModelAndView mv=null; 
	   if(op.equalsIgnoreCase("Approve"))
   {
	   
	  EmpDao l=new EmpDao();
	  
      int y=l.approveReq(r);//calling function to approve request
      if(y==1)
      {
    	   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//        String email=c.getEmail();
//     	 String subject="Asset Management Employee Account Creation";
//     	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//        sendMail(email,subject,message);

    	  EmpDao rd=new EmpDao();
	       ArrayList<Request>list=rd.viewRequest();
	       mv=new ModelAndView("viewReq");//view name
	       mv.addObject("LIST",list);
	       mv.addObject("msg2","Request Approved ");
      }
   }  else if(op.equalsIgnoreCase("Reject"))//Rejecting asset request by support
         {
	   
	   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//     String email=c.getEmail();
//  	 String subject="Asset Management Employee Account Creation";
//  	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//     sendMail(email,subject,message);

	   EmpDao l=new EmpDao();
      int y=l.rejectReq(r);//calling function to reject request by  support
      if(y==1)
      {
    	  EmpDao rd=new EmpDao();
	      ArrayList<Request>list=rd.viewRequest();
	       mv=new ModelAndView("viewReq");//view name
	       mv.addObject("LIST",list);
	       mv.addObject("msg1","Request Reject for Employee");
      
      }
      }
	   
    return mv;

	}

   @RequestMapping("/reports")//Suppport can view all the reports i.e track records of all asset
	public ModelAndView  viewReports()
	{
	   EmpDao rd=new EmpDao();
	      ArrayList<Request>list=rd.viewReports();//calling function to view reports
	      ModelAndView mv=new ModelAndView("viewReport");//view name
	       mv.addObject("LIST",list);
     
     return mv;

	}

   @RequestMapping("/supop")//support operation 
	public ModelAndView  supop(@RequestParam String eid,@RequestParam String op)
	{  ModelAndView mv=null; 
	   if(op.equalsIgnoreCase("Home"))//if operation equals to Home redirect it to support home
   {
	        mv=new ModelAndView("Support");//view name
	        mv.addObject("user",eid);
	            
	  }
    else if(op.equalsIgnoreCase("Logout"))//if operation equals to Logout redirect it to logout
         {
        mv=new ModelAndView("logout");//view name
      }
    return mv;

	}
   @RequestMapping("/adminop")//admin operation
	public ModelAndView  adop(@RequestParam String userid,@RequestParam String op)
	{  ModelAndView mv=null; 
	   if(op.equalsIgnoreCase("Home"))//if operation equals to Home redirect it to admin home
   {
	        mv=new ModelAndView("Admin");//view name
	        mv.addObject("user",userid);
	            
	  }
    else if(op.equalsIgnoreCase("Logout"))//if operation equals to Home redirect it to logout
        {
        mv=new ModelAndView("Adlogout");//view name
      }
    return mv;

	}

   @RequestMapping("/managerop")//manager operation
  	public ModelAndView  managerop(@RequestParam String op)
  	{  ModelAndView mv=null; 
  	   if(op.equalsIgnoreCase("Home"))//if operation equals to Home redirect it to manager home
     {
  	        mv=new ModelAndView("managerhome");//view name
  	       
  	  }
      else if(op.equalsIgnoreCase("Logout"))//if operation equals to Home redirect it to logout
           {
          mv=new ModelAndView("Mlogout");//view name
        }
      return mv;

  	}
   @RequestMapping("/empop")//employee operation
  	public ModelAndView  empop(@RequestParam String op)
  	{  ModelAndView mv=null; 
  	   if(op.equalsIgnoreCase("Home"))//if operation equals to Home redirect it to employee home
     {
  	        mv=new ModelAndView("EmployeeHome");//view name
  	           
  	  }
      else if(op.equalsIgnoreCase("Logout"))//if operation equals to Home redirect it to logout
           {
          mv=new ModelAndView("logout");//view name
        }
      return mv;

  	}

  @RequestMapping("/fetchMid")//fetching Managerid
  public ModelAndView  fetchMid()
     { 
	  ModelAndView mv=null; 
      mv=new ModelAndView("/FetchMid");//view name
	     return mv;
    }
  @RequestMapping("/fetchSid")//fetching support id
  public ModelAndView  fetchSid()
     { 
	  ModelAndView mv=null; 
      mv=new ModelAndView("/FetchSid");//view name
	     return mv;
    }
  @RequestMapping("/AssetRequest")//Employee creating asset request to manager
	protected ModelAndView assetReq(HttpServletRequest request) {
     
	  HttpSession ss=request.getSession();
	  String eid=(String) ss.getAttribute("user");
	   EmpDao ed=new EmpDao();
	   Employee e=ed.viewEmpUpdate(eid);//calling function to create request
	  ModelAndView mv=new ModelAndView("createRequest");
	  mv.addObject("E", e);
	  return mv;
}
  @RequestMapping("/insertRequest")//inserting request 
protected ModelAndView insertReq(@ModelAttribute("Request") Request r,HttpServletRequest request)
{

	ModelAndView mv=null;
  	RequestDao rd=new RequestDao();

  	int y=rd.insertReq(r);
  	 
  	HttpSession ss=request.getSession();
	  String eid=(String) ss.getAttribute("user");
	   EmpDao ed=new EmpDao();
	 
  	if(y==1)
  	{
  	   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//      String email=c.getEmail();
//   	 String subject="Asset Management Employee Account Creation";
//   	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//      sendMail(email,subject,message);

  		Employee e=ed.viewEmpUpdate(eid);
		mv=new ModelAndView("createRequest");
		  mv.addObject("msg", "Request Sent To Manager");
		  mv.addObject("E", e);
				 
		  
  	}
  	else {
  	   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//      String email=c.getEmail();
//   	 String subject="Asset Management Employee Account Creation";
//   	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//      sendMail(email,subject,message);

  	   Employee e=ed.viewEmpUpdate(eid);
		mv=new ModelAndView("createRequest");
  		mv.addObject("msg", "Request not sent ");
  	  mv.addObject("E", e);
  	
  	}
  	return mv;
}
  @RequestMapping("/ViewRequest")//employee can view his asset request
 	protected ModelAndView viewAsset(HttpServletRequest request) {
 	  ModelAndView mv=null;
 	
 	  HttpSession ss=request.getSession();
 	  String y=(String)ss.getAttribute("user");
      RequestDao ld=new RequestDao();
	  ArrayList<Request> list=ld.viewReq(y);//calling function  to view request
 	    mv=new ModelAndView("ViewRequest");
 		 mv.addObject("LIST", list);
 	  
 	  return mv;
   }

  @RequestMapping("/ViewAsset")//employee can view his asset that his allocated 
 	protected ModelAndView viewMyAsset(HttpServletRequest request) {
 	  ModelAndView mv=null;
 	
 	  HttpSession ss=request.getSession();
 	  String y=(String)ss.getAttribute("user");
      RequestDao ld=new RequestDao();
	  ArrayList<AllotedAsset> list=ld.viewMyAsset(y);
 	    mv=new ModelAndView("ViewMyAsset");
 		 mv.addObject("LIST", list);
 	  
 	  return mv;
   }

  @RequestMapping("/AssetETransfer")//Employee can transfer his asset to other employee
	protected ModelAndView AssetETransfer(HttpServletRequest request) {
	  ModelAndView mv=null;
		
 	  HttpSession ss=request.getSession();
 	  String y=(String)ss.getAttribute("user");
      RequestDao ld=new RequestDao();
	  ArrayList<AllotedAsset> list=ld.viewMyAsset(y);
	  mv=new ModelAndView("AssetETransfer");
 		 mv.addObject("LIST", list);
	    return mv;
 }
  
  @RequestMapping("/TransferEasset")
	protected ModelAndView TransferEasset(HttpServletRequest request,@RequestParam ("eid1")String eid1,@RequestParam ("eid")String eid,@RequestParam ("assetid")String assetid) {
	  ModelAndView mv=null;
		
		EmpDao l=new EmpDao();
		  
	    int x=l.TransferEasset(eid1,eid,assetid);
	    if(x==1)
	    {
	       	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//	         String email=c.getEmail();
//	      	 String subject="Asset Management Employee Account Creation";
//	      	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//	         sendMail(email,subject,message);
	 
	  mv=new ModelAndView("EmployeeHome");
      mv.addObject("msg","Asset Transfered");
	    }
 		 return mv;
 }
    
@RequestMapping("/CancelRequest")
protected ModelAndView cancelRequest(@ModelAttribute("Request") Request r,HttpServletRequest request)
{
	ModelAndView mv=null;
	EmpDao l=new EmpDao();
	  
    int x=l.cancelReq(r);
    if(x==1)
    {
       	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//      String email=c.getEmail();
//   	 String subject="Asset Management Employee Account Creation";
//   	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//      sendMail(email,subject,message);
 
	HttpSession ss=request.getSession();
	  String y=(String)ss.getAttribute("user");
    RequestDao ld=new RequestDao();
	ArrayList<Request> list=ld.viewReq(y);
	    mv=new ModelAndView("ViewRequest");
		 mv.addObject("LIST", list);
	     mv.addObject("msg","Your Request Has been cancelled");
    }
	     return mv;
    
    }
  @RequestMapping("/ViewProfile")
  protected ModelAndView viewProfile(HttpServletRequest request)
  {
  	ModelAndView mv=null;
  	 HttpSession ss=request.getSession();
     String y=(String)ss.getAttribute("user");
    	  //System.out.println(y);
  	EmpDao ed=new EmpDao();
  	ArrayList<Employee>list=ed.viewProfile(y);
  	mv=new ModelAndView("ViewProfile");
  	mv.addObject("LIST", list);
  	
  	
  	return mv;
  }
  
  @RequestMapping("/controller")
  protected ModelAndView result(HttpServletRequest request)
  {System.out.println("helooooooooo");
  	ModelAndView mv=null;
  	HttpSession ss=request.getSession();
  	String eid=(String)ss.getAttribute("user");
  	EmpDao ed=new EmpDao();
       ArrayList<Employee>list=ed.viewEmUpdate(eid);
       mv=new ModelAndView("updEmp");
       mv.addObject("LIST", list);
  	return mv;
  	
  	
  }
  
  @RequestMapping("/changeEPass")
  protected ModelAndView newpass()
  {
  	
  	ModelAndView mv=new ModelAndView("changeEPassword");
  	
  	return mv;
  	
  	
  }
  
  
  @RequestMapping("/changeEPassword")
  protected ModelAndView chndpwd(@RequestParam("cpass")String cpass,@RequestParam("npass2") String npass2,@RequestParam("eid1")String eid1 )
  {

	  ModelAndView mv=null;
  	
  	 EmpDao ed=new EmpDao();
  	 int y=ed.chngEPass(cpass,npass2,eid1);
  	 if(y==1)
  	 {
  	   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//       String email=c.getEmail();
//    	 String subject="Asset Management Employee Account Creation";
//    	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//       sendMail(email,subject,message);

  		 mv=new ModelAndView("changeEPassword");
  		 mv.addObject("change", "Password Change");
  		 
  		 
  	 }
  	 else
  	 {
  	   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//       String email=c.getEmail();
//    	 String subject="Asset Management Employee Account Creation";
//    	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//       sendMail(email,subject,message);

  		 mv=new ModelAndView("changeEPassword");
  		 mv.addObject("chng", "Failed,Current Password Doesnt Match");
  	 }
  	return mv;
  }

  @RequestMapping("/FetchData")
  protected ModelAndView data(@RequestParam("assetname") String assetname)
  {
  	String x="";
  	ModelAndView mv=null;
  	EmpDao ed=new EmpDao();
  	String y=ed.getAssetid(assetname);
  	System.out.println(y);
	    	if(y!=null)
	    	{
	    	x=x+y;
	    	//System.out.println("Aaa gyi x mai"+x);
	    	mv=new ModelAndView("getassetid");
	    	mv.addObject("value", x);
	    	}
//	    	else {
//	    		String s="No Content";
//	    		
//	    		return s;
//	    	}
	    	return mv;
  }
  
  
 //--------------------------------Manager Module------------------//
  
  
  @RequestMapping("/viewa")	
 	public ModelAndView viewa(HttpServletRequest request) 
 	{
 		ModelAndView mv=null;
 		EmpDao ed=new EmpDao();
 		HttpSession ss=request.getSession();
 		String user1=(String)ss.getAttribute("user");
 		System.out.println(user1);
 		ArrayList<Employee> list=ed.viewmanagerprofile(user1);
 		mv=new ModelAndView("managerprofile");
 		mv.addObject("LIST", list);
 	return mv;		
 	}
   
   @RequestMapping("/Mupdatepassword")
 	public ModelAndView opreation(HttpServletRequest request)
 	{
 	ModelAndView mv=null;
 	mv=new ModelAndView("ManagerupdatePassword");
 	return mv;
 	}
   
   @RequestMapping("/Manageropreation")
 	public ModelAndView confirmupdate(@RequestParam String mid,@RequestParam String npass2,@RequestParam String cpass)
 	{
 		ModelAndView mv=null;
 		
     	EmpDao ed=new EmpDao();
// 		HttpSession ss=request.getSession();
// 		String user1=(String)ss.getAttribute("user");
 		int y=ed.Managerupdateinsert(mid,npass2,cpass);
 		if(y!=0)
 		{
 		   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//          String email=c.getEmail();
//       	 String subject="Asset Management Employee Account Creation";
//       	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//          sendMail(email,subject,message);
  
 			mv=new ModelAndView("ManagerupdatePassword");
 			mv.addObject("msg", "password successfully changed");
 		}
 		else
 		{
 			mv=new ModelAndView("ManagerupdatePassword");	
 			   mv.addObject("msg","Password Not Changed because current password not matched");
 	         
 	       }	return mv;
 	}
   
   @RequestMapping("/Managercreate")	
   public ModelAndView McreateR() 
   {
      ModelAndView mv=new ModelAndView("ManagerCreateRequest");
 		mv.addObject("msg", "Manager Create Request");
 		return mv;
 	}
 	@RequestMapping("/InsertManager")
 	//public ModelAndView create(@RequestParam String uid, @RequestParam String name,@RequestParam String salary,@RequestParam String mobile,@RequestParam String email) 
 	public ModelAndView McreateR(@ModelAttribute("Request") Request r)
 	{
 	        ModelAndView mv=null;
 	        int status=1;
 	        EmpDao ed=new EmpDao();
 	        r.setStatus(status);
 	        r.setSid("S101");
 			int y=ed.MinsertRequest(r);
 			if(y!=0)
 			{
 			   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
// 	         String email=c.getEmail();
// 	      	 String subject="Asset Management Employee Account Creation";
// 	      	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
// 	         sendMail(email,subject,message);
 	 
 				mv=new ModelAndView("ManagerCreateRequest");
 				mv.addObject("msg", "Request sent to Support");
 			}
 			else
 			{
 				mv=new ModelAndView("ManagerCreateRequest");	
 			    mv.addObject("msg", " Request Not sent,try again");
 			}
 			return mv;
 	}
 	@RequestMapping("/Mviewrequest")
 	public ModelAndView MviewR(ModelAndView response,HttpServletRequest request) 
 	{
 		ModelAndView mv=null;
 		EmpDao ed=new EmpDao();
 		HttpSession ss=request.getSession();
 		String mid=(String) ss.getAttribute("user");
 		ArrayList<Request> list=ed.MviewRequest(mid);
 		mv=new ModelAndView("ManagerViewRequest");
 		mv.addObject("LIST", list);
 	return mv;		
 	}
 	
 	@RequestMapping("/managerasset")	
 	public ModelAndView viewmyasset(HttpServletRequest request,ModelAndView response) 
 	{
 		ModelAndView mv=null;
 		EmpDao ed=new EmpDao();
 		HttpSession ss=request.getSession();
 		String mid2=(String) ss.getAttribute("user");
 		ArrayList<AllotedAsset> list=ed.viewmanagerasset(mid2);
 		mv=new ModelAndView("viewmanagerasset");
 		mv.addObject("LIST", list);
 	return mv;			

 	}
 	
 	@RequestMapping("/viewMallemployee")	
 	public ModelAndView viewallemployee(HttpServletRequest request) 
 	{
 		ModelAndView mv=null;
 		HttpSession ss=request.getSession();
 		String user1=(String)ss.getAttribute("user");
 		System.out.println(user1);
 		EmpDao ed=new EmpDao();
 		ArrayList<Employee> list=ed.viewMallemployee(user1);
 		mv=new ModelAndView("viewManagerEmployee");
 	    mv.addObject("LIST", list);
 		
 		return mv;
 	}
 	 @RequestMapping("/mpendingrequest")
  	public ModelAndView  mpendingrequest()
  	{
 	   EmpDao rd=new EmpDao();
 	      ArrayList<Request>list=rd.viewMRequest();
 	      ModelAndView mv=new ModelAndView("pendingMrequest");//view name
 	       mv.addObject("LIST",list);
       
       return mv;

  	}
 	@RequestMapping("/mpendingtransferrequest")
  	public ModelAndView  mpendingtransferrequest()
  	{
 	   EmpDao rd=new EmpDao();
 	      ArrayList<UserAssetRequest>list=rd.viewTransferRequest();
 	      ModelAndView mv=new ModelAndView("pendingTransferrequest");//view name
 	       mv.addObject("LIST",list);
       
       return mv;

  	}


 	 
 	 
 	 
 	 
 	 @RequestMapping("/MApproveTransferReq")
 	public ModelAndView MApproveTransferReq(@ModelAttribute("UserAssetRequest")UserAssetRequest r,@RequestParam String op)
 	{
 	   ModelAndView mv=null; 
 	   if(op.equalsIgnoreCase("Approve"))
    {
 	   
 		   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//         String email=c.getEmail();
//      	 String subject="Asset Management Employee Account Creation";
//      	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//         sendMail(email,subject,message);
 
 		   EmpDao l=new EmpDao();
 	  
       int y=l.approveMTransferReq(r);
       if(y==1)
       {
     	   EmpDao rd=new EmpDao();
  	      ArrayList<UserAssetRequest>list=rd.viewTransferRequest();
  	       mv=new ModelAndView("pendingTransferrequest");//view name
  	       mv.addObject("LIST",list);
           mv.addObject("msg2","Request Approved ");
       }
    }  
 	   else if(op.equalsIgnoreCase("Reject"))
          {
 	   
 		   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//         String email=c.getEmail();
//      	 String subject="Asset Management Employee Account Creation";
//      	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//         sendMail(email,subject,message);
 
 		   EmpDao l=new EmpDao();
       int y=l.rejectMTransferRequest(r);
       if(y==1)
       {
     	   EmpDao rd=new EmpDao();
  	      ArrayList<UserAssetRequest>list=rd.viewTransferRequest();
  	      mv=new ModelAndView("pendingTransferrequest");//view name
  	       mv.addObject("LIST",list);
           mv.addObject("msg1","Request Reject for Employee");
       
       }
       }
 	   
     return mv;

 	}
 	 
 	 @RequestMapping("/ManagerApproveReq")
 	public ModelAndView ManagerApproveReq(@ModelAttribute("Request")Request r,@RequestParam String op)
 	{
 	   ModelAndView mv=null; 
 	   if(op.equalsIgnoreCase("Approve"))
    {
 	   
 		   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//         String email=c.getEmail();
//      	 String subject="Asset Management Employee Account Creation";
//      	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//         sendMail(email,subject,message);
 
 		   EmpDao l=new EmpDao();
 	  
       int y=l.approveMReq(r);
       if(y==1)
       {
    	   EmpDao rd=new EmpDao();
  	      ArrayList<Request>list=rd.viewMRequest();
  	       mv=new ModelAndView("pendingMrequest");//view name
  	       mv.addObject("LIST",list);
           mv.addObject("msg2","Request Approved ");
       }
    }  
 	   else if(op.equalsIgnoreCase("Reject"))
          {
 	   
 		   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//         String email=c.getEmail();
//      	 String subject="Asset Management Employee Account Creation";
//      	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//         sendMail(email,subject,message);
 
 		   EmpDao l=new EmpDao();
       int y=l.rejectMRequest(r);
       if(y==1)
       {
    	   EmpDao rd=new EmpDao();
  	       ArrayList<Request>list=rd.viewMRequest();
  	       mv=new ModelAndView("pendingMrequest");//view name
  	       mv.addObject("LIST",list);
           mv.addObject("msg1","Request Reject for Employee");
       
       }
       }
 	   
     return mv;

 	}


 	@RequestMapping("/CheckData")
 	protected void checkdata(@RequestParam String eid,HttpServletResponse response)
 	{
 		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
 		//ModelAndView mv=null;
 		EmpDao l=new EmpDao();
 		  
 	    int x=l.checkdata(eid);
 	    if(x==1)
 	    {
 	 	  out.println("1");
 		
 	    
 	    }
 	    else
 	    {
 	    	out.println("0");
 	    }
}
 	
 	
 	
 	
 	@RequestMapping("/CheckEmail")
 	protected void checkmail(@RequestParam String email1,HttpServletResponse response)
 	{
 		System.out.println("Hello");
 		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
 		ModelAndView mv=null;
 		EmpDao l=new EmpDao();
 		  
 	    int x=l.checkemail(email1);
 	    if(x==1)
 	    {
 	 	  out.println("1");
 		
 	    }
 	    else
 	    {
 	    	out.println("0");
 	    }
}
 	
 	
	@RequestMapping("/CheckMobile")
 	protected void checkmonbile(@RequestParam long mobile1,HttpServletResponse response)
 	{
 		System.out.println("Hello");
 		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
 		ModelAndView mv=null;
 		EmpDao l=new EmpDao();
 		  
 	    int x=l.checkmobile(mobile1);
 	    if(x==1)
 	    {
 	 	  out.println("1");
 		
 	    }
 	    else
 	    {
 	    	out.println("0");
 	    }
}

 
	@RequestMapping("/CheckMid")
 	protected void checkmid(@RequestParam String mid2,HttpServletResponse response)
 	{
 		System.out.println("Hello");
 		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
 		ModelAndView mv=null;
 		EmpDao l=new EmpDao();
 		  
 	    int x=l.checkmid(mid2);
 	    if(x==1)
 	    {
 	 	  out.println("1");
 		
 	    }
 	    else
 	    {
 	    	out.println("0");
 	    }
}


	@RequestMapping("/CheckSid")
 	protected void checksid(@RequestParam String eid2,HttpServletResponse response)
 	{
 		System.out.println("Hello");
 		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
 		ModelAndView mv=null;
 		EmpDao l=new EmpDao();
 		  
 	    int x=l.checksid(eid2);
 	    if(x==1)
 	    {
 	 	  out.println("1");
 		
 	    }
 	    else
 	    {
 	    	out.println("0");
 	    }
}

	
	

	  @RequestMapping("/ViewAllotedAsset")//employee can view his asset that his allocated 
	 	protected ModelAndView viewallotedAsset(HttpServletRequest request) {
	 	  ModelAndView mv=null;
	 	
	 	  HttpSession ss=request.getSession();
	 	  String y=(String)ss.getAttribute("user");
	      RequestDao ld=new RequestDao();
		  ArrayList<AllotedAsset> list=ld.viewallotedAsset();
	 	    mv=new ModelAndView("ViewAllotedAsset");
	 		 mv.addObject("LIST", list);
	 	  
	 	  return mv;

	
	
	
	
	  }
	  
	  @RequestMapping("/TransferRequest")//employee can view his asset that his allocated 
	 	protected ModelAndView TransferRequest(@ModelAttribute("UserAssetRequest") UserAssetRequest u,HttpServletRequest request ) {
	 	  ModelAndView mv=null;
	 	 RequestDao rd=new RequestDao();
	 	HttpSession ss=request.getSession();
		  String fromemp=(String) ss.getAttribute("user");
		    u.setFromemp(fromemp);
	 	  	int y=rd.insertTransferReq(u);
	 	  	 	 		 
	 	  	if(y==1)
	 	  	{
	 	  	   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//	 	      String email=c.getEmail();
//	 	   	 String subject="Asset Management Employee Account Creation";
//	 	   	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//	 	      sendMail(email,subject,message);
		      RequestDao ld=new RequestDao();
			  ArrayList<AllotedAsset> list=ld.viewallotedAsset();
		 	    mv=new ModelAndView("ViewAllotedAsset");
		 		 mv.addObject("LIST", list);
		 	  		  mv.addObject("msg", "Request Sent");
	 					 
	 			  
	 	  	}
	 	  	else {
	 	  	   	//   String url="http://localhost:8080/Asset_Management/Emplogin";
//	 	      String email=c.getEmail();
//	 	   	 String subject="Asset Management Employee Account Creation";
//	 	   	 String message="Welcome to Systango Your Employee Id is : "+c.getEid1()+" Your password is :  "+c.getPassword()+" Your Manager id : "+c.getMid1()+"   Url For login : "+url;
//	 	      sendMail(email,subject,message);

			      RequestDao ld=new RequestDao();
				  ArrayList<AllotedAsset> list=ld.viewallotedAsset();
		 			mv=new ModelAndView("ViewAllotedAsset");
		 	  		mv.addObject("msg", "Request not sent ");
			 		 mv.addObject("LIST", list);
	 	  	
	 	  	}

	 	  
	 	  
	 	  
	 	  return mv;

	  
	  
	  }	  
	  
	  
	  
	  @RequestMapping("/ViewEAssetTransferRequest")//employee can view his asset that his allocated 
	 	protected ModelAndView viewAssetTransfer(HttpServletRequest request) {
	 	  ModelAndView mv=null;
	 	System.out.println("View Asset Request ");
	 	  HttpSession ss=request.getSession();
	 	  String y=(String)ss.getAttribute("user");
	      RequestDao ld=new RequestDao();
	      List<UserAssetRequest> list=ld.viewAssetTransfer(y);
	      mv=new ModelAndView("ViewAssetTransferRequest");
	      mv.addObject("User", list);
	 	  return mv;

	

	  
	  
	  
	  
}
	  @RequestMapping("/ViewRequestFromOther")//employee can view his asset that his allocated 
	 	protected ModelAndView viewAssetRequestFromOther(HttpServletRequest request) {
	 	  ModelAndView mv=null;
	 	  //System.out.println("View Asset Request ");
	 	  HttpSession ss=request.getSession();
	 	  String y=(String)ss.getAttribute("user");
	      RequestDao ld=new RequestDao();
	      List<UserAssetRequest> list=ld.viewRequestFromOther(y);
	      mv=new ModelAndView("ViewAssetRequestFromOther");
	      mv.addObject("User", list);
	 	  return mv;

	

}
	  
	  
	  
	  @RequestMapping("/ETransferAssetFromOther")//employee can view his asset that his allocated 
	 	protected ModelAndView viewETransferRequestFromOther(HttpServletRequest request,@RequestParam String op) {  
		  ModelAndView mv=null;
		  if(op.equals("Approve"))
	 	  {
		 
	 	  //System.out.println("View Asset Request ");
	 	  HttpSession ss=request.getSession();
	 	  String y=(String)ss.getAttribute("user");
	      RequestDao ld=new RequestDao();
	      List<UserAssetRequest> list=ld.viewRequestFromOther(y);
	      mv=new ModelAndView("ViewAssetRequestFromOther");
	      mv.addObject("User", list);

	 	  }
	 	  if(op.equals("Reject"))
	 	  {
	 		  
	 		  
	 		  
	 	  }
		return mv;

}
	  
	  
	  
	  
	  
	  
	  
	  
}