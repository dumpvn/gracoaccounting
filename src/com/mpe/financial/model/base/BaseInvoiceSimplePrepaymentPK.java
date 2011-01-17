package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseInvoiceSimplePrepaymentPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.InvoiceSimple invoiceSimple;
	private com.mpe.financial.model.CustomerPrepayment customerPrepayment;


	public BaseInvoiceSimplePrepaymentPK () {}
	
	public BaseInvoiceSimplePrepaymentPK (
		com.mpe.financial.model.InvoiceSimple invoiceSimple,
		com.mpe.financial.model.CustomerPrepayment customerPrepayment) {

		this.setInvoiceSimple(invoiceSimple);
		this.setCustomerPrepayment(customerPrepayment);
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
		if (!(obj instanceof com.mpe.financial.model.InvoiceSimplePrepaymentPK)) return false;
		else {
			com.mpe.financial.model.InvoiceSimplePrepaymentPK mObj = (com.mpe.financial.model.InvoiceSimplePrepaymentPK) obj;
			if (null != this.getInvoiceSimple() && null != mObj.getInvoiceSimple()) {
				if (!this.getInvoiceSimple().equals(mObj.getInvoiceSimple())) {
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
			if (null != this.getInvoiceSimple()) {
				sb.append(this.getInvoiceSimple().hashCode());
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