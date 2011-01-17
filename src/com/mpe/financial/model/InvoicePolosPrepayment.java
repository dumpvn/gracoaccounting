package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInvoicePolosPrepayment;



public class InvoicePolosPrepayment extends BaseInvoicePolosPrepayment {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoicePolosPrepayment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InvoicePolosPrepayment (com.mpe.financial.model.InvoicePolosPrepaymentPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InvoicePolosPrepayment (
		com.mpe.financial.model.InvoicePolosPrepaymentPK id,
		double amount) {

		super (
			id,
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/


}