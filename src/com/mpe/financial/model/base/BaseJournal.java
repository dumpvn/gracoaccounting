package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the journal table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="journal"
 */

public abstract class BaseJournal  implements Serializable {

	public static String REF = "Journal";
	public static String PROP_NUMBER = "Number";
	public static String PROP_JOURNAL_DATE = "JournalDate";
	public static String PROP_REFERENCE = "Reference";
	public static String PROP_POSTED = "Posted";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseJournal () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseJournal (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseJournal (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		java.lang.String number,
		java.util.Date journalDate,
		boolean posted,
		double exchangeRate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setOrganization(organization);
		this.setNumber(number);
		this.setJournalDate(journalDate);
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
	private java.util.Date journalDate;
	private java.lang.String reference;
	private boolean posted;
	private java.lang.String description;
	private double exchangeRate;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.JournalType journalType;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Customers customer;
	private com.mpe.financial.model.Vendors vendor;
	private com.mpe.financial.model.Project project;
	private com.mpe.financial.model.StockOpname stockOpname;
	private com.mpe.financial.model.ReturToVendor returToVendor;
	private com.mpe.financial.model.DeliveryOrder deliveryOrder;
	private com.mpe.financial.model.CustomerRetur customerRetur;
	private com.mpe.financial.model.Invoice invoice;
	private com.mpe.financial.model.InvoiceSimple invoiceSimple;
	private com.mpe.financial.model.InvoicePolos invoicePolos;
	private com.mpe.financial.model.CustomerPrepayment customerPrepayment;
	private com.mpe.financial.model.CustomerPayment customerPayment;
	private com.mpe.financial.model.PrepaymentToVendor prepaymentToVendor;
	private com.mpe.financial.model.PaymentToVendor paymentToVendor;
	private com.mpe.financial.model.VendorBill vendorBill;
	private com.mpe.financial.model.BankTransaction bankTransaction;
	private com.mpe.financial.model.BankReconcile bankReconcile;

	// collections
	private java.util.Set journalDetails;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="journal_id"
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
	 * Return the value associated with the column: journal_date
	 */
	public java.util.Date getJournalDate () {
		return journalDate;
	}

	/**
	 * Set the value related to the column: journal_date
	 * @param journalDate the journal_date value
	 */
	public void setJournalDate (java.util.Date journalDate) {
		this.journalDate = journalDate;
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
	 * Return the value associated with the column: journal_type_id
	 */
	public com.mpe.financial.model.JournalType getJournalType () {
		return journalType;
	}

	/**
	 * Set the value related to the column: journal_type_id
	 * @param journalType the journal_type_id value
	 */
	public void setJournalType (com.mpe.financial.model.JournalType journalType) {
		this.journalType = journalType;
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
	 * Return the value associated with the column: stock_opname_id
	 */
	public com.mpe.financial.model.StockOpname getStockOpname () {
		return stockOpname;
	}

	/**
	 * Set the value related to the column: stock_opname_id
	 * @param stockOpname the stock_opname_id value
	 */
	public void setStockOpname (com.mpe.financial.model.StockOpname stockOpname) {
		this.stockOpname = stockOpname;
	}



	/**
	 * Return the value associated with the column: retur_to_vendor_id
	 */
	public com.mpe.financial.model.ReturToVendor getReturToVendor () {
		return returToVendor;
	}

	/**
	 * Set the value related to the column: retur_to_vendor_id
	 * @param returToVendor the retur_to_vendor_id value
	 */
	public void setReturToVendor (com.mpe.financial.model.ReturToVendor returToVendor) {
		this.returToVendor = returToVendor;
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
	 * Return the value associated with the column: customer_retur_id
	 */
	public com.mpe.financial.model.CustomerRetur getCustomerRetur () {
		return customerRetur;
	}

	/**
	 * Set the value related to the column: customer_retur_id
	 * @param customerRetur the customer_retur_id value
	 */
	public void setCustomerRetur (com.mpe.financial.model.CustomerRetur customerRetur) {
		this.customerRetur = customerRetur;
	}



	/**
	 * Return the value associated with the column: invoice_id
	 */
	public com.mpe.financial.model.Invoice getInvoice () {
		return invoice;
	}

	/**
	 * Set the value related to the column: invoice_id
	 * @param invoice the invoice_id value
	 */
	public void setInvoice (com.mpe.financial.model.Invoice invoice) {
		this.invoice = invoice;
	}



	/**
	 * Return the value associated with the column: invoice_simple_id
	 */
	public com.mpe.financial.model.InvoiceSimple getInvoiceSimple () {
		return invoiceSimple;
	}

	/**
	 * Set the value related to the column: invoice_simple_id
	 * @param invoiceSimple the invoice_simple_id value
	 */
	public void setInvoiceSimple (com.mpe.financial.model.InvoiceSimple invoiceSimple) {
		this.invoiceSimple = invoiceSimple;
	}



	/**
	 * Return the value associated with the column: invoice_polos_id
	 */
	public com.mpe.financial.model.InvoicePolos getInvoicePolos () {
		return invoicePolos;
	}

	/**
	 * Set the value related to the column: invoice_polos_id
	 * @param invoicePolos the invoice_polos_id value
	 */
	public void setInvoicePolos (com.mpe.financial.model.InvoicePolos invoicePolos) {
		this.invoicePolos = invoicePolos;
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
	 * Return the value associated with the column: vendor_bill_id
	 */
	public com.mpe.financial.model.VendorBill getVendorBill () {
		return vendorBill;
	}

	/**
	 * Set the value related to the column: vendor_bill_id
	 * @param vendorBill the vendor_bill_id value
	 */
	public void setVendorBill (com.mpe.financial.model.VendorBill vendorBill) {
		this.vendorBill = vendorBill;
	}



	/**
	 * Return the value associated with the column: bank_transaction_id
	 */
	public com.mpe.financial.model.BankTransaction getBankTransaction () {
		return bankTransaction;
	}

	/**
	 * Set the value related to the column: bank_transaction_id
	 * @param bankTransaction the bank_transaction_id value
	 */
	public void setBankTransaction (com.mpe.financial.model.BankTransaction bankTransaction) {
		this.bankTransaction = bankTransaction;
	}



	/**
	 * Return the value associated with the column: bank_reconcile_id
	 */
	public com.mpe.financial.model.BankReconcile getBankReconcile () {
		return bankReconcile;
	}

	/**
	 * Set the value related to the column: bank_reconcile_id
	 * @param bankReconcile the bank_reconcile_id value
	 */
	public void setBankReconcile (com.mpe.financial.model.BankReconcile bankReconcile) {
		this.bankReconcile = bankReconcile;
	}



	/**
	 * Return the value associated with the column: JournalDetails
	 */
	public java.util.Set getJournalDetails () {
		return journalDetails;
	}

	/**
	 * Set the value related to the column: JournalDetails
	 * @param journalDetails the JournalDetails value
	 */
	public void setJournalDetails (java.util.Set journalDetails) {
		this.journalDetails = journalDetails;
	}

	public void addToJournalDetails (com.mpe.financial.model.JournalDetail journalDetail) {
		if (null == getJournalDetails()) setJournalDetails(new java.util.HashSet());
		getJournalDetails().add(journalDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Journal)) return false;
		else {
			com.mpe.financial.model.Journal journal = (com.mpe.financial.model.Journal) obj;
			return (this.getId() == journal.getId());
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