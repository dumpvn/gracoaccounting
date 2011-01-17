package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BasePosOrderDetail;



public class PosOrderDetail extends BasePosOrderDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PosOrderDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PosOrderDetail (com.mpe.financial.model.PosOrderDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PosOrderDetail (
		com.mpe.financial.model.PosOrderDetailPK id,
		com.mpe.financial.model.Currency currency,
		double quantity,
		double price,
		double exchangeRate) {

		super (
			id,
			currency,
			quantity,
			price,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public double getQuantityPrice() {
	    return getQuantity() * getPrice();
	}
	
	public String getFormatedPrice() {
	    return Formater.getFormatedOutput(getId().getPosOrder().getNumberOfDigit(), getPrice());
	}
	
	public String getFormatedQuantityPrice() {
	    return Formater.getFormatedOutput(getId().getPosOrder().getNumberOfDigit(), getQuantityPrice());
	}


}