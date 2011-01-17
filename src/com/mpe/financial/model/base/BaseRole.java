package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the role table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="role"
 */

public abstract class BaseRole  implements Serializable {

	public static String REF = "Role";
	public static String PROP_ROLE_NAME = "RoleName";
	public static String PROP_DESCRIPTION = "Description";


	// constructors
	public BaseRole () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRole (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseRole (
		long id,
		java.lang.String roleName) {

		this.setId(id);
		this.setRoleName(roleName);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String roleName;
	private java.lang.String description;

	// collections
	private java.util.List views;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="role_id"
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
	 * Return the value associated with the column: role_name
	 */
	public java.lang.String getRoleName () {
		return roleName;
	}

	/**
	 * Set the value related to the column: role_name
	 * @param roleName the role_name value
	 */
	public void setRoleName (java.lang.String roleName) {
		this.roleName = roleName;
	}



	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}



	/**
	 * Return the value associated with the column: Views
	 */
	public java.util.List getViews () {
		return views;
	}

	/**
	 * Set the value related to the column: Views
	 * @param views the Views value
	 */
	public void setViews (java.util.List views) {
		this.views = views;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Role)) return false;
		else {
			com.mpe.financial.model.Role role = (com.mpe.financial.model.Role) obj;
			return (this.getId() == role.getId());
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