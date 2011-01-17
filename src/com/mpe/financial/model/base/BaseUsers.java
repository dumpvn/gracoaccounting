package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the users table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="users"
 */

public abstract class BaseUsers  implements Serializable {

	public static String REF = "Users";
	public static String PROP_USER_NAME = "UserName";
	public static String PROP_USER_PASS = "UserPass";
	public static String PROP_ACTIVE = "Active";
	public static String PROP_SSO = "Sso";
	public static String PROP_USER_TYPE = "UserType";
	public static String PROP_IP = "Ip";
	public static String PROP_PRINTER = "Printer";


	// constructors
	public BaseUsers () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUsers (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseUsers (
		long id,
		java.lang.String userName,
		java.lang.String userPass) {

		this.setId(id);
		this.setUserName(userName);
		this.setUserPass(userPass);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String userName;
	private java.lang.String userPass;
	private boolean active;
	private boolean sso;
	private java.lang.String userType;
	private java.lang.String ip;
	private java.lang.String printer;

	// many to one
	private com.mpe.financial.model.Organization organization;

	// collections
	private java.util.Set roles;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="user_id"
     */
	public long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: user_name
	 */
	public java.lang.String getUserName () {
		return userName;
	}

	/**
	 * Set the value related to the column: user_name
	 * @param userName the user_name value
	 */
	public void setUserName (java.lang.String userName) {
		this.userName = userName;
	}



	/**
	 * Return the value associated with the column: user_pass
	 */
	public java.lang.String getUserPass () {
		return userPass;
	}

	/**
	 * Set the value related to the column: user_pass
	 * @param userPass the user_pass value
	 */
	public void setUserPass (java.lang.String userPass) {
		this.userPass = userPass;
	}



	/**
	 * Return the value associated with the column: is_active
	 */
	public boolean isActive () {
		return active;
	}

	/**
	 * Set the value related to the column: is_active
	 * @param active the is_active value
	 */
	public void setActive (boolean active) {
		this.active = active;
	}



	/**
	 * Return the value associated with the column: is_sso
	 */
	public boolean isSso () {
		return sso;
	}

	/**
	 * Set the value related to the column: is_sso
	 * @param sso the is_sso value
	 */
	public void setSso (boolean sso) {
		this.sso = sso;
	}



	/**
	 * Return the value associated with the column: user_type
	 */
	public java.lang.String getUserType () {
		return userType;
	}

	/**
	 * Set the value related to the column: user_type
	 * @param userType the user_type value
	 */
	public void setUserType (java.lang.String userType) {
		this.userType = userType;
	}



	/**
	 * Return the value associated with the column: ip
	 */
	public java.lang.String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: ip
	 * @param ip the ip value
	 */
	public void setIp (java.lang.String ip) {
		this.ip = ip;
	}



	/**
	 * Return the value associated with the column: printer
	 */
	public java.lang.String getPrinter () {
		return printer;
	}

	/**
	 * Set the value related to the column: printer
	 * @param printer the printer value
	 */
	public void setPrinter (java.lang.String printer) {
		this.printer = printer;
	}



	/**
	 * Return the value associated with the column: organization_id
	 */
	public com.mpe.financial.model.Organization getOrganization () {
		return organization;
	}

	/**
	 * Set the value related to the column: organization_id
	 * @param organization the organization_id value
	 */
	public void setOrganization (com.mpe.financial.model.Organization organization) {
		this.organization = organization;
	}



	/**
	 * Return the value associated with the column: Roles
	 */
	public java.util.Set getRoles () {
		return roles;
	}

	/**
	 * Set the value related to the column: Roles
	 * @param roles the Roles value
	 */
	public void setRoles (java.util.Set roles) {
		this.roles = roles;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Users)) return false;
		else {
			com.mpe.financial.model.Users users = (com.mpe.financial.model.Users) obj;
			return (this.getId() == users.getId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}