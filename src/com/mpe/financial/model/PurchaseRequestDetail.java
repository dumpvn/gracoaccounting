package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BasePurchaseRequestDetail;



public class PurchaseRequestDetail extends BasePurchaseRequestDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PurchaseRequestDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PurchaseRequestDetail (com.mpe.financial.model.PurchaseRequestDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PurchaseRequestDetail (
		com.mpe.financial.model.PurchaseRequestDetailPK id,
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
		return Formater.getFormatedOutput(getId().getPurchaseRequest().getNumberOfDigit(), getPrice());
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}
	
	public double getPriceQuantity() {
		return getPrice() * getQuantity() * getExchangeRate() * getUnitConversion();
	}
	
	public String getFormatedPriceQuantity() {
		return Formater.getFormatedOutput(getId().getPurchaseRequest().getNumberOfDigit(), getPriceQuantity());
	}


}