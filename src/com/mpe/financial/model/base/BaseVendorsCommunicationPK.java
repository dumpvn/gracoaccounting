package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseVendorsCommunicationPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.Vendors vendors;
	private java.lang.String contactPerson;


	public BaseVendorsCommunicationPK () {}
	
	public BaseVendorsCommunicationPK (
		com.mpe.financial.model.Vendors vendors,
		java.lang.String contactPerson) {

		this.setVendors(vendors);
		this.setContactPerson(contactPerson);
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



	/**
	 * Return the value associated with the column: contact_person
	 */
	public java.lang.String getContactPerson () {
		return contactPerson;
	}

	/**
	 * Set the value related to the column: contact_person
	 * @param contactPerson the contact_person value
	 */
	public void setContactPerson (java.lang.String contactPerson) {
		this.contactPerson = contactPerson;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.VendorsCommunicationPK)) return false;
		else {
			com.mpe.financial.model.VendorsCommunicationPK mObj = (com.mpe.financial.model.VendorsCommunicationPK) obj;
			if (null != this.getVendors() && null != mObj.getVendors()) {
				if (!this.getVendors().equals(mObj.getVendors())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getContactPerson() && null != mObj.getContactPerson()) {
				if (!this.getContactPerson().equals(mObj.getContactPerson())) {
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
			if (null != this.getVendors()) {
				sb.append(this.getVendors().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getContactPerson()) {
				sb.append(this.getContactPerson().hashCode());
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