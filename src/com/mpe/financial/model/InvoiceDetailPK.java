package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInvoiceDetailPK;

public class InvoiceDetailPK extends BaseInvoiceDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoiceDetailPK () {}
	
	public InvoiceDetailPK (
		com.mpe.financial.model.Invoice invoice,
		com.mpe.financial.model.Item item) {

		super (
			invoice,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}