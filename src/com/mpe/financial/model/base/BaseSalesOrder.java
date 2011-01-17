package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the sales_order table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="sales_order"
 */

public abstract class BaseSalesOrder  implements Serializable {

	public static String REF = "SalesOrder";
	public static String PROP_ORDER_DATE = "OrderDate";
	public static String PROP_NUMBER = "Number";
	public static String PROP_STATUS = "Status";
	public static String PROP_DELIVERY_ORDER_STATUS = "DeliveryOrderStatus";
	public static String PROP_CUSTOMER_PAYMENT_STATUS = "CustomerPaymentStatus";
	public static String PROP_CREDIT_LIMIT = "CreditLimit";
	public static String PROP_SHIP_TO = "ShipTo";
	public static String PROP_BILL_TO = "BillTo";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_DISCOUNT_AMOUNT = "DiscountAmount";
	public static String PROP_TAX_AMOUNT = "TaxAmount";
	public static String PROP_DISCOUNT_PROCENT = "DiscountProcent";
	public static String PROP_PURCHASE_ORDER = "PurchaseOrder";
	public static String PROP_DELIVERY_DATE = "DeliveryDate";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseSalesOrder () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSalesOrder (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSalesOrder (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Customers customer,
		java.util.Date orderDate,
		java.lang.String number,
		java.lang.String status,
		java.lang.String deliveryOrderStatus,
		java.lang.String customerPaymentStatus,
		double exchangeRate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setOrganization(organization);
		this.setCustomer(customer);
		this.setOrderDate(orderDate);
		this.setNumber(number);
		this.setStatus(status);
		this.setDeliveryOrderStatus(deliveryOrderStatus);
		this.setCustomerPaymentStatus(customerPaymentStatus);
		this.setExchangeRate(exchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date orderDate;
	private java.lang.String number;
	private java.lang.String status;
	private java.lang.String deliveryOrderStatus;
	private java.lang.String customerPaymentStatus;
	private int creditLimit;
	private java.lang.String shipTo;
	private java.lang.String billTo;
	private java.lang.String description;
	private double exchangeRate;
	private double discountAmount;
	private double taxAmount;
	private double discountProcent;
	private java.lang.String purchaseOrder;
	private java.util.Date deliveryDate;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Customers customer;
	private com.mpe.financial.model.Customers customerAlias;
	private com.mpe.financial.model.Project project;
	private com.mpe.financial.model.Tax tax;
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Department department;
	private com.mpe.financial.model.Salesman salesman;
	private com.mpe.financial.model.TermOfPayment termOfPayment;

	// collections
	private java.util.Set deliveryOrders;
	private java.util.Set customerPrepayments;
	private java.util.Set salesOrderDetails;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="sales_order_id"
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
	 * Return the value associated with the column: order_date
	 */
	public java.util.Date getOrderDate () {
		return orderDate;
	}

	/**
	 * Set the value related to the column: order_date
	 * @param orderDate the order_date value
	 */
	public void setOrderDate (java.util.Date orderDate) {
		this.orderDate = orderDate;
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
	 * Return the value associated with the column: delivery_order_status
	 */
	public java.lang.String getDeliveryOrderStatus () {
		return deliveryOrderStatus;
	}

	/**
	 * Set the value related to the column: delivery_order_status
	 * @param deliveryOrderStatus the delivery_order_status value
	 */
	public void setDeliveryOrderStatus (java.lang.String deliveryOrderStatus) {
		this.deliveryOrderStatus = deliveryOrderStatus;
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
	 * Return the value associated with the column: ship_to
	 */
	public java.lang.String getShipTo () {
		return shipTo;
	}

	/**
	 * Set the value related to the column: ship_to
	 * @param shipTo the ship_to value
	 */
	public void setShipTo (java.lang.String shipTo) {
		this.shipTo = shipTo;
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
	 * Return the value associated with the column: purchase_order
	 */
	public java.lang.String getPurchaseOrder () {
		return purchaseOrder;
	}

	/**
	 * Set the value related to the column: purchase_order
	 * @param purchaseOrder the purchase_order value
	 */
	public void setPurchaseOrder (java.lang.String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}



	/**
	 * Return the value associated with the column: delivery_date
	 */
	public java.util.Date getDeliveryDate () {
		return deliveryDate;
	}

	/**
	 * Set the value related to the column: delivery_date
	 * @param deliveryDate the delivery_date value
	 */
	public void setDeliveryDate (java.util.Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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
	 * Return the value associated with the column: salesman_id
	 */
	public com.mpe.financial.model.Salesman getSalesman () {
		return salesman;
	}

	/**
	 * Set the value related to the column: salesman_id
	 * @param salesman the salesman_id value
	 */
	public void setSalesman (com.mpe.financial.model.Salesman salesman) {
		this.salesman = salesman;
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
	 * Return the value associated with the column: DeliveryOrders
	 */
	public java.util.Set getDeliveryOrders () {
		return deliveryOrders;
	}

	/**
	 * Set the value related to the column: DeliveryOrders
	 * @param deliveryOrders the DeliveryOrders value
	 */
	public void setDeliveryOrders (java.util.Set deliveryOrders) {
		this.deliveryOrders = deliveryOrders;
	}

	public void addToDeliveryOrders (com.mpe.financial.model.DeliveryOrder deliveryOrder) {
		if (null == getDeliveryOrders()) setDeliveryOrders(new java.util.HashSet());
		getDeliveryOrders().add(deliveryOrder);
	}



	/**
	 * Return the value associated with the column: CustomerPrepayments
	 */
	public java.util.Set getCustomerPrepayments () {
		return customerPrepayments;
	}

	/**
	 * Set the value related to the column: CustomerPrepayments
	 * @param customerPrepayments the CustomerPrepayments value
	 */
	public void setCustomerPrepayments (java.util.Set customerPrepayments) {
		this.customerPrepayments = customerPrepayments;
	}

	public void addToCustomerPrepayments (com.mpe.financial.model.CustomerPrepayment customerPrepayment) {
		if (null == getCustomerPrepayments()) setCustomerPrepayments(new java.util.HashSet());
		getCustomerPrepayments().add(customerPrepayment);
	}



	/**
	 * Return the value associated with the column: SalesOrderDetails
	 */
	public java.util.Set getSalesOrderDetails () {
		return salesOrderDetails;
	}

	/**
	 * Set the value related to the column: SalesOrderDetails
	 * @param salesOrderDetails the SalesOrderDetails value
	 */
	public void setSalesOrderDetails (java.util.Set salesOrderDetails) {
		this.salesOrderDetails = salesOrderDetails;
	}

	public void addToSalesOrderDetails (com.mpe.financial.model.SalesOrderDetail salesOrderDetail) {
		if (null == getSalesOrderDetails()) setSalesOrderDetails(new java.util.HashSet());
		getSalesOrderDetails().add(salesOrderDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.SalesOrder)) return false;
		else {
			com.mpe.financial.model.SalesOrder salesOrder = (com.mpe.financial.model.SalesOrder) obj;
			return (this.getId() == salesOrder.getId());
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