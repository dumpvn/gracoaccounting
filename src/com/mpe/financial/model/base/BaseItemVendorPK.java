package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseItemVendorPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.Vendors vendor;
	private com.mpe.financial.model.Item item;


	public BaseItemVendorPK () {}
	
	public BaseItemVendorPK (
		com.mpe.financial.model.Vendors vendor,
		com.mpe.financial.model.Item item) {

		this.setVendor(vendor);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: vendor_id
	 */
	public com.mpe.financial.model.Vendors getVendor () {
		return vendor;
	}

	/**
	 * Set the value related to the column: vendor_id
	 * @param vendor the vendor_id value
	 */
	public void setVendor (com.mpe.financial.model.Vendors vendor) {
		this.vendor = vendor;
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
		if (!(obj instanceof com.mpe.financial.model.ItemVendorPK)) return false;
		else {
			com.mpe.financial.model.ItemVendorPK mObj = (com.mpe.financial.model.ItemVendorPK) obj;
			if (null != this.getVendor() && null != mObj.getVendor()) {
				if (!this.getVendor().equals(mObj.getVendor())) {
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
			if (null != this.getVendor()) {
				sb.append(this.getVendor().hashCode());
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