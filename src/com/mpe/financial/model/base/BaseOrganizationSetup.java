package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the organization_setup table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="organization_setup"
 */

public abstract class BaseOrganizationSetup extends com.mpe.financial.model.Organization  implements Serializable {

	public static String REF = "OrganizationSetup";
	public static String PROP_SETUP_DATE = "SetupDate";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";
	public static String PROP_POS_TAX = "PosTax";
	public static String PROP_CURRENCY_EXCHANGE_TYPE = "CurrencyExchangeType";
	public static String PROP_POSTING_PERIODE = "PostingPeriode";


	// constructors
	public BaseOrganizationSetup () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseOrganizationSetup (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public BaseOrganizationSetup (
		long id,
		java.lang.String name) {

		super (
			id,
			name);
	}



	private int hashCode = Integer.MIN_VALUE;


	// fields
	private java.util.Date setupDate;
	private int numberOfDigit;
	private double posTax;
	private java.lang.String currencyExchangeType;
	private java.lang.String postingPeriode;

	// one to one
	private com.mpe.financial.model.RunningNumber runningNumber;

	// many to one
	private com.mpe.financial.model.ChartOfAccount realizedCurrencyLossGainAccount;
	private com.mpe.financial.model.ChartOfAccount unrealizedCurrencyLossGainAccount;
	private com.mpe.financial.model.ChartOfAccount retainedAccount;
	private com.mpe.financial.model.ChartOfAccount undepositAccount;
	private com.mpe.financial.model.ChartOfAccount prepaymentAccount;
	private com.mpe.financial.model.ChartOfAccount salesAccount;
	private com.mpe.financial.model.ChartOfAccount profitLossAccount;
	private com.mpe.financial.model.ChartOfAccount cogsAccount;
	private com.mpe.financial.model.ChartOfAccount stockOpnameAccount;
	private com.mpe.financial.model.ChartOfAccount inventoryBeginningAccount;
	private com.mpe.financial.model.ChartOfAccount apAccount;
	private com.mpe.financial.model.ChartOfAccount customerReturAccount;
	private com.mpe.financial.model.Currency defaultCurrency;
	private com.mpe.financial.model.ChartOfAccount arAccount;
	private com.mpe.financial.model.ChartOfAccount prepaymentToVendorAccount;
	private com.mpe.financial.model.ChartOfAccount inventoryAccount;
	private com.mpe.financial.model.ChartOfAccount inventoryEndingAccount;
	private com.mpe.financial.model.ChartOfAccount fixAssetAccount;






	/**
	 * Return the value associated with the column: setup_date
	 */
	public java.util.Date getSetupDate () {
		return setupDate;
	}

	/**
	 * Set the value related to the column: setup_date
	 * @param setupDate the setup_date value
	 */
	public void setSetupDate (java.util.Date setupDate) {
		this.setupDate = setupDate;
	}



	/**
	 * Return the value associated with the column: number_of_digit
	 */
	public int getNumberOfDigit () {
		return numberOfDigit;
	}

	/**
	 * Set the value related to the column: number_of_digit
	 * @param numberOfDigit the number_of_digit value
	 */
	public void setNumberOfDigit (int numberOfDigit) {
		this.numberOfDigit = numberOfDigit;
	}



	/**
	 * Return the value associated with the column: pos_tax
	 */
	public double getPosTax () {
		return posTax;
	}

	/**
	 * Set the value related to the column: pos_tax
	 * @param posTax the pos_tax value
	 */
	public void setPosTax (double posTax) {
		this.posTax = posTax;
	}



	/**
	 * Return the value associated with the column: currency_exchange_type
	 */
	public java.lang.String getCurrencyExchangeType () {
		return currencyExchangeType;
	}

	/**
	 * Set the value related to the column: currency_exchange_type
	 * @param currencyExchangeType the currency_exchange_type value
	 */
	public void setCurrencyExchangeType (java.lang.String currencyExchangeType) {
		this.currencyExchangeType = currencyExchangeType;
	}



	/**
	 * Return the value associated with the column: posting_periode
	 */
	public java.lang.String getPostingPeriode () {
		return postingPeriode;
	}

	/**
	 * Set the value related to the column: posting_periode
	 * @param postingPeriode the posting_periode value
	 */
	public void setPostingPeriode (java.lang.String postingPeriode) {
		this.postingPeriode = postingPeriode;
	}



	/**
	 * Return the value associated with the column: RunningNumber
	 */
	public com.mpe.financial.model.RunningNumber getRunningNumber () {
		return runningNumber;
	}

	/**
	 * Set the value related to the column: RunningNumber
	 * @param runningNumber the RunningNumber value
	 */
	public void setRunningNumber (com.mpe.financial.model.RunningNumber runningNumber) {
		this.runningNumber = runningNumber;
	}



	/**
	 * Return the value associated with the column: realized_currency_loss_gain_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getRealizedCurrencyLossGainAccount () {
		return realizedCurrencyLossGainAccount;
	}

	/**
	 * Set the value related to the column: realized_currency_loss_gain_account_id
	 * @param realizedCurrencyLossGainAccount the realized_currency_loss_gain_account_id value
	 */
	public void setRealizedCurrencyLossGainAccount (com.mpe.financial.model.ChartOfAccount realizedCurrencyLossGainAccount) {
		this.realizedCurrencyLossGainAccount = realizedCurrencyLossGainAccount;
	}



	/**
	 * Return the value associated with the column: unrealized_currency_loss_gain_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getUnrealizedCurrencyLossGainAccount () {
		return unrealizedCurrencyLossGainAccount;
	}

	/**
	 * Set the value related to the column: unrealized_currency_loss_gain_account_id
	 * @param unrealizedCurrencyLossGainAccount the unrealized_currency_loss_gain_account_id value
	 */
	public void setUnrealizedCurrencyLossGainAccount (com.mpe.financial.model.ChartOfAccount unrealizedCurrencyLossGainAccount) {
		this.unrealizedCurrencyLossGainAccount = unrealizedCurrencyLossGainAccount;
	}



	/**
	 * Return the value associated with the column: retained_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getRetainedAccount () {
		return retainedAccount;
	}

	/**
	 * Set the value related to the column: retained_account_id
	 * @param retainedAccount the retained_account_id value
	 */
	public void setRetainedAccount (com.mpe.financial.model.ChartOfAccount retainedAccount) {
		this.retainedAccount = retainedAccount;
	}



	/**
	 * Return the value associated with the column: undeposit_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getUndepositAccount () {
		return undepositAccount;
	}

	/**
	 * Set the value related to the column: undeposit_account_id
	 * @param undepositAccount the undeposit_account_id value
	 */
	public void setUndepositAccount (com.mpe.financial.model.ChartOfAccount undepositAccount) {
		this.undepositAccount = undepositAccount;
	}



	/**
	 * Return the value associated with the column: prepayment_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getPrepaymentAccount () {
		return prepaymentAccount;
	}

	/**
	 * Set the value related to the column: prepayment_account_id
	 * @param prepaymentAccount the prepayment_account_id value
	 */
	public void setPrepaymentAccount (com.mpe.financial.model.ChartOfAccount prepaymentAccount) {
		this.prepaymentAccount = prepaymentAccount;
	}



	/**
	 * Return the value associated with the column: sales_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getSalesAccount () {
		return salesAccount;
	}

	/**
	 * Set the value related to the column: sales_account_id
	 * @param salesAccount the sales_account_id value
	 */
	public void setSalesAccount (com.mpe.financial.model.ChartOfAccount salesAccount) {
		this.salesAccount = salesAccount;
	}



	/**
	 * Return the value associated with the column: profit_loss_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getProfitLossAccount () {
		return profitLossAccount;
	}

	/**
	 * Set the value related to the column: profit_loss_account_id
	 * @param profitLossAccount the profit_loss_account_id value
	 */
	public void setProfitLossAccount (com.mpe.financial.model.ChartOfAccount profitLossAccount) {
		this.profitLossAccount = profitLossAccount;
	}



	/**
	 * Return the value associated with the column: cogs_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getCogsAccount () {
		return cogsAccount;
	}

	/**
	 * Set the value related to the column: cogs_account_id
	 * @param cogsAccount the cogs_account_id value
	 */
	public void setCogsAccount (com.mpe.financial.model.ChartOfAccount cogsAccount) {
		this.cogsAccount = cogsAccount;
	}



	/**
	 * Return the value associated with the column: stock_opname_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getStockOpnameAccount () {
		return stockOpnameAccount;
	}

	/**
	 * Set the value related to the column: stock_opname_account_id
	 * @param stockOpnameAccount the stock_opname_account_id value
	 */
	public void setStockOpnameAccount (com.mpe.financial.model.ChartOfAccount stockOpnameAccount) {
		this.stockOpnameAccount = stockOpnameAccount;
	}



	/**
	 * Return the value associated with the column: inventory_beginning_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getInventoryBeginningAccount () {
		return inventoryBeginningAccount;
	}

	/**
	 * Set the value related to the column: inventory_beginning_account_id
	 * @param inventoryBeginningAccount the inventory_beginning_account_id value
	 */
	public void setInventoryBeginningAccount (com.mpe.financial.model.ChartOfAccount inventoryBeginningAccount) {
		this.inventoryBeginningAccount = inventoryBeginningAccount;
	}



	/**
	 * Return the value associated with the column: ap_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getApAccount () {
		return apAccount;
	}

	/**
	 * Set the value related to the column: ap_account_id
	 * @param apAccount the ap_account_id value
	 */
	public void setApAccount (com.mpe.financial.model.ChartOfAccount apAccount) {
		this.apAccount = apAccount;
	}



	/**
	 * Return the value associated with the column: customer_retur_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getCustomerReturAccount () {
		return customerReturAccount;
	}

	/**
	 * Set the value related to the column: customer_retur_account_id
	 * @param customerReturAccount the customer_retur_account_id value
	 */
	public void setCustomerReturAccount (com.mpe.financial.model.ChartOfAccount customerReturAccount) {
		this.customerReturAccount = customerReturAccount;
	}



	/**
	 * Return the value associated with the column: default_currency_id
	 */
	public com.mpe.financial.model.Currency getDefaultCurrency () {
		return defaultCurrency;
	}

	/**
	 * Set the value related to the column: default_currency_id
	 * @param defaultCurrency the default_currency_id value
	 */
	public void setDefaultCurrency (com.mpe.financial.model.Currency defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}



	/**
	 * Return the value associated with the column: ar_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getArAccount () {
		return arAccount;
	}

	/**
	 * Set the value related to the column: ar_account_id
	 * @param arAccount the ar_account_id value
	 */
	public void setArAccount (com.mpe.financial.model.ChartOfAccount arAccount) {
		this.arAccount = arAccount;
	}



	/**
	 * Return the value associated with the column: prepayment_to_vendor_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getPrepaymentToVendorAccount () {
		return prepaymentToVendorAccount;
	}

	/**
	 * Set the value related to the column: prepayment_to_vendor_account_id
	 * @param prepaymentToVendorAccount the prepayment_to_vendor_account_id value
	 */
	public void setPrepaymentToVendorAccount (com.mpe.financial.model.ChartOfAccount prepaymentToVendorAccount) {
		this.prepaymentToVendorAccount = prepaymentToVendorAccount;
	}



	/**
	 * Return the value associated with the column: inventory_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getInventoryAccount () {
		return inventoryAccount;
	}

	/**
	 * Set the value related to the column: inventory_account_id
	 * @param inventoryAccount the inventory_account_id value
	 */
	public void setInventoryAccount (com.mpe.financial.model.ChartOfAccount inventoryAccount) {
		this.inventoryAccount = inventoryAccount;
	}



	/**
	 * Return the value associated with the column: inventory_ending_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getInventoryEndingAccount () {
		return inventoryEndingAccount;
	}

	/**
	 * Set the value related to the column: inventory_ending_account_id
	 * @param inventoryEndingAccount the inventory_ending_account_id value
	 */
	public void setInventoryEndingAccount (com.mpe.financial.model.ChartOfAccount inventoryEndingAccount) {
		this.inventoryEndingAccount = inventoryEndingAccount;
	}



	/**
	 * Return the value associated with the column: fix_asset_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getFixAssetAccount () {
		return fixAssetAccount;
	}

	/**
	 * Set the value related to the column: fix_asset_account_id
	 * @param fixAssetAccount the fix_asset_account_id value
	 */
	public void setFixAssetAccount (com.mpe.financial.model.ChartOfAccount fixAssetAccount) {
		this.fixAssetAccount = fixAssetAccount;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.OrganizationSetup)) return false;
		else {
			com.mpe.financial.model.OrganizationSetup organizationSetup = (com.mpe.financial.model.OrganizationSetup) obj;
			return (this.getId() == organizationSetup.getId());
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