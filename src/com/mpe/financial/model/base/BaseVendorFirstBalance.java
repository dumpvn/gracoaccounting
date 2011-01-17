package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the vendor_first_balance table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="vendor_first_balance"
 */

public abstract class BaseVendorFirstBalance  implements Serializable {

	public static String REF = "VendorFirstBalance";
	public static String PROP_FIRST_BALANCE_DATE = "FirstBalanceDate";
	public static String PROP_AMOUNT = "Amount";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseVendorFirstBalance () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseVendorFirstBalance (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseVendorFirstBalance (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Vendors vendor,
		java.util.Date firstBalanceDate,
		double amount,
		double exchangeRate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setOrganization(organization);
		this.setVendor(vendor);
		this.setFirstBalanceDate(firstBalanceDate);
		this.setAmount(amount);
		this.setExchangeRate(exchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date firstBalanceDate;
	private double amount;
	private double exchangeRate;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Vendors vendor;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="vendor_first_balance_id"
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
	 * Return the value associated with the column: amount
	 */
	public double getAmount () {
		return amount;
	}

	/**
	 * Set the value related to the column: amount
	 * @param amount the amount value
	 */
	public void setAmount (double amount) {
		this.amount = amount;
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
	 * Return the value associated with the column: vendor_id
	 */
	public com.mpe.financial.model.Vendors getVendor () {
		return vendor;
	}

	/**
	 * Set the value related to the column: vendor_id
	 * @param vendor the vendor_id value
	 */
	public void setVendor (com.mpe.financial.model.Vendors vendor) {
		this.vendor = vendor;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.VendorFirstBalance)) return false;
		else {
			com.mpe.financial.model.VendorFirstBalance vendorFirstBalance = (com.mpe.financial.model.VendorFirstBalance) obj;
			return (this.getId() == vendorFirstBalance.getId());
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