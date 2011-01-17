package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseSalesOrderDetailPK;

public class SalesOrderDetailPK extends BaseSalesOrderDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SalesOrderDetailPK () {}
	
	public SalesOrderDetailPK (
		com.mpe.financial.model.SalesOrder salesOrder,
		com.mpe.financial.model.Item item) {

		super (
			salesOrder,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}