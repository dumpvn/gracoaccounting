package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseVendors;



public class Vendors extends BaseVendors {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Vendors () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Vendors (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Vendors (
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