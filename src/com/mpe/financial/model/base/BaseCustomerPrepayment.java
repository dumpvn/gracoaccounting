package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the customer_prepayment table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="customer_prepayment"
 */

public abstract class BaseCustomerPrepayment  implements Serializable {

	public static String REF = "CustomerPrepayment";
	public static String PROP_PREPAYMENT_DATE = "PrepaymentDate";
	public static String PROP_NUMBER = "Number";
	public static String PROP_AMOUNT = "Amount";
	public static String PROP_STATUS = "Status";
	public static String PROP_INVOICE_STATUS = "InvoiceStatus";
	public static String PROP_REFERENCE = "Reference";
	public static String PROP_POSTED = "Posted";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseCustomerPrepayment () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCustomerPrepayment (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCustomerPrepayment (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Customers customer,
		com.mpe.financial.model.SalesOrder salesOrder,
		com.mpe.financial.model.BankAccount bankAccount,
		java.util.Date prepaymentDate,
		java.lang.String number,
		double amount,
		java.lang.String invoiceStatus,
		double exchangeRate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setOrganization(organization);
		this.setCustomer(customer);
		this.setSalesOrder(salesOrder);
		this.setBankAccount(bankAccount);
		this.setPrepaymentDate(prepaymentDate);
		this.setNumber(number);
		this.setAmount(amount);
		this.setInvoiceStatus(invoiceStatus);
		this.setExchangeRate(exchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date prepaymentDate;
	private java.lang.String number;
	private double amount;
	private java.lang.String status;
	private java.lang.String invoiceStatus;
	private java.lang.String reference;
	private boolean posted;
	private java.lang.String description;
	private double exchangeRate;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// one to one
	private com.mpe.financial.model.BankTransaction bankTransaction;
	private com.mpe.financial.model.Journal journal;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Customers customer;
	private com.mpe.financial.model.Customers customerAlias;
	private com.mpe.financial.model.SalesOrder salesOrder;
	private com.mpe.financial.model.BankAccount bankAccount;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Department department;
	private com.mpe.financial.model.Project project;

	// collections
	private java.util.Set invoices;
	private java.util.Set invoiceSimples;
	private java.util.Set invoicePolos;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="customer_prepayment_id"
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
	 * Return the value associated with the column: prepayment_date
	 */
	public java.util.Date getPrepaymentDate () {
		return prepaymentDate;
	}

	/**
	 * Set the value related to the column: prepayment_date
	 * @param prepaymentDate the prepayment_date value
	 */
	public void setPrepaymentDate (java.util.Date prepaymentDate) {
		this.prepaymentDate = prepaymentDate;
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
	 * Return the value associated with the column: invoice_status
	 */
	public java.lang.String getInvoiceStatus () {
		return invoiceStatus;
	}

	/**
	 * Set the value related to the column: invoice_status
	 * @param invoiceStatus the invoice_status value
	 */
	public void setInvoiceStatus (java.lang.String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
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
	 * Return the value associated with the column: sales_order_id
	 */
	public com.mpe.financial.model.SalesOrder getSalesOrder () {
		return salesOrder;
	}

	/**
	 * Set the value related to the column: sales_order_id
	 * @param salesOrder the sales_order_id value
	 */
	public void setSalesOrder (com.mpe.financial.model.SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
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
	 * Return the value associated with the column: Invoices
	 */
	public java.util.Set getInvoices () {
		return invoices;
	}

	/**
	 * Set the value related to the column: Invoices
	 * @param invoices the Invoices value
	 */
	public void setInvoices (java.util.Set invoices) {
		this.invoices = invoices;
	}

	public void addToInvoices (com.mpe.financial.model.InvoicePrepayment invoicePrepayment) {
		if (null == getInvoices()) setInvoices(new java.util.HashSet());
		getInvoices().add(invoicePrepayment);
	}



	/**
	 * Return the value associated with the column: InvoiceSimples
	 */
	public java.util.Set getInvoiceSimples () {
		return invoiceSimples;
	}

	/**
	 * Set the value related to the column: InvoiceSimples
	 * @param invoiceSimples the InvoiceSimples value
	 */
	public void setInvoiceSimples (java.util.Set invoiceSimples) {
		this.invoiceSimples = invoiceSimples;
	}

	public void addToInvoiceSimples (com.mpe.financial.model.InvoiceSimplePrepayment invoiceSimplePrepayment) {
		if (null == getInvoiceSimples()) setInvoiceSimples(new java.util.HashSet());
		getInvoiceSimples().add(invoiceSimplePrepayment);
	}



	/**
	 * Return the value associated with the column: InvoicePolos
	 */
	public java.util.Set getInvoicePolos () {
		return invoicePolos;
	}

	/**
	 * Set the value related to the column: InvoicePolos
	 * @param invoicePolos the InvoicePolos value
	 */
	public void setInvoicePolos (java.util.Set invoicePolos) {
		this.invoicePolos = invoicePolos;
	}

	public void addToInvoicePolos (com.mpe.financial.model.InvoicePolosPrepayment invoicePolosPrepayment) {
		if (null == getInvoicePolos()) setInvoicePolos(new java.util.HashSet());
		getInvoicePolos().add(invoicePolosPrepayment);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CustomerPrepayment)) return false;
		else {
			com.mpe.financial.model.CustomerPrepayment customerPrepayment = (com.mpe.financial.model.CustomerPrepayment) obj;
			return (this.getId() == customerPrepayment.getId());
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