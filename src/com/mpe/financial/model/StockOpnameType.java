package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseStockOpnameType;



public class StockOpnameType extends BaseStockOpnameType {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public StockOpnameType () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public StockOpnameType (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public StockOpnameType (
		long id,
		java.lang.String code,
		java.lang.String name,
		boolean receipt) {

		super (
			id,
			code,
			name,
			receipt);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}