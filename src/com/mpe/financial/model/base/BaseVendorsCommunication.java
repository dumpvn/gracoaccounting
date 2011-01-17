package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the vendors_communication table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="vendors_communication"
 */

public abstract class BaseVendorsCommunication  implements Serializable {

	public static String REF = "VendorsCommunication";
	public static String PROP_TITLE = "Title";
	public static String PROP_OFFICE_PHONE = "OfficePhone";
	public static String PROP_MOBILE = "Mobile";
	public static String PROP_FAX = "Fax";
	public static String PROP_EMAIL = "Email";


	// constructors
	public BaseVendorsCommunication () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseVendorsCommunication (com.mpe.financial.model.VendorsCommunicationPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.VendorsCommunicationPK id;

	// fields
	private java.lang.String title;
	private java.lang.String officePhone;
	private java.lang.String mobile;
	private java.lang.String fax;
	private java.lang.String email;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.VendorsCommunicationPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.VendorsCommunicationPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: title
	 */
	public java.lang.String getTitle () {
		return title;
	}

	/**
	 * Set the value related to the column: title
	 * @param title the title value
	 */
	public void setTitle (java.lang.String title) {
		this.title = title;
	}



	/**
	 * Return the value associated with the column: office_phone
	 */
	public java.lang.String getOfficePhone () {
		return officePhone;
	}

	/**
	 * Set the value related to the column: office_phone
	 * @param officePhone the office_phone value
	 */
	public void setOfficePhone (java.lang.String officePhone) {
		this.officePhone = officePhone;
	}



	/**
	 * Return the value associated with the column: mobile
	 */
	public java.lang.String getMobile () {
		return mobile;
	}

	/**
	 * Set the value related to the column: mobile
	 * @param mobile the mobile value
	 */
	public void setMobile (java.lang.String mobile) {
		this.mobile = mobile;
	}



	/**
	 * Return the value associated with the column: fax
	 */
	public java.lang.String getFax () {
		return fax;
	}

	/**
	 * Set the value related to the column: fax
	 * @param fax the fax value
	 */
	public void setFax (java.lang.String fax) {
		this.fax = fax;
	}



	/**
	 * Return the value associated with the column: email
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * @param email the email value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.VendorsCommunication)) return false;
		else {
			com.mpe.financial.model.VendorsCommunication vendorsCommunication = (com.mpe.financial.model.VendorsCommunication) obj;
			if (null == this.getId() || null == vendorsCommunication.getId()) return false;
			else return (this.getId().equals(vendorsCommunication.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}