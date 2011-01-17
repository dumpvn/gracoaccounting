package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInvoicePrepayment;



public class InvoicePrepayment extends BaseInvoicePrepayment {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoicePrepayment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InvoicePrepayment (com.mpe.financial.model.InvoicePrepaymentPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InvoicePrepayment (
		com.mpe.financial.model.InvoicePrepaymentPK id,
		double amount) {

		super (
			id,
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/


}