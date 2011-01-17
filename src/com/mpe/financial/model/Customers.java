package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCustomers;



public class Customers extends BaseCustomers {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Customers () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Customers (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Customers (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String company,
		boolean blockOverCreditLimit) {

		super (
			id,
			organization,
			company,
			blockOverCreditLimit);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}

	public String getFormatedCreditLimit() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getCreditLimit());
	}

}