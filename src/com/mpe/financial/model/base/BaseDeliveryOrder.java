package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the delivery_order table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="delivery_order"
 */

public abstract class BaseDeliveryOrder  implements Serializable {

	public static String REF = "DeliveryOrder";
	public static String PROP_DELIVERY_DATE = "DeliveryDate";
	public static String PROP_NUMBER = "Number";
	public static String PROP_STATUS = "Status";
	public static String PROP_INVOICE_STATUS = "InvoiceStatus";
	public static String PROP_POSTED = "Posted";
	public static String PROP_BON_KUNING = "BonKuning";
	public static String PROP_STORE = "Store";
	public static String PROP_SHIP_TO = "ShipTo";
	public static String PROP_POLICE_NUMBER = "PoliceNumber";
	public static String PROP_VEHICLE = "Vehicle";
	public static String PROP_EXPEDITION = "Expedition";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_BILL_TO = "BillTo";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_DISCOUNT1 = "Discount1";
	public static String PROP_DISCOUNT2 = "Discount2";
	public static String PROP_DISCOUNT3 = "Discount3";
	public static String PROP_FROM_BON_KUNING_DATE = "FromBonKuningDate";
	public static String PROP_TO_BON_KUNING_DATE = "ToBonKuningDate";
	public static String PROP_DETAIL = "Detail";
	public static String PROP_REKAP = "Rekap";
	public static String PROP_TAX_AMOUNT = "TaxAmount";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseDeliveryOrder () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDeliveryOrder (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDeliveryOrder (
		long id,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Customers customer,
		java.util.Date deliveryDate,
		java.lang.String number,
		boolean posted,
		boolean bonKuning,
		boolean store,
		double exchangeRate) {

		this.setId(id);
		this.setOrganization(organization);
		this.setCustomer(customer);
		this.setDeliveryDate(deliveryDate);
		this.setNumber(number);
		this.setPosted(posted);
		this.setBonKuning(bonKuning);
		this.setStore(store);
		this.setExchangeRate(exchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date deliveryDate;
	private java.lang.String number;
	private java.lang.String status;
	private java.lang.String invoiceStatus;
	private boolean posted;
	private boolean bonKuning;
	private boolean store;
	private java.lang.String shipTo;
	private java.lang.String policeNumber;
	private java.lang.String vehicle;
	private java.lang.String expedition;
	private java.lang.String description;
	private java.lang.String billTo;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private double discount1;
	private double discount2;
	private double discount3;
	private java.util.Date fromBonKuningDate;
	private java.util.Date toBonKuningDate;
	private boolean detail;
	private boolean rekap;
	private double taxAmount;
	private double exchangeRate;
	private int numberOfDigit;

	// one to one
	private com.mpe.financial.model.CustomerRetur customerRetur;
	private com.mpe.financial.model.Journal journal;
	private com.mpe.financial.model.Invoice invoice;
	private com.mpe.financial.model.InvoiceSimple invoiceSimple;
	private com.mpe.financial.model.InvoicePolos invoicePolos;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.SalesOrder salesOrder;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Customers customer;
	private com.mpe.financial.model.Customers customerAlias;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Department department;
	private com.mpe.financial.model.Project project;
	private com.mpe.financial.model.Tax tax;

	// collections
	private java.util.Set deliveryOrderDetails;
	private java.util.Set deliveryOrders;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="delivery_order_id"
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
	 * Return the value associated with the column: is_bon_kuning
	 */
	public boolean isBonKuning () {
		return bonKuning;
	}

	/**
	 * Set the value related to the column: is_bon_kuning
	 * @param bonKuning the is_bon_kuning value
	 */
	public void setBonKuning (boolean bonKuning) {
		this.bonKuning = bonKuning;
	}



	/**
	 * Return the value associated with the column: is_store
	 */
	public boolean isStore () {
		return store;
	}

	/**
	 * Set the value related to the column: is_store
	 * @param store the is_store value
	 */
	public void setStore (boolean store) {
		this.store = store;
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
	 * Return the value associated with the column: police_number
	 */
	public java.lang.String getPoliceNumber () {
		return policeNumber;
	}

	/**
	 * Set the value related to the column: police_number
	 * @param policeNumber the police_number value
	 */
	public void setPoliceNumber (java.lang.String policeNumber) {
		this.policeNumber = policeNumber;
	}



	/**
	 * Return the value associated with the column: vehicle
	 */
	public java.lang.String getVehicle () {
		return vehicle;
	}

	/**
	 * Set the value related to the column: vehicle
	 * @param vehicle the vehicle value
	 */
	public void setVehicle (java.lang.String vehicle) {
		this.vehicle = vehicle;
	}



	/**
	 * Return the value associated with the column: expedition
	 */
	public java.lang.String getExpedition () {
		return expedition;
	}

	/**
	 * Set the value related to the column: expedition
	 * @param expedition the expedition value
	 */
	public void setExpedition (java.lang.String expedition) {
		this.expedition = expedition;
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
	 * Return the value associated with the column: from_bon_kuning_date
	 */
	public java.util.Date getFromBonKuningDate () {
		return fromBonKuningDate;
	}

	/**
	 * Set the value related to the column: from_bon_kuning_date
	 * @param fromBonKuningDate the from_bon_kuning_date value
	 */
	public void setFromBonKuningDate (java.util.Date fromBonKuningDate) {
		this.fromBonKuningDate = fromBonKuningDate;
	}



	/**
	 * Return the value associated with the column: to_bon_kuning_date
	 */
	public java.util.Date getToBonKuningDate () {
		return toBonKuningDate;
	}

	/**
	 * Set the value related to the column: to_bon_kuning_date
	 * @param toBonKuningDate the to_bon_kuning_date value
	 */
	public void setToBonKuningDate (java.util.Date toBonKuningDate) {
		this.toBonKuningDate = toBonKuningDate;
	}



	/**
	 * Return the value associated with the column: is_detail
	 */
	public boolean isDetail () {
		return detail;
	}

	/**
	 * Set the value related to the column: is_detail
	 * @param detail the is_detail value
	 */
	public void setDetail (boolean detail) {
		this.detail = detail;
	}



	/**
	 * Return the value associated with the column: is_rekap
	 */
	public boolean isRekap () {
		return rekap;
	}

	/**
	 * Set the value related to the column: is_rekap
	 * @param rekap the is_rekap value
	 */
	public void setRekap (boolean rekap) {
		this.rekap = rekap;
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
	 * Return the value associated with the column: CustomerRetur
	 */
	public com.mpe.financial.model.CustomerRetur getCustomerRetur () {
		return customerRetur;
	}

	/**
	 * Set the value related to the column: CustomerRetur
	 * @param customerRetur the CustomerRetur value
	 */
	public void setCustomerRetur (com.mpe.financial.model.CustomerRetur customerRetur) {
		this.customerRetur = customerRetur;
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
	 * Return the value associated with the column: Invoice
	 */
	public com.mpe.financial.model.Invoice getInvoice () {
		return invoice;
	}

	/**
	 * Set the value related to the column: Invoice
	 * @param invoice the Invoice value
	 */
	public void setInvoice (com.mpe.financial.model.Invoice invoice) {
		this.invoice = invoice;
	}



	/**
	 * Return the value associated with the column: InvoiceSimple
	 */
	public com.mpe.financial.model.InvoiceSimple getInvoiceSimple () {
		return invoiceSimple;
	}

	/**
	 * Set the value related to the column: InvoiceSimple
	 * @param invoiceSimple the InvoiceSimple value
	 */
	public void setInvoiceSimple (com.mpe.financial.model.InvoiceSimple invoiceSimple) {
		this.invoiceSimple = invoiceSimple;
	}



	/**
	 * Return the value associated with the column: InvoicePolos
	 */
	public com.mpe.financial.model.InvoicePolos getInvoicePolos () {
		return invoicePolos;
	}

	/**
	 * Set the value related to the column: InvoicePolos
	 * @param invoicePolos the InvoicePolos value
	 */
	public void setInvoicePolos (com.mpe.financial.model.InvoicePolos invoicePolos) {
		this.invoicePolos = invoicePolos;
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
	 * Return the value associated with the column: DeliveryOrderDetails
	 */
	public java.util.Set getDeliveryOrderDetails () {
		return deliveryOrderDetails;
	}

	/**
	 * Set the value related to the column: DeliveryOrderDetails
	 * @param deliveryOrderDetails the DeliveryOrderDetails value
	 */
	public void setDeliveryOrderDetails (java.util.Set deliveryOrderDetails) {
		this.deliveryOrderDetails = deliveryOrderDetails;
	}

	public void addToDeliveryOrderDetails (com.mpe.financial.model.DeliveryOrderDetail deliveryOrderDetail) {
		if (null == getDeliveryOrderDetails()) setDeliveryOrderDetails(new java.util.HashSet());
		getDeliveryOrderDetails().add(deliveryOrderDetail);
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





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.DeliveryOrder)) return false;
		else {
			com.mpe.financial.model.DeliveryOrder deliveryOrder = (com.mpe.financial.model.DeliveryOrder) obj;
			return (this.getId() == deliveryOrder.getId());
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