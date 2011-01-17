package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseVendorBillDetailPK;

public class VendorBillDetailPK extends BaseVendorBillDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorBillDetailPK () {}
	
	public VendorBillDetailPK (
		com.mpe.financial.model.VendorBill vendorBill,
		com.mpe.financial.model.Item item) {

		super (
			vendorBill,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}