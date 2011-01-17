package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BasePurchaseOrderDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.PurchaseOrder purchaseOrder;
	private com.mpe.financial.model.Item item;


	public BasePurchaseOrderDetailPK () {}
	
	public BasePurchaseOrderDetailPK (
		com.mpe.financial.model.PurchaseOrder purchaseOrder,
		com.mpe.financial.model.Item item) {

		this.setPurchaseOrder(purchaseOrder);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: purchase_order_id
	 */
	public com.mpe.financial.model.PurchaseOrder getPurchaseOrder () {
		return purchaseOrder;
	}

	/**
	 * Set the value related to the column: purchase_order_id
	 * @param purchaseOrder the purchase_order_id value
	 */
	public void setPurchaseOrder (com.mpe.financial.model.PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
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
		if (!(obj instanceof com.mpe.financial.model.PurchaseOrderDetailPK)) return false;
		else {
			com.mpe.financial.model.PurchaseOrderDetailPK mObj = (com.mpe.financial.model.PurchaseOrderDetailPK) obj;
			if (null != this.getPurchaseOrder() && null != mObj.getPurchaseOrder()) {
				if (!this.getPurchaseOrder().equals(mObj.getPurchaseOrder())) {
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
			if (null != this.getPurchaseOrder()) {
				sb.append(this.getPurchaseOrder().hashCode());
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