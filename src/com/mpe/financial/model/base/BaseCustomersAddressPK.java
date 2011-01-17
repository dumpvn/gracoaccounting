package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseCustomersAddressPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String invoiceTaxAddress;
	private java.lang.String invoiceAddress;
	private java.lang.String deliveryAddress;
	private com.mpe.financial.model.Customers customers;


	public BaseCustomersAddressPK () {}
	
	public BaseCustomersAddressPK (
		java.lang.String invoiceTaxAddress,
		java.lang.String invoiceAddress,
		java.lang.String deliveryAddress,
		com.mpe.financial.model.Customers customers) {

		this.setInvoiceTaxAddress(invoiceTaxAddress);
		this.setInvoiceAddress(invoiceAddress);
		this.setDeliveryAddress(deliveryAddress);
		this.setCustomers(customers);
	}


	/**
	 * Return the value associated with the column: is_invoice_tax_address
	 */
	public java.lang.String getInvoiceTaxAddress () {
		return invoiceTaxAddress;
	}

	/**
	 * Set the value related to the column: is_invoice_tax_address
	 * @param invoiceTaxAddress the is_invoice_tax_address value
	 */
	public void setInvoiceTaxAddress (java.lang.String invoiceTaxAddress) {
		this.invoiceTaxAddress = invoiceTaxAddress;
	}



	/**
	 * Return the value associated with the column: is_invoice_address
	 */
	public java.lang.String getInvoiceAddress () {
		return invoiceAddress;
	}

	/**
	 * Set the value related to the column: is_invoice_address
	 * @param invoiceAddress the is_invoice_address value
	 */
	public void setInvoiceAddress (java.lang.String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}



	/**
	 * Return the value associated with the column: is_delivery_address
	 */
	public java.lang.String getDeliveryAddress () {
		return deliveryAddress;
	}

	/**
	 * Set the value related to the column: is_delivery_address
	 * @param deliveryAddress the is_delivery_address value
	 */
	public void setDeliveryAddress (java.lang.String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}



	/**
	 * Return the value associated with the column: customer_id
	 */
	public com.mpe.financial.model.Customers getCustomers () {
		return customers;
	}

	/**
	 * Set the value related to the column: customer_id
	 * @param customers the customer_id value
	 */
	public void setCustomers (com.mpe.financial.model.Customers customers) {
		this.customers = customers;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CustomersAddressPK)) return false;
		else {
			com.mpe.financial.model.CustomersAddressPK mObj = (com.mpe.financial.model.CustomersAddressPK) obj;
			if (null != this.getInvoiceTaxAddress() && null != mObj.getInvoiceTaxAddress()) {
				if (!this.getInvoiceTaxAddress().equals(mObj.getInvoiceTaxAddress())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getInvoiceAddress() && null != mObj.getInvoiceAddress()) {
				if (!this.getInvoiceAddress().equals(mObj.getInvoiceAddress())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getDeliveryAddress() && null != mObj.getDeliveryAddress()) {
				if (!this.getDeliveryAddress().equals(mObj.getDeliveryAddress())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getCustomers() && null != mObj.getCustomers()) {
				if (!this.getCustomers().equals(mObj.getCustomers())) {
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
			if (null != this.getInvoiceTaxAddress()) {
				sb.append(this.getInvoiceTaxAddress().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getInvoiceAddress()) {
				sb.append(this.getInvoiceAddress().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getDeliveryAddress()) {
				sb.append(this.getDeliveryAddress().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getCustomers()) {
				sb.append(this.getCustomers().hashCode());
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