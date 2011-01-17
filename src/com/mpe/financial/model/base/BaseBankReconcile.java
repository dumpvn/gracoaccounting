package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the bank_reconcile table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="bank_reconcile"
 */

public abstract class BaseBankReconcile  implements Serializable {

	public static String REF = "BankReconcile";
	public static String PROP_RECONCILE_DATE = "ReconcileDate";
	public static String PROP_BEGINNING_BALANCE = "BeginningBalance";
	public static String PROP_ENDING_BALANCE = "EndingBalance";
	public static String PROP_SERVICE_CHARGE = "ServiceCharge";
	public static String PROP_INTEREST_CHARGE = "InterestCharge";
	public static String PROP_CLEARED_BALANCE = "ClearedBalance";
	public static String PROP_DIFFERENCE = "Difference";
	public static String PROP_POSTED = "Posted";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseBankReconcile () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseBankReconcile (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseBankReconcile (
		long id,
		com.mpe.financial.model.Organization organization,
		java.util.Date reconcileDate,
		double beginningBalance,
		boolean posted) {

		this.setId(id);
		this.setOrganization(organization);
		this.setReconcileDate(reconcileDate);
		this.setBeginningBalance(beginningBalance);
		this.setPosted(posted);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date reconcileDate;
	private double beginningBalance;
	private double endingBalance;
	private double serviceCharge;
	private double interestCharge;
	private double clearedBalance;
	private double difference;
	private boolean posted;
	private java.lang.String description;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// one to one
	private com.mpe.financial.model.Journal journal;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.ChartOfAccount interestEarnedAccount;
	private com.mpe.financial.model.BankAccount bankAccount;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.ChartOfAccount serviceChargeAccount;
	private com.mpe.financial.model.ChartOfAccount differenceAccount;
	private com.mpe.financial.model.Currency currency;

	// collections
	private java.util.Set bankTransactions;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="bank_reconcile_id"
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
	 * Return the value associated with the column: reconcile_date
	 */
	public java.util.Date getReconcileDate () {
		return reconcileDate;
	}

	/**
	 * Set the value related to the column: reconcile_date
	 * @param reconcileDate the reconcile_date value
	 */
	public void setReconcileDate (java.util.Date reconcileDate) {
		this.reconcileDate = reconcileDate;
	}



	/**
	 * Return the value associated with the column: beginning_balance
	 */
	public double getBeginningBalance () {
		return beginningBalance;
	}

	/**
	 * Set the value related to the column: beginning_balance
	 * @param beginningBalance the beginning_balance value
	 */
	public void setBeginningBalance (double beginningBalance) {
		this.beginningBalance = beginningBalance;
	}



	/**
	 * Return the value associated with the column: ending_balance
	 */
	public double getEndingBalance () {
		return endingBalance;
	}

	/**
	 * Set the value related to the column: ending_balance
	 * @param endingBalance the ending_balance value
	 */
	public void setEndingBalance (double endingBalance) {
		this.endingBalance = endingBalance;
	}



	/**
	 * Return the value associated with the column: service_charge
	 */
	public double getServiceCharge () {
		return serviceCharge;
	}

	/**
	 * Set the value related to the column: service_charge
	 * @param serviceCharge the service_charge value
	 */
	public void setServiceCharge (double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}



	/**
	 * Return the value associated with the column: interest_charge
	 */
	public double getInterestCharge () {
		return interestCharge;
	}

	/**
	 * Set the value related to the column: interest_charge
	 * @param interestCharge the interest_charge value
	 */
	public void setInterestCharge (double interestCharge) {
		this.interestCharge = interestCharge;
	}



	/**
	 * Return the value associated with the column: cleared_balance
	 */
	public double getClearedBalance () {
		return clearedBalance;
	}

	/**
	 * Set the value related to the column: cleared_balance
	 * @param clearedBalance the cleared_balance value
	 */
	public void setClearedBalance (double clearedBalance) {
		this.clearedBalance = clearedBalance;
	}



	/**
	 * Return the value associated with the column: difference
	 */
	public double getDifference () {
		return difference;
	}

	/**
	 * Set the value related to the column: difference
	 * @param difference the difference value
	 */
	public void setDifference (double difference) {
		this.difference = difference;
	}



	/**
	 * Return the value associated with the column: is_posted
	 */
	public boolean isPosted () {
		return posted;
	}

	/**
	 * Set the value related to the column: is_posted
	 * @param posted the is_posted value
	 */
	public void setPosted (boolean posted) {
		this.posted = posted;
	}



	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}



	/**
	 * Return the value associated with the column: create_on
	 */
	public java.util.Date getCreateOn () {
		return createOn;
	}

	/**
	 * Set the value related to the column: create_on
	 * @param createOn the create_on value
	 */
	public void setCreateOn (java.util.Date createOn) {
		this.createOn = createOn;
	}



	/**
	 * Return the value associated with the column: change_on
	 */
	public java.util.Date getChangeOn () {
		return changeOn;
	}

	/**
	 * Set the value related to the column: change_on
	 * @param changeOn the change_on value
	 */
	public void setChangeOn (java.util.Date changeOn) {
		this.changeOn = changeOn;
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
	 * Return the value associated with the column: Journal
	 */
	public com.mpe.financial.model.Journal getJournal () {
		return journal;
	}

	/**
	 * Set the value related to the column: Journal
	 * @param journal the Journal value
	 */
	public void setJournal (com.mpe.financial.model.Journal journal) {
		this.journal = journal;
	}



	/**
	 * Return the value associated with the column: create_by
	 */
	public com.mpe.financial.model.Users getCreateBy () {
		return createBy;
	}

	/**
	 * Set the value related to the column: create_by
	 * @param createBy the create_by value
	 */
	public void setCreateBy (com.mpe.financial.model.Users createBy) {
		this.createBy = createBy;
	}



	/**
	 * Return the value associated with the column: change_by
	 */
	public com.mpe.financial.model.Users getChangeBy () {
		return changeBy;
	}

	/**
	 * Set the value related to the column: change_by
	 * @param changeBy the change_by value
	 */
	public void setChangeBy (com.mpe.financial.model.Users changeBy) {
		this.changeBy = changeBy;
	}



	/**
	 * Return the value associated with the column: interest_earned_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getInterestEarnedAccount () {
		return interestEarnedAccount;
	}

	/**
	 * Set the value related to the column: interest_earned_account_id
	 * @param interestEarnedAccount the interest_earned_account_id value
	 */
	public void setInterestEarnedAccount (com.mpe.financial.model.ChartOfAccount interestEarnedAccount) {
		this.interestEarnedAccount = interestEarnedAccount;
	}



	/**
	 * Return the value associated with the column: bank_account_id
	 */
	public com.mpe.financial.model.BankAccount getBankAccount () {
		return bankAccount;
	}

	/**
	 * Set the value related to the column: bank_account_id
	 * @param bankAccount the bank_account_id value
	 */
	public void setBankAccount (com.mpe.financial.model.BankAccount bankAccount) {
		this.bankAccount = bankAccount;
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
	 * Return the value associated with the column: service_charge_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getServiceChargeAccount () {
		return serviceChargeAccount;
	}

	/**
	 * Set the value related to the column: service_charge_account_id
	 * @param serviceChargeAccount the service_charge_account_id value
	 */
	public void setServiceChargeAccount (com.mpe.financial.model.ChartOfAccount serviceChargeAccount) {
		this.serviceChargeAccount = serviceChargeAccount;
	}



	/**
	 * Return the value associated with the column: difference_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getDifferenceAccount () {
		return differenceAccount;
	}

	/**
	 * Set the value related to the column: difference_account_id
	 * @param differenceAccount the difference_account_id value
	 */
	public void setDifferenceAccount (com.mpe.financial.model.ChartOfAccount differenceAccount) {
		this.differenceAccount = differenceAccount;
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
	 * Return the value associated with the column: BankTransactions
	 */
	public java.util.Set getBankTransactions () {
		return bankTransactions;
	}

	/**
	 * Set the value related to the column: BankTransactions
	 * @param bankTransactions the BankTransactions value
	 */
	public void setBankTransactions (java.util.Set bankTransactions) {
		this.bankTransactions = bankTransactions;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.BankReconcile)) return false;
		else {
			com.mpe.financial.model.BankReconcile bankReconcile = (com.mpe.financial.model.BankReconcile) obj;
			return (this.getId() == bankReconcile.getId());
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