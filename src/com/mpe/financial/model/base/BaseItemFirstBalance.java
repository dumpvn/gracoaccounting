package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the inventory_first_balance table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="inventory_first_balance"
 */

public abstract class BaseItemFirstBalance  implements Serializable {

	public static String REF = "ItemFirstBalance";
	public static String PROP_FIRST_BALANCE_DATE = "FirstBalanceDate";
	public static String PROP_PRICE = "Price";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_QUANTITY = "Quantity";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItemFirstBalance () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemFirstBalance (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseItemFirstBalance (
		long id,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Item item,
		java.util.Date firstBalanceDate,
		double quantity) {

		this.setId(id);
		this.setOrganization(organization);
		this.setItem(item);
		this.setFirstBalanceDate(firstBalanceDate);
		this.setQuantity(quantity);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date firstBalanceDate;
	private double price;
	private double exchangeRate;
	private double quantity;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Customers customer;
	private com.mpe.financial.model.Item item;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Warehouse warehouse;
	private com.mpe.financial.model.ItemUnit itemUnit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="inventory_first_balance_id"
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
	 * Return the value associated with the column: first_balance_date
	 */
	public java.util.Date getFirstBalanceDate () {
		return firstBalanceDate;
	}

	/**
	 * Set the value related to the column: first_balance_date
	 * @param firstBalanceDate the first_balance_date value
	 */
	public void setFirstBalanceDate (java.util.Date firstBalanceDate) {
		this.firstBalanceDate = firstBalanceDate;
	}



	/**
	 * Return the value associated with the column: price
	 */
	public double getPrice () {
		return price;
	}

	/**
	 * Set the value related to the column: price
	 * @param price the price value
	 */
	public void setPrice (double price) {
		this.price = price;
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
	 * Return the value associated with the column: quantity
	 */
	public double getQuantity () {
		return quantity;
	}

	/**
	 * Set the value related to the column: quantity
	 * @param quantity the quantity value
	 */
	public void setQuantity (double quantity) {
		this.quantity = quantity;
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
	 * Return the value associated with the column: item_id
	 */
	public com.mpe.financial.model.Item getItem () {
		return item;
	}

	/**
	 * Set the value related to the column: item_id
	 * @param item the item_id value
	 */
	public void setItem (com.mpe.financial.model.Item item) {
		this.item = item;
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
	 * Return the value associated with the column: warehouse_id
	 */
	public com.mpe.financial.model.Warehouse getWarehouse () {
		return warehouse;
	}

	/**
	 * Set the value related to the column: warehouse_id
	 * @param warehouse the warehouse_id value
	 */
	public void setWarehouse (com.mpe.financial.model.Warehouse warehouse) {
		this.warehouse = warehouse;
	}



	/**
	 * Return the value associated with the column: item_unit_id
	 */
	public com.mpe.financial.model.ItemUnit getItemUnit () {
		return itemUnit;
	}

	/**
	 * Set the value related to the column: item_unit_id
	 * @param itemUnit the item_unit_id value
	 */
	public void setItemUnit (com.mpe.financial.model.ItemUnit itemUnit) {
		this.itemUnit = itemUnit;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemFirstBalance)) return false;
		else {
			com.mpe.financial.model.ItemFirstBalance itemFirstBalance = (com.mpe.financial.model.ItemFirstBalance) obj;
			return (this.getId() == itemFirstBalance.getId());
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