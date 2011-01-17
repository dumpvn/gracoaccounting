package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item_vendor table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item_vendor"
 */

public abstract class BaseItemVendor  implements Serializable {

	public static String REF = "ItemVendor";
	public static String PROP_COST_PRICE = "CostPrice";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItemVendor () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemVendor (com.mpe.financial.model.ItemVendorPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseItemVendor (
		com.mpe.financial.model.ItemVendorPK id,
		com.mpe.financial.model.Currency currency,
		double costPrice) {

		this.setId(id);
		this.setCurrency(currency);
		this.setCostPrice(costPrice);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.ItemVendorPK id;

	// fields
	private double costPrice;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Tax purchaseTax;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.ItemVendorPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.ItemVendorPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: cost_price
	 */
	public double getCostPrice () {
		return costPrice;
	}

	/**
	 * Set the value related to the column: cost_price
	 * @param costPrice the cost_price value
	 */
	public void setCostPrice (double costPrice) {
		this.costPrice = costPrice;
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
	 * Return the value associated with the column: purchase_tax_id
	 */
	public com.mpe.financial.model.Tax getPurchaseTax () {
		return purchaseTax;
	}

	/**
	 * Set the value related to the column: purchase_tax_id
	 * @param purchaseTax the purchase_tax_id value
	 */
	public void setPurchaseTax (com.mpe.financial.model.Tax purchaseTax) {
		this.purchaseTax = purchaseTax;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemVendor)) return false;
		else {
			com.mpe.financial.model.ItemVendor itemVendor = (com.mpe.financial.model.ItemVendor) obj;
			if (null == this.getId() || null == itemVendor.getId()) return false;
			else return (this.getId().equals(itemVendor.getId()));
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