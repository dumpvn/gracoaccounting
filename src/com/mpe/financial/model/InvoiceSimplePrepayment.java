package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInvoiceSimplePrepayment;



public class InvoiceSimplePrepayment extends BaseInvoiceSimplePrepayment {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoiceSimplePrepayment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InvoiceSimplePrepayment (com.mpe.financial.model.InvoiceSimplePrepaymentPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InvoiceSimplePrepayment (
		com.mpe.financial.model.InvoiceSimplePrepaymentPK id,
		double amount) {

		super (
			id,
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/


}