package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemUsageDetail;



public class ItemUsageDetail extends BaseItemUsageDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemUsageDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemUsageDetail (com.mpe.financial.model.ItemUsageDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ItemUsageDetail (
		com.mpe.financial.model.ItemUsageDetailPK id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.ItemUnit itemUnit,
		double outQuantity,
		double returQuantity,
		double price,
		double exchangeRate) {

		super (
			id,
			currency,
			itemUnit,
			outQuantity,
			returQuantity,
			price,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrice() {
		return Formater.getFormatedOutput(getId().getItemUsage().getNumberOfDigit(), getPrice());
	}
	
	public double getItemPriceAmount() {
		return (getOutQuantity() - getReturQuantity()) * getPrice() * getExchangeRate();
	}


}