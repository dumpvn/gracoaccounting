package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseItemCustomFieldPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.Item item;
	private com.mpe.financial.model.CustomField customField;


	public BaseItemCustomFieldPK () {}
	
	public BaseItemCustomFieldPK (
		com.mpe.financial.model.Item item,
		com.mpe.financial.model.CustomField customField) {

		this.setItem(item);
		this.setCustomField(customField);
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
	 * Return the value associated with the column: custom_field_id
	 */
	public com.mpe.financial.model.CustomField getCustomField () {
		return customField;
	}

	/**
	 * Set the value related to the column: custom_field_id
	 * @param customField the custom_field_id value
	 */
	public void setCustomField (com.mpe.financial.model.CustomField customField) {
		this.customField = customField;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemCustomFieldPK)) return false;
		else {
			com.mpe.financial.model.ItemCustomFieldPK mObj = (com.mpe.financial.model.ItemCustomFieldPK) obj;
			if (null != this.getItem() && null != mObj.getItem()) {
				if (!this.getItem().equals(mObj.getItem())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getCustomField() && null != mObj.getCustomField()) {
				if (!this.getCustomField().equals(mObj.getCustomField())) {
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
			if (null != this.getCustomField()) {
				sb.append(this.getCustomField().hashCode());
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