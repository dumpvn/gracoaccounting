package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseVendorBillDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.VendorBill vendorBill;
	private com.mpe.financial.model.Item item;


	public BaseVendorBillDetailPK () {}
	
	public BaseVendorBillDetailPK (
		com.mpe.financial.model.VendorBill vendorBill,
		com.mpe.financial.model.Item item) {

		this.setVendorBill(vendorBill);
		this.setItem(item);
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
	 * Return the value associated with the column: item_id
	 */
	public com.mpe.financial.model.Item getItem () {
		return item;
	}

	/**
	 * Set the value related to the column: item_id
	 * @param item the item_id value
	 */
	public void setItem (com.mpe.financial.model.Item item) {
		this.item = item;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.VendorBillDetailPK)) return false;
		else {
			com.mpe.financial.model.VendorBillDetailPK mObj = (com.mpe.financial.model.VendorBillDetailPK) obj;
			if (null != this.getVendorBill() && null != mObj.getVendorBill()) {
				if (!this.getVendorBill().equals(mObj.getVendorBill())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getItem() && null != mObj.getItem()) {
				if (!this.getItem().equals(mObj.getItem())) {
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
			if (null != this.getItem()) {
				sb.append(this.getItem().hashCode());
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