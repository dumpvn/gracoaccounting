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

public abstract class BaseItemUnitExchangeFK  implements Serializable {

	public static String REF = "ItemUnitExchangeFK";
	public static String PROP_CONVERSION = "Conversion";


	// constructors
	public BaseItemUnitExchangeFK () {
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseItemUnitExchangeFK (
		com.mpe.financial.model.ItemUnit toItemUnit,
		double conversion) {

		this.setToItemUnit(toItemUnit);
		this.setConversion(conversion);
		initialize();
	}

	protected void initialize () {}



	// fields
	private double conversion;

	// many to one
	private com.mpe.financial.model.ItemUnit toItemUnit;
	private com.mpe.financial.model.Item item;






	/**
	 * Return the value associated with the column: conversion
	 */
	public double getConversion () {
		return conversion;
	}

	/**
	 * Set the value related to the column: conversion
	 * @param conversion the conversion value
	 */
	public void setConversion (double conversion) {
		this.conversion = conversion;
	}



	/**
	 * Return the value associated with the column: to_item_unit_id
	 */
	public com.mpe.financial.model.ItemUnit getToItemUnit () {
		return toItemUnit;
	}

	/**
	 * Set the value related to the column: to_item_unit_id
	 * @param toItemUnit the to_item_unit_id value
	 */
	public void setToItemUnit (com.mpe.financial.model.ItemUnit toItemUnit) {
		this.toItemUnit = toItemUnit;
	}



	/**
	 * Return the value associated with the column: item_id
	 */
	public com.mpe.financial.model.Item getItem () {
		return item;
	}

	/**
	 * Set the value related to the column: item_id
	 * @param item the item_id value
	 */
	public void setItem (com.mpe.financial.model.Item item) {
		this.item = item;
	}








	public String toString () {
		return super.toString();
	}


}