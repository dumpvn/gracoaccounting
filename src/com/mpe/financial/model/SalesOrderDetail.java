package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseSalesOrderDetail;



public class SalesOrderDetail extends BaseSalesOrderDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SalesOrderDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SalesOrderDetail (com.mpe.financial.model.SalesOrderDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SalesOrderDetail (
		com.mpe.financial.model.SalesOrderDetailPK id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.ItemUnit itemUnit,
		double quantity,
		double price,
		double exchangeRate,
		double unitConversion) {

		super (
			id,
			currency,
			itemUnit,
			quantity,
			price,
			exchangeRate,
			unitConversion);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrice() {
		return Formater.getFormatedOutput(getId().getSalesOrder().getNumberOfDigit(), getPrice());
	}
	
	public double getPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getSalesOrder().getNumberOfDigit(), getPrice() * getQuantity() * getExchangeRate() * getUnitConversion());
	}
	
	public String getFormatedPriceQuantity() {
		return Formater.getFormatedOutput(getId().getSalesOrder().getNumberOfDigit(), getPriceQuantity());
	}
	
	public String getFormatedPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutput(getId().getSalesOrder().getNumberOfDigit(), getPriceQuantityAfterDiscountTax());
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}
	
	public String getFormatedDiscountAmount() {
		return Formater.getFormatedOutput(getId().getSalesOrder().getNumberOfDigit(), getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscount() {
		return Formater.getFormatedOutputResult(getId().getSalesOrder().getNumberOfDigit(), (getPriceQuantity() * (1 - (getDiscountProcent()/100))) - getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutputResult(getId().getSalesOrder().getNumberOfDigit(), getPriceQuantityAfterDiscount() * (1 + (getTaxAmount()/100)));
	}


}