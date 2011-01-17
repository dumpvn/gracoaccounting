package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the  table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table=""
 */

public abstract class BaseItemPosReportStockOpname  implements Serializable {

	public static String REF = "ItemPosReportStockOpname";
	public static String PROP_MERK = "Merk";
	public static String PROP_TYPE = "Type";
	public static String PROP_COLOR = "Color";
	public static String PROP_NAME = "Name";
	public static String PROP_CODE = "Code";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItemPosReportStockOpname () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemPosReportStockOpname (long itemId) {
		this.setItemId(itemId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long itemId;

	// fields
	private java.lang.String merk;
	private java.lang.String type;
	private java.lang.String color;
	private java.lang.String name;
	private java.lang.String code;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="ItemId"
     */
	public long getItemId () {
		return itemId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param itemId the new ID
	 */
	public void setItemId (long itemId) {
		this.itemId = itemId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: Merk
	 */
	public java.lang.String getMerk () {
		return merk;
	}

	/**
	 * Set the value related to the column: Merk
	 * @param merk the Merk value
	 */
	public void setMerk (java.lang.String merk) {
		this.merk = merk;
	}



	/**
	 * Return the value associated with the column: Type
	 */
	public java.lang.String getType () {
		return type;
	}

	/**
	 * Set the value related to the column: Type
	 * @param type the Type value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
	}



	/**
	 * Return the value associated with the column: Color
	 */
	public java.lang.String getColor () {
		return color;
	}

	/**
	 * Set the value related to the column: Color
	 * @param color the Color value
	 */
	public void setColor (java.lang.String color) {
		this.color = color;
	}



	/**
	 * Return the value associated with the column: Name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: Name
	 * @param name the Name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: Code
	 */
	public java.lang.String getCode () {
		return code;
	}

	/**
	 * Set the value related to the column: Code
	 * @param code the Code value
	 */
	public void setCode (java.lang.String code) {
		this.code = code;
	}



	/**
	 * Return the value associated with the column: NumberOfDigit
	 */
	public int getNumberOfDigit () {
		return numberOfDigit;
	}

	/**
	 * Set the value related to the column: NumberOfDigit
	 * @param numberOfDigit the NumberOfDigit value
	 */
	public void setNumberOfDigit (int numberOfDigit) {
		this.numberOfDigit = numberOfDigit;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemPosReportStockOpname)) return false;
		else {
			com.mpe.financial.model.ItemPosReportStockOpname itemPosReportStockOpname = (com.mpe.financial.model.ItemPosReportStockOpname) obj;
			return (this.getItemId() == itemPosReportStockOpname.getItemId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getItemId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}