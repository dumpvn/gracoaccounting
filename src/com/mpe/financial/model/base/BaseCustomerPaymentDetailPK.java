package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseCustomerPaymentDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.CustomerPayment customerPayment;
	private java.lang.String identity;


	public BaseCustomerPaymentDetailPK () {}
	
	public BaseCustomerPaymentDetailPK (
		com.mpe.financial.model.CustomerPayment customerPayment,
		java.lang.String identity) {

		this.setCustomerPayment(customerPayment);
		this.setIdentity(identity);
	}


	/**
	 * Return the value associated with the column: customer_payment_id
	 */
	public com.mpe.financial.model.CustomerPayment getCustomerPayment () {
		return customerPayment;
	}

	/**
	 * Set the value related to the column: customer_payment_id
	 * @param customerPayment the customer_payment_id value
	 */
	public void setCustomerPayment (com.mpe.financial.model.CustomerPayment customerPayment) {
		this.customerPayment = customerPayment;
	}



	/**
	 * Return the value associated with the column: identity
	 */
	public java.lang.String getIdentity () {
		return identity;
	}

	/**
	 * Set the value related to the column: identity
	 * @param identity the identity value
	 */
	public void setIdentity (java.lang.String identity) {
		this.identity = identity;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CustomerPaymentDetailPK)) return false;
		else {
			com.mpe.financial.model.CustomerPaymentDetailPK mObj = (com.mpe.financial.model.CustomerPaymentDetailPK) obj;
			if (null != this.getCustomerPayment() && null != mObj.getCustomerPayment()) {
				if (!this.getCustomerPayment().equals(mObj.getCustomerPayment())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getIdentity() && null != mObj.getIdentity()) {
				if (!this.getIdentity().equals(mObj.getIdentity())) {
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
			if (null != this.getCustomerPayment()) {
				sb.append(this.getCustomerPayment().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getIdentity()) {
				sb.append(this.getIdentity().hashCode());
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