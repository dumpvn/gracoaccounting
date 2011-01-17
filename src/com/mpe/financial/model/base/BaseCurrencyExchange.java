package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the currency_exchange table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="currency_exchange"
 */

public abstract class BaseCurrencyExchange  implements Serializable {

	public static String REF = "CurrencyExchange";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_POSTED = "Posted";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseCurrencyExchange () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCurrencyExchange (com.mpe.financial.model.CurrencyExchangePK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCurrencyExchange (
		com.mpe.financial.model.CurrencyExchangePK id,
		com.mpe.financial.model.Organization organization,
		double exchangeRate) {

		this.setId(id);
		this.setOrganization(organization);
		this.setExchangeRate(exchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.CurrencyExchangePK id;

	// fields
	private double exchangeRate;
	private boolean posted;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.ChartOfAccount chartOfAccount;
	private com.mpe.financial.model.Organization organization;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.CurrencyExchangePK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.CurrencyExchangePK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
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





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CurrencyExchange)) return false;
		else {
			com.mpe.financial.model.CurrencyExchange currencyExchange = (com.mpe.financial.model.CurrencyExchange) obj;
			if (null == this.getId() || null == currencyExchange.getId()) return false;
			else return (this.getId().equals(currencyExchange.getId()));
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