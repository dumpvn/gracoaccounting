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

public abstract class BaseInvoicePolosCustomerPrepaymentFK  implements Serializable {

	public static String REF = "InvoicePolosCustomerPrepaymentFK";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BaseInvoicePolosCustomerPrepaymentFK () {
		initialize();
	}

	protected void initialize () {}



	// fields
	private double amount;

	// many to one
	private com.mpe.financial.model.InvoicePolos invoicePolos;






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








	public String toString () {
		return super.toString();
	}


}