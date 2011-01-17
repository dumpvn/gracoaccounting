package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemPrice;



public class ItemPrice extends BaseItemPrice {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemPrice () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemPrice (com.mpe.financial.model.ItemPricePK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ItemPrice (
		com.mpe.financial.model.ItemPricePK id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.ItemUnit itemUnit,
		boolean m_default,
		double price) {

		super (
			id,
			currency,
			itemUnit,
			m_default,
			price);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrice() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getPrice());
	}


}