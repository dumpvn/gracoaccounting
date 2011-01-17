package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseInvoicePolosDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.InvoicePolos invoicePolos;
	private com.mpe.financial.model.Item item;


	public BaseInvoicePolosDetailPK () {}
	
	public BaseInvoicePolosDetailPK (
		com.mpe.financial.model.InvoicePolos invoicePolos,
		com.mpe.financial.model.Item item) {

		this.setInvoicePolos(invoicePolos);
		this.setItem(item);
	}


	/**
	 * Return the value associated with the column: invoice_polos_id
	 */
	public com.mpe.financial.model.InvoicePolos getInvoicePolos () {
		return invoicePolos;
	}

	/**
	 * Set the value related to the column: invoice_polos_id
	 * @param invoicePolos the invoice_polos_id value
	 */
	public void setInvoicePolos (com.mpe.financial.model.InvoicePolos invoicePolos) {
		this.invoicePolos = invoicePolos;
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
		if (!(obj instanceof com.mpe.financial.model.InvoicePolosDetailPK)) return false;
		else {
			com.mpe.financial.model.InvoicePolosDetailPK mObj = (com.mpe.financial.model.InvoicePolosDetailPK) obj;
			if (null != this.getInvoicePolos() && null != mObj.getInvoicePolos()) {
				if (!this.getInvoicePolos().equals(mObj.getInvoicePolos())) {
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
			if (null != this.getInvoicePolos()) {
				sb.append(this.getInvoicePolos().hashCode());
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