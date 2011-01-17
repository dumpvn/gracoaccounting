package com.mpe.financial.model;

import com.mpe.financial.model.base.BasePaymentToVendorDetailPK;

public class PaymentToVendorDetailPK extends BasePaymentToVendorDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PaymentToVendorDetailPK () {}
	
	public PaymentToVendorDetailPK (
		com.mpe.financial.model.VendorBill vendorBill,
		com.mpe.financial.model.PaymentToVendor paymentToVendor) {

		super (
			vendorBill,
			paymentToVendor);
	}
/*[CONSTRUCTOR MARKER END]*/


}