package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseTax;



public class Tax extends BaseTax {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Tax () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Tax (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Tax (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String number,
		java.lang.String name,
		double quantity,
		boolean ap) {

		super (
			id,
			organization,
			number,
			name,
			quantity,
			ap);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}