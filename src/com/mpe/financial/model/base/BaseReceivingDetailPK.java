package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseReceivingDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.Receiving receiving;
	private com.mpe.financial.model.Item item;


	public BaseReceivingDetailPK () {}
	
	public BaseReceivingDetailPK (
		com.mpe.financial.model.Receiving receiving,
		com.mpe.financial.model.Item item) {

		this.setReceiving(receiving);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: receiving_id
	 */
	public com.mpe.financial.model.Receiving getReceiving () {
		return receiving;
	}

	/**
	 * Set the value related to the column: receiving_id
	 * @param receiving the receiving_id value
	 */
	public void setReceiving (com.mpe.financial.model.Receiving receiving) {
		this.receiving = receiving;
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
		if (!(obj instanceof com.mpe.financial.model.ReceivingDetailPK)) return false;
		else {
			com.mpe.financial.model.ReceivingDetailPK mObj = (com.mpe.financial.model.ReceivingDetailPK) obj;
			if (null != this.getReceiving() && null != mObj.getReceiving()) {
				if (!this.getReceiving().equals(mObj.getReceiving())) {
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
			if (null != this.getReceiving()) {
				sb.append(this.getReceiving().hashCode());
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