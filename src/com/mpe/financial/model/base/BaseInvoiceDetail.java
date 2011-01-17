package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the invoice_detail table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="invoice_detail"
 */

public abstract class BaseInvoiceDetail  implements Serializable {

	public static String REF = "InvoiceDetail";
	public static String PROP_QUANTITY = "Quantity";
	public static String PROP_PRICE = "Price";
	public static String PROP_DISCOUNT_AMOUNT = "DiscountAmount";
	public static String PROP_DISCOUNT_PROCENT = "DiscountProcent";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_UNIT_CONVERSION = "UnitConversion";
	public static String PROP_COST_PRICE = "CostPrice";
	public static String PROP_COST_PRICE_EXCHANGE_RATE = "CostPriceExchangeRate";
	public static String PROP_TAX_AMOUNT = "TaxAmount";
	public static String PROP_DESCRIPTION = "Description";


	// constructors
	public BaseInvoiceDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInvoiceDetail (com.mpe.financial.model.InvoiceDetailPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInvoiceDetail (
		com.mpe.financial.model.InvoiceDetailPK id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.ItemUnit itemUnit,
		com.mpe.financial.model.Currency costPriceCurrency,
		double quantity,
		double price,
		double exchangeRate,
		double unitConversion,
		double costPrice,
		double costPriceExchangeRate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setItemUnit(itemUnit);
		this.setCostPriceCurrency(costPriceCurrency);
		this.setQuantity(quantity);
		this.setPrice(price);
		this.setExchangeRate(exchangeRate);
		this.setUnitConversion(unitConversion);
		this.setCostPrice(costPrice);
		this.setCostPriceExchangeRate(costPriceExchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.InvoiceDetailPK id;

	// fields
	private double quantity;
	private double price;
	private double discountAmount;
	private double discountProcent;
	private double exchangeRate;
	private double unitConversion;
	private double costPrice;
	private double costPriceExchangeRate;
	private double taxAmount;
	private java.lang.String description;

	// many to one
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.ItemUnit itemUnit;
	private com.mpe.financial.model.Currency costPriceCurrency;
	private com.mpe.financial.model.Warehouse warehouse;
	private com.mpe.financial.model.Tax tax;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.InvoiceDetailPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.InvoiceDetailPK id) {
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
	 * Return the value associated with the column: unit_conversion
	 */
	public double getUnitConversion () {
		return unitConversion;
	}

	/**
	 * Set the value related to the column: unit_conversion
	 * @param unitConversion the unit_conversion value
	 */
	public void setUnitConversion (double unitConversion) {
		this.unitConversion = unitConversion;
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
	 * Return the value associated with the column: cost_price_exchange_rate
	 */
	public double getCostPriceExchangeRate () {
		return costPriceExchangeRate;
	}

	/**
	 * Set the value related to the column: cost_price_exchange_rate
	 * @param costPriceExchangeRate the cost_price_exchange_rate value
	 */
	public void setCostPriceExchangeRate (double costPriceExchangeRate) {
		this.costPriceExchangeRate = costPriceExchangeRate;
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



	/**
	 * Return the value associated with the column: cost_price_currency_id
	 */
	public com.mpe.financial.model.Currency getCostPriceCurrency () {
		return costPriceCurrency;
	}

	/**
	 * Set the value related to the column: cost_price_currency_id
	 * @param costPriceCurrency the cost_price_currency_id value
	 */
	public void setCostPriceCurrency (com.mpe.financial.model.Currency costPriceCurrency) {
		this.costPriceCurrency = costPriceCurrency;
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





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.InvoiceDetail)) return false;
		else {
			com.mpe.financial.model.InvoiceDetail invoiceDetail = (com.mpe.financial.model.InvoiceDetail) obj;
			if (null == this.getId() || null == invoiceDetail.getId()) return false;
			else return (this.getId().equals(invoiceDetail.getId()));
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