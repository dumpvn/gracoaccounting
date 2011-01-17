package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the customer_payment table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="customer_payment"
 */

public abstract class BaseCustomerPayment  implements Serializable {

	public static String REF = "CustomerPayment";
	public static String PROP_PAYMENT_DATE = "PaymentDate";
	public static String PROP_NUMBER = "Number";
	public static String PROP_AMOUNT = "Amount";
	public static String PROP_EXCEED_AMOUNT = "ExceedAmount";
	public static String PROP_STATUS = "Status";
	public static String PROP_POSTED = "Posted";
	public static String PROP_METHOD = "Method";
	public static String PROP_CARD_NO = "CardNo";
	public static String PROP_CHECK_NO = "CheckNo";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_REFERENCE = "Reference";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_DISCOUNT = "Discount";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseCustomerPayment () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCustomerPayment (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCustomerPayment (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Customers customer,
		com.mpe.financial.model.BankAccount bankAccount,
		java.util.Date paymentDate,
		java.lang.String number,
		double amount,
		boolean posted,
		java.lang.String method,
		double exchangeRate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setCustomer(customer);
		this.setBankAccount(bankAccount);
		this.setPaymentDate(paymentDate);
		this.setNumber(number);
		this.setAmount(amount);
		this.setPosted(posted);
		this.setMethod(method);
		this.setExchangeRate(exchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date paymentDate;
	private java.lang.String number;
	private double amount;
	private double exceedAmount;
	private java.lang.String status;
	private boolean posted;
	private java.lang.String method;
	private java.lang.String cardNo;
	private java.lang.String checkNo;
	private java.lang.String description;
	private java.lang.String reference;
	private double exchangeRate;
	private double discount;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// one to one
	private com.mpe.financial.model.Journal journal;
	private com.mpe.financial.model.BankTransaction bankTransaction;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.ChartOfAccount discountAccount;
	private com.mpe.financial.model.ChartOfAccount exceedAccount;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Customers customer;
	private com.mpe.financial.model.Customers customerAlias;
	private com.mpe.financial.model.BankAccount bankAccount;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Department department;
	private com.mpe.financial.model.Project project;

	// collections
	private java.util.Set customerPaymentDetails;
	private java.util.Set customerReturs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="customer_payment_id"
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
	 * Return the value associated with the column: payment_date
	 */
	public java.util.Date getPaymentDate () {
		return paymentDate;
	}

	/**
	 * Set the value related to the column: payment_date
	 * @param paymentDate the payment_date value
	 */
	public void setPaymentDate (java.util.Date paymentDate) {
		this.paymentDate = paymentDate;
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
	 * Return the value associated with the column: exceed_amount
	 */
	public double getExceedAmount () {
		return exceedAmount;
	}

	/**
	 * Set the value related to the column: exceed_amount
	 * @param exceedAmount the exceed_amount value
	 */
	public void setExceedAmount (double exceedAmount) {
		this.exceedAmount = exceedAmount;
	}



	/**
	 * Return the value associated with the column: status
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
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
	 * Return the value associated with the column: method
	 */
	public java.lang.String getMethod () {
		return method;
	}

	/**
	 * Set the value related to the column: method
	 * @param method the method value
	 */
	public void setMethod (java.lang.String method) {
		this.method = method;
	}



	/**
	 * Return the value associated with the column: card_no
	 */
	public java.lang.String getCardNo () {
		return cardNo;
	}

	/**
	 * Set the value related to the column: card_no
	 * @param cardNo the card_no value
	 */
	public void setCardNo (java.lang.String cardNo) {
		this.cardNo = cardNo;
	}



	/**
	 * Return the value associated with the column: check_no
	 */
	public java.lang.String getCheckNo () {
		return checkNo;
	}

	/**
	 * Set the value related to the column: check_no
	 * @param checkNo the check_no value
	 */
	public void setCheckNo (java.lang.String checkNo) {
		this.checkNo = checkNo;
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
	 * Return the value associated with the column: discount
	 */
	public double getDiscount () {
		return discount;
	}

	/**
	 * Set the value related to the column: discount
	 * @param discount the discount value
	 */
	public void setDiscount (double discount) {
		this.discount = discount;
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
	 * Return the value associated with the column: BankTransaction
	 */
	public com.mpe.financial.model.BankTransaction getBankTransaction () {
		return bankTransaction;
	}

	/**
	 * Set the value related to the column: BankTransaction
	 * @param bankTransaction the BankTransaction value
	 */
	public void setBankTransaction (com.mpe.financial.model.BankTransaction bankTransaction) {
		this.bankTransaction = bankTransaction;
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
	 * Return the value associated with the column: discount_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getDiscountAccount () {
		return discountAccount;
	}

	/**
	 * Set the value related to the column: discount_account_id
	 * @param discountAccount the discount_account_id value
	 */
	public void setDiscountAccount (com.mpe.financial.model.ChartOfAccount discountAccount) {
		this.discountAccount = discountAccount;
	}



	/**
	 * Return the value associated with the column: exceed_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getExceedAccount () {
		return exceedAccount;
	}

	/**
	 * Set the value related to the column: exceed_account_id
	 * @param exceedAccount the exceed_account_id value
	 */
	public void setExceedAccount (com.mpe.financial.model.ChartOfAccount exceedAccount) {
		this.exceedAccount = exceedAccount;
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
	 * Return the value associated with the column: customer_alias_id
	 */
	public com.mpe.financial.model.Customers getCustomerAlias () {
		return customerAlias;
	}

	/**
	 * Set the value related to the column: customer_alias_id
	 * @param customerAlias the customer_alias_id value
	 */
	public void setCustomerAlias (com.mpe.financial.model.Customers customerAlias) {
		this.customerAlias = customerAlias;
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
	 * Return the value associated with the column: location_id
	 */
	public com.mpe.financial.model.Location getLocation () {
		return location;
	}

	/**
	 * Set the value related to the column: location_id
	 * @param location the location_id value
	 */
	public void setLocation (com.mpe.financial.model.Location location) {
		this.location = location;
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
	 * Return the value associated with the column: CustomerPaymentDetails
	 */
	public java.util.Set getCustomerPaymentDetails () {
		return customerPaymentDetails;
	}

	/**
	 * Set the value related to the column: CustomerPaymentDetails
	 * @param customerPaymentDetails the CustomerPaymentDetails value
	 */
	public void setCustomerPaymentDetails (java.util.Set customerPaymentDetails) {
		this.customerPaymentDetails = customerPaymentDetails;
	}

	public void addToCustomerPaymentDetails (com.mpe.financial.model.CustomerPaymentDetail customerPaymentDetail) {
		if (null == getCustomerPaymentDetails()) setCustomerPaymentDetails(new java.util.HashSet());
		getCustomerPaymentDetails().add(customerPaymentDetail);
	}



	/**
	 * Return the value associated with the column: CustomerReturs
	 */
	public java.util.Set getCustomerReturs () {
		return customerReturs;
	}

	/**
	 * Set the value related to the column: CustomerReturs
	 * @param customerReturs the CustomerReturs value
	 */
	public void setCustomerReturs (java.util.Set customerReturs) {
		this.customerReturs = customerReturs;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CustomerPayment)) return false;
		else {
			com.mpe.financial.model.CustomerPayment customerPayment = (com.mpe.financial.model.CustomerPayment) obj;
			return (this.getId() == customerPayment.getId());
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