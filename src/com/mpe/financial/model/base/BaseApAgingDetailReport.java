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

public abstract class BaseApAgingDetailReport  implements Serializable {

	public static String REF = "ApAgingDetailReport";
	public static String PROP_NUMBER = "Number";
	public static String PROP_BILL_DATE = "BillDate";
	public static String PROP_BILL_DUE = "BillDue";
	public static String PROP_VOUCHER = "Voucher";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_AMOUNT = "Amount";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseApAgingDetailReport () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseApAgingDetailReport (long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String number;
	private java.util.Date billDate;
	private java.util.Date billDue;
	private java.lang.String voucher;
	private java.lang.String description;
	private double amount;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="Id"
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
	 * Return the value associated with the column: Number
	 */
	public java.lang.String getNumber () {
		return number;
	}

	/**
	 * Set the value related to the column: Number
	 * @param number the Number value
	 */
	public void setNumber (java.lang.String number) {
		this.number = number;
	}



	/**
	 * Return the value associated with the column: BillDate
	 */
	public java.util.Date getBillDate () {
		return billDate;
	}

	/**
	 * Set the value related to the column: BillDate
	 * @param billDate the BillDate value
	 */
	public void setBillDate (java.util.Date billDate) {
		this.billDate = billDate;
	}



	/**
	 * Return the value associated with the column: BillDue
	 */
	public java.util.Date getBillDue () {
		return billDue;
	}

	/**
	 * Set the value related to the column: BillDue
	 * @param billDue the BillDue value
	 */
	public void setBillDue (java.util.Date billDue) {
		this.billDue = billDue;
	}



	/**
	 * Return the value associated with the column: Voucher
	 */
	public java.lang.String getVoucher () {
		return voucher;
	}

	/**
	 * Set the value related to the column: Voucher
	 * @param voucher the Voucher value
	 */
	public void setVoucher (java.lang.String voucher) {
		this.voucher = voucher;
	}



	/**
	 * Return the value associated with the column: Description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: Description
	 * @param description the Description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}



	/**
	 * Return the value associated with the column: Amount
	 */
	public double getAmount () {
		return amount;
	}

	/**
	 * Set the value related to the column: Amount
	 * @param amount the Amount value
	 */
	public void setAmount (double amount) {
		this.amount = amount;
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
		if (!(obj instanceof com.mpe.financial.model.ApAgingDetailReport)) return false;
		else {
			com.mpe.financial.model.ApAgingDetailReport apAgingDetailReport = (com.mpe.financial.model.ApAgingDetailReport) obj;
			return (this.getId() == apAgingDetailReport.getId());
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