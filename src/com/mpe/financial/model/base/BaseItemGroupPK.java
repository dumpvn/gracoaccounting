package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseItemGroupPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.Item item;
	private com.mpe.financial.model.Item group;


	public BaseItemGroupPK () {}
	
	public BaseItemGroupPK (
		com.mpe.financial.model.Item item,
		com.mpe.financial.model.Item group) {

		this.setItem(item);
		this.setGroup(group);
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



	/**
	 * Return the value associated with the column: group_id
	 */
	public com.mpe.financial.model.Item getGroup () {
		return group;
	}

	/**
	 * Set the value related to the column: group_id
	 * @param group the group_id value
	 */
	public void setGroup (com.mpe.financial.model.Item group) {
		this.group = group;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemGroupPK)) return false;
		else {
			com.mpe.financial.model.ItemGroupPK mObj = (com.mpe.financial.model.ItemGroupPK) obj;
			if (null != this.getItem() && null != mObj.getItem()) {
				if (!this.getItem().equals(mObj.getItem())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getGroup() && null != mObj.getGroup()) {
				if (!this.getGroup().equals(mObj.getGroup())) {
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
			if (null != this.getItem()) {
				sb.append(this.getItem().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getGroup()) {
				sb.append(this.getGroup().hashCode());
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