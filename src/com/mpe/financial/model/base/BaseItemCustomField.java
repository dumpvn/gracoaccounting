package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item_custom_field table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item_custom_field"
 */

public abstract class BaseItemCustomField  implements Serializable {

	public static String REF = "ItemCustomField";
	public static String PROP_VALUE = "Value";


	// constructors
	public BaseItemCustomField () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemCustomField (com.mpe.financial.model.ItemCustomFieldPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.ItemCustomFieldPK id;

	// fields
	private java.lang.String value;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.ItemCustomFieldPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.ItemCustomFieldPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: value
	 */
	public java.lang.String getValue () {
		return value;
	}

	/**
	 * Set the value related to the column: value
	 * @param value the value value
	 */
	public void setValue (java.lang.String value) {
		this.value = value;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemCustomField)) return false;
		else {
			com.mpe.financial.model.ItemCustomField itemCustomField = (com.mpe.financial.model.ItemCustomField) obj;
			if (null == this.getId() || null == itemCustomField.getId()) return false;
			else return (this.getId().equals(itemCustomField.getId()));
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