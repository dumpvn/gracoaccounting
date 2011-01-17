package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the  table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table=""
 */

public abstract class BaseArAgingReport  implements Serializable {

	public static String REF = "ArAgingReport";
	public static String PROP_COMPANY = "Company";
	public static String PROP_ADDRESS = "Address";
	public static String PROP_CITY = "City";
	public static String PROP_POSTAL_CODE = "PostalCode";
	public static String PROP_AGING0 = "Aging0";
	public static String PROP_AGING030 = "Aging030";
	public static String PROP_AGING3060 = "Aging3060";
	public static String PROP_AGING6090 = "Aging6090";
	public static String PROP_AGING90 = "Aging90";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseArAgingReport () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseArAgingReport (long customerId) {
		this.setCustomerId(customerId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long customerId;

	// fields
	private java.lang.String company;
	private java.lang.String address;
	private java.lang.String city;
	private java.lang.String postalCode;
	private double aging0;
	private double aging030;
	private double aging3060;
	private double aging6090;
	private double aging90;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="CustomerId"
     */
	public long getCustomerId () {
		return customerId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param customerId the new ID
	 */
	public void setCustomerId (long customerId) {
		this.customerId = customerId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: Company
	 */
	public java.lang.String getCompany () {
		return company;
	}

	/**
	 * Set the value related to the column: Company
	 * @param company the Company value
	 */
	public void setCompany (java.lang.String company) {
		this.company = company;
	}



	/**
	 * Return the value associated with the column: Address
	 */
	public java.lang.String getAddress () {
		return address;
	}

	/**
	 * Set the value related to the column: Address
	 * @param address the Address value
	 */
	public void setAddress (java.lang.String address) {
		this.address = address;
	}



	/**
	 * Return the value associated with the column: City
	 */
	public java.lang.String getCity () {
		return city;
	}

	/**
	 * Set the value related to the column: City
	 * @param city the City value
	 */
	public void setCity (java.lang.String city) {
		this.city = city;
	}



	/**
	 * Return the value associated with the column: PostalCode
	 */
	public java.lang.String getPostalCode () {
		return postalCode;
	}

	/**
	 * Set the value related to the column: PostalCode
	 * @param postalCode the PostalCode value
	 */
	public void setPostalCode (java.lang.String postalCode) {
		this.postalCode = postalCode;
	}



	/**
	 * Return the value associated with the column: Aging0
	 */
	public double getAging0 () {
		return aging0;
	}

	/**
	 * Set the value related to the column: Aging0
	 * @param aging0 the Aging0 value
	 */
	public void setAging0 (double aging0) {
		this.aging0 = aging0;
	}



	/**
	 * Return the value associated with the column: Aging030
	 */
	public double getAging030 () {
		return aging030;
	}

	/**
	 * Set the value related to the column: Aging030
	 * @param aging030 the Aging030 value
	 */
	public void setAging030 (double aging030) {
		this.aging030 = aging030;
	}



	/**
	 * Return the value associated with the column: Aging3060
	 */
	public double getAging3060 () {
		return aging3060;
	}

	/**
	 * Set the value related to the column: Aging3060
	 * @param aging3060 the Aging3060 value
	 */
	public void setAging3060 (double aging3060) {
		this.aging3060 = aging3060;
	}



	/**
	 * Return the value associated with the column: Aging6090
	 */
	public double getAging6090 () {
		return aging6090;
	}

	/**
	 * Set the value related to the column: Aging6090
	 * @param aging6090 the Aging6090 value
	 */
	public void setAging6090 (double aging6090) {
		this.aging6090 = aging6090;
	}



	/**
	 * Return the value associated with the column: Aging90
	 */
	public double getAging90 () {
		return aging90;
	}

	/**
	 * Set the value related to the column: Aging90
	 * @param aging90 the Aging90 value
	 */
	public void setAging90 (double aging90) {
		this.aging90 = aging90;
	}



	/**
	 * Return the value associated with the column: NumberOfDigit
	 */
	public int getNumberOfDigit () {
		return numberOfDigit;
	}

	/**
	 * Set the value related to the column: NumberOfDigit
	 * @param numberOfDigit the NumberOfDigit value
	 */
	public void setNumberOfDigit (int numberOfDigit) {
		this.numberOfDigit = numberOfDigit;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ArAgingReport)) return false;
		else {
			com.mpe.financial.model.ArAgingReport arAgingReport = (com.mpe.financial.model.ArAgingReport) obj;
			return (this.getCustomerId() == arAgingReport.getCustomerId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getCustomerId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}