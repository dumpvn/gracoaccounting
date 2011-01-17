package com.mpe.financial.model;

import com.mpe.financial.model.base.BasePurchaseRequestDetailPK;

public class PurchaseRequestDetailPK extends BasePurchaseRequestDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PurchaseRequestDetailPK () {}
	
	public PurchaseRequestDetailPK (
		com.mpe.financial.model.PurchaseRequest purchaseRequest,
		com.mpe.financial.model.Item item) {

		super (
			purchaseRequest,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}