package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseLendingDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.Lending lending;
	private com.mpe.financial.model.Item item;


	public BaseLendingDetailPK () {}
	
	public BaseLendingDetailPK (
		com.mpe.financial.model.Lending lending,
		com.mpe.financial.model.Item item) {

		this.setLending(lending);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: lending_id
	 */
	public com.mpe.financial.model.Lending getLending () {
		return lending;
	}

	/**
	 * Set the value related to the column: lending_id
	 * @param lending the lending_id value
	 */
	public void setLending (com.mpe.financial.model.Lending lending) {
		this.lending = lending;
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
		if (!(obj instanceof com.mpe.financial.model.LendingDetailPK)) return false;
		else {
			com.mpe.financial.model.LendingDetailPK mObj = (com.mpe.financial.model.LendingDetailPK) obj;
			if (null != this.getLending() && null != mObj.getLending()) {
				if (!this.getLending().equals(mObj.getLending())) {
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
			if (null != this.getLending()) {
				sb.append(this.getLending().hashCode());
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