package com.mpe.financial.model;

import com.mpe.financial.model.base.BasePurchaseOrderDetailPK;

public class PurchaseOrderDetailPK extends BasePurchaseOrderDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PurchaseOrderDetailPK () {}
	
	public PurchaseOrderDetailPK (
		com.mpe.financial.model.PurchaseOrder purchaseOrder,
		com.mpe.financial.model.Item item) {

		super (
			purchaseOrder,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}