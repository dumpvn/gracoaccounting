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

public abstract class BaseItemPosReportRekapReceivableCreditCard  implements Serializable {

	public static String REF = "ItemPosReportRekapReceivableCreditCard";
	public static String PROP_NAME = "Name";
	public static String PROP_BEGINNING_GROSS = "BeginningGross";
	public static String PROP_BEGINNING_NETTO = "BeginningNetto";
	public static String PROP_BEGINNING_ADM = "BeginningAdm";
	public static String PROP_TRANSACTION_GROSS = "TransactionGross";
	public static String PROP_TRANSACTION_NETTO = "TransactionNetto";
	public static String PROP_TRANSACTION_ADM = "TransactionAdm";
	public static String PROP_PAID_GROSS = "PaidGross";
	public static String PROP_PAID_NETTO = "PaidNetto";
	public static String PROP_PAID_ADM = "PaidAdm";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItemPosReportRekapReceivableCreditCard () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemPosReportRekapReceivableCreditCard (long locationId) {
		this.setLocationId(locationId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long locationId;

	// fields
	private java.lang.String name;
	private double beginningGross;
	private double beginningNetto;
	private double beginningAdm;
	private double transactionGross;
	private double transactionNetto;
	private double transactionAdm;
	private double paidGross;
	private double paidNetto;
	private double paidAdm;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="LocationId"
     */
	public long getLocationId () {
		return locationId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param locationId the new ID
	 */
	public void setLocationId (long locationId) {
		this.locationId = locationId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: Name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: Name
	 * @param name the Name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: BeginningGross
	 */
	public double getBeginningGross () {
		return beginningGross;
	}

	/**
	 * Set the value related to the column: BeginningGross
	 * @param beginningGross the BeginningGross value
	 */
	public void setBeginningGross (double beginningGross) {
		this.beginningGross = beginningGross;
	}



	/**
	 * Return the value associated with the column: BeginningNetto
	 */
	public double getBeginningNetto () {
		return beginningNetto;
	}

	/**
	 * Set the value related to the column: BeginningNetto
	 * @param beginningNetto the BeginningNetto value
	 */
	public void setBeginningNetto (double beginningNetto) {
		this.beginningNetto = beginningNetto;
	}



	/**
	 * Return the value associated with the column: BeginningAdm
	 */
	public double getBeginningAdm () {
		return beginningAdm;
	}

	/**
	 * Set the value related to the column: BeginningAdm
	 * @param beginningAdm the BeginningAdm value
	 */
	public void setBeginningAdm (double beginningAdm) {
		this.beginningAdm = beginningAdm;
	}



	/**
	 * Return the value associated with the column: TransactionGross
	 */
	public double getTransactionGross () {
		return transactionGross;
	}

	/**
	 * Set the value related to the column: TransactionGross
	 * @param transactionGross the TransactionGross value
	 */
	public void setTransactionGross (double transactionGross) {
		this.transactionGross = transactionGross;
	}



	/**
	 * Return the value associated with the column: TransactionNetto
	 */
	public double getTransactionNetto () {
		return transactionNetto;
	}

	/**
	 * Set the value related to the column: TransactionNetto
	 * @param transactionNetto the TransactionNetto value
	 */
	public void setTransactionNetto (double transactionNetto) {
		this.transactionNetto = transactionNetto;
	}



	/**
	 * Return the value associated with the column: TransactionAdm
	 */
	public double getTransactionAdm () {
		return transactionAdm;
	}

	/**
	 * Set the value related to the column: TransactionAdm
	 * @param transactionAdm the TransactionAdm value
	 */
	public void setTransactionAdm (double transactionAdm) {
		this.transactionAdm = transactionAdm;
	}



	/**
	 * Return the value associated with the column: PaidGross
	 */
	public double getPaidGross () {
		return paidGross;
	}

	/**
	 * Set the value related to the column: PaidGross
	 * @param paidGross the PaidGross value
	 */
	public void setPaidGross (double paidGross) {
		this.paidGross = paidGross;
	}



	/**
	 * Return the value associated with the column: PaidNetto
	 */
	public double getPaidNetto () {
		return paidNetto;
	}

	/**
	 * Set the value related to the column: PaidNetto
	 * @param paidNetto the PaidNetto value
	 */
	public void setPaidNetto (double paidNetto) {
		this.paidNetto = paidNetto;
	}



	/**
	 * Return the value associated with the column: PaidAdm
	 */
	public double getPaidAdm () {
		return paidAdm;
	}

	/**
	 * Set the value related to the column: PaidAdm
	 * @param paidAdm the PaidAdm value
	 */
	public void setPaidAdm (double paidAdm) {
		this.paidAdm = paidAdm;
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
		if (!(obj instanceof com.mpe.financial.model.ItemPosReportRekapReceivableCreditCard)) return false;
		else {
			com.mpe.financial.model.ItemPosReportRekapReceivableCreditCard itemPosReportRekapReceivableCreditCard = (com.mpe.financial.model.ItemPosReportRekapReceivableCreditCard) obj;
			return (this.getLocationId() == itemPosReportRekapReceivableCreditCard.getLocationId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getLocationId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}