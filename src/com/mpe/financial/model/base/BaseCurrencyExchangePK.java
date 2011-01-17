package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseCurrencyExchangePK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.Currency toCurrency;
	private com.mpe.financial.model.Currency fromCurrency;
	private com.mpe.financial.model.CurrencyExchangeRateType currencyExchangeRateType;
	private java.util.Date validFrom;
	private java.util.Date validTo;


	public BaseCurrencyExchangePK () {}
	
	public BaseCurrencyExchangePK (
		com.mpe.financial.model.Currency toCurrency,
		com.mpe.financial.model.Currency fromCurrency,
		com.mpe.financial.model.CurrencyExchangeRateType currencyExchangeRateType,
		java.util.Date validFrom,
		java.util.Date validTo) {

		this.setToCurrency(toCurrency);
		this.setFromCurrency(fromCurrency);
		this.setCurrencyExchangeRateType(currencyExchangeRateType);
		this.setValidFrom(validFrom);
		this.setValidTo(validTo);
	}


	/**
	 * Return the value associated with the column: to_currency_id
	 */
	public com.mpe.financial.model.Currency getToCurrency () {
		return toCurrency;
	}

	/**
	 * Set the value related to the column: to_currency_id
	 * @param toCurrency the to_currency_id value
	 */
	public void setToCurrency (com.mpe.financial.model.Currency toCurrency) {
		this.toCurrency = toCurrency;
	}



	/**
	 * Return the value associated with the column: from_currency_id
	 */
	public com.mpe.financial.model.Currency getFromCurrency () {
		return fromCurrency;
	}

	/**
	 * Set the value related to the column: from_currency_id
	 * @param fromCurrency the from_currency_id value
	 */
	public void setFromCurrency (com.mpe.financial.model.Currency fromCurrency) {
		this.fromCurrency = fromCurrency;
	}



	/**
	 * Return the value associated with the column: currency_exchange_rate_type_id
	 */
	public com.mpe.financial.model.CurrencyExchangeRateType getCurrencyExchangeRateType () {
		return currencyExchangeRateType;
	}

	/**
	 * Set the value related to the column: currency_exchange_rate_type_id
	 * @param currencyExchangeRateType the currency_exchange_rate_type_id value
	 */
	public void setCurrencyExchangeRateType (com.mpe.financial.model.CurrencyExchangeRateType currencyExchangeRateType) {
		this.currencyExchangeRateType = currencyExchangeRateType;
	}



	/**
	 * Return the value associated with the column: valid_from
	 */
	public java.util.Date getValidFrom () {
		return validFrom;
	}

	/**
	 * Set the value related to the column: valid_from
	 * @param validFrom the valid_from value
	 */
	public void setValidFrom (java.util.Date validFrom) {
		this.validFrom = validFrom;
	}



	/**
	 * Return the value associated with the column: valid_to
	 */
	public java.util.Date getValidTo () {
		return validTo;
	}

	/**
	 * Set the value related to the column: valid_to
	 * @param validTo the valid_to value
	 */
	public void setValidTo (java.util.Date validTo) {
		this.validTo = validTo;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CurrencyExchangePK)) return false;
		else {
			com.mpe.financial.model.CurrencyExchangePK mObj = (com.mpe.financial.model.CurrencyExchangePK) obj;
			if (null != this.getToCurrency() && null != mObj.getToCurrency()) {
				if (!this.getToCurrency().equals(mObj.getToCurrency())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getFromCurrency() && null != mObj.getFromCurrency()) {
				if (!this.getFromCurrency().equals(mObj.getFromCurrency())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getCurrencyExchangeRateType() && null != mObj.getCurrencyExchangeRateType()) {
				if (!this.getCurrencyExchangeRateType().equals(mObj.getCurrencyExchangeRateType())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getValidFrom() && null != mObj.getValidFrom()) {
				if (!this.getValidFrom().equals(mObj.getValidFrom())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getValidTo() && null != mObj.getValidTo()) {
				if (!this.getValidTo().equals(mObj.getValidTo())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuffer sb = new StringBuffer();
			if (null != this.getToCurrency()) {
				sb.append(this.getToCurrency().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getFromCurrency()) {
				sb.append(this.getFromCurrency().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getCurrencyExchangeRateType()) {
				sb.append(this.getCurrencyExchangeRateType().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getValidFrom()) {
				sb.append(this.getValidFrom().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getValidTo()) {
				sb.append(this.getValidTo().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}