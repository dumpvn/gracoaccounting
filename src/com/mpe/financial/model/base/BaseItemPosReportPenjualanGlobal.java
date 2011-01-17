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

public abstract class BaseItemPosReportPenjualanGlobal  implements Serializable {

	public static String REF = "ItemPosReportPenjualanGlobal";
	public static String PROP_MERK = "Merk";
	public static String PROP_NAME = "Name";
	public static String PROP_CODE = "Code";
	public static String PROP_PRICE = "Price";
	public static String PROP_STOCK_AWAL = "StockAwal";
	public static String PROP_MASUK = "Masuk";
	public static String PROP_TUKAR_MASUK = "TukarMasuk";
	public static String PROP_RETUR = "Retur";
	public static String PROP_TUKAR_KELUAR = "TukarKeluar";
	public static String PROP_JUAL = "Jual";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItemPosReportPenjualanGlobal () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemPosReportPenjualanGlobal (long itemId) {
		this.setItemId(itemId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long itemId;

	// fields
	private java.lang.String merk;
	private java.lang.String name;
	private java.lang.String code;
	private double price;
	private double stockAwal;
	private double masuk;
	private double tukarMasuk;
	private double retur;
	private double tukarKeluar;
	private double jual;
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
	 * Return the value associated with the column: Price
	 */
	public double getPrice () {
		return price;
	}

	/**
	 * Set the value related to the column: Price
	 * @param price the Price value
	 */
	public void setPrice (double price) {
		this.price = price;
	}



	/**
	 * Return the value associated with the column: StockAwal
	 */
	public double getStockAwal () {
		return stockAwal;
	}

	/**
	 * Set the value related to the column: StockAwal
	 * @param stockAwal the StockAwal value
	 */
	public void setStockAwal (double stockAwal) {
		this.stockAwal = stockAwal;
	}



	/**
	 * Return the value associated with the column: Masuk
	 */
	public double getMasuk () {
		return masuk;
	}

	/**
	 * Set the value related to the column: Masuk
	 * @param masuk the Masuk value
	 */
	public void setMasuk (double masuk) {
		this.masuk = masuk;
	}



	/**
	 * Return the value associated with the column: TukarMasuk
	 */
	public double getTukarMasuk () {
		return tukarMasuk;
	}

	/**
	 * Set the value related to the column: TukarMasuk
	 * @param tukarMasuk the TukarMasuk value
	 */
	public void setTukarMasuk (double tukarMasuk) {
		this.tukarMasuk = tukarMasuk;
	}



	/**
	 * Return the value associated with the column: Retur
	 */
	public double getRetur () {
		return retur;
	}

	/**
	 * Set the value related to the column: Retur
	 * @param retur the Retur value
	 */
	public void setRetur (double retur) {
		this.retur = retur;
	}



	/**
	 * Return the value associated with the column: TukarKeluar
	 */
	public double getTukarKeluar () {
		return tukarKeluar;
	}

	/**
	 * Set the value related to the column: TukarKeluar
	 * @param tukarKeluar the TukarKeluar value
	 */
	public void setTukarKeluar (double tukarKeluar) {
		this.tukarKeluar = tukarKeluar;
	}



	/**
	 * Return the value associated with the column: Jual
	 */
	public double getJual () {
		return jual;
	}

	/**
	 * Set the value related to the column: Jual
	 * @param jual the Jual value
	 */
	public void setJual (double jual) {
		this.jual = jual;
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
		if (!(obj instanceof com.mpe.financial.model.ItemPosReportPenjualanGlobal)) return false;
		else {
			com.mpe.financial.model.ItemPosReportPenjualanGlobal itemPosReportPenjualanGlobal = (com.mpe.financial.model.ItemPosReportPenjualanGlobal) obj;
			return (this.getItemId() == itemPosReportPenjualanGlobal.getItemId());
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