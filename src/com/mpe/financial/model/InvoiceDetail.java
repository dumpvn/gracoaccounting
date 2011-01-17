package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseInvoiceDetail;



public class InvoiceDetail extends BaseInvoiceDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoiceDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InvoiceDetail (com.mpe.financial.model.InvoiceDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InvoiceDetail (
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

		super (
			id,
			currency,
			itemUnit,
			costPriceCurrency,
			quantity,
			price,
			exchangeRate,
			unitConversion,
			costPrice,
			costPriceExchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrice() {
		return Formater.getFormatedOutput(getId().getInvoice().getNumberOfDigit(), getPrice());
	}
	
	public double getPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getInvoice().getNumberOfDigit(), getPrice() * getQuantity() * getExchangeRate() * getUnitConversion());
	}
	
	public double getCostPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getInvoice().getNumberOfDigit(), getCostPrice() * getQuantity() * getCostPriceExchangeRate() * getUnitConversion());
	}
	
	public String getFormatedPriceQuantity() {
		return Formater.getFormatedOutput(getId().getInvoice().getNumberOfDigit(), getPriceQuantity());
	}
	
	public String getFormatedPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutput(getId().getInvoice().getNumberOfDigit(), getPriceQuantityAfterDiscountTax());
	}
	

	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}
	
	public String getFormatedDiscountAmount() {
		return Formater.getFormatedOutput(getId().getInvoice().getNumberOfDigit(), getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscount() {
		return Formater.getFormatedOutputResult(getId().getInvoice().getNumberOfDigit(), (getPriceQuantity() * (1 - (getDiscountProcent()/100))) - getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutputResult(getId().getInvoice().getNumberOfDigit(), getPriceQuantityAfterDiscount() * (1 + (getTaxAmount()/100)));
	}

}