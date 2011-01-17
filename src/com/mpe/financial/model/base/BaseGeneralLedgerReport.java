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

public abstract class BaseGeneralLedgerReport  implements Serializable {

	public static String REF = "GeneralLedgerReport";
	public static String PROP_NUMBER = "Number";
	public static String PROP_NAME = "Name";
	public static String PROP_TYPE = "Type";
	public static String PROP_GROUPS = "Groups";
	public static String PROP_DEBIT = "Debit";
	public static String PROP_FIRST_SETUP_AMOUNT = "FirstSetupAmount";
	public static String PROP_PREVIOUS_AMOUNT = "PreviousAmount";
	public static String PROP_AMOUNT = "Amount";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseGeneralLedgerReport () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGeneralLedgerReport (long chartOfAccountId) {
		this.setChartOfAccountId(chartOfAccountId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long chartOfAccountId;

	// fields
	private java.lang.String number;
	private java.lang.String name;
	private java.lang.String type;
	private java.lang.String groups;
	private boolean debit;
	private double firstSetupAmount;
	private double previousAmount;
	private double amount;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="ChartOfAccountId"
     */
	public long getChartOfAccountId () {
		return chartOfAccountId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param chartOfAccountId the new ID
	 */
	public void setChartOfAccountId (long chartOfAccountId) {
		this.chartOfAccountId = chartOfAccountId;
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
	 * Return the value associated with the column: Type
	 */
	public java.lang.String getType () {
		return type;
	}

	/**
	 * Set the value related to the column: Type
	 * @param type the Type value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
	}



	/**
	 * Return the value associated with the column: Groups
	 */
	public java.lang.String getGroups () {
		return groups;
	}

	/**
	 * Set the value related to the column: Groups
	 * @param groups the Groups value
	 */
	public void setGroups (java.lang.String groups) {
		this.groups = groups;
	}



	/**
	 * Return the value associated with the column: Debit
	 */
	public boolean isDebit () {
		return debit;
	}

	/**
	 * Set the value related to the column: Debit
	 * @param debit the Debit value
	 */
	public void setDebit (boolean debit) {
		this.debit = debit;
	}



	/**
	 * Return the value associated with the column: FirstSetupAmount
	 */
	public double getFirstSetupAmount () {
		return firstSetupAmount;
	}

	/**
	 * Set the value related to the column: FirstSetupAmount
	 * @param firstSetupAmount the FirstSetupAmount value
	 */
	public void setFirstSetupAmount (double firstSetupAmount) {
		this.firstSetupAmount = firstSetupAmount;
	}



	/**
	 * Return the value associated with the column: PreviousAmount
	 */
	public double getPreviousAmount () {
		return previousAmount;
	}

	/**
	 * Set the value related to the column: PreviousAmount
	 * @param previousAmount the PreviousAmount value
	 */
	public void setPreviousAmount (double previousAmount) {
		this.previousAmount = previousAmount;
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
		if (!(obj instanceof com.mpe.financial.model.GeneralLedgerReport)) return false;
		else {
			com.mpe.financial.model.GeneralLedgerReport generalLedgerReport = (com.mpe.financial.model.GeneralLedgerReport) obj;
			return (this.getChartOfAccountId() == generalLedgerReport.getChartOfAccountId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getChartOfAccountId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}