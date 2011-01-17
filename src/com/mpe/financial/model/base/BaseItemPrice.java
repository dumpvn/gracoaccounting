package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item_price table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item_price"
 */

public abstract class BaseItemPrice  implements Serializable {

	public static String REF = "ItemPrice";
	public static String PROP_DEFAULT = "Default";
	public static String PROP_PRICE = "Price";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItemPrice () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemPrice (com.mpe.financial.model.ItemPricePK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseItemPrice (
		com.mpe.financial.model.ItemPricePK id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.ItemUnit itemUnit,
		boolean m_default,
		double price) {

		this.setId(id);
		this.setCurrency(currency);
		this.setItemUnit(itemUnit);
		this.setDefault(m_default);
		this.setPrice(price);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.ItemPricePK id;

	// fields
	private boolean m_default;
	private double price;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.ChartOfAccount chartOfAccount;
	private com.mpe.financial.model.ItemUnit itemUnit;
	private com.mpe.financial.model.Tax salesTax;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.ItemPricePK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.ItemPricePK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: is_default
	 */
	public boolean isDefault () {
		return m_default;
	}

	/**
	 * Set the value related to the column: is_default
	 * @param m_default the is_default value
	 */
	public void setDefault (boolean m_default) {
		this.m_default = m_default;
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
	 * Return the value associated with the column: chart_of_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getChartOfAccount () {
		return chartOfAccount;
	}

	/**
	 * Set the value related to the column: chart_of_account_id
	 * @param chartOfAccount the chart_of_account_id value
	 */
	public void setChartOfAccount (com.mpe.financial.model.ChartOfAccount chartOfAccount) {
		this.chartOfAccount = chartOfAccount;
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



	/**
	 * Return the value associated with the column: sales_tax_id
	 */
	public com.mpe.financial.model.Tax getSalesTax () {
		return salesTax;
	}

	/**
	 * Set the value related to the column: sales_tax_id
	 * @param salesTax the sales_tax_id value
	 */
	public void setSalesTax (com.mpe.financial.model.Tax salesTax) {
		this.salesTax = salesTax;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemPrice)) return false;
		else {
			com.mpe.financial.model.ItemPrice itemPrice = (com.mpe.financial.model.ItemPrice) obj;
			if (null == this.getId() || null == itemPrice.getId()) return false;
			else return (this.getId().equals(itemPrice.getId()));
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