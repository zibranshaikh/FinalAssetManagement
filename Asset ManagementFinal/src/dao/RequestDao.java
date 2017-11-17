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
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.controllers.AssetController;

import beans.AllotedAsset;
import beans.Asset;
import beans.Employee;
import beans.Request;
import beans.UserAssetRequest;

public class RequestDao {
	
	
	public int insertReq(Request r) 
	{
		int x=0;
		Date d=new Date();
		d.getTime();
		//Connection con=new LoginDao().start();
		AssetController ac=new AssetController();
		Session ss= ac.session();
		System.out.println(r);
		Transaction tt=ss.beginTransaction();
		SimpleDateFormat s=new SimpleDateFormat("dd/MM/yyyy");
		r.setRequestdate(s.format(new Date()));
			if(ss.save(r)!=null)
			{
		      x=1;	
			}	
			
			tt.commit();
			ss.close();
			
			return x;
	}
	
	
	public ArrayList<Request> viewReq(String y)
	{   
	
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(Request.class);
		crit.add(Restrictions.eq("eid1",y));
	    ArrayList<Request> list = (ArrayList<Request>) crit.list();
		ss.close();
		
		return list;
	
	}
	
	public ArrayList<AllotedAsset> viewMyAsset(String y)
	{     
		
	    int status=1;
		AssetController ac=new AssetController();
		Session ss= ac.session();
		Criteria crit = ss.createCriteria(AllotedAsset.class);
		crit.add(Restrictions.eq("eid1",y));
		crit.add(Restrictions.eq("status",status));
		
	    ArrayList<AllotedAsset> list = (ArrayList<AllotedAsset>) crit.list();
		ss.close();
		
		return list;
	
	}
	
	public ArrayList<AllotedAsset> viewallotedAsset()
	{     
	    int status=1;
			AssetController ac=new AssetController();
			Session ss= ac.session();
			Criteria crit = ss.createCriteria(AllotedAsset.class);
			crit.add(Restrictions.eq("status",status));
			
		    ArrayList<AllotedAsset> list = (ArrayList<AllotedAsset>) crit.list();
			ss.close();
			
			return list;
		
	}
	
	
	
	public List<UserAssetRequest> viewAssetTransfer(String fromemp)
	{     
		UserAssetRequest u=null;
	    //int status=1,x=0;
			AssetController ac=new AssetController();
			Session ss= ac.session();
			String hql="from UserAssetRequest where fromemp=:a";
			Query q=ss.createQuery(hql);
			q.setString("a", fromemp);
			List<UserAssetRequest> list=(List<UserAssetRequest>)q.list();
						ss.close();
			
			return list;
		
	}

	
	
	
	public List<UserAssetRequest> viewRequestFromOther(String toemp)
	{     
		UserAssetRequest u=null;
	    //int status=1,x=0;
			AssetController ac=new AssetController();
			Session ss= ac.session();
			String hql="from UserAssetRequest where toemp=:a";
			Query q=ss.createQuery(hql);
			q.setString("a",toemp);
			List<UserAssetRequest> list=(List<UserAssetRequest>)q.list();
			ss.close();
			return list;
		
	}



	
	
	public int insertTransferReq(UserAssetRequest u) 
	{
		int x=0;
		Date d=new Date();
		d.getTime();
		//Connection con=new LoginDao().start();
		AssetController ac=new AssetController();
		Session ss= ac.session();
		System.out.println(u);
		Transaction tt=ss.beginTransaction();
		SimpleDateFormat s=new SimpleDateFormat("dd/MM/yyyy");
		u.setRequestdate(s.format(new Date()));
			if(ss.save(u)!=null)
			{
		      x=1;	
			}	
			
			tt.commit();
			ss.close();
			
			return x;
	}
	
	
	
	
	
	
}
