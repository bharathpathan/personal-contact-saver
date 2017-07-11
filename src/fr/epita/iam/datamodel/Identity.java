/**
 * 
 */
package fr.epita.iam.datamodel;

import java.sql.Date;

/**
 * @author bharath
 * 
 * this is the class for Identity data model
 *
 */
public class Identity {
	
	private String displayName;
	private String uid;
	private String email;
	private Date birthdate;
	private String member_id;
	
	
	public String getMember_id() {
		return member_id;
	}


	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}


	


	/**
	 * @param displayName
	 * @param uid
	 * @param email
	 */
	public Identity(String uid,String displayName,  String email , Date birthdate, String mid) {
		
		this.displayName = displayName;
		this.uid = uid;
		
		this.email = email;
		this.birthdate=birthdate;
		this.member_id=mid;
	}
	
	
	public Date getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}


	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Identity [uid=" + uid + ", displayName=" + displayName + ",  email=" + email + ", birthdate=" + birthdate + ",  member_id=" + member_id + "]";
	}
	

	
	

}
