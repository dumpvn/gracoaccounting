package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseReturToVendorDetailPK;

public class ReturToVendorDetailPK extends BaseReturToVendorDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ReturToVendorDetailPK () {}
	
	public ReturToVendorDetailPK (
		com.mpe.financial.model.ReturToVendor returToVendor,
		com.mpe.financial.model.Item item) {

		super (
			returToVendor,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}