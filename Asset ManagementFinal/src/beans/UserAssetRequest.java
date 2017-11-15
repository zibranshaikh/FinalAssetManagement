package beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserAssetRequest {
	@Id
	@GeneratedValue
	int requestid;
	String assetid;
	String assetname;
	String fromemp;
	String toemp;
	String mid1;
	String requestdate;
	int status;
	public int getRequestid() {
		return requestid;
	}
	public void setRequestid(int requestid) {
		this.requestid = requestid;
	}
	public String getAssetid() {
		return assetid;
	}
	public void setAssetid(String assetid) {
		this.assetid = assetid;
	}
	public String getAssetname() {
		return assetname;
	}
	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}
	public String getFromemp() {
		return fromemp;
	}
	public void setFromemp(String fromemp) {
		this.fromemp = fromemp;
	}
	public String getToemp() {
		return toemp;
	}
	public void setToemp(String toemp) {
		this.toemp = toemp;
	}
	public String getMid1() {
		return mid1;
	}
	public void setMid1(String mid1) {
		this.mid1 = mid1;
	}
	public String getRequestdate() {
		return requestdate;
	}
	public void setRequestdate(String requestdate) {
		this.requestdate = requestdate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


}
