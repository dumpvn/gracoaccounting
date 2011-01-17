package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemVendor;



public class ItemVendor extends BaseItemVendor {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemVendor () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemVendor (com.mpe.financial.model.ItemVendorPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ItemVendor (
		com.mpe.financial.model.ItemVendorPK id,
		com.mpe.financial.model.Currency currency,
		double costPrice) {

		super (
			id,
			currency,
			costPrice);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCostPrice() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getCostPrice());
	}


}