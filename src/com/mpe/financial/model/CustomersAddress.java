package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseCustomersAddress;



public class CustomersAddress extends BaseCustomersAddress {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomersAddress () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CustomersAddress (com.mpe.financial.model.CustomersAddressPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CustomersAddress (
		com.mpe.financial.model.CustomersAddressPK id,
		java.lang.String code,
		java.lang.String address) {

		super (
			id,
			code,
			address);
	}

/*[CONSTRUCTOR MARKER END]*/


}