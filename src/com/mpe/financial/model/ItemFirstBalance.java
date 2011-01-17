package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemFirstBalance;



public class ItemFirstBalance extends BaseItemFirstBalance {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemFirstBalance () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemFirstBalance (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ItemFirstBalance (
		long id,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Item item,
		java.util.Date firstBalanceDate,
		double quantity) {

		super (
			id,
			organization,
			item,
			firstBalanceDate,
			quantity);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrice() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPrice());
	}
	
	public double getQuantityPrice() {
	    return getQuantity() * getPrice();
	}
	
	public String getFormatedQuantityPrice() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getQuantityPrice());
	}


}