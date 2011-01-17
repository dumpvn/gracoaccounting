package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the stock_opname_type table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="stock_opname_type"
 */

public abstract class BaseStockOpnameType  implements Serializable {

	public static String REF = "StockOpnameType";
	public static String PROP_CODE = "Code";
	public static String PROP_NAME = "Name";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_RECEIPT = "Receipt";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";


	// constructors
	public BaseStockOpnameType () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseStockOpnameType (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseStockOpnameType (
		long id,
		java.lang.String code,
		java.lang.String name,
		boolean receipt) {

		this.setId(id);
		this.setCode(code);
		this.setName(name);
		this.setReceipt(receipt);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String code;
	private java.lang.String name;
	private java.lang.String description;
	private boolean receipt;
	private java.util.Date createOn;
	private java.util.Date changeOn;

	// many to one
	private com.mpe.financial.model.ChartOfAccount chartOfAccount;
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="stock_opname_type_id"
     */
	public long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: code
	 */
	public java.lang.String getCode () {
		return code;
	}

	/**
	 * Set the value related to the column: code
	 * @param code the code value
	 */
	public void setCode (java.lang.String code) {
		this.code = code;
	}



	/**
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name
	 * @param name the name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}



	/**
	 * Return the value associated with the column: is_receipt
	 */
	public boolean isReceipt () {
		return receipt;
	}

	/**
	 * Set the value related to the column: is_receipt
	 * @param receipt the is_receipt value
	 */
	public void setReceipt (boolean receipt) {
		this.receipt = receipt;
	}



	/**
	 * Return the value associated with the column: create_on
	 */
	public java.util.Date getCreateOn () {
		return createOn;
	}

	/**
	 * Set the value related to the column: create_on
	 * @param createOn the create_on value
	 */
	public void setCreateOn (java.util.Date createOn) {
		this.createOn = createOn;
	}



	/**
	 * Return the value associated with the column: change_on
	 */
	public java.util.Date getChangeOn () {
		return changeOn;
	}

	/**
	 * Set the value related to the column: change_on
	 * @param changeOn the change_on value
	 */
	public void setChangeOn (java.util.Date changeOn) {
		this.changeOn = changeOn;
	}



	/**
	 * Return the value associated with the column: chart_of_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getChartOfAccount () {
		return chartOfAccount;
	}

	/**
	 * Set the value related to the column: chart_of_account_id
	 * @param chartOfAccount the chart_of_account_id value
	 */
	public void setChartOfAccount (com.mpe.financial.model.ChartOfAccount chartOfAccount) {
		this.chartOfAccount = chartOfAccount;
	}



	/**
	 * Return the value associated with the column: create_by
	 */
	public com.mpe.financial.model.Users getCreateBy () {
		return createBy;
	}

	/**
	 * Set the value related to the column: create_by
	 * @param createBy the create_by value
	 */
	public void setCreateBy (com.mpe.financial.model.Users createBy) {
		this.createBy = createBy;
	}



	/**
	 * Return the value associated with the column: change_by
	 */
	public com.mpe.financial.model.Users getChangeBy () {
		return changeBy;
	}

	/**
	 * Set the value related to the column: change_by
	 * @param changeBy the change_by value
	 */
	public void setChangeBy (com.mpe.financial.model.Users changeBy) {
		this.changeBy = changeBy;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.StockOpnameType)) return false;
		else {
			com.mpe.financial.model.StockOpnameType stockOpnameType = (com.mpe.financial.model.StockOpnameType) obj;
			return (this.getId() == stockOpnameType.getId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}