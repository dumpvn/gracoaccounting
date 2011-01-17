package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the  table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table=""
 */

public abstract class BaseRekapMutationStockReport  implements Serializable {

	public static String REF = "RekapMutationStockReport";
	public static String PROP_CODE = "Code";
	public static String PROP_NAME = "Name";
	public static String PROP_PRICE = "Price";
	public static String PROP_BEGINNING_QUANTITY = "BeginningQuantity";
	public static String PROP_BEGINNING_TOTAL = "BeginningTotal";
	public static String PROP_RECEIVING_QUANTITY = "ReceivingQuantity";
	public static String PROP_RECEIVING_TOTAL = "ReceivingTotal";
	public static String PROP_RETUR_TO_VENDOR_QUANTITY = "ReturToVendorQuantity";
	public static String PROP_RETUR_TO_VENDOR_TOTAL = "ReturToVendorTotal";
	public static String PROP_DELIVERY_ORDER_QUANTITY = "DeliveryOrderQuantity";
	public static String PROP_DELIVERY_ORDER_TOTAL = "DeliveryOrderTotal";
	public static String PROP_CUSTOMER_RETUR_QUANTITY = "CustomerReturQuantity";
	public static String PROP_CUSTOMER_RETUR_TOTAL = "CustomerReturTotal";
	public static String PROP_STOCK_OPNAME_QUANTITY = "StockOpnameQuantity";
	public static String PROP_STOCK_OPNAME_TOTAL = "StockOpnameTotal";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseRekapMutationStockReport () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRekapMutationStockReport (long itemId) {
		this.setItemId(itemId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long itemId;

	// fields
	private java.lang.String code;
	private java.lang.String name;
	private double price;
	private double beginningQuantity;
	private double beginningTotal;
	private double receivingQuantity;
	private double receivingTotal;
	private double returToVendorQuantity;
	private double returToVendorTotal;
	private double deliveryOrderQuantity;
	private double deliveryOrderTotal;
	private double customerReturQuantity;
	private double customerReturTotal;
	private double stockOpnameQuantity;
	private double stockOpnameTotal;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="ItemId"
     */
	public long getItemId () {
		return itemId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param itemId the new ID
	 */
	public void setItemId (long itemId) {
		this.itemId = itemId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: Code
	 */
	public java.lang.String getCode () {
		return code;
	}

	/**
	 * Set the value related to the column: Code
	 * @param code the Code value
	 */
	public void setCode (java.lang.String code) {
		this.code = code;
	}



	/**
	 * Return the value associated with the column: Name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: Name
	 * @param name the Name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: Price
	 */
	public double getPrice () {
		return price;
	}

	/**
	 * Set the value related to the column: Price
	 * @param price the Price value
	 */
	public void setPrice (double price) {
		this.price = price;
	}



	/**
	 * Return the value associated with the column: BeginningQuantity
	 */
	public double getBeginningQuantity () {
		return beginningQuantity;
	}

	/**
	 * Set the value related to the column: BeginningQuantity
	 * @param beginningQuantity the BeginningQuantity value
	 */
	public void setBeginningQuantity (double beginningQuantity) {
		this.beginningQuantity = beginningQuantity;
	}



	/**
	 * Return the value associated with the column: BeginningTotal
	 */
	public double getBeginningTotal () {
		return beginningTotal;
	}

	/**
	 * Set the value related to the column: BeginningTotal
	 * @param beginningTotal the BeginningTotal value
	 */
	public void setBeginningTotal (double beginningTotal) {
		this.beginningTotal = beginningTotal;
	}



	/**
	 * Return the value associated with the column: ReceivingQuantity
	 */
	public double getReceivingQuantity () {
		return receivingQuantity;
	}

	/**
	 * Set the value related to the column: ReceivingQuantity
	 * @param receivingQuantity the ReceivingQuantity value
	 */
	public void setReceivingQuantity (double receivingQuantity) {
		this.receivingQuantity = receivingQuantity;
	}



	/**
	 * Return the value associated with the column: ReceivingTotal
	 */
	public double getReceivingTotal () {
		return receivingTotal;
	}

	/**
	 * Set the value related to the column: ReceivingTotal
	 * @param receivingTotal the ReceivingTotal value
	 */
	public void setReceivingTotal (double receivingTotal) {
		this.receivingTotal = receivingTotal;
	}



	/**
	 * Return the value associated with the column: ReturToVendorQuantity
	 */
	public double getReturToVendorQuantity () {
		return returToVendorQuantity;
	}

	/**
	 * Set the value related to the column: ReturToVendorQuantity
	 * @param returToVendorQuantity the ReturToVendorQuantity value
	 */
	public void setReturToVendorQuantity (double returToVendorQuantity) {
		this.returToVendorQuantity = returToVendorQuantity;
	}



	/**
	 * Return the value associated with the column: ReturToVendorTotal
	 */
	public double getReturToVendorTotal () {
		return returToVendorTotal;
	}

	/**
	 * Set the value related to the column: ReturToVendorTotal
	 * @param returToVendorTotal the ReturToVendorTotal value
	 */
	public void setReturToVendorTotal (double returToVendorTotal) {
		this.returToVendorTotal = returToVendorTotal;
	}



	/**
	 * Return the value associated with the column: DeliveryOrderQuantity
	 */
	public double getDeliveryOrderQuantity () {
		return deliveryOrderQuantity;
	}

	/**
	 * Set the value related to the column: DeliveryOrderQuantity
	 * @param deliveryOrderQuantity the DeliveryOrderQuantity value
	 */
	public void setDeliveryOrderQuantity (double deliveryOrderQuantity) {
		this.deliveryOrderQuantity = deliveryOrderQuantity;
	}



	/**
	 * Return the value associated with the column: DeliveryOrderTotal
	 */
	public double getDeliveryOrderTotal () {
		return deliveryOrderTotal;
	}

	/**
	 * Set the value related to the column: DeliveryOrderTotal
	 * @param deliveryOrderTotal the DeliveryOrderTotal value
	 */
	public void setDeliveryOrderTotal (double deliveryOrderTotal) {
		this.deliveryOrderTotal = deliveryOrderTotal;
	}



	/**
	 * Return the value associated with the column: CustomerReturQuantity
	 */
	public double getCustomerReturQuantity () {
		return customerReturQuantity;
	}

	/**
	 * Set the value related to the column: CustomerReturQuantity
	 * @param customerReturQuantity the CustomerReturQuantity value
	 */
	public void setCustomerReturQuantity (double customerReturQuantity) {
		this.customerReturQuantity = customerReturQuantity;
	}



	/**
	 * Return the value associated with the column: CustomerReturTotal
	 */
	public double getCustomerReturTotal () {
		return customerReturTotal;
	}

	/**
	 * Set the value related to the column: CustomerReturTotal
	 * @param customerReturTotal the CustomerReturTotal value
	 */
	public void setCustomerReturTotal (double customerReturTotal) {
		this.customerReturTotal = customerReturTotal;
	}



	/**
	 * Return the value associated with the column: StockOpnameQuantity
	 */
	public double getStockOpnameQuantity () {
		return stockOpnameQuantity;
	}

	/**
	 * Set the value related to the column: StockOpnameQuantity
	 * @param stockOpnameQuantity the StockOpnameQuantity value
	 */
	public void setStockOpnameQuantity (double stockOpnameQuantity) {
		this.stockOpnameQuantity = stockOpnameQuantity;
	}



	/**
	 * Return the value associated with the column: StockOpnameTotal
	 */
	public double getStockOpnameTotal () {
		return stockOpnameTotal;
	}

	/**
	 * Set the value related to the column: StockOpnameTotal
	 * @param stockOpnameTotal the StockOpnameTotal value
	 */
	public void setStockOpnameTotal (double stockOpnameTotal) {
		this.stockOpnameTotal = stockOpnameTotal;
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





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.RekapMutationStockReport)) return false;
		else {
			com.mpe.financial.model.RekapMutationStockReport rekapMutationStockReport = (com.mpe.financial.model.RekapMutationStockReport) obj;
			return (this.getItemId() == rekapMutationStockReport.getItemId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getItemId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}