package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseVendorsCommunicationPK;

public class VendorsCommunicationPK extends BaseVendorsCommunicationPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorsCommunicationPK () {}
	
	public VendorsCommunicationPK (
		com.mpe.financial.model.Vendors vendors,
		java.lang.String contactPerson) {

		super (
			vendors,
			contactPerson);
	}
/*[CONSTRUCTOR MARKER END]*/


}