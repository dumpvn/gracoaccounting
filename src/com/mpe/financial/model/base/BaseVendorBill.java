package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the vendor_bill table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="vendor_bill"
 */

public abstract class BaseVendorBill  implements Serializable {

	public static String REF = "VendorBill";
	public static String PROP_BILL_DATE = "BillDate";
	public static String PROP_BILL_DUE = "BillDue";
	public static String PROP_NUMBER = "Number";
	public static String PROP_VOUCHER = "Voucher";
	public static String PROP_TAX_SERIAL_NUMBER = "TaxSerialNumber";
	public static String PROP_TAX_DATE = "TaxDate";
	public static String PROP_STATUS = "Status";
	public static String PROP_PAYMENT_TO_VENDOR_STATUS = "PaymentToVendorStatus";
	public static String PROP_POSTED = "Posted";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_TAX_AMOUNT = "TaxAmount";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_DISCOUNT_AMOUNT = "DiscountAmount";
	public static String PROP_DISCOUNT_PROCENT = "DiscountProcent";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseVendorBill () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseVendorBill (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseVendorBill (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Vendors vendor,
		com.mpe.financial.model.Receiving receiving,
		java.util.Date billDate,
		java.util.Date billDue,
		java.lang.String number,
		java.lang.String status,
		java.lang.String paymentToVendorStatus,
		boolean posted,
		double exchangeRate,
		double taxAmount) {

		this.setId(id);
		this.setCurrency(currency);
		this.setOrganization(organization);
		this.setVendor(vendor);
		this.setReceiving(receiving);
		this.setBillDate(billDate);
		this.setBillDue(billDue);
		this.setNumber(number);
		this.setStatus(status);
		this.setPaymentToVendorStatus(paymentToVendorStatus);
		this.setPosted(posted);
		this.setExchangeRate(exchangeRate);
		this.setTaxAmount(taxAmount);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date billDate;
	private java.util.Date billDue;
	private java.lang.String number;
	private java.lang.String voucher;
	private java.lang.String taxSerialNumber;
	private java.util.Date taxDate;
	private java.lang.String status;
	private java.lang.String paymentToVendorStatus;
	private boolean posted;
	private java.lang.String description;
	private double exchangeRate;
	private double taxAmount;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private double discountAmount;
	private double discountProcent;
	private int numberOfDigit;

	// one to one
	private com.mpe.financial.model.Journal journal;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Vendors vendor;
	private com.mpe.financial.model.Receiving receiving;
	private com.mpe.financial.model.Tax tax;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Department department;
	private com.mpe.financial.model.Project project;

	// collections
	private java.util.Set paymentToVendors;
	private java.util.Set vendorBillDetails;
	private java.util.Set vendorBillPrepayments;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="vendor_bill_id"
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
	 * Return the value associated with the column: bill_date
	 */
	public java.util.Date getBillDate () {
		return billDate;
	}

	/**
	 * Set the value related to the column: bill_date
	 * @param billDate the bill_date value
	 */
	public void setBillDate (java.util.Date billDate) {
		this.billDate = billDate;
	}



	/**
	 * Return the value associated with the column: bill_due
	 */
	public java.util.Date getBillDue () {
		return billDue;
	}

	/**
	 * Set the value related to the column: bill_due
	 * @param billDue the bill_due value
	 */
	public void setBillDue (java.util.Date billDue) {
		this.billDue = billDue;
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
	 * Return the value associated with the column: voucher
	 */
	public java.lang.String getVoucher () {
		return voucher;
	}

	/**
	 * Set the value related to the column: voucher
	 * @param voucher the voucher value
	 */
	public void setVoucher (java.lang.String voucher) {
		this.voucher = voucher;
	}



	/**
	 * Return the value associated with the column: tax_serial_number
	 */
	public java.lang.String getTaxSerialNumber () {
		return taxSerialNumber;
	}

	/**
	 * Set the value related to the column: tax_serial_number
	 * @param taxSerialNumber the tax_serial_number value
	 */
	public void setTaxSerialNumber (java.lang.String taxSerialNumber) {
		this.taxSerialNumber = taxSerialNumber;
	}



	/**
	 * Return the value associated with the column: tax_date
	 */
	public java.util.Date getTaxDate () {
		return taxDate;
	}

	/**
	 * Set the value related to the column: tax_date
	 * @param taxDate the tax_date value
	 */
	public void setTaxDate (java.util.Date taxDate) {
		this.taxDate = taxDate;
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
	 * Return the value associated with the column: receiving_id
	 */
	public com.mpe.financial.model.Receiving getReceiving () {
		return receiving;
	}

	/**
	 * Set the value related to the column: receiving_id
	 * @param receiving the receiving_id value
	 */
	public void setReceiving (com.mpe.financial.model.Receiving receiving) {
		this.receiving = receiving;
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
	 * Return the value associated with the column: PaymentToVendors
	 */
	public java.util.Set getPaymentToVendors () {
		return paymentToVendors;
	}

	/**
	 * Set the value related to the column: PaymentToVendors
	 * @param paymentToVendors the PaymentToVendors value
	 */
	public void setPaymentToVendors (java.util.Set paymentToVendors) {
		this.paymentToVendors = paymentToVendors;
	}

	public void addToPaymentToVendors (com.mpe.financial.model.PaymentToVendorDetail paymentToVendorDetail) {
		if (null == getPaymentToVendors()) setPaymentToVendors(new java.util.HashSet());
		getPaymentToVendors().add(paymentToVendorDetail);
	}



	/**
	 * Return the value associated with the column: VendorBillDetails
	 */
	public java.util.Set getVendorBillDetails () {
		return vendorBillDetails;
	}

	/**
	 * Set the value related to the column: VendorBillDetails
	 * @param vendorBillDetails the VendorBillDetails value
	 */
	public void setVendorBillDetails (java.util.Set vendorBillDetails) {
		this.vendorBillDetails = vendorBillDetails;
	}

	public void addToVendorBillDetails (com.mpe.financial.model.VendorBillDetail vendorBillDetail) {
		if (null == getVendorBillDetails()) setVendorBillDetails(new java.util.HashSet());
		getVendorBillDetails().add(vendorBillDetail);
	}



	/**
	 * Return the value associated with the column: VendorBillPrepayments
	 */
	public java.util.Set getVendorBillPrepayments () {
		return vendorBillPrepayments;
	}

	/**
	 * Set the value related to the column: VendorBillPrepayments
	 * @param vendorBillPrepayments the VendorBillPrepayments value
	 */
	public void setVendorBillPrepayments (java.util.Set vendorBillPrepayments) {
		this.vendorBillPrepayments = vendorBillPrepayments;
	}

	public void addToVendorBillPrepayments (com.mpe.financial.model.VendorBillPrepayment vendorBillPrepayment) {
		if (null == getVendorBillPrepayments()) setVendorBillPrepayments(new java.util.HashSet());
		getVendorBillPrepayments().add(vendorBillPrepayment);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.VendorBill)) return false;
		else {
			com.mpe.financial.model.VendorBill vendorBill = (com.mpe.financial.model.VendorBill) obj;
			return (this.getId() == vendorBill.getId());
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