package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the invoice table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="invoice"
 */

public abstract class BaseInvoice  implements Serializable {

	public static String REF = "Invoice";
	public static String PROP_INVOICE_DATE = "InvoiceDate";
	public static String PROP_INVOICE_DUE = "InvoiceDue";
	public static String PROP_NUMBER = "Number";
	public static String PROP_STATUS = "Status";
	public static String PROP_CUSTOMER_PAYMENT_STATUS = "CustomerPaymentStatus";
	public static String PROP_POSTED = "Posted";
	public static String PROP_BILL_TO = "BillTo";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_NPWP_NUMBER = "NpwpNumber";
	public static String PROP_NPWP_DATE = "NpwpDate";
	public static String PROP_REFERENCE = "Reference";
	public static String PROP_TAX_AMOUNT = "TaxAmount";
	public static String PROP_DISCOUNT_PROCENT = "DiscountProcent";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_DISCOUNT1 = "Discount1";
	public static String PROP_DISCOUNT2 = "Discount2";
	public static String PROP_DISCOUNT3 = "Discount3";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseInvoice () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInvoice (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInvoice (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Customers customer,
		com.mpe.financial.model.DeliveryOrder deliveryOrder,
		java.util.Date invoiceDate,
		java.util.Date invoiceDue,
		java.lang.String number,
		boolean posted,
		double taxAmount,
		double discountProcent) {

		this.setId(id);
		this.setCurrency(currency);
		this.setCustomer(customer);
		this.setDeliveryOrder(deliveryOrder);
		this.setInvoiceDate(invoiceDate);
		this.setInvoiceDue(invoiceDue);
		this.setNumber(number);
		this.setPosted(posted);
		this.setTaxAmount(taxAmount);
		this.setDiscountProcent(discountProcent);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date invoiceDate;
	private java.util.Date invoiceDue;
	private java.lang.String number;
	private java.lang.String status;
	private java.lang.String customerPaymentStatus;
	private boolean posted;
	private java.lang.String billTo;
	private java.lang.String description;
	private double exchangeRate;
	private java.lang.String npwpNumber;
	private java.util.Date npwpDate;
	private java.lang.String reference;
	private double taxAmount;
	private double discountProcent;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private double discount1;
	private double discount2;
	private double discount3;
	private int numberOfDigit;

	// one to one
	private com.mpe.financial.model.Journal journal;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.BankAccount bankAccount;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Customers customer;
	private com.mpe.financial.model.Customers customerAlias;
	private com.mpe.financial.model.Tax tax;
	private com.mpe.financial.model.DeliveryOrder deliveryOrder;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Department department;
	private com.mpe.financial.model.Project project;

	// collections
	private java.util.Set invoiceDetails;
	private java.util.Set invoicePrepayments;
	private java.util.Set customerPayments;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="invoice_id"
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
	 * Return the value associated with the column: invoice_date
	 */
	public java.util.Date getInvoiceDate () {
		return invoiceDate;
	}

	/**
	 * Set the value related to the column: invoice_date
	 * @param invoiceDate the invoice_date value
	 */
	public void setInvoiceDate (java.util.Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}



	/**
	 * Return the value associated with the column: invoice_due
	 */
	public java.util.Date getInvoiceDue () {
		return invoiceDue;
	}

	/**
	 * Set the value related to the column: invoice_due
	 * @param invoiceDue the invoice_due value
	 */
	public void setInvoiceDue (java.util.Date invoiceDue) {
		this.invoiceDue = invoiceDue;
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
	 * Return the value associated with the column: customer_payment_status
	 */
	public java.lang.String getCustomerPaymentStatus () {
		return customerPaymentStatus;
	}

	/**
	 * Set the value related to the column: customer_payment_status
	 * @param customerPaymentStatus the customer_payment_status value
	 */
	public void setCustomerPaymentStatus (java.lang.String customerPaymentStatus) {
		this.customerPaymentStatus = customerPaymentStatus;
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
	 * Return the value associated with the column: bill_to
	 */
	public java.lang.String getBillTo () {
		return billTo;
	}

	/**
	 * Set the value related to the column: bill_to
	 * @param billTo the bill_to value
	 */
	public void setBillTo (java.lang.String billTo) {
		this.billTo = billTo;
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
	 * Return the value associated with the column: npwp_number
	 */
	public java.lang.String getNpwpNumber () {
		return npwpNumber;
	}

	/**
	 * Set the value related to the column: npwp_number
	 * @param npwpNumber the npwp_number value
	 */
	public void setNpwpNumber (java.lang.String npwpNumber) {
		this.npwpNumber = npwpNumber;
	}



	/**
	 * Return the value associated with the column: npwp_date
	 */
	public java.util.Date getNpwpDate () {
		return npwpDate;
	}

	/**
	 * Set the value related to the column: npwp_date
	 * @param npwpDate the npwp_date value
	 */
	public void setNpwpDate (java.util.Date npwpDate) {
		this.npwpDate = npwpDate;
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
	 * Return the value associated with the column: tax_amount
	 */
	public double getTaxAmount () {
		return taxAmount;
	}

	/**
	 * Set the value related to the column: tax_amount
	 * @param taxAmount the tax_amount value
	 */
	public void setTaxAmount (double taxAmount) {
		this.taxAmount = taxAmount;
	}



	/**
	 * Return the value associated with the column: discount_procent
	 */
	public double getDiscountProcent () {
		return discountProcent;
	}

	/**
	 * Set the value related to the column: discount_procent
	 * @param discountProcent the discount_procent value
	 */
	public void setDiscountProcent (double discountProcent) {
		this.discountProcent = discountProcent;
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
	 * Return the value associated with the column: discount_1
	 */
	public double getDiscount1 () {
		return discount1;
	}

	/**
	 * Set the value related to the column: discount_1
	 * @param discount1 the discount_1 value
	 */
	public void setDiscount1 (double discount1) {
		this.discount1 = discount1;
	}



	/**
	 * Return the value associated with the column: discount_2
	 */
	public double getDiscount2 () {
		return discount2;
	}

	/**
	 * Set the value related to the column: discount_2
	 * @param discount2 the discount_2 value
	 */
	public void setDiscount2 (double discount2) {
		this.discount2 = discount2;
	}



	/**
	 * Return the value associated with the column: discount_3
	 */
	public double getDiscount3 () {
		return discount3;
	}

	/**
	 * Set the value related to the column: discount_3
	 * @param discount3 the discount_3 value
	 */
	public void setDiscount3 (double discount3) {
		this.discount3 = discount3;
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
	 * Return the value associated with the column: tax_id
	 */
	public com.mpe.financial.model.Tax getTax () {
		return tax;
	}

	/**
	 * Set the value related to the column: tax_id
	 * @param tax the tax_id value
	 */
	public void setTax (com.mpe.financial.model.Tax tax) {
		this.tax = tax;
	}



	/**
	 * Return the value associated with the column: delivery_order_id
	 */
	public com.mpe.financial.model.DeliveryOrder getDeliveryOrder () {
		return deliveryOrder;
	}

	/**
	 * Set the value related to the column: delivery_order_id
	 * @param deliveryOrder the delivery_order_id value
	 */
	public void setDeliveryOrder (com.mpe.financial.model.DeliveryOrder deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
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
	 * Return the value associated with the column: InvoiceDetails
	 */
	public java.util.Set getInvoiceDetails () {
		return invoiceDetails;
	}

	/**
	 * Set the value related to the column: InvoiceDetails
	 * @param invoiceDetails the InvoiceDetails value
	 */
	public void setInvoiceDetails (java.util.Set invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public void addToInvoiceDetails (com.mpe.financial.model.InvoiceDetail invoiceDetail) {
		if (null == getInvoiceDetails()) setInvoiceDetails(new java.util.HashSet());
		getInvoiceDetails().add(invoiceDetail);
	}



	/**
	 * Return the value associated with the column: InvoicePrepayments
	 */
	public java.util.Set getInvoicePrepayments () {
		return invoicePrepayments;
	}

	/**
	 * Set the value related to the column: InvoicePrepayments
	 * @param invoicePrepayments the InvoicePrepayments value
	 */
	public void setInvoicePrepayments (java.util.Set invoicePrepayments) {
		this.invoicePrepayments = invoicePrepayments;
	}

	public void addToInvoicePrepayments (com.mpe.financial.model.InvoicePrepayment invoicePrepayment) {
		if (null == getInvoicePrepayments()) setInvoicePrepayments(new java.util.HashSet());
		getInvoicePrepayments().add(invoicePrepayment);
	}



	/**
	 * Return the value associated with the column: CustomerPayments
	 */
	public java.util.Set getCustomerPayments () {
		return customerPayments;
	}

	/**
	 * Set the value related to the column: CustomerPayments
	 * @param customerPayments the CustomerPayments value
	 */
	public void setCustomerPayments (java.util.Set customerPayments) {
		this.customerPayments = customerPayments;
	}

	public void addToCustomerPayments (com.mpe.financial.model.CustomerPaymentDetail customerPaymentDetail) {
		if (null == getCustomerPayments()) setCustomerPayments(new java.util.HashSet());
		getCustomerPayments().add(customerPaymentDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Invoice)) return false;
		else {
			com.mpe.financial.model.Invoice invoice = (com.mpe.financial.model.Invoice) obj;
			return (this.getId() == invoice.getId());
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