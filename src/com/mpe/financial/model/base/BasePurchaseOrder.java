package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the purchase_order table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="purchase_order"
 */

public abstract class BasePurchaseOrder  implements Serializable {

	public static String REF = "PurchaseOrder";
	public static String PROP_PURCHASE_DATE = "PurchaseDate";
	public static String PROP_NUMBER = "Number";
	public static String PROP_STATUS = "Status";
	public static String PROP_RECEIVING_STATUS = "ReceivingStatus";
	public static String PROP_PAYMENT_TO_VENDOR_STATUS = "PaymentToVendorStatus";
	public static String PROP_CREDIT_LIMIT = "CreditLimit";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_TAX_AMOUNT = "TaxAmount";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_DISCOUNT_AMOUNT = "DiscountAmount";
	public static String PROP_DISCOUNT_PROCENT = "DiscountProcent";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BasePurchaseOrder () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePurchaseOrder (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BasePurchaseOrder (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Vendors vendor,
		java.util.Date purchaseDate,
		java.lang.String number,
		java.lang.String status,
		java.lang.String receivingStatus,
		java.lang.String paymentToVendorStatus) {

		this.setId(id);
		this.setCurrency(currency);
		this.setOrganization(organization);
		this.setVendor(vendor);
		this.setPurchaseDate(purchaseDate);
		this.setNumber(number);
		this.setStatus(status);
		this.setReceivingStatus(receivingStatus);
		this.setPaymentToVendorStatus(paymentToVendorStatus);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date purchaseDate;
	private java.lang.String number;
	private java.lang.String status;
	private java.lang.String receivingStatus;
	private java.lang.String paymentToVendorStatus;
	private int creditLimit;
	private java.lang.String description;
	private double exchangeRate;
	private double taxAmount;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private double discountAmount;
	private double discountProcent;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Vendors vendor;
	private com.mpe.financial.model.Project project;
	private com.mpe.financial.model.Tax tax;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Department department;
	private com.mpe.financial.model.PurchaseRequest purchaseRequest;
	private com.mpe.financial.model.TermOfPayment termOfPayment;

	// collections
	private java.util.Set receivings;
	private java.util.Set prepaymentToVendors;
	private java.util.Set purchaseOrderDetails;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="purchase_order_id"
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
	 * Return the value associated with the column: purchase_date
	 */
	public java.util.Date getPurchaseDate () {
		return purchaseDate;
	}

	/**
	 * Set the value related to the column: purchase_date
	 * @param purchaseDate the purchase_date value
	 */
	public void setPurchaseDate (java.util.Date purchaseDate) {
		this.purchaseDate = purchaseDate;
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
	 * Return the value associated with the column: receiving_status
	 */
	public java.lang.String getReceivingStatus () {
		return receivingStatus;
	}

	/**
	 * Set the value related to the column: receiving_status
	 * @param receivingStatus the receiving_status value
	 */
	public void setReceivingStatus (java.lang.String receivingStatus) {
		this.receivingStatus = receivingStatus;
	}



	/**
	 * Return the value associated with the column: payment_to_vendor_status
	 */
	public java.lang.String getPaymentToVendorStatus () {
		return paymentToVendorStatus;
	}

	/**
	 * Set the value related to the column: payment_to_vendor_status
	 * @param paymentToVendorStatus the payment_to_vendor_status value
	 */
	public void setPaymentToVendorStatus (java.lang.String paymentToVendorStatus) {
		this.paymentToVendorStatus = paymentToVendorStatus;
	}



	/**
	 * Return the value associated with the column: credit_limit
	 */
	public int getCreditLimit () {
		return creditLimit;
	}

	/**
	 * Set the value related to the column: credit_limit
	 * @param creditLimit the credit_limit value
	 */
	public void setCreditLimit (int creditLimit) {
		this.creditLimit = creditLimit;
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
	 * Return the value associated with the column: discount_amount
	 */
	public double getDiscountAmount () {
		return discountAmount;
	}

	/**
	 * Set the value related to the column: discount_amount
	 * @param discountAmount the discount_amount value
	 */
	public void setDiscountAmount (double discountAmount) {
		this.discountAmount = discountAmount;
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
	 * Return the value associated with the column: purchase_request_id
	 */
	public com.mpe.financial.model.PurchaseRequest getPurchaseRequest () {
		return purchaseRequest;
	}

	/**
	 * Set the value related to the column: purchase_request_id
	 * @param purchaseRequest the purchase_request_id value
	 */
	public void setPurchaseRequest (com.mpe.financial.model.PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}



	/**
	 * Return the value associated with the column: term_of_payment_id
	 */
	public com.mpe.financial.model.TermOfPayment getTermOfPayment () {
		return termOfPayment;
	}

	/**
	 * Set the value related to the column: term_of_payment_id
	 * @param termOfPayment the term_of_payment_id value
	 */
	public void setTermOfPayment (com.mpe.financial.model.TermOfPayment termOfPayment) {
		this.termOfPayment = termOfPayment;
	}



	/**
	 * Return the value associated with the column: Receivings
	 */
	public java.util.Set getReceivings () {
		return receivings;
	}

	/**
	 * Set the value related to the column: Receivings
	 * @param receivings the Receivings value
	 */
	public void setReceivings (java.util.Set receivings) {
		this.receivings = receivings;
	}

	public void addToReceivings (com.mpe.financial.model.Receiving receiving) {
		if (null == getReceivings()) setReceivings(new java.util.HashSet());
		getReceivings().add(receiving);
	}



	/**
	 * Return the value associated with the column: PrepaymentToVendors
	 */
	public java.util.Set getPrepaymentToVendors () {
		return prepaymentToVendors;
	}

	/**
	 * Set the value related to the column: PrepaymentToVendors
	 * @param prepaymentToVendors the PrepaymentToVendors value
	 */
	public void setPrepaymentToVendors (java.util.Set prepaymentToVendors) {
		this.prepaymentToVendors = prepaymentToVendors;
	}

	public void addToPrepaymentToVendors (com.mpe.financial.model.PrepaymentToVendor prepaymentToVendor) {
		if (null == getPrepaymentToVendors()) setPrepaymentToVendors(new java.util.HashSet());
		getPrepaymentToVendors().add(prepaymentToVendor);
	}



	/**
	 * Return the value associated with the column: PurchaseOrderDetails
	 */
	public java.util.Set getPurchaseOrderDetails () {
		return purchaseOrderDetails;
	}

	/**
	 * Set the value related to the column: PurchaseOrderDetails
	 * @param purchaseOrderDetails the PurchaseOrderDetails value
	 */
	public void setPurchaseOrderDetails (java.util.Set purchaseOrderDetails) {
		this.purchaseOrderDetails = purchaseOrderDetails;
	}

	public void addToPurchaseOrderDetails (com.mpe.financial.model.PurchaseOrderDetail purchaseOrderDetail) {
		if (null == getPurchaseOrderDetails()) setPurchaseOrderDetails(new java.util.HashSet());
		getPurchaseOrderDetails().add(purchaseOrderDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.PurchaseOrder)) return false;
		else {
			com.mpe.financial.model.PurchaseOrder purchaseOrder = (com.mpe.financial.model.PurchaseOrder) obj;
			return (this.getId() == purchaseOrder.getId());
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