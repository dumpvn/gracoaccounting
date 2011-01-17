package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the customers_address table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="customers_address"
 */

public abstract class BaseCustomersAddress  implements Serializable {

	public static String REF = "CustomersAddress";
	public static String PROP_CODE = "Code";
	public static String PROP_ADDRESS = "Address";
	public static String PROP_CITY = "City";
	public static String PROP_POSTAL_CODE = "PostalCode";


	// constructors
	public BaseCustomersAddress () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCustomersAddress (com.mpe.financial.model.CustomersAddressPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCustomersAddress (
		com.mpe.financial.model.CustomersAddressPK id,
		java.lang.String code,
		java.lang.String address) {

		this.setId(id);
		this.setCode(code);
		this.setAddress(address);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.CustomersAddressPK id;

	// fields
	private java.lang.String code;
	private java.lang.String address;
	private java.lang.String city;
	private java.lang.String postalCode;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.CustomersAddressPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.CustomersAddressPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: code
	 */
	public java.lang.String getCode () {
		return code;
	}

	/**
	 * Set the value related to the column: code
	 * @param code the code value
	 */
	public void setCode (java.lang.String code) {
		this.code = code;
	}



	/**
	 * Return the value associated with the column: address
	 */
	public java.lang.String getAddress () {
		return address;
	}

	/**
	 * Set the value related to the column: address
	 * @param address the address value
	 */
	public void setAddress (java.lang.String address) {
		this.address = address;
	}



	/**
	 * Return the value associated with the column: city
	 */
	public java.lang.String getCity () {
		return city;
	}

	/**
	 * Set the value related to the column: city
	 * @param city the city value
	 */
	public void setCity (java.lang.String city) {
		this.city = city;
	}



	/**
	 * Return the value associated with the column: postal_code
	 */
	public java.lang.String getPostalCode () {
		return postalCode;
	}

	/**
	 * Set the value related to the column: postal_code
	 * @param postalCode the postal_code value
	 */
	public void setPostalCode (java.lang.String postalCode) {
		this.postalCode = postalCode;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CustomersAddress)) return false;
		else {
			com.mpe.financial.model.CustomersAddress customersAddress = (com.mpe.financial.model.CustomersAddress) obj;
			if (null == this.getId() || null == customersAddress.getId()) return false;
			else return (this.getId().equals(customersAddress.getId()));
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