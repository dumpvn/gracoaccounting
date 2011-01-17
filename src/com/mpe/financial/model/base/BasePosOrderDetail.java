package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the pos_order_detail table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="pos_order_detail"
 */

public abstract class BasePosOrderDetail  implements Serializable {

	public static String REF = "PosOrderDetail";
	public static String PROP_QUANTITY = "Quantity";
	public static String PROP_PRICE = "Price";
	public static String PROP_DISCOUNT_PROCENT = "DiscountProcent";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";


	// constructors
	public BasePosOrderDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePosOrderDetail (com.mpe.financial.model.PosOrderDetailPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BasePosOrderDetail (
		com.mpe.financial.model.PosOrderDetailPK id,
		com.mpe.financial.model.Currency currency,
		double quantity,
		double price,
		double exchangeRate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setQuantity(quantity);
		this.setPrice(price);
		this.setExchangeRate(exchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.PosOrderDetailPK id;

	// fields
	private double quantity;
	private double price;
	private double discountProcent;
	private double exchangeRate;

	// many to one
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.ItemUnit itemUnit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.PosOrderDetailPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.PosOrderDetailPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
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
		if (!(obj instanceof com.mpe.financial.model.PosOrderDetail)) return false;
		else {
			com.mpe.financial.model.PosOrderDetail posOrderDetail = (com.mpe.financial.model.PosOrderDetail) obj;
			if (null == this.getId() || null == posOrderDetail.getId()) return false;
			else return (this.getId().equals(posOrderDetail.getId()));
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