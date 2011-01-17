package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInvoiceSimplePrepaymentPK;

public class InvoiceSimplePrepaymentPK extends BaseInvoiceSimplePrepaymentPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoiceSimplePrepaymentPK () {}
	
	public InvoiceSimplePrepaymentPK (
		com.mpe.financial.model.InvoiceSimple invoiceSimple,
		com.mpe.financial.model.CustomerPrepayment customerPrepayment) {

		super (
			invoiceSimple,
			customerPrepayment);
	}
/*[CONSTRUCTOR MARKER END]*/


}