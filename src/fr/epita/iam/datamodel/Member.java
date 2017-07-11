package fr.epita.iam.datamodel;
/**
 * @author bharath
 * 
 * this is the class for Member data model
 *
 */
public class Member {

	private String uname;
	private String pass;
	private String mid;
	
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Member(String uname,String pass, String mid) {
		
		this.uname = uname;
		this.pass = pass;
		this.mid= mid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Member [username=" + uname + ", password=******,  mid=" + mid + "]";
	}
}
