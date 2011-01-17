package com.mpe.financial.model;

import com.mpe.financial.model.base.BasePrepaymentToVendorPurchaseOrderFK;



public class PrepaymentToVendorPurchaseOrderFK extends BasePrepaymentToVendorPurchaseOrderFK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PrepaymentToVendorPurchaseOrderFK () {
		super();
	}

	/**
	 * Constructor for required fields
	 */
	public PrepaymentToVendorPurchaseOrderFK (
		double amount) {

		super (
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/


}