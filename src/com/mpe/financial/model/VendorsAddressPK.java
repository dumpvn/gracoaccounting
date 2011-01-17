package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseVendorsAddressPK;

public class VendorsAddressPK extends BaseVendorsAddressPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorsAddressPK () {}
	
	public VendorsAddressPK (
		java.lang.String addressCode,
		com.mpe.financial.model.Vendors vendors) {

		super (
			addressCode,
			vendors);
	}
/*[CONSTRUCTOR MARKER END]*/


}