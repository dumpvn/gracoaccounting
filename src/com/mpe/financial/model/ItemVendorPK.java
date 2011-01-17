package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseItemVendorPK;

public class ItemVendorPK extends BaseItemVendorPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemVendorPK () {}
	
	public ItemVendorPK (
		com.mpe.financial.model.Vendors vendor,
		com.mpe.financial.model.Item item) {

		super (
			vendor,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}