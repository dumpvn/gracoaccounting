package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseInvoiceDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.Invoice invoice;
	private com.mpe.financial.model.Item item;


	public BaseInvoiceDetailPK () {}
	
	public BaseInvoiceDetailPK (
		com.mpe.financial.model.Invoice invoice,
		com.mpe.financial.model.Item item) {

		this.setInvoice(invoice);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: invoice_id
	 */
	public com.mpe.financial.model.Invoice getInvoice () {
		return invoice;
	}

	/**
	 * Set the value related to the column: invoice_id
	 * @param invoice the invoice_id value
	 */
	public void setInvoice (com.mpe.financial.model.Invoice invoice) {
		this.invoice = invoice;
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
		if (!(obj instanceof com.mpe.financial.model.InvoiceDetailPK)) return false;
		else {
			com.mpe.financial.model.InvoiceDetailPK mObj = (com.mpe.financial.model.InvoiceDetailPK) obj;
			if (null != this.getInvoice() && null != mObj.getInvoice()) {
				if (!this.getInvoice().equals(mObj.getInvoice())) {
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
			if (null != this.getInvoice()) {
				sb.append(this.getInvoice().hashCode());
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