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

public abstract class BaseCustomerPrepaymentSalesOrderFK  implements Serializable {

	public static String REF = "CustomerPrepaymentSalesOrderFK";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BaseCustomerPrepaymentSalesOrderFK () {
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCustomerPrepaymentSalesOrderFK (
		double amount) {

		this.setAmount(amount);
		initialize();
	}

	protected void initialize () {}



	// fields
	private double amount;

	// many to one
	private com.mpe.financial.model.SalesOrder salesOrder;






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
	 * Return the value associated with the column: sales_order_id
	 */
	public com.mpe.financial.model.SalesOrder getSalesOrder () {
		return salesOrder;
	}

	/**
	 * Set the value related to the column: sales_order_id
	 * @param salesOrder the sales_order_id value
	 */
	public void setSalesOrder (com.mpe.financial.model.SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}








	public String toString () {
		return super.toString();
	}


}