package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseVendorBillPrepaymentPK;

public class VendorBillPrepaymentPK extends BaseVendorBillPrepaymentPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorBillPrepaymentPK () {}
	
	public VendorBillPrepaymentPK (
		com.mpe.financial.model.VendorBill vendorBill,
		com.mpe.financial.model.PrepaymentToVendor prepaymentToVendor) {

		super (
			vendorBill,
			prepaymentToVendor);
	}
/*[CONSTRUCTOR MARKER END]*/


}