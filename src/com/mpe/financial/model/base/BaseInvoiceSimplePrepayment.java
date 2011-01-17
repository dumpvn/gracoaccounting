package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the invoice_simple_customer_prepayment table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="invoice_simple_customer_prepayment"
 */

public abstract class BaseInvoiceSimplePrepayment  implements Serializable {

	public static String REF = "InvoiceSimplePrepayment";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BaseInvoiceSimplePrepayment () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInvoiceSimplePrepayment (com.mpe.financial.model.InvoiceSimplePrepaymentPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInvoiceSimplePrepayment (
		com.mpe.financial.model.InvoiceSimplePrepaymentPK id,
		double amount) {

		this.setId(id);
		this.setAmount(amount);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.InvoiceSimplePrepaymentPK id;

	// fields
	private double amount;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.InvoiceSimplePrepaymentPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.InvoiceSimplePrepaymentPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




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





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.InvoiceSimplePrepayment)) return false;
		else {
			com.mpe.financial.model.InvoiceSimplePrepayment invoiceSimplePrepayment = (com.mpe.financial.model.InvoiceSimplePrepayment) obj;
			if (null == this.getId() || null == invoiceSimplePrepayment.getId()) return false;
			else return (this.getId().equals(invoiceSimplePrepayment.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}