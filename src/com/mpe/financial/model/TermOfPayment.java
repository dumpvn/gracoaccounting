package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseTermOfPayment;



public class TermOfPayment extends BaseTermOfPayment {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TermOfPayment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TermOfPayment (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TermOfPayment (
		long id,
		java.lang.String code,
		java.lang.String name,
		int days) {

		super (
			id,
			code,
			name,
			days);
	}

/*[CONSTRUCTOR MARKER END]*/


}