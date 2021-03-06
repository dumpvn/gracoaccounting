package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the chart_group table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="chart_group"
 */

public abstract class BaseChartGroup  implements Serializable {

	public static String REF = "ChartGroup";
	public static String PROP_GROUPS = "Groups";
	public static String PROP_NAME = "Name";
	public static String PROP_DEBIT = "Debit";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";


	// constructors
	public BaseChartGroup () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseChartGroup (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseChartGroup (
		long id,
		java.lang.String groups,
		java.lang.String name,
		boolean debit) {

		this.setId(id);
		this.setGroups(groups);
		this.setName(name);
		this.setDebit(debit);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String groups;
	private java.lang.String name;
	private boolean debit;
	private java.lang.String description;
	private java.util.Date createOn;
	private java.util.Date changeOn;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;

	// collections
	private java.util.Set chartOfAccounts;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="chart_group_id"
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
	 * Return the value associated with the column: groups
	 */
	public java.lang.String getGroups () {
		return groups;
	}

	/**
	 * Set the value related to the column: groups
	 * @param groups the groups value
	 */
	public void setGroups (java.lang.String groups) {
		this.groups = groups;
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
	 * Return the value associated with the column: is_debit
	 */
	public boolean isDebit () {
		return debit;
	}

	/**
	 * Set the value related to the column: is_debit
	 * @param debit the is_debit value
	 */
	public void setDebit (boolean debit) {
		this.debit = debit;
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



	/**
	 * Return the value associated with the column: ChartOfAccounts
	 */
	public java.util.Set getChartOfAccounts () {
		return chartOfAccounts;
	}

	/**
	 * Set the value related to the column: ChartOfAccounts
	 * @param chartOfAccounts the ChartOfAccounts value
	 */
	public void setChartOfAccounts (java.util.Set chartOfAccounts) {
		this.chartOfAccounts = chartOfAccounts;
	}

	public void addToChartOfAccounts (com.mpe.financial.model.ChartOfAccount chartOfAccount) {
		if (null == getChartOfAccounts()) setChartOfAccounts(new java.util.HashSet());
		getChartOfAccounts().add(chartOfAccount);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ChartGroup)) return false;
		else {
			com.mpe.financial.model.ChartGroup chartGroup = (com.mpe.financial.model.ChartGroup) obj;
			return (this.getId() == chartGroup.getId());
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