package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInvoicePolosPrepaymentPK;

public class InvoicePolosPrepaymentPK extends BaseInvoicePolosPrepaymentPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoicePolosPrepaymentPK () {}
	
	public InvoicePolosPrepaymentPK (
		com.mpe.financial.model.InvoicePolos invoicePolos,
		com.mpe.financial.model.CustomerPrepayment customerPrepayment) {

		super (
			invoicePolos,
			customerPrepayment);
	}
/*[CONSTRUCTOR MARKER END]*/


}