/*
 * Created on May 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.mpe.financial.model;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UserStream {
	
	long userId;
	String userName;
	String userType;
	
	public UserStream() {
	}
	
	

	/**
	 * @return
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param l
	 */
	public void setUserId(long l) {
		userId = l;
	}

	/**
	 * @param string
	 */
	public void setUserName(String string) {
		userName = string;
	}
	
	
	
	public void updateUser(Users users) {
		this.userId = users.getId();
		this.userName = users.getUserName();
		this.userType = users.getUserType();
	}

	/**
	 * @return
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param string
	 */
	public void setUserType(String string) {
		userType = string;
	}

}
