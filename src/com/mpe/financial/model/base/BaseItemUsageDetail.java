package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item_usage_detail table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item_usage_detail"
 */

public abstract class BaseItemUsageDetail  implements Serializable {

	public static String REF = "ItemUsageDetail";
	public static String PROP_OUT_QUANTITY = "OutQuantity";
	public static String PROP_RETUR_QUANTITY = "ReturQuantity";
	public static String PROP_PRICE = "Price";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_NOTE = "Note";


	// constructors
	public BaseItemUsageDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemUsageDetail (com.mpe.financial.model.ItemUsageDetailPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseItemUsageDetail (
		com.mpe.financial.model.ItemUsageDetailPK id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.ItemUnit itemUnit,
		double outQuantity,
		double returQuantity,
		double price,
		double exchangeRate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setItemUnit(itemUnit);
		this.setOutQuantity(outQuantity);
		this.setReturQuantity(returQuantity);
		this.setPrice(price);
		this.setExchangeRate(exchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.ItemUsageDetailPK id;

	// fields
	private double outQuantity;
	private double returQuantity;
	private double price;
	private double exchangeRate;
	private java.lang.String note;

	// many to one
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.ItemUnit itemUnit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.ItemUsageDetailPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.ItemUsageDetailPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: out_quantity
	 */
	public double getOutQuantity () {
		return outQuantity;
	}

	/**
	 * Set the value related to the column: out_quantity
	 * @param outQuantity the out_quantity value
	 */
	public void setOutQuantity (double outQuantity) {
		this.outQuantity = outQuantity;
	}



	/**
	 * Return the value associated with the column: retur_quantity
	 */
	public double getReturQuantity () {
		return returQuantity;
	}

	/**
	 * Set the value related to the column: retur_quantity
	 * @param returQuantity the retur_quantity value
	 */
	public void setReturQuantity (double returQuantity) {
		this.returQuantity = returQuantity;
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
	 * Return the value associated with the column: note
	 */
	public java.lang.String getNote () {
		return note;
	}

	/**
	 * Set the value related to the column: note
	 * @param note the note value
	 */
	public void setNote (java.lang.String note) {
		this.note = note;
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
		if (!(obj instanceof com.mpe.financial.model.ItemUsageDetail)) return false;
		else {
			com.mpe.financial.model.ItemUsageDetail itemUsageDetail = (com.mpe.financial.model.ItemUsageDetail) obj;
			if (null == this.getId() || null == itemUsageDetail.getId()) return false;
			else return (this.getId().equals(itemUsageDetail.getId()));
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