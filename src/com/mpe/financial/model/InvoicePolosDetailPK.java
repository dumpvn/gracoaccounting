package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInvoicePolosDetailPK;

public class InvoicePolosDetailPK extends BaseInvoicePolosDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoicePolosDetailPK () {}
	
	public InvoicePolosDetailPK (
		com.mpe.financial.model.InvoicePolos invoicePolos,
		com.mpe.financial.model.Item item) {

		super (
			invoicePolos,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}