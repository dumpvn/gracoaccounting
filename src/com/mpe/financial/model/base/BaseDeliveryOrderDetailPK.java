package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseDeliveryOrderDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.DeliveryOrder deliveryOrder;
	private com.mpe.financial.model.Item item;


	public BaseDeliveryOrderDetailPK () {}
	
	public BaseDeliveryOrderDetailPK (
		com.mpe.financial.model.DeliveryOrder deliveryOrder,
		com.mpe.financial.model.Item item) {

		this.setDeliveryOrder(deliveryOrder);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: delivery_order_id
	 */
	public com.mpe.financial.model.DeliveryOrder getDeliveryOrder () {
		return deliveryOrder;
	}

	/**
	 * Set the value related to the column: delivery_order_id
	 * @param deliveryOrder the delivery_order_id value
	 */
	public void setDeliveryOrder (com.mpe.financial.model.DeliveryOrder deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
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
		if (!(obj instanceof com.mpe.financial.model.DeliveryOrderDetailPK)) return false;
		else {
			com.mpe.financial.model.DeliveryOrderDetailPK mObj = (com.mpe.financial.model.DeliveryOrderDetailPK) obj;
			if (null != this.getDeliveryOrder() && null != mObj.getDeliveryOrder()) {
				if (!this.getDeliveryOrder().equals(mObj.getDeliveryOrder())) {
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
			if (null != this.getDeliveryOrder()) {
				sb.append(this.getDeliveryOrder().hashCode());
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