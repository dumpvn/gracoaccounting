package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseCustomersCommunicationPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String contactPerson;
	private com.mpe.financial.model.Customers customers;


	public BaseCustomersCommunicationPK () {}
	
	public BaseCustomersCommunicationPK (
		java.lang.String contactPerson,
		com.mpe.financial.model.Customers customers) {

		this.setContactPerson(contactPerson);
		this.setCustomers(customers);
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



	/**
	 * Return the value associated with the column: customer_id
	 */
	public com.mpe.financial.model.Customers getCustomers () {
		return customers;
	}

	/**
	 * Set the value related to the column: customer_id
	 * @param customers the customer_id value
	 */
	public void setCustomers (com.mpe.financial.model.Customers customers) {
		this.customers = customers;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CustomersCommunicationPK)) return false;
		else {
			com.mpe.financial.model.CustomersCommunicationPK mObj = (com.mpe.financial.model.CustomersCommunicationPK) obj;
			if (null != this.getContactPerson() && null != mObj.getContactPerson()) {
				if (!this.getContactPerson().equals(mObj.getContactPerson())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getCustomers() && null != mObj.getCustomers()) {
				if (!this.getCustomers().equals(mObj.getCustomers())) {
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
			if (null != this.getContactPerson()) {
				sb.append(this.getContactPerson().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getCustomers()) {
				sb.append(this.getCustomers().hashCode());
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