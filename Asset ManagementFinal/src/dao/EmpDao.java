package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import com.controllers.AssetController;

import beans.AdminLogin;
import beans.AllotedAsset;
import beans.Asset;
import beans.Employee;
import beans.Request;
import beans.UserAssetRequest;

public class EmpDao {

	
	public int insertEmp(Employee e)
	{
		int x=0;
		//Connection con=new LoginDao().start();
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Transaction tt=ss.beginTransaction();
		
		System.out.println(e);
		if(e.getDesignation().equalsIgnoreCase("Manager"))
		{	
		
			if(ss.save(e)!=null)
			{
		      x=1;	
			}	
		}
		else if(e.getDesignation().equalsIgnoreCase("Employee"))
			
		{
			
			if(ss.save(e)!=null)
			{
			x=1;
				}
					
		}
			 
		else if(e.getDesignation().equalsIgnoreCase("Support Team"))
			
		{
			
			if(ss.save(e)!=null)
			{
			x=1;
				}
		}
		
		tt.commit();
		ss.close();
		
		
		return x;
		}
		
	public ArrayList<Employee>viewEmp()
	{
	
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Employee.class);
		ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
		
		ss.close();
		

		return list;
		
	}
	public int activateEmp(String eid) {
		int x=0;
		int status=1;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		if(eid.contains("E"))
		{
	    String hql="update Employee set status=:a where eid1=:b";
	    Query q=ss.createQuery(hql);
	    q.setInteger("a",status);
	    q.setString("b",eid);
	    Transaction tt=ss.beginTransaction();
	     x=q.executeUpdate();
		    
	 	tt.commit();
		}
		if(eid.contains("S"))
		{
		    String hql="update Employee set status=:a where eid2=:b";
		    Query q=ss.createQuery(hql);
		    q.setInteger("a",status);
		    q.setString("b",eid);
		    Transaction tt=ss.beginTransaction();
		     x=q.executeUpdate();
			    
		 	tt.commit();
			}
		if(eid.contains("M"))
			{
			    String hql="update Employee set status=:a where mid2=:b";
			    Query q=ss.createQuery(hql);
			    q.setInteger("a",status);
			    q.setString("b",eid);
			    Transaction tt=ss.beginTransaction();
			     x=q.executeUpdate();
				    
			 	tt.commit();
				}
	 	ss.close();
			
	    
		return x;

	}
	public int deactivateEmp(String eid) {
		int x=0;
		int status=0;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		if(eid.contains("E"))
		{
	    String hql="update Employee set status=:a where eid1=:b";
	    Query q=ss.createQuery(hql);
	    q.setInteger("a",status);
	    q.setString("b",eid);
	    Transaction tt=ss.beginTransaction();
	     x=q.executeUpdate();
		    
	 	tt.commit();
		}
		if(eid.contains("S"))
		{
		    String hql="update Employee set status=:a where eid2=:b";
		    Query q=ss.createQuery(hql);
		    q.setInteger("a",status);
		    q.setString("b",eid);
		    Transaction tt=ss.beginTransaction();
		     x=q.executeUpdate();
			    
		 	tt.commit();
			}
		if(eid.contains("M"))
			{
			    String hql="update Employee set status=:a where mid2=:b";
			    Query q=ss.createQuery(hql);
			    q.setInteger("a",status);
			    q.setString("b",eid);
			    Transaction tt=ss.beginTransaction();
			     x=q.executeUpdate();
				    
			 	tt.commit();
				}
	 		ss.close();
			

		return x;

	}

	public int updateEmp(Employee e) {
		int x=0;
	
			AssetController ac=new AssetController();
			Session ss= ac.session();
		   if(e.getMid2()!=null)
			{
	
			   if(e.getDesignation().equals("Employee"))
				{
				    String hql="update Employee set eid1=:a,designation=:b,mid1=:c where mid2=:d";
				    Query q=ss.createQuery(hql);
				    q.setString("a",e.getEid1());
				    q.setString("b",e.getDesignation());
				    q.setString("c",e.getMid1());
				    q.setString("d",e.getMid2());
				    
				    String hql1="update Employee set mid2=:a where eid1=:c";
				    Query q1=ss.createQuery(hql1);
				    q1.setString("a","0");
				    q1.setString("c",e.getEid1());
				    
				    Transaction tt=ss.beginTransaction();
				     x=q.executeUpdate();
				     x=q1.executeUpdate();
					      tt.commit();
					    	
				}
		   
			   if(e.getDesignation().equals("Support Team"))
				{
				    String hql="update Employee set eid2=:a,designation=:b where mid2=:c";
				    Query q=ss.createQuery(hql);
				    q.setString("a",e.getEid2());
				    q.setString("b",e.getDesignation());
				    q.setString("c",e.getMid2());

				    String hql1="update Employee set mid2=:a where eid2=:b";
				    Query q1=ss.createQuery(hql1);
				    q1.setString("a","0");
				    q1.setString("b",e.getEid2());
					    
				    Transaction tt=ss.beginTransaction();
				     x=q.executeUpdate();
				     x=q1.executeUpdate();
					    tt.commit();
					    	
				}
		   
			}
		   if(e.getEid2()!=null)
		{   
		   
		   if(e.getDesignation().equals("Employee"))
			{
			    
				String hql="update Employee set eid1=:a,designation=:b,mid1=:c where eid2=:d";
			    Query q=ss.createQuery(hql);
			    q.setString("a",e.getEid1());
			    q.setString("b",e.getDesignation());
			    q.setString("c",e.getMid1());
			    q.setString("d",e.getEid2());
			    
			    String hql1="update Employee set eid2=:a where eid1=:b";
			    Query q1=ss.createQuery(hql1);
			    q1.setString("a","0");
			    q1.setString("b",e.getEid1());
				
			    Transaction tt=ss.beginTransaction();
			     x=q.executeUpdate();
			     x=q1.executeUpdate();
				    tt.commit();
				    	
			}
	   
		   if(e.getDesignation().equals("Manager"))
			{
				String hql="update Employee set mid2=:a,designation=:b,sid=:c where eid2=:d";
			    Query q=ss.createQuery(hql);
			    q.setString("a",e.getMid2());
			    q.setString("b",e.getDesignation());
			    q.setString("c",e.getSid());
				q.setString("d",e.getEid2());
			    
			    String hql1="update Employee set eid2=:a where mid2=:b";
			    Query q1=ss.createQuery(hql1);
			    q1.setString("a","0");
			    q1.setString("b",e.getMid2());
				
			    
			    Transaction tt=ss.beginTransaction();
			     x=q.executeUpdate();
			     x=q1.executeUpdate();
				    tt.commit();
				    	
			}
	   
		}
		   if(e.getEid1()!=null)
		{
		   
		   if(e.getDesignation().equals("Manager"))
					{
						String hql="update Employee set mid2=:a,designation=:b,sid=:c where eid1=:d";
					    Query q=ss.createQuery(hql);
					    q.setString("a",e.getMid2());
					    q.setString("b",e.getDesignation());
					    q.setString("c",e.getSid());
					    q.setString("d",e.getEid1());
							
					    String hql1="update Employee set eid1=:a,mid1=:b where mid2=:c";
					    Query q1=ss.createQuery(hql1);
					    q1.setString("a","0");
					    q1.setString("b","0");
					    q1.setString("c",e.getMid2());
						
					    Transaction tt=ss.beginTransaction();
					     x=q.executeUpdate();
					     x=q1.executeUpdate();
						    tt.commit();
						    	
					}
			   
				   if(e.getDesignation().equals("Support Team"))
					{
						String hql="update Employee set eid2=:a,designation=:b where eid1=:c";
					    Query q=ss.createQuery(hql);
					    q.setString("a",e.getEid2());
					    q.setString("b",e.getDesignation());
					    q.setString("c",e.getEid1());
					    
					    String hql1="update Employee set eid1=:a,mid1=:b where eid2=:c";
					    Query q1=ss.createQuery(hql1);
					    q1.setString("a","0");
					    q1.setString("b","0");
						q1.setString("c",e.getEid2());
						
					    Transaction tt=ss.beginTransaction();
					     x=q.executeUpdate();
					     x=q1.executeUpdate();
						    tt.commit();
						    	
					}
			   
		}
		 	ss.close();
				

		return x;
	}

	public Employee viewEmpUpdate(String eid) {
		    
		  Employee e=null;
	
//			AssetController ac=new AssetController();
//			Session ss= ac.session();
		 SessionFactory sf=new AnnotationConfiguration().configure().buildSessionFactory();//Class.forName
			org.hibernate.Session ss=sf.openSession(); //Connection
			System.out.println(eid);
			if(eid.contains("E"))
		    {
		    String hql="from Employee where eid1=:a";
			Query q=ss.createQuery(hql);
			q.setString("a", eid);
			List<Employee> list=(List<Employee>)q.list();
			  e=list.get(0);
		    }
			
			if(eid.contains("S"))
		    {
		    String hql="from Employee where eid2=:a";
			Query q=ss.createQuery(hql);
			q.setString("a", eid);
			List<Employee> list=(List<Employee>)q.list();
			  e=list.get(0);
		    }
			
			if(eid.contains("M"))
		    {
		    String hql="from Employee where mid2=:a";
			Query q=ss.createQuery(hql);
			q.setString("a", eid);
			 List<Employee> list=(List<Employee>)q.list();
			  e=list.get(0);
			
			/*Iterator it=q.iterate();
			while(it.hasNext())
			{
				 e=(Employee) it.next();
			}*/
			/*Criteria ct=ss.createCriteria(Employee.class);
			ct.add(Restrictions.eq("mid2",eid));
			  List<Employee> list=(List<Employee>)ct.list();
			  e=list.get(0);*/
				
		    }
			ss.close();
			
		    
		    return e;
	}

	public ArrayList<Request> viewRequest() {
		int status=1;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Request.class);
		crit.add(Restrictions.eq("status",status));
	    ArrayList<Request> list = (ArrayList<Request>) crit.list();
		for(Request r:list)
		{
			System.out.println(r);
		}
	    ss.close();
		
		return list;
	}
	public ArrayList<Request> viewMRequest() {
		int status=0;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Request.class);
		crit.add(Restrictions.eq("status",status));
	    ArrayList<Request> list = (ArrayList<Request>) crit.list();
		for(Request r:list)
		{
			System.out.println(r);
		}
	    ss.close();
		
		return list;
	}
//public static void main(String[] args) {
//	System.out.println(new EmpDao().viewRequest());
//}
	public int approveReq(Request r) {
		

		int x=0;
		int status=3;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		if(r.getEid1()!=null)
		{
		System.out.println("Approve req "+status+" "+r.getEid1()+" "+r.getRequestid());
	    String hql="update Request set status=:a where eid1=:b and requestid=:c ";
	    Query q=ss.createQuery(hql);
	    q.setInteger("a",status);
	    q.setString("b",r.getEid1());
	    q.setString("c",r.getRequestid());
	    
	    Transaction tt=ss.beginTransaction();
	     x=q.executeUpdate();
				
			    String hql2="from Request where requestid=:a and eid1=:b";
				Query q2=ss.createQuery(hql2);
				q2.setString("a",r.getRequestid());
				q2.setString("b",r.getEid1());
				List<Request> list=(List<Request>)q2.list();
				for(Request r1:list)
				{
                AllotedAsset aa=new AllotedAsset(); 
				int x1=0;
				aa.setAssetid(r1.getAssetid());
				aa.setAssetname(r1.getAssetname());
				aa.setEid1(r.getEid1());
				aa.setMid1(r.getMid1());
				aa.setStatus(1);
					if(ss.save(aa)!=null)
					{
				      x1=1;	
					}
			          System.out.println("X in save func in allotedasset "+x1);
			      	
				}	
    			     	tt.commit();
						ss.close();

				
		}
		if(r.getMid2()!=null)
		{
		System.out.println("Approve req "+status+" "+r.getMid2()+" "+r.getRequestid());	  
		String hql="update Request set status=:a where mid2=:b and requestid=:c";
	    Query q=ss.createQuery(hql);
	    q.setInteger("a",status);
	    q.setString("b",r.getMid2());
	    q.setString("c",r.getRequestid());
		    Transaction tt=ss.beginTransaction();
	     x=q.executeUpdate();

	        String hql2="from Request where requestid=:a and mid2=:b";
			Query q2=ss.createQuery(hql2);
			q2.setString("a",r.getRequestid());
			q2.setString("b",r.getMid2());
			List<Request> list=(List<Request>)q2.list();
			for(Request r1:list)
			{
         AllotedAsset aa=new AllotedAsset(); 
			int x1=0;
			aa.setAssetid(r1.getAssetid());
			aa.setAssetname(r1.getAssetname());
			aa.setMid2(r.getMid2());
			aa.setStatus(1);
				if(ss.save(aa)!=null)
				{
			      x1=1;	
				}
		          System.out.println("X in save func in allotedasset "+x1);
		      	
			}	
			     	tt.commit();
					ss.close();		 
		}
	
	    	

		return x;
	}
        public int approveMReq(Request r) {
		

		int x=0;
		int status=1;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		if(r.getEid1()!=null)
		{
		System.out.println("Approve req "+status+" "+r.getEid1()+" "+r.getRequestid());
	    String hql="update Request set status=:a where eid1=:b and requestid=:c ";
	    Query q=ss.createQuery(hql);
	    q.setInteger("a",status);
	    q.setString("b",r.getEid1());
	    q.setString("c",r.getRequestid());
	    
	    Transaction tt=ss.beginTransaction();
	     x=q.executeUpdate();
				     	tt.commit();
						ss.close();

				
		}
		
	    	return x;
	}
	
       public int rejectMRequest(Request r) {
	

	int x=0;
	int status=2;
	AssetController ac=new AssetController();
	Session ss= ac.session();
	if(r.getEid1()!=null)
	{
	System.out.println("Approve req "+status+" "+r.getEid1()+" "+r.getRequestid());
    String hql="update Request set status=:a where eid1=:b and requestid=:c ";
    Query q=ss.createQuery(hql);
    q.setInteger("a",status);
    q.setString("b",r.getEid1());
    q.setString("c",r.getRequestid());
    
    Transaction tt=ss.beginTransaction();
     x=q.executeUpdate();
			     	tt.commit();
					ss.close();

			
	}
	
    	return x;
}
       public int approveMTransferReq(UserAssetRequest r) {
   		

   		int x=0;
   		int status=1;
   		AssetController ac=new AssetController();
   		Session ss= ac.session();
   		if(r.getFromemp()!=null)
   		{
   		System.out.println("Approve req "+status+" "+r.getFromemp()+" "+r.getRequestid());
   	    String hql="update UserAssetRequest set status=:a where fromemp=:b and requestid=:c ";
   	    Query q=ss.createQuery(hql);
   	    q.setInteger("a",status);
   	    q.setString("b",r.getFromemp());
   	    q.setInteger("c",r.getRequestid());
   	    
   	    Transaction tt=ss.beginTransaction();
   	     x=q.executeUpdate();
   				     	tt.commit();
   						ss.close();

   				
   		}
   		
   	    	return x;
   	}
   	
          public int rejectMTransferRequest(UserAssetRequest r) {
   	

   	int x=0;
   	int status=2;
   	AssetController ac=new AssetController();
   	Session ss= ac.session();
   	if(r.getFromemp()!=null)
   	{
   	System.out.println("Approve req "+status+" "+r.getFromemp()+" "+r.getRequestid());
       String hql="update UserAssetRequest set status=:a where fromemp=:b and requestid=:c ";
       Query q=ss.createQuery(hql);
       q.setInteger("a",status);
       q.setString("b",r.getFromemp());
       q.setInteger("c",r.getRequestid());
       
       Transaction tt=ss.beginTransaction();
        x=q.executeUpdate();
   			     	tt.commit();
   					ss.close();

   			
   	}
   	
       	return x;
   }

       
       
       
 //-------------------------------------------------------------------------------------------      
public int rejectReq(Request r) {
		int x=0;
		int status=4;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		if(r.getEid1()!=null)
		{
	    String hql="update Request set status=:a where eid1=:b and requestid=:c ";
	    Query q=ss.createQuery(hql);
	    q.setInteger("a",status);
	    q.setString("b",r.getEid1());
	    q.setString("c",r.getRequestid());
	    
	    Transaction tt=ss.beginTransaction();
	    x=q.executeUpdate();
		tt.commit();
	    ss.close();

		}
		if(r.getMid2()!=null)
		{
	    String hql="update Request set status=:a where mid2=:b and requestid=:c";
	    Query q=ss.createQuery(hql);
	    q.setInteger("a",status);
	    q.setString("b",r.getMid2());
	    q.setString("c",r.getRequestid());
		    
	    Transaction tt=ss.beginTransaction();
	     x=q.executeUpdate();
		
	     tt.commit();
		 ss.close();
		}
		 	    		

		return x;

	}

	public ArrayList<Request> viewReports() {
		

		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Request.class);
	    ArrayList<Request> list = (ArrayList<Request>) crit.list();
		ss.close();
		
		return list;
	
	}
	

	
	public String getAssetid(String assetname)
	{
		String a="";
		AssetController ac=new AssetController();
		Session ss= ac.session();
		 String hql="select assetid from Asset where assetname=:a";
		Query q=ss.createQuery(hql);
		q.setString("a",assetname);
		Iterator it=q.iterate();
		while(it.hasNext())
		{
			 a=(String) it.next();
		}
		ss.close();
	return a;
	}
	

	public ArrayList<Employee> viewProfile(String y)
	{
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Employee.class);
		crit.add(Restrictions.eq("eid1",y));
	    ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
		ss.close();

	  return list;		
	}

	public ArrayList<Employee> viewSProfile(String y)
	{
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Employee.class);
		crit.add(Restrictions.eq("eid2",y));
	    ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
		ss.close();

	  return list;		
	}
	
	public ArrayList<Employee> viewEmUpdate(String eid) {
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Employee.class);
		crit.add(Restrictions.eq("eid1",eid));
	    ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
		ss.close();


		return list;
	}

	
	
	
	
	public int chngEPass(String cpass,String npass2,String eid1)
	{
		int x=0;
		String a=null;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		String hql1="select password from Employee where eid1=:a";
	    Query q1=ss.createQuery(hql1);
	    q1.setString("a",eid1);
		Iterator it=q1.iterate();
		while(it.hasNext())
		{
			 a=(String) it.next();
		}
	   
		if(a.equals(cpass))
		{
			
	    String hql="update Employee set password=:b where eid1=:a";
	    Query q=ss.createQuery(hql);
	    q.setString("b",npass2);
	    q.setString("a",eid1);
	     Transaction tt=ss.beginTransaction();
	     x=q.executeUpdate();
		    
	 	tt.commit();
	 	
		}
			
			ss.close();
		
			return x;
}

	public ArrayList<Employee> viewmanagerprofile(String user1) {

		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Employee.class);
		crit.add(Restrictions.eq("mid2",user1));
		crit.add(Restrictions.eq("designation","Manager"));
	    ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
		Transaction tt=ss.beginTransaction();
		
		tt.commit();
		ss.close();

		return list;
		}

	public int MinsertRequest(Request r) 
	{
		int x=0;
		//Connection con=new LoginDao().start();
		AssetController ac=new AssetController();
		Session ss= ac.session();
		System.out.println(r);
		Transaction tt=ss.beginTransaction();
		SimpleDateFormat s=new SimpleDateFormat("dd/MM/yyyy");
		r.setRequestdate(s.format(new Date()));
		r.setDesignation("Manager");
			if(ss.save(r)!=null)
			{
		      x=1;	
			}	
			
			tt.commit();
			ss.close();
			
			return x;
	
	}
	public ArrayList<Request> MviewRequest(String mid) 
	{
	
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Request.class);
		crit.add(Restrictions.eq("mid2",mid));
		
		ArrayList<Request> list = (ArrayList<Request>) crit.list();
		ss.close();
		
		return list;
	
			}

	public ArrayList<AllotedAsset> viewmanagerasset(String mid2) 
	{
		int status=1;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(AllotedAsset.class);
		crit.add(Restrictions.eq("status",status));
		crit.add(Restrictions.eq("mid2",mid2));
		ArrayList<AllotedAsset> list = (ArrayList<AllotedAsset>) crit.list();
		ss.close();
		
		return list;
	}

	public ArrayList<Employee> viewMallemployee(String user1) 
	{
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Employee.class);
		crit.add(Restrictions.eq("mid1",user1));
		crit.add(Restrictions.eq("designation","Employee"));
		ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
		ss.close();
		
		return list;
	}

	public int Managerupdateinsert(String mid,String npass2, String cpass) 
	{
		int x=0;
		String a=null;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		String hql1="select password from Employee where mid2=:a";
	    Query q1=ss.createQuery(hql1);
	    q1.setString("a",mid);
		Iterator it=q1.iterate();
		while(it.hasNext())
		{
			 a=(String) it.next();
		}
	   
		if(a.equals(cpass))
		{
			
	    String hql="update Employee set password=:b where mid2=:a";
	    Query q=ss.createQuery(hql);
		Transaction tt=ss.beginTransaction();
		q.setString("b",npass2);
	    q.setString("a",mid);
	     x=q.executeUpdate();
	     tt.commit();
			
		}
			
			ss.close();
		
			return x;
	}


	public int cancelReq(Request r) {
   
		int x=0;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		System.out.println("cancel request "+r.getRequestid());
		String hql="delete from Request where requestid=:a";
	    Query q=ss.createQuery(hql);
		Transaction tt=ss.beginTransaction();
		q.setString("a",r.getRequestid());
	     x=q.executeUpdate();
	     tt.commit();
	 	ss.close();
		
		
		return x;
	}

	public int eupdateEmp(Employee c) {
    
		int x=0;
		SessionFactory sf=new AnnotationConfiguration().configure().buildSessionFactory();//Class.forName
		org.hibernate.Session ss=sf.openSession(); //Connection
		
		if(c.getEid1()!=null)
		{
		String hql="update Employee set address=:a,email=:b,mobile=:c where eid1=:d";
	    Query q=ss.createQuery(hql);
	    q.setString("a",c.getAddress());
	    q.setString("b",c.getEmail());
	    q.setLong("c",c.getMobile());
	    q.setString("d",c.getEid1());
	    Transaction tt=ss.beginTransaction();
	     x=q.executeUpdate();
		    tt.commit();
		}
		ss.close();
	return x;
	}

	public int TransferEasset(String eid1, String eid, String assetid) {

		int x=0;
		AssetController ac=new AssetController();
		Session ss= ac.session();
	     Transaction tt=ss.beginTransaction();
	     if(eid.contains("M"))
	     {
     	String hql1="update Asset set mid2=:a where eid1=:b and assetid=:c ";
	    Query q1=ss.createQuery(hql1);
	            q1.setString("a",eid);
			    q1.setString("b",eid1);
			    q1.setString("c",assetid);
			    x=q1.executeUpdate();
			    
			    String hql="update Asset set eid1=:a,mid1=:b where eid1=:c ";
			    Query q=ss.createQuery(hql);
			            q.setString("a","null");
					    q.setString("b","null");
					    q.setString("c",eid1);
					    
					    x=q.executeUpdate();
					    tt.commit();
						ss.close();

	     	}
	     if(eid.contains("E"))
	     {
     	String hql1="update Asset set eid1=:a where eid1=:b and assetid=:c ";
	    Query q1=ss.createQuery(hql1);
	            q1.setString("a",eid);
			    q1.setString("b",eid1);
			    q1.setString("c",assetid);
			    
			     x=q1.executeUpdate();
				     	tt.commit();
						ss.close();

	     	}

	return x;
	}
	

	public ArrayList<Employee> getEmpDetail()
	{		
	    AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Employee.class);
		ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
		
		
			    ss.close();
	            return list;
		}
	public static void main(String[] args) {
		System.out.println(new EmpDao().getEmpDetail());
	}


public int checkdata(String eid)
{
	int x=0;
	AssetController ac=new AssetController();
	Session ss= ac.session();
	Criteria crit=ss.createCriteria(Employee.class);
	crit.add(Restrictions.eq("eid1", eid));
	ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
	if(!list.isEmpty())
	{
		x=1;
	}
ss.close();
return x;
	
}


public int checkemail(String email)
{
	//System.out.println("Heloo");
	int x=0;
	AssetController ac=new AssetController();
	Session ss= ac.session();
	Criteria crit=ss.createCriteria(Employee.class);
	crit.add(Restrictions.eq("email", email));
	ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
	if(!list.isEmpty())
	{
		x=1;
	}
ss.close();
return x;
	
}

public int checkmobile(long mobile)
{
	//System.out.println("Heloo");
	int x=0;
	AssetController ac=new AssetController();
	Session ss= ac.session();
	Criteria crit=ss.createCriteria(Employee.class);
	crit.add(Restrictions.eq("mobile", mobile));
	ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
	if(!list.isEmpty())
	{
		x=1;
	}
ss.close();
return x;
	
}


public int checkmid(String mid)
{
	//System.out.println("Heloo");
	int x=0;
	AssetController ac=new AssetController();
	Session ss= ac.session();
	Criteria crit=ss.createCriteria(Employee.class);
	crit.add(Restrictions.eq("mid2", mid));
	ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
	if(!list.isEmpty())
	{
		x=1;
	}
ss.close();
return x;
	
}



public int checksid(String eid2)
{
	//System.out.println("Heloo");
	int x=0;
	AssetController ac=new AssetController();
	Session ss= ac.session();
	Criteria crit=ss.createCriteria(Employee.class);
	crit.add(Restrictions.eq("eid2", eid2));
	ArrayList<Employee> list = (ArrayList<Employee>) crit.list();
	if(!list.isEmpty())
	{
		x=1;
	}
ss.close();
return x;
	
}



public ArrayList<UserAssetRequest> viewTransferRequest() {
	int status=0;
	AssetController ac=new AssetController();
	Session ss= ac.session();
	Criteria crit = ss.createCriteria(UserAssetRequest.class);
	crit.add(Restrictions.eq("status",status));
    ArrayList<UserAssetRequest> list = (ArrayList<UserAssetRequest>) crit.list();
	for(UserAssetRequest r:list)
	{
		System.out.println(r);
	}
    ss.close();
	
	return list;
}







public int approveEmpTransferReq(Request r) {
	

int x=0;
int status=1;
AssetController ac=new AssetController();
Session ss= ac.session();
if(r.getEid1()!=null)
{
System.out.println("Approve req "+status+" "+r.getEid1()+" "+r.getRequestid());
String hql="update Request set status=:a where eid1=:b and requestid=:c ";
Query q=ss.createQuery(hql);
q.setInteger("a",status);
q.setString("b",r.getEid1());
q.setString("c",r.getRequestid());

Transaction tt=ss.beginTransaction();
 x=q.executeUpdate();
		     	tt.commit();
				ss.close();

		
}

	return x;
}

public int rejectEmpTransferReq(Request r) {


int x=0;
int status=2;
AssetController ac=new AssetController();
Session ss= ac.session();
if(r.getEid1()!=null)
{
System.out.println("Approve req "+status+" "+r.getEid1()+" "+r.getRequestid());
String hql="update Request set status=:a where eid1=:b and requestid=:c ";
Query q=ss.createQuery(hql);
q.setInteger("a",status);
q.setString("b",r.getEid1());
q.setString("c",r.getRequestid());

Transaction tt=ss.beginTransaction();
x=q.executeUpdate();
	     	tt.commit();
			ss.close();

	
}

return x;
}







}

	
	

