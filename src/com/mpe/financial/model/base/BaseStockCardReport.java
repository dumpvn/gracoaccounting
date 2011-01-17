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

public abstract class BaseStockCardReport  implements Serializable {

	public static String REF = "StockCardReport";
	public static String PROP_CODE = "Code";
	public static String PROP_NAME = "Name";
	public static String PROP_PRICE = "Price";
	public static String PROP_QUANTITY = "Quantity";
	public static String PROP_NUMBER = "Number";
	public static String PROP_STOCK_CARD_DATE = "StockCardDate";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseStockCardReport () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseStockCardReport (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String code;
	private java.lang.String name;
	private double price;
	private int quantity;
	private java.lang.String number;
	private java.util.Date stockCardDate;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="Id"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
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
	 * Return the value associated with the column: Quantity
	 */
	public int getQuantity () {
		return quantity;
	}

	/**
	 * Set the value related to the column: Quantity
	 * @param quantity the Quantity value
	 */
	public void setQuantity (int quantity) {
		this.quantity = quantity;
	}



	/**
	 * Return the value associated with the column: Number
	 */
	public java.lang.String getNumber () {
		return number;
	}

	/**
	 * Set the value related to the column: Number
	 * @param number the Number value
	 */
	public void setNumber (java.lang.String number) {
		this.number = number;
	}



	/**
	 * Return the value associated with the column: StockCardDate
	 */
	public java.util.Date getStockCardDate () {
		return stockCardDate;
	}

	/**
	 * Set the value related to the column: StockCardDate
	 * @param stockCardDate the StockCardDate value
	 */
	public void setStockCardDate (java.util.Date stockCardDate) {
		this.stockCardDate = stockCardDate;
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
		if (!(obj instanceof com.mpe.financial.model.StockCardReport)) return false;
		else {
			com.mpe.financial.model.StockCardReport stockCardReport = (com.mpe.financial.model.StockCardReport) obj;
			if (null == this.getId() || null == stockCardReport.getId()) return false;
			else return (this.getId().equals(stockCardReport.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}