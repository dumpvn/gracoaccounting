package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BasePosOrderDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.PosOrder posOrder;
	private com.mpe.financial.model.Item item;


	public BasePosOrderDetailPK () {}
	
	public BasePosOrderDetailPK (
		com.mpe.financial.model.PosOrder posOrder,
		com.mpe.financial.model.Item item) {

		this.setPosOrder(posOrder);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: pos_order_id
	 */
	public com.mpe.financial.model.PosOrder getPosOrder () {
		return posOrder;
	}

	/**
	 * Set the value related to the column: pos_order_id
	 * @param posOrder the pos_order_id value
	 */
	public void setPosOrder (com.mpe.financial.model.PosOrder posOrder) {
		this.posOrder = posOrder;
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
		if (!(obj instanceof com.mpe.financial.model.PosOrderDetailPK)) return false;
		else {
			com.mpe.financial.model.PosOrderDetailPK mObj = (com.mpe.financial.model.PosOrderDetailPK) obj;
			if (null != this.getPosOrder() && null != mObj.getPosOrder()) {
				if (!this.getPosOrder().equals(mObj.getPosOrder())) {
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
			if (null != this.getPosOrder()) {
				sb.append(this.getPosOrder().hashCode());
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