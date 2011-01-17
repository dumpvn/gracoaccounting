package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the customer_prepayment table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="customer_prepayment"
 */

public abstract class BaseInvoiceCustomerPrepaymentFK  implements Serializable {

	public static String REF = "InvoiceCustomerPrepaymentFK";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BaseInvoiceCustomerPrepaymentFK () {
		initialize();
	}

	protected void initialize () {}



	// fields
	private double amount;

	// many to one
	private com.mpe.financial.model.Invoice invoice;






	/**
	 * Return the value associated with the column: amount
	 */
	public double getAmount () {
		return amount;
	}

	/**
	 * Set the value related to the column: amount
	 * @param amount the amount value
	 */
	public void setAmount (double amount) {
		this.amount = amount;
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








	public String toString () {
		return super.toString();
	}


}