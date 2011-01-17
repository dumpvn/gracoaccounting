package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseInvoicePrepaymentPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.Invoice invoice;
	private com.mpe.financial.model.CustomerPrepayment customerPrepayment;


	public BaseInvoicePrepaymentPK () {}
	
	public BaseInvoicePrepaymentPK (
		com.mpe.financial.model.Invoice invoice,
		com.mpe.financial.model.CustomerPrepayment customerPrepayment) {

		this.setInvoice(invoice);
		this.setCustomerPrepayment(customerPrepayment);
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
	 * Return the value associated with the column: customer_prepayment_id
	 */
	public com.mpe.financial.model.CustomerPrepayment getCustomerPrepayment () {
		return customerPrepayment;
	}

	/**
	 * Set the value related to the column: customer_prepayment_id
	 * @param customerPrepayment the customer_prepayment_id value
	 */
	public void setCustomerPrepayment (com.mpe.financial.model.CustomerPrepayment customerPrepayment) {
		this.customerPrepayment = customerPrepayment;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.InvoicePrepaymentPK)) return false;
		else {
			com.mpe.financial.model.InvoicePrepaymentPK mObj = (com.mpe.financial.model.InvoicePrepaymentPK) obj;
			if (null != this.getInvoice() && null != mObj.getInvoice()) {
				if (!this.getInvoice().equals(mObj.getInvoice())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getCustomerPrepayment() && null != mObj.getCustomerPrepayment()) {
				if (!this.getCustomerPrepayment().equals(mObj.getCustomerPrepayment())) {
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
			if (null != this.getCustomerPrepayment()) {
				sb.append(this.getCustomerPrepayment().hashCode());
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