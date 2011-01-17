package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseItemPricePK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.ItemPriceCategory itemPriceCategory;
	private com.mpe.financial.model.Item item;


	public BaseItemPricePK () {}
	
	public BaseItemPricePK (
		com.mpe.financial.model.ItemPriceCategory itemPriceCategory,
		com.mpe.financial.model.Item item) {

		this.setItemPriceCategory(itemPriceCategory);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: item_price_category_id
	 */
	public com.mpe.financial.model.ItemPriceCategory getItemPriceCategory () {
		return itemPriceCategory;
	}

	/**
	 * Set the value related to the column: item_price_category_id
	 * @param itemPriceCategory the item_price_category_id value
	 */
	public void setItemPriceCategory (com.mpe.financial.model.ItemPriceCategory itemPriceCategory) {
		this.itemPriceCategory = itemPriceCategory;
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
		if (!(obj instanceof com.mpe.financial.model.ItemPricePK)) return false;
		else {
			com.mpe.financial.model.ItemPricePK mObj = (com.mpe.financial.model.ItemPricePK) obj;
			if (null != this.getItemPriceCategory() && null != mObj.getItemPriceCategory()) {
				if (!this.getItemPriceCategory().equals(mObj.getItemPriceCategory())) {
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
			if (null != this.getItemPriceCategory()) {
				sb.append(this.getItemPriceCategory().hashCode());
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