package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseDeliveryOrderDetail;



public class DeliveryOrderDetail extends BaseDeliveryOrderDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DeliveryOrderDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DeliveryOrderDetail (com.mpe.financial.model.DeliveryOrderDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DeliveryOrderDetail (
		com.mpe.financial.model.DeliveryOrderDetailPK id,
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
		return Formater.getFormatedOutput(getId().getDeliveryOrder().getNumberOfDigit(), getPrice());
	}
	
	public double getPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getDeliveryOrder().getNumberOfDigit(), getPrice() * getQuantity() * getExchangeRate() * getUnitConversion());
	}
	
	public double getCostPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getDeliveryOrder().getNumberOfDigit(), getCostPrice() * getQuantity() * getCostPriceExchangeRate() * getUnitConversion());
	}
	
	public String getFormatedPriceQuantity() {
		return Formater.getFormatedOutput(getId().getDeliveryOrder().getNumberOfDigit(), getPriceQuantityAfterDiscountTax());
	}
	
	public double getOutstandingQuantity() {
	    return getReturQuantity();
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}
	
	public double getPriceQuantityBruto() {
		return Formater.getFormatedOutputResult(getId().getDeliveryOrder().getNumberOfDigit(), getPrice() * getQuantity() * getExchangeRate() * getUnitConversion());
	}
	
	public double getPriceQuantityDiscount() {
	    return getPriceQuantityBruto() - getPriceQuantity();
	}
	
	public String getFormatedPriceQuantityBruto() {
		return Formater.getFormatedOutput(getId().getDeliveryOrder().getNumberOfDigit(), getPriceQuantityBruto());
	}
	
	public String getFormatedPriceQuantityDiscount() {
		return Formater.getFormatedOutput(getId().getDeliveryOrder().getNumberOfDigit(), getPriceQuantityDiscount());
	}
	
	public String getFormatedDiscountAmount() {
		return Formater.getFormatedOutput(getId().getDeliveryOrder().getNumberOfDigit(), getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscount() {
		return Formater.getFormatedOutputResult(getId().getDeliveryOrder().getNumberOfDigit(), (getPriceQuantity() * (1 - (getDiscountProcent()/100))) - getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutputResult(getId().getDeliveryOrder().getNumberOfDigit(), getPriceQuantityAfterDiscount() * (1 + (getTaxAmount()/100)));
	}


}