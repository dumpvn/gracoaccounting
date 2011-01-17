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

public abstract class BaseItemPosReportKomisiSpg  implements Serializable {

	public static String REF = "ItemPosReportKomisiSpg";
	public static String PROP_FULL_NAME = "FullName";
	public static String PROP_NICK_NAME = "NickName";
	public static String PROP_CODE = "Code";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItemPosReportKomisiSpg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemPosReportKomisiSpg (long salesmanId) {
		this.setSalesmanId(salesmanId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long salesmanId;

	// fields
	private java.lang.String fullName;
	private java.lang.String nickName;
	private java.lang.String code;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="SalesmanId"
     */
	public long getSalesmanId () {
		return salesmanId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param salesmanId the new ID
	 */
	public void setSalesmanId (long salesmanId) {
		this.salesmanId = salesmanId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: FullName
	 */
	public java.lang.String getFullName () {
		return fullName;
	}

	/**
	 * Set the value related to the column: FullName
	 * @param fullName the FullName value
	 */
	public void setFullName (java.lang.String fullName) {
		this.fullName = fullName;
	}



	/**
	 * Return the value associated with the column: NickName
	 */
	public java.lang.String getNickName () {
		return nickName;
	}

	/**
	 * Set the value related to the column: NickName
	 * @param nickName the NickName value
	 */
	public void setNickName (java.lang.String nickName) {
		this.nickName = nickName;
	}



	/**
	 * Return the value associated with the column: Code
	 */
	public java.lang.String getCode () {
		return code;
	}

	/**
	 * Set the value related to the column: Code
	 * @param code the Code value
	 */
	public void setCode (java.lang.String code) {
		this.code = code;
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
		if (!(obj instanceof com.mpe.financial.model.ItemPosReportKomisiSpg)) return false;
		else {
			com.mpe.financial.model.ItemPosReportKomisiSpg itemPosReportKomisiSpg = (com.mpe.financial.model.ItemPosReportKomisiSpg) obj;
			return (this.getSalesmanId() == itemPosReportKomisiSpg.getSalesmanId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getSalesmanId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}