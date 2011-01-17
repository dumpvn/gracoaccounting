package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BasePaymentToVendorDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.VendorBill vendorBill;
	private com.mpe.financial.model.PaymentToVendor paymentToVendor;


	public BasePaymentToVendorDetailPK () {}
	
	public BasePaymentToVendorDetailPK (
		com.mpe.financial.model.VendorBill vendorBill,
		com.mpe.financial.model.PaymentToVendor paymentToVendor) {

		this.setVendorBill(vendorBill);
		this.setPaymentToVendor(paymentToVendor);
	}


	/**
	 * Return the value associated with the column: vendor_bill_id
	 */
	public com.mpe.financial.model.VendorBill getVendorBill () {
		return vendorBill;
	}

	/**
	 * Set the value related to the column: vendor_bill_id
	 * @param vendorBill the vendor_bill_id value
	 */
	public void setVendorBill (com.mpe.financial.model.VendorBill vendorBill) {
		this.vendorBill = vendorBill;
	}



	/**
	 * Return the value associated with the column: payment_to_vendor_id
	 */
	public com.mpe.financial.model.PaymentToVendor getPaymentToVendor () {
		return paymentToVendor;
	}

	/**
	 * Set the value related to the column: payment_to_vendor_id
	 * @param paymentToVendor the payment_to_vendor_id value
	 */
	public void setPaymentToVendor (com.mpe.financial.model.PaymentToVendor paymentToVendor) {
		this.paymentToVendor = paymentToVendor;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.PaymentToVendorDetailPK)) return false;
		else {
			com.mpe.financial.model.PaymentToVendorDetailPK mObj = (com.mpe.financial.model.PaymentToVendorDetailPK) obj;
			if (null != this.getVendorBill() && null != mObj.getVendorBill()) {
				if (!this.getVendorBill().equals(mObj.getVendorBill())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getPaymentToVendor() && null != mObj.getPaymentToVendor()) {
				if (!this.getPaymentToVendor().equals(mObj.getPaymentToVendor())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuffer sb = new StringBuffer();
			if (null != this.getVendorBill()) {
				sb.append(this.getVendorBill().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getPaymentToVendor()) {
				sb.append(this.getPaymentToVendor().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}