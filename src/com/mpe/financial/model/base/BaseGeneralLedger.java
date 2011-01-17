package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the general_ledger table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="general_ledger"
 */

public abstract class BaseGeneralLedger  implements Serializable {

	public static String REF = "GeneralLedger";
	public static String PROP_LEDGER_DATE = "LedgerDate";
	public static String PROP_AMOUNT = "Amount";
	public static String PROP_DEBIT = "Debit";
	public static String PROP_CLOSED = "Closed";
	public static String PROP_SETUP = "Setup";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseGeneralLedger () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGeneralLedger (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseGeneralLedger (
		long id,
		java.util.Date ledgerDate,
		double amount) {

		this.setId(id);
		this.setLedgerDate(ledgerDate);
		this.setAmount(amount);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date ledgerDate;
	private double amount;
	private boolean debit;
	private boolean closed;
	private boolean setup;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.ChartOfAccount chartOfAccount;
	private com.mpe.financial.model.Organization organization;

	// collections
	private java.util.Set journals;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="general_ledger_id"
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
	 * Return the value associated with the column: ledger_date
	 */
	public java.util.Date getLedgerDate () {
		return ledgerDate;
	}

	/**
	 * Set the value related to the column: ledger_date
	 * @param ledgerDate the ledger_date value
	 */
	public void setLedgerDate (java.util.Date ledgerDate) {
		this.ledgerDate = ledgerDate;
	}



	/**
	 * Return the value associated with the column: amount
	 */
	public double getAmount () {
		return amount;
	}

	/**
	 * Set the value related to the column: amount
	 * @param amount the amount value
	 */
	public void setAmount (double amount) {
		this.amount = amount;
	}



	/**
	 * Return the value associated with the column: is_debit
	 */
	public boolean isDebit () {
		return debit;
	}

	/**
	 * Set the value related to the column: is_debit
	 * @param debit the is_debit value
	 */
	public void setDebit (boolean debit) {
		this.debit = debit;
	}



	/**
	 * Return the value associated with the column: is_closed
	 */
	public boolean isClosed () {
		return closed;
	}

	/**
	 * Set the value related to the column: is_closed
	 * @param closed the is_closed value
	 */
	public void setClosed (boolean closed) {
		this.closed = closed;
	}



	/**
	 * Return the value associated with the column: is_setup
	 */
	public boolean isSetup () {
		return setup;
	}

	/**
	 * Set the value related to the column: is_setup
	 * @param setup the is_setup value
	 */
	public void setSetup (boolean setup) {
		this.setup = setup;
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



	/**
	 * Return the value associated with the column: currency_id
	 */
	public com.mpe.financial.model.Currency getCurrency () {
		return currency;
	}

	/**
	 * Set the value related to the column: currency_id
	 * @param currency the currency_id value
	 */
	public void setCurrency (com.mpe.financial.model.Currency currency) {
		this.currency = currency;
	}



	/**
	 * Return the value associated with the column: chart_of_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getChartOfAccount () {
		return chartOfAccount;
	}

	/**
	 * Set the value related to the column: chart_of_account_id
	 * @param chartOfAccount the chart_of_account_id value
	 */
	public void setChartOfAccount (com.mpe.financial.model.ChartOfAccount chartOfAccount) {
		this.chartOfAccount = chartOfAccount;
	}



	/**
	 * Return the value associated with the column: organization_id
	 */
	public com.mpe.financial.model.Organization getOrganization () {
		return organization;
	}

	/**
	 * Set the value related to the column: organization_id
	 * @param organization the organization_id value
	 */
	public void setOrganization (com.mpe.financial.model.Organization organization) {
		this.organization = organization;
	}



	/**
	 * Return the value associated with the column: Journals
	 */
	public java.util.Set getJournals () {
		return journals;
	}

	/**
	 * Set the value related to the column: Journals
	 * @param journals the Journals value
	 */
	public void setJournals (java.util.Set journals) {
		this.journals = journals;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.GeneralLedger)) return false;
		else {
			com.mpe.financial.model.GeneralLedger generalLedger = (com.mpe.financial.model.GeneralLedger) obj;
			return (this.getId() == generalLedger.getId());
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