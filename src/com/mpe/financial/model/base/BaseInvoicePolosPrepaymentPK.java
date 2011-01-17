package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseInvoicePolosPrepaymentPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.InvoicePolos invoicePolos;
	private com.mpe.financial.model.CustomerPrepayment customerPrepayment;


	public BaseInvoicePolosPrepaymentPK () {}
	
	public BaseInvoicePolosPrepaymentPK (
		com.mpe.financial.model.InvoicePolos invoicePolos,
		com.mpe.financial.model.CustomerPrepayment customerPrepayment) {

		this.setInvoicePolos(invoicePolos);
		this.setCustomerPrepayment(customerPrepayment);
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
		if (!(obj instanceof com.mpe.financial.model.InvoicePolosPrepaymentPK)) return false;
		else {
			com.mpe.financial.model.InvoicePolosPrepaymentPK mObj = (com.mpe.financial.model.InvoicePolosPrepaymentPK) obj;
			if (null != this.getInvoicePolos() && null != mObj.getInvoicePolos()) {
				if (!this.getInvoicePolos().equals(mObj.getInvoicePolos())) {
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
			if (null != this.getInvoicePolos()) {
				sb.append(this.getInvoicePolos().hashCode());
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