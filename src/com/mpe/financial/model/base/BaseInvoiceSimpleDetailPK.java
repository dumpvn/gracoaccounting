package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseInvoiceSimpleDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.InvoiceSimple invoiceSimple;
	private com.mpe.financial.model.Item item;


	public BaseInvoiceSimpleDetailPK () {}
	
	public BaseInvoiceSimpleDetailPK (
		com.mpe.financial.model.InvoiceSimple invoiceSimple,
		com.mpe.financial.model.Item item) {

		this.setInvoiceSimple(invoiceSimple);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: invoice_simple_id
	 */
	public com.mpe.financial.model.InvoiceSimple getInvoiceSimple () {
		return invoiceSimple;
	}

	/**
	 * Set the value related to the column: invoice_simple_id
	 * @param invoiceSimple the invoice_simple_id value
	 */
	public void setInvoiceSimple (com.mpe.financial.model.InvoiceSimple invoiceSimple) {
		this.invoiceSimple = invoiceSimple;
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
		if (!(obj instanceof com.mpe.financial.model.InvoiceSimpleDetailPK)) return false;
		else {
			com.mpe.financial.model.InvoiceSimpleDetailPK mObj = (com.mpe.financial.model.InvoiceSimpleDetailPK) obj;
			if (null != this.getInvoiceSimple() && null != mObj.getInvoiceSimple()) {
				if (!this.getInvoiceSimple().equals(mObj.getInvoiceSimple())) {
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
			if (null != this.getInvoiceSimple()) {
				sb.append(this.getInvoiceSimple().hashCode());
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