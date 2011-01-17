package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseVendorsAddressPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String addressCode;
	private com.mpe.financial.model.Vendors vendors;


	public BaseVendorsAddressPK () {}
	
	public BaseVendorsAddressPK (
		java.lang.String addressCode,
		com.mpe.financial.model.Vendors vendors) {

		this.setAddressCode(addressCode);
		this.setVendors(vendors);
	}


	/**
	 * Return the value associated with the column: address_code
	 */
	public java.lang.String getAddressCode () {
		return addressCode;
	}

	/**
	 * Set the value related to the column: address_code
	 * @param addressCode the address_code value
	 */
	public void setAddressCode (java.lang.String addressCode) {
		this.addressCode = addressCode;
	}



	/**
	 * Return the value associated with the column: vendor_id
	 */
	public com.mpe.financial.model.Vendors getVendors () {
		return vendors;
	}

	/**
	 * Set the value related to the column: vendor_id
	 * @param vendors the vendor_id value
	 */
	public void setVendors (com.mpe.financial.model.Vendors vendors) {
		this.vendors = vendors;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.VendorsAddressPK)) return false;
		else {
			com.mpe.financial.model.VendorsAddressPK mObj = (com.mpe.financial.model.VendorsAddressPK) obj;
			if (null != this.getAddressCode() && null != mObj.getAddressCode()) {
				if (!this.getAddressCode().equals(mObj.getAddressCode())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getVendors() && null != mObj.getVendors()) {
				if (!this.getVendors().equals(mObj.getVendors())) {
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
			if (null != this.getAddressCode()) {
				sb.append(this.getAddressCode().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getVendors()) {
				sb.append(this.getVendors().hashCode());
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