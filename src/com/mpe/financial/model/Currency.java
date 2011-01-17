package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCurrency;



public class Currency extends BaseCurrency {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Currency () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Currency (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Currency (
		long id,
		java.lang.String name,
		java.lang.String symbol) {

		super (
			id,
			name,
			symbol);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}