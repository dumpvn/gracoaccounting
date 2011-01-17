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

public abstract class BaseRekapMutationReceivableReport  implements Serializable {

	public static String REF = "RekapMutationReceivableReport";
	public static String PROP_CODE = "Code";
	public static String PROP_COMPANY = "Company";
	public static String PROP_FIRST_BALANCE = "FirstBalance";
	public static String PROP_DEBIT = "Debit";
	public static String PROP_DEBIT_POLOS = "DebitPolos";
	public static String PROP_DEBIT_SIMPLE = "DebitSimple";
	public static String PROP_CREDIT_RETUR = "CreditRetur";
	public static String PROP_CREDIT_PAYMENT = "CreditPayment";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseRekapMutationReceivableReport () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRekapMutationReceivableReport (long customerId) {
		this.setCustomerId(customerId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long customerId;

	// fields
	private java.lang.String code;
	private java.lang.String company;
	private double firstBalance;
	private double debit;
	private double debitPolos;
	private double debitSimple;
	private double creditRetur;
	private double creditPayment;
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
	 * Return the value associated with the column: Debit
	 */
	public double getDebit () {
		return debit;
	}

	/**
	 * Set the value related to the column: Debit
	 * @param debit the Debit value
	 */
	public void setDebit (double debit) {
		this.debit = debit;
	}



	/**
	 * Return the value associated with the column: DebitPolos
	 */
	public double getDebitPolos () {
		return debitPolos;
	}

	/**
	 * Set the value related to the column: DebitPolos
	 * @param debitPolos the DebitPolos value
	 */
	public void setDebitPolos (double debitPolos) {
		this.debitPolos = debitPolos;
	}



	/**
	 * Return the value associated with the column: DebitSimple
	 */
	public double getDebitSimple () {
		return debitSimple;
	}

	/**
	 * Set the value related to the column: DebitSimple
	 * @param debitSimple the DebitSimple value
	 */
	public void setDebitSimple (double debitSimple) {
		this.debitSimple = debitSimple;
	}



	/**
	 * Return the value associated with the column: CreditRetur
	 */
	public double getCreditRetur () {
		return creditRetur;
	}

	/**
	 * Set the value related to the column: CreditRetur
	 * @param creditRetur the CreditRetur value
	 */
	public void setCreditRetur (double creditRetur) {
		this.creditRetur = creditRetur;
	}



	/**
	 * Return the value associated with the column: CreditPayment
	 */
	public double getCreditPayment () {
		return creditPayment;
	}

	/**
	 * Set the value related to the column: CreditPayment
	 * @param creditPayment the CreditPayment value
	 */
	public void setCreditPayment (double creditPayment) {
		this.creditPayment = creditPayment;
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
		if (!(obj instanceof com.mpe.financial.model.RekapMutationReceivableReport)) return false;
		else {
			com.mpe.financial.model.RekapMutationReceivableReport rekapMutationReceivableReport = (com.mpe.financial.model.RekapMutationReceivableReport) obj;
			return (this.getCustomerId() == rekapMutationReceivableReport.getCustomerId());
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