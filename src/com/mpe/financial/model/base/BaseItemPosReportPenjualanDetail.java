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

public abstract class BaseItemPosReportPenjualanDetail  implements Serializable {

	public static String REF = "ItemPosReportPenjualanDetail";
	public static String PROP_DATE = "Date";
	public static String PROP_NUMBER = "Number";
	public static String PROP_NOTE = "Note";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItemPosReportPenjualanDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemPosReportPenjualanDetail (long posOrderId) {
		this.setPosOrderId(posOrderId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long posOrderId;

	// fields
	private java.util.Date date;
	private java.lang.String number;
	private java.lang.String note;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="PosOrderId"
     */
	public long getPosOrderId () {
		return posOrderId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param posOrderId the new ID
	 */
	public void setPosOrderId (long posOrderId) {
		this.posOrderId = posOrderId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: Date
	 */
	public java.util.Date getDate () {
		return date;
	}

	/**
	 * Set the value related to the column: Date
	 * @param date the Date value
	 */
	public void setDate (java.util.Date date) {
		this.date = date;
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
	 * Return the value associated with the column: Note
	 */
	public java.lang.String getNote () {
		return note;
	}

	/**
	 * Set the value related to the column: Note
	 * @param note the Note value
	 */
	public void setNote (java.lang.String note) {
		this.note = note;
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
		if (!(obj instanceof com.mpe.financial.model.ItemPosReportPenjualanDetail)) return false;
		else {
			com.mpe.financial.model.ItemPosReportPenjualanDetail itemPosReportPenjualanDetail = (com.mpe.financial.model.ItemPosReportPenjualanDetail) obj;
			return (this.getPosOrderId() == itemPosReportPenjualanDetail.getPosOrderId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getPosOrderId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}