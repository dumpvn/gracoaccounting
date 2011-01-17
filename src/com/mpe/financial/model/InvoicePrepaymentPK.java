package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInvoicePrepaymentPK;

public class InvoicePrepaymentPK extends BaseInvoicePrepaymentPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoicePrepaymentPK () {}
	
	public InvoicePrepaymentPK (
		com.mpe.financial.model.Invoice invoice,
		com.mpe.financial.model.CustomerPrepayment customerPrepayment) {

		super (
			invoice,
			customerPrepayment);
	}
/*[CONSTRUCTOR MARKER END]*/


}