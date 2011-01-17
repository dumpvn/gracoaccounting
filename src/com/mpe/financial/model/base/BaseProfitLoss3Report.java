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

public abstract class BaseProfitLoss3Report  implements Serializable {

	public static String REF = "ProfitLoss3Report";
	public static String PROP_NUMBER = "Number";
	public static String PROP_NAME = "Name";
	public static String PROP_TYPE = "Type";
	public static String PROP_GROUPS = "Groups";
	public static String PROP_DEBIT = "Debit";
	public static String PROP_CURRENT_AMOUNT = "CurrentAmount";
	public static String PROP_LAST_AMOUNT = "LastAmount";
	public static String PROP_YTD_AMOUNT = "YtdAmount";
	public static String PROP_FROM_TO_AMOUNT = "FromToAmount";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseProfitLoss3Report () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseProfitLoss3Report (long chartOfAccountId) {
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
	private double currentAmount;
	private double lastAmount;
	private double ytdAmount;
	private double fromToAmount;
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
	 * Return the value associated with the column: CurrentAmount
	 */
	public double getCurrentAmount () {
		return currentAmount;
	}

	/**
	 * Set the value related to the column: CurrentAmount
	 * @param currentAmount the CurrentAmount value
	 */
	public void setCurrentAmount (double currentAmount) {
		this.currentAmount = currentAmount;
	}



	/**
	 * Return the value associated with the column: LastAmount
	 */
	public double getLastAmount () {
		return lastAmount;
	}

	/**
	 * Set the value related to the column: LastAmount
	 * @param lastAmount the LastAmount value
	 */
	public void setLastAmount (double lastAmount) {
		this.lastAmount = lastAmount;
	}



	/**
	 * Return the value associated with the column: YtdAmount
	 */
	public double getYtdAmount () {
		return ytdAmount;
	}

	/**
	 * Set the value related to the column: YtdAmount
	 * @param ytdAmount the YtdAmount value
	 */
	public void setYtdAmount (double ytdAmount) {
		this.ytdAmount = ytdAmount;
	}



	/**
	 * Return the value associated with the column: FromToAmount
	 */
	public double getFromToAmount () {
		return fromToAmount;
	}

	/**
	 * Set the value related to the column: FromToAmount
	 * @param fromToAmount the FromToAmount value
	 */
	public void setFromToAmount (double fromToAmount) {
		this.fromToAmount = fromToAmount;
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
		if (!(obj instanceof com.mpe.financial.model.ProfitLoss3Report)) return false;
		else {
			com.mpe.financial.model.ProfitLoss3Report profitLoss3Report = (com.mpe.financial.model.ProfitLoss3Report) obj;
			return (this.getChartOfAccountId() == profitLoss3Report.getChartOfAccountId());
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