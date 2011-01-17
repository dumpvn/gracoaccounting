package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCustomersCategory;



public class CustomersCategory extends BaseCustomersCategory {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomersCategory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CustomersCategory (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CustomersCategory (
		long id,
		java.lang.String name) {

		super (
			id,
			name);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}