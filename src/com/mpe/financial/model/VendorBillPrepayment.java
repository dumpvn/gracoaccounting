package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseVendorBillPrepayment;



public class VendorBillPrepayment extends BaseVendorBillPrepayment {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorBillPrepayment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public VendorBillPrepayment (com.mpe.financial.model.VendorBillPrepaymentPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public VendorBillPrepayment (
		com.mpe.financial.model.VendorBillPrepaymentPK id,
		double amount) {

		super (
			id,
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/


}