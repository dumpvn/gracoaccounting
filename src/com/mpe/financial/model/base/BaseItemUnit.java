package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item_unit table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item_unit"
 */

public abstract class BaseItemUnit  implements Serializable {

	public static String REF = "ItemUnit";
	public static String PROP_NAME = "Name";
	public static String PROP_SYMBOL = "Symbol";
	public static String PROP_BASE = "Base";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";


	// constructors
	public BaseItemUnit () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemUnit (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseItemUnit (
		long id,
		java.lang.String name,
		java.lang.String symbol,
		boolean base) {

		this.setId(id);
		this.setName(name);
		this.setSymbol(symbol);
		this.setBase(base);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String name;
	private java.lang.String symbol;
	private boolean base;
	private java.util.Date createOn;
	private java.util.Date changeOn;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;

	// collections
	private java.util.Set itemUnitExchangesByFromItemUnit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="item_unit_id"
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
	 * Return the value associated with the column: symbol
	 */
	public java.lang.String getSymbol () {
		return symbol;
	}

	/**
	 * Set the value related to the column: symbol
	 * @param symbol the symbol value
	 */
	public void setSymbol (java.lang.String symbol) {
		this.symbol = symbol;
	}



	/**
	 * Return the value associated with the column: is_base
	 */
	public boolean isBase () {
		return base;
	}

	/**
	 * Set the value related to the column: is_base
	 * @param base the is_base value
	 */
	public void setBase (boolean base) {
		this.base = base;
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
	 * Return the value associated with the column: ItemUnitExchangesByFromItemUnit
	 */
	public java.util.Set getItemUnitExchangesByFromItemUnit () {
		return itemUnitExchangesByFromItemUnit;
	}

	/**
	 * Set the value related to the column: ItemUnitExchangesByFromItemUnit
	 * @param itemUnitExchangesByFromItemUnit the ItemUnitExchangesByFromItemUnit value
	 */
	public void setItemUnitExchangesByFromItemUnit (java.util.Set itemUnitExchangesByFromItemUnit) {
		this.itemUnitExchangesByFromItemUnit = itemUnitExchangesByFromItemUnit;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemUnit)) return false;
		else {
			com.mpe.financial.model.ItemUnit itemUnit = (com.mpe.financial.model.ItemUnit) obj;
			return (this.getId() == itemUnit.getId());
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