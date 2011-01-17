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

public abstract class BaseRekapMutationPayableReport  implements Serializable {

	public static String REF = "RekapMutationPayableReport";
	public static String PROP_CODE = "Code";
	public static String PROP_COMPANY = "Company";
	public static String PROP_FIRST_BALANCE = "FirstBalance";
	public static String PROP_DEBIT_PAYMENT = "DebitPayment";
	public static String PROP_DEBIT_RETUR = "DebitRetur";
	public static String PROP_CREDIT = "Credit";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseRekapMutationPayableReport () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRekapMutationPayableReport (long vendorId) {
		this.setVendorId(vendorId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long vendorId;

	// fields
	private java.lang.String code;
	private java.lang.String company;
	private double firstBalance;
	private double debitPayment;
	private double debitRetur;
	private double credit;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="VendorId"
     */
	public long getVendorId () {
		return vendorId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param vendorId the new ID
	 */
	public void setVendorId (long vendorId) {
		this.vendorId = vendorId;
		this.hashCode = Integer.MIN_VALUE;
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
	 * Return the value associated with the column: FirstBalance
	 */
	public double getFirstBalance () {
		return firstBalance;
	}

	/**
	 * Set the value related to the column: FirstBalance
	 * @param firstBalance the FirstBalance value
	 */
	public void setFirstBalance (double firstBalance) {
		this.firstBalance = firstBalance;
	}



	/**
	 * Return the value associated with the column: DebitPayment
	 */
	public double getDebitPayment () {
		return debitPayment;
	}

	/**
	 * Set the value related to the column: DebitPayment
	 * @param debitPayment the DebitPayment value
	 */
	public void setDebitPayment (double debitPayment) {
		this.debitPayment = debitPayment;
	}



	/**
	 * Return the value associated with the column: DebitRetur
	 */
	public double getDebitRetur () {
		return debitRetur;
	}

	/**
	 * Set the value related to the column: DebitRetur
	 * @param debitRetur the DebitRetur value
	 */
	public void setDebitRetur (double debitRetur) {
		this.debitRetur = debitRetur;
	}



	/**
	 * Return the value associated with the column: Credit
	 */
	public double getCredit () {
		return credit;
	}

	/**
	 * Set the value related to the column: Credit
	 * @param credit the Credit value
	 */
	public void setCredit (double credit) {
		this.credit = credit;
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
		if (!(obj instanceof com.mpe.financial.model.RekapMutationPayableReport)) return false;
		else {
			com.mpe.financial.model.RekapMutationPayableReport rekapMutationPayableReport = (com.mpe.financial.model.RekapMutationPayableReport) obj;
			return (this.getVendorId() == rekapMutationPayableReport.getVendorId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getVendorId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}