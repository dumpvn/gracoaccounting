package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseReturToVendorDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.ReturToVendor returToVendor;
	private com.mpe.financial.model.Item item;


	public BaseReturToVendorDetailPK () {}
	
	public BaseReturToVendorDetailPK (
		com.mpe.financial.model.ReturToVendor returToVendor,
		com.mpe.financial.model.Item item) {

		this.setReturToVendor(returToVendor);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: retur_to_vendor_id
	 */
	public com.mpe.financial.model.ReturToVendor getReturToVendor () {
		return returToVendor;
	}

	/**
	 * Set the value related to the column: retur_to_vendor_id
	 * @param returToVendor the retur_to_vendor_id value
	 */
	public void setReturToVendor (com.mpe.financial.model.ReturToVendor returToVendor) {
		this.returToVendor = returToVendor;
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
		if (!(obj instanceof com.mpe.financial.model.ReturToVendorDetailPK)) return false;
		else {
			com.mpe.financial.model.ReturToVendorDetailPK mObj = (com.mpe.financial.model.ReturToVendorDetailPK) obj;
			if (null != this.getReturToVendor() && null != mObj.getReturToVendor()) {
				if (!this.getReturToVendor().equals(mObj.getReturToVendor())) {
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
			if (null != this.getReturToVendor()) {
				sb.append(this.getReturToVendor().hashCode());
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