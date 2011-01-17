package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseStockOpnameDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.StockOpname stockOpname;
	private com.mpe.financial.model.Item item;


	public BaseStockOpnameDetailPK () {}
	
	public BaseStockOpnameDetailPK (
		com.mpe.financial.model.StockOpname stockOpname,
		com.mpe.financial.model.Item item) {

		this.setStockOpname(stockOpname);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: stock_opname_id
	 */
	public com.mpe.financial.model.StockOpname getStockOpname () {
		return stockOpname;
	}

	/**
	 * Set the value related to the column: stock_opname_id
	 * @param stockOpname the stock_opname_id value
	 */
	public void setStockOpname (com.mpe.financial.model.StockOpname stockOpname) {
		this.stockOpname = stockOpname;
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
		if (!(obj instanceof com.mpe.financial.model.StockOpnameDetailPK)) return false;
		else {
			com.mpe.financial.model.StockOpnameDetailPK mObj = (com.mpe.financial.model.StockOpnameDetailPK) obj;
			if (null != this.getStockOpname() && null != mObj.getStockOpname()) {
				if (!this.getStockOpname().equals(mObj.getStockOpname())) {
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
			if (null != this.getStockOpname()) {
				sb.append(this.getStockOpname().hashCode());
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