package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInvoiceSimpleDetailPK;

public class InvoiceSimpleDetailPK extends BaseInvoiceSimpleDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoiceSimpleDetailPK () {}
	
	public InvoiceSimpleDetailPK (
		com.mpe.financial.model.InvoiceSimple invoiceSimple,
		com.mpe.financial.model.Item item) {

		super (
			invoiceSimple,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}