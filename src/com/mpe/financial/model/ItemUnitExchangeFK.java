package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseItemUnitExchangeFK;



public class ItemUnitExchangeFK extends BaseItemUnitExchangeFK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemUnitExchangeFK () {
		super();
	}

	/**
	 * Constructor for required fields
	 */
	public ItemUnitExchangeFK (
		com.mpe.financial.model.ItemUnit toItemUnit,
		double conversion) {

		super (
			toItemUnit,
			conversion);
	}

/*[CONSTRUCTOR MARKER END]*/


}