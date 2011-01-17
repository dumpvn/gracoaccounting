package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the bank_transaction table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="bank_transaction"
 */

public abstract class BaseBankTransaction  implements Serializable {

	public static String REF = "BankTransaction";
	public static String PROP_NUMBER = "Number";
	public static String PROP_TRANSACTION_DATE = "TransactionDate";
	public static String PROP_REFERENCE = "Reference";
	public static String PROP_AMOUNT = "Amount";
	public static String PROP_ONLINE_TRANSFER = "OnlineTransfer";
	public static String PROP_TRANSFER = "Transfer";
	public static String PROP_MANUAL_TRANSACTION = "ManualTransaction";
	public static String PROP_POSTED = "Posted";
	public static String PROP_NOTE = "Note";
	public static String PROP_RECONCILE_BANK_FROM = "ReconcileBankFrom";
	public static String PROP_RECONCILE_BANK_TO = "ReconcileBankTo";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseBankTransaction () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseBankTransaction (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseBankTransaction (
		long id,
		com.mpe.financial.model.Currency currency,
		java.lang.String number,
		java.util.Date transactionDate,
		double amount,
		boolean posted,
		double exchangeRate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setNumber(number);
		this.setTransactionDate(transactionDate);
		this.setAmount(amount);
		this.setPosted(posted);
		this.setExchangeRate(exchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String number;
	private java.util.Date transactionDate;
	private java.lang.String reference;
	private double amount;
	private boolean onlineTransfer;
	private boolean transfer;
	private boolean manualTransaction;
	private boolean posted;
	private java.lang.String note;
	private boolean reconcileBankFrom;
	private boolean reconcileBankTo;
	private double exchangeRate;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// one to one
	private com.mpe.financial.model.Journal journal;

	// many to one
	private com.mpe.financial.model.PaymentToVendor paymentToVendor;
	private com.mpe.financial.model.PrepaymentToVendor prepaymentToVendor;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.BankAccount fromBankAccount;
	private com.mpe.financial.model.CustomerPrepayment customerPrepayment;
	private com.mpe.financial.model.BankAccount toBankAccount;
	private com.mpe.financial.model.Customers customer;
	private com.mpe.financial.model.Vendors vendor;
	private com.mpe.financial.model.CustomerPayment customerPayment;
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Project project;
	private com.mpe.financial.model.Department department;

	// collections
	private java.util.Set bankReconciles;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="bank_transaction_id"
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
	 * Return the value associated with the column: number
	 */
	public java.lang.String getNumber () {
		return number;
	}

	/**
	 * Set the value related to the column: number
	 * @param number the number value
	 */
	public void setNumber (java.lang.String number) {
		this.number = number;
	}



	/**
	 * Return the value associated with the column: transaction_date
	 */
	public java.util.Date getTransactionDate () {
		return transactionDate;
	}

	/**
	 * Set the value related to the column: transaction_date
	 * @param transactionDate the transaction_date value
	 */
	public void setTransactionDate (java.util.Date transactionDate) {
		this.transactionDate = transactionDate;
	}



	/**
	 * Return the value associated with the column: reference
	 */
	public java.lang.String getReference () {
		return reference;
	}

	/**
	 * Set the value related to the column: reference
	 * @param reference the reference value
	 */
	public void setReference (java.lang.String reference) {
		this.reference = reference;
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
	 * Return the value associated with the column: is_online_transfer
	 */
	public boolean isOnlineTransfer () {
		return onlineTransfer;
	}

	/**
	 * Set the value related to the column: is_online_transfer
	 * @param onlineTransfer the is_online_transfer value
	 */
	public void setOnlineTransfer (boolean onlineTransfer) {
		this.onlineTransfer = onlineTransfer;
	}



	/**
	 * Return the value associated with the column: is_transfer
	 */
	public boolean isTransfer () {
		return transfer;
	}

	/**
	 * Set the value related to the column: is_transfer
	 * @param transfer the is_transfer value
	 */
	public void setTransfer (boolean transfer) {
		this.transfer = transfer;
	}



	/**
	 * Return the value associated with the column: is_manual_transaction
	 */
	public boolean isManualTransaction () {
		return manualTransaction;
	}

	/**
	 * Set the value related to the column: is_manual_transaction
	 * @param manualTransaction the is_manual_transaction value
	 */
	public void setManualTransaction (boolean manualTransaction) {
		this.manualTransaction = manualTransaction;
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
	 * Return the value associated with the column: note
	 */
	public java.lang.String getNote () {
		return note;
	}

	/**
	 * Set the value related to the column: note
	 * @param note the note value
	 */
	public void setNote (java.lang.String note) {
		this.note = note;
	}



	/**
	 * Return the value associated with the column: reconcile_bank_from
	 */
	public boolean isReconcileBankFrom () {
		return reconcileBankFrom;
	}

	/**
	 * Set the value related to the column: reconcile_bank_from
	 * @param reconcileBankFrom the reconcile_bank_from value
	 */
	public void setReconcileBankFrom (boolean reconcileBankFrom) {
		this.reconcileBankFrom = reconcileBankFrom;
	}



	/**
	 * Return the value associated with the column: reconcile_bank_to
	 */
	public boolean isReconcileBankTo () {
		return reconcileBankTo;
	}

	/**
	 * Set the value related to the column: reconcile_bank_to
	 * @param reconcileBankTo the reconcile_bank_to value
	 */
	public void setReconcileBankTo (boolean reconcileBankTo) {
		this.reconcileBankTo = reconcileBankTo;
	}



	/**
	 * Return the value associated with the column: exchange_rate
	 */
	public double getExchangeRate () {
		return exchangeRate;
	}

	/**
	 * Set the value related to the column: exchange_rate
	 * @param exchangeRate the exchange_rate value
	 */
	public void setExchangeRate (double exchangeRate) {
		this.exchangeRate = exchangeRate;
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
	 * Return the value associated with the column: payment_to_vendor_id
	 */
	public com.mpe.financial.model.PaymentToVendor getPaymentToVendor () {
		return paymentToVendor;
	}

	/**
	 * Set the value related to the column: payment_to_vendor_id
	 * @param paymentToVendor the payment_to_vendor_id value
	 */
	public void setPaymentToVendor (com.mpe.financial.model.PaymentToVendor paymentToVendor) {
		this.paymentToVendor = paymentToVendor;
	}



	/**
	 * Return the value associated with the column: prepayment_to_vendor_id
	 */
	public com.mpe.financial.model.PrepaymentToVendor getPrepaymentToVendor () {
		return prepaymentToVendor;
	}

	/**
	 * Set the value related to the column: prepayment_to_vendor_id
	 * @param prepaymentToVendor the prepayment_to_vendor_id value
	 */
	public void setPrepaymentToVendor (com.mpe.financial.model.PrepaymentToVendor prepaymentToVendor) {
		this.prepaymentToVendor = prepaymentToVendor;
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
	 * Return the value associated with the column: from_bank_account_id
	 */
	public com.mpe.financial.model.BankAccount getFromBankAccount () {
		return fromBankAccount;
	}

	/**
	 * Set the value related to the column: from_bank_account_id
	 * @param fromBankAccount the from_bank_account_id value
	 */
	public void setFromBankAccount (com.mpe.financial.model.BankAccount fromBankAccount) {
		this.fromBankAccount = fromBankAccount;
	}



	/**
	 * Return the value associated with the column: customer_prepayment_id
	 */
	public com.mpe.financial.model.CustomerPrepayment getCustomerPrepayment () {
		return customerPrepayment;
	}

	/**
	 * Set the value related to the column: customer_prepayment_id
	 * @param customerPrepayment the customer_prepayment_id value
	 */
	public void setCustomerPrepayment (com.mpe.financial.model.CustomerPrepayment customerPrepayment) {
		this.customerPrepayment = customerPrepayment;
	}



	/**
	 * Return the value associated with the column: to_bank_account_id
	 */
	public com.mpe.financial.model.BankAccount getToBankAccount () {
		return toBankAccount;
	}

	/**
	 * Set the value related to the column: to_bank_account_id
	 * @param toBankAccount the to_bank_account_id value
	 */
	public void setToBankAccount (com.mpe.financial.model.BankAccount toBankAccount) {
		this.toBankAccount = toBankAccount;
	}



	/**
	 * Return the value associated with the column: customer_id
	 */
	public com.mpe.financial.model.Customers getCustomer () {
		return customer;
	}

	/**
	 * Set the value related to the column: customer_id
	 * @param customer the customer_id value
	 */
	public void setCustomer (com.mpe.financial.model.Customers customer) {
		this.customer = customer;
	}



	/**
	 * Return the value associated with the column: vendor_id
	 */
	public com.mpe.financial.model.Vendors getVendor () {
		return vendor;
	}

	/**
	 * Set the value related to the column: vendor_id
	 * @param vendor the vendor_id value
	 */
	public void setVendor (com.mpe.financial.model.Vendors vendor) {
		this.vendor = vendor;
	}



	/**
	 * Return the value associated with the column: customer_payment_id
	 */
	public com.mpe.financial.model.CustomerPayment getCustomerPayment () {
		return customerPayment;
	}

	/**
	 * Set the value related to the column: customer_payment_id
	 * @param customerPayment the customer_payment_id value
	 */
	public void setCustomerPayment (com.mpe.financial.model.CustomerPayment customerPayment) {
		this.customerPayment = customerPayment;
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
	 * Return the value associated with the column: project_id
	 */
	public com.mpe.financial.model.Project getProject () {
		return project;
	}

	/**
	 * Set the value related to the column: project_id
	 * @param project the project_id value
	 */
	public void setProject (com.mpe.financial.model.Project project) {
		this.project = project;
	}



	/**
	 * Return the value associated with the column: department_id
	 */
	public com.mpe.financial.model.Department getDepartment () {
		return department;
	}

	/**
	 * Set the value related to the column: department_id
	 * @param department the department_id value
	 */
	public void setDepartment (com.mpe.financial.model.Department department) {
		this.department = department;
	}



	/**
	 * Return the value associated with the column: BankReconciles
	 */
	public java.util.Set getBankReconciles () {
		return bankReconciles;
	}

	/**
	 * Set the value related to the column: BankReconciles
	 * @param bankReconciles the BankReconciles value
	 */
	public void setBankReconciles (java.util.Set bankReconciles) {
		this.bankReconciles = bankReconciles;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.BankTransaction)) return false;
		else {
			com.mpe.financial.model.BankTransaction bankTransaction = (com.mpe.financial.model.BankTransaction) obj;
			return (this.getId() == bankTransaction.getId());
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