package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the journal_detail table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="journal_detail"
 */

public abstract class BaseJournalDetail  implements Serializable {

	public static String REF = "JournalDetail";
	public static String PROP_AMOUNT = "Amount";
	public static String PROP_DESCRIPTION = "Description";


	// constructors
	public BaseJournalDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseJournalDetail (com.mpe.financial.model.JournalDetailPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseJournalDetail (
		com.mpe.financial.model.JournalDetailPK id,
		double amount) {

		this.setId(id);
		this.setAmount(amount);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.JournalDetailPK id;

	// fields
	private double amount;
	private java.lang.String description;

	// many to one
	private com.mpe.financial.model.Department department;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.JournalDetailPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.JournalDetailPK id) {
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
	 * Return the value associated with the column: department_id
	 */
	public com.mpe.financial.model.Department getDepartment () {
		return department;
	}

	/**
	 * Set the value related to the column: department_id
	 * @param department the department_id value
	 */
	public void setDepartment (com.mpe.financial.model.Department department) {
		this.department = department;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.JournalDetail)) return false;
		else {
			com.mpe.financial.model.JournalDetail journalDetail = (com.mpe.financial.model.JournalDetail) obj;
			if (null == this.getId() || null == journalDetail.getId()) return false;
			else return (this.getId().equals(journalDetail.getId()));
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