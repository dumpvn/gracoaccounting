package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseVendorBillPrepaymentPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.VendorBill vendorBill;
	private com.mpe.financial.model.PrepaymentToVendor prepaymentToVendor;


	public BaseVendorBillPrepaymentPK () {}
	
	public BaseVendorBillPrepaymentPK (
		com.mpe.financial.model.VendorBill vendorBill,
		com.mpe.financial.model.PrepaymentToVendor prepaymentToVendor) {

		this.setVendorBill(vendorBill);
		this.setPrepaymentToVendor(prepaymentToVendor);
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
	 * Return the value associated with the column: prepayment_to_vendor_id
	 */
	public com.mpe.financial.model.PrepaymentToVendor getPrepaymentToVendor () {
		return prepaymentToVendor;
	}

	/**
	 * Set the value related to the column: prepayment_to_vendor_id
	 * @param prepaymentToVendor the prepayment_to_vendor_id value
	 */
	public void setPrepaymentToVendor (com.mpe.financial.model.PrepaymentToVendor prepaymentToVendor) {
		this.prepaymentToVendor = prepaymentToVendor;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.VendorBillPrepaymentPK)) return false;
		else {
			com.mpe.financial.model.VendorBillPrepaymentPK mObj = (com.mpe.financial.model.VendorBillPrepaymentPK) obj;
			if (null != this.getVendorBill() && null != mObj.getVendorBill()) {
				if (!this.getVendorBill().equals(mObj.getVendorBill())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getPrepaymentToVendor() && null != mObj.getPrepaymentToVendor()) {
				if (!this.getPrepaymentToVendor().equals(mObj.getPrepaymentToVendor())) {
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
			if (null != this.getPrepaymentToVendor()) {
				sb.append(this.getPrepaymentToVendor().hashCode());
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