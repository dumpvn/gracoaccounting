package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseMutationDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.Item item;
	private com.mpe.financial.model.Mutation mutation;


	public BaseMutationDetailPK () {}
	
	public BaseMutationDetailPK (
		com.mpe.financial.model.Item item,
		com.mpe.financial.model.Mutation mutation) {

		this.setItem(item);
		this.setMutation(mutation);
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
	 * Return the value associated with the column: mutation_id
	 */
	public com.mpe.financial.model.Mutation getMutation () {
		return mutation;
	}

	/**
	 * Set the value related to the column: mutation_id
	 * @param mutation the mutation_id value
	 */
	public void setMutation (com.mpe.financial.model.Mutation mutation) {
		this.mutation = mutation;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.MutationDetailPK)) return false;
		else {
			com.mpe.financial.model.MutationDetailPK mObj = (com.mpe.financial.model.MutationDetailPK) obj;
			if (null != this.getItem() && null != mObj.getItem()) {
				if (!this.getItem().equals(mObj.getItem())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMutation() && null != mObj.getMutation()) {
				if (!this.getMutation().equals(mObj.getMutation())) {
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
			if (null != this.getMutation()) {
				sb.append(this.getMutation().hashCode());
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