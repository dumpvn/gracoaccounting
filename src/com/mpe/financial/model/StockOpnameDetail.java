package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseStockOpnameDetail;



public class StockOpnameDetail extends BaseStockOpnameDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public StockOpnameDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public StockOpnameDetail (com.mpe.financial.model.StockOpnameDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public StockOpnameDetail (
		com.mpe.financial.model.StockOpnameDetailPK id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.ItemUnit itemUnit,
		double quantity,
		double price,
		double difference,
		double exchangeRate) {

		super (
			id,
			currency,
			itemUnit,
			quantity,
			price,
			difference,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public double getPriceDifference() {
	    return getPrice() * Math.abs(getDifference()) * getExchangeRate();
	}
	
	public String getFormatedPrice() {
		return Formater.getFormatedOutput(getId().getStockOpname().getNumberOfDigit(), getPrice());
	}
	
	public double getCurrentQuantity() {
	    return getQuantity() + getDifference();
	}
	
	public String getFormatedPriceDifference() {
		return Formater.getFormatedOutput(getId().getStockOpname().getNumberOfDigit(), getPriceDifference());
	}


}