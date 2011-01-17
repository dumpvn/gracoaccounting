package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BasePurchaseRequestDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.PurchaseRequest purchaseRequest;
	private com.mpe.financial.model.Item item;


	public BasePurchaseRequestDetailPK () {}
	
	public BasePurchaseRequestDetailPK (
		com.mpe.financial.model.PurchaseRequest purchaseRequest,
		com.mpe.financial.model.Item item) {

		this.setPurchaseRequest(purchaseRequest);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: purchase_request_id
	 */
	public com.mpe.financial.model.PurchaseRequest getPurchaseRequest () {
		return purchaseRequest;
	}

	/**
	 * Set the value related to the column: purchase_request_id
	 * @param purchaseRequest the purchase_request_id value
	 */
	public void setPurchaseRequest (com.mpe.financial.model.PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
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
		if (!(obj instanceof com.mpe.financial.model.PurchaseRequestDetailPK)) return false;
		else {
			com.mpe.financial.model.PurchaseRequestDetailPK mObj = (com.mpe.financial.model.PurchaseRequestDetailPK) obj;
			if (null != this.getPurchaseRequest() && null != mObj.getPurchaseRequest()) {
				if (!this.getPurchaseRequest().equals(mObj.getPurchaseRequest())) {
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
			if (null != this.getPurchaseRequest()) {
				sb.append(this.getPurchaseRequest().hashCode());
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