package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseItemUsageDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.ItemUsage itemUsage;
	private com.mpe.financial.model.Item item;


	public BaseItemUsageDetailPK () {}
	
	public BaseItemUsageDetailPK (
		com.mpe.financial.model.ItemUsage itemUsage,
		com.mpe.financial.model.Item item) {

		this.setItemUsage(itemUsage);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: item_usage_id
	 */
	public com.mpe.financial.model.ItemUsage getItemUsage () {
		return itemUsage;
	}

	/**
	 * Set the value related to the column: item_usage_id
	 * @param itemUsage the item_usage_id value
	 */
	public void setItemUsage (com.mpe.financial.model.ItemUsage itemUsage) {
		this.itemUsage = itemUsage;
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
		if (!(obj instanceof com.mpe.financial.model.ItemUsageDetailPK)) return false;
		else {
			com.mpe.financial.model.ItemUsageDetailPK mObj = (com.mpe.financial.model.ItemUsageDetailPK) obj;
			if (null != this.getItemUsage() && null != mObj.getItemUsage()) {
				if (!this.getItemUsage().equals(mObj.getItemUsage())) {
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
			if (null != this.getItemUsage()) {
				sb.append(this.getItemUsage().hashCode());
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