package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseSalesOrderDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.SalesOrder salesOrder;
	private com.mpe.financial.model.Item item;


	public BaseSalesOrderDetailPK () {}
	
	public BaseSalesOrderDetailPK (
		com.mpe.financial.model.SalesOrder salesOrder,
		com.mpe.financial.model.Item item) {

		this.setSalesOrder(salesOrder);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: sales_order_id
	 */
	public com.mpe.financial.model.SalesOrder getSalesOrder () {
		return salesOrder;
	}

	/**
	 * Set the value related to the column: sales_order_id
	 * @param salesOrder the sales_order_id value
	 */
	public void setSalesOrder (com.mpe.financial.model.SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
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
		if (!(obj instanceof com.mpe.financial.model.SalesOrderDetailPK)) return false;
		else {
			com.mpe.financial.model.SalesOrderDetailPK mObj = (com.mpe.financial.model.SalesOrderDetailPK) obj;
			if (null != this.getSalesOrder() && null != mObj.getSalesOrder()) {
				if (!this.getSalesOrder().equals(mObj.getSalesOrder())) {
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
			if (null != this.getSalesOrder()) {
				sb.append(this.getSalesOrder().hashCode());
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