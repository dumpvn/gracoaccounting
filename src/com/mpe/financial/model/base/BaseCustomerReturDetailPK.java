package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseCustomerReturDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.CustomerRetur customerRetur;
	private com.mpe.financial.model.Item item;


	public BaseCustomerReturDetailPK () {}
	
	public BaseCustomerReturDetailPK (
		com.mpe.financial.model.CustomerRetur customerRetur,
		com.mpe.financial.model.Item item) {

		this.setCustomerRetur(customerRetur);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: customer_retur_id
	 */
	public com.mpe.financial.model.CustomerRetur getCustomerRetur () {
		return customerRetur;
	}

	/**
	 * Set the value related to the column: customer_retur_id
	 * @param customerRetur the customer_retur_id value
	 */
	public void setCustomerRetur (com.mpe.financial.model.CustomerRetur customerRetur) {
		this.customerRetur = customerRetur;
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
		if (!(obj instanceof com.mpe.financial.model.CustomerReturDetailPK)) return false;
		else {
			com.mpe.financial.model.CustomerReturDetailPK mObj = (com.mpe.financial.model.CustomerReturDetailPK) obj;
			if (null != this.getCustomerRetur() && null != mObj.getCustomerRetur()) {
				if (!this.getCustomerRetur().equals(mObj.getCustomerRetur())) {
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
			if (null != this.getCustomerRetur()) {
				sb.append(this.getCustomerRetur().hashCode());
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