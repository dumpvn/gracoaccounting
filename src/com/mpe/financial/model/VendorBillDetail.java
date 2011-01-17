package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseVendorBillDetail;



public class VendorBillDetail extends BaseVendorBillDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorBillDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public VendorBillDetail (com.mpe.financial.model.VendorBillDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public VendorBillDetail (
		com.mpe.financial.model.VendorBillDetailPK id,
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
		return Formater.getFormatedOutput(getId().getVendorBill().getNumberOfDigit(), getPrice());
	}
	
	public String getFormatedDiscountAmount() {
		return Formater.getFormatedOutput(getId().getVendorBill().getNumberOfDigit(), getDiscountAmount());
	}
	
	public double getPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getVendorBill().getNumberOfDigit(), getPrice() * getQuantity() * getExchangeRate() * getUnitConversion());
	}
	
	public double getPriceQuantityAfterDiscount() {
		return Formater.getFormatedOutputResult(getId().getVendorBill().getNumberOfDigit(), (getPriceQuantity() * (1 - (getDiscountProcent()/100))) - getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutputResult(getId().getVendorBill().getNumberOfDigit(), getPriceQuantityAfterDiscount() * (1 + (getTaxAmount()/100)));
	}
	
	public String getFormatedPriceQuantity() {
		return Formater.getFormatedOutput(getId().getVendorBill().getNumberOfDigit(), getPriceQuantity());
	}
	
	public String getFormatedPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutput(getId().getVendorBill().getNumberOfDigit(), getPriceQuantityAfterDiscountTax());
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}


}