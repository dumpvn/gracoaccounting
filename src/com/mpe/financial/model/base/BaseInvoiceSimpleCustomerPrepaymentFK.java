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

public abstract class BaseInvoiceSimpleCustomerPrepaymentFK  implements Serializable {

	public static String REF = "InvoiceSimpleCustomerPrepaymentFK";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BaseInvoiceSimpleCustomerPrepaymentFK () {
		initialize();
	}

	protected void initialize () {}



	// fields
	private double amount;

	// many to one
	private com.mpe.financial.model.InvoiceSimple invoiceSimple;






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








	public String toString () {
		return super.toString();
	}


}