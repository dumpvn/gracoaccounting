/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.mpe.pos.xml;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class PosOrderDetailx.
 * 
 * @version $Revision$ $Date$
 */
public class PosOrderDetailx implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id
     */
    private long _id;

    /**
     * keeps track of state for field: _id
     */
    private boolean _has_id;

    /**
     * Field _quantity
     */
    private int _quantity;

    /**
     * keeps track of state for field: _quantity
     */
    private boolean _has_quantity;

    /**
     * Field _price
     */
    private double _price;

    /**
     * keeps track of state for field: _price
     */
    private boolean _has_price;

    /**
     * Field _discountProcent
     */
    private double _discountProcent;

    /**
     * keeps track of state for field: _discountProcent
     */
    private boolean _has_discountProcent;

    /**
     * Field _exchangeRate
     */
    private double _exchangeRate;

    /**
     * keeps track of state for field: _exchangeRate
     */
    private boolean _has_exchangeRate;

    /**
     * Field _itemUnitSymbol
     */
    private java.lang.String _itemUnitSymbol;

    /**
     * Field _itemCode
     */
    private java.lang.String _itemCode;

    /**
     * Field _currencySymbol
     */
    private java.lang.String _currencySymbol;


      //----------------/
     //- Constructors -/
    //----------------/

    public PosOrderDetailx() {
        super();
    } //-- com.mpe.pos.xml.PosOrderDetailx()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteId
     */
    public void deleteId()
    {
        this._has_id= false;
    } //-- void deleteId() 

    /**
     * Returns the value of field 'currencySymbol'.
     * 
     * @return the value of field 'currencySymbol'.
     */
    public java.lang.String getCurrencySymbol()
    {
        return this._currencySymbol;
    } //-- java.lang.String getCurrencySymbol() 

    /**
     * Returns the value of field 'discountProcent'.
     * 
     * @return the value of field 'discountProcent'.
     */
    public double getDiscountProcent()
    {
        return this._discountProcent;
    } //-- double getDiscountProcent() 

    /**
     * Returns the value of field 'exchangeRate'.
     * 
     * @return the value of field 'exchangeRate'.
     */
    public double getExchangeRate()
    {
        return this._exchangeRate;
    } //-- double getExchangeRate() 

    /**
     * Returns the value of field 'id'.
     * 
     * @return the value of field 'id'.
     */
    public long getId()
    {
        return this._id;
    } //-- long getId() 

    /**
     * Returns the value of field 'itemCode'.
     * 
     * @return the value of field 'itemCode'.
     */
    public java.lang.String getItemCode()
    {
        return this._itemCode;
    } //-- java.lang.String getItemCode() 

    /**
     * Returns the value of field 'itemUnitSymbol'.
     * 
     * @return the value of field 'itemUnitSymbol'.
     */
    public java.lang.String getItemUnitSymbol()
    {
        return this._itemUnitSymbol;
    } //-- java.lang.String getItemUnitSymbol() 

    /**
     * Returns the value of field 'price'.
     * 
     * @return the value of field 'price'.
     */
    public double getPrice()
    {
        return this._price;
    } //-- double getPrice() 

    /**
     * Returns the value of field 'quantity'.
     * 
     * @return the value of field 'quantity'.
     */
    public int getQuantity()
    {
        return this._quantity;
    } //-- int getQuantity() 

    /**
     * Method hasDiscountProcent
     */
    public boolean hasDiscountProcent()
    {
        return this._has_discountProcent;
    } //-- boolean hasDiscountProcent() 

    /**
     * Method hasExchangeRate
     */
    public boolean hasExchangeRate()
    {
        return this._has_exchangeRate;
    } //-- boolean hasExchangeRate() 

    /**
     * Method hasId
     */
    public boolean hasId()
    {
        return this._has_id;
    } //-- boolean hasId() 

    /**
     * Method hasPrice
     */
    public boolean hasPrice()
    {
        return this._has_price;
    } //-- boolean hasPrice() 

    /**
     * Method hasQuantity
     */
    public boolean hasQuantity()
    {
        return this._has_quantity;
    } //-- boolean hasQuantity() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'currencySymbol'.
     * 
     * @param currencySymbol the value of field 'currencySymbol'.
     */
    public void setCurrencySymbol(java.lang.String currencySymbol)
    {
        this._currencySymbol = currencySymbol;
    } //-- void setCurrencySymbol(java.lang.String) 

    /**
     * Sets the value of field 'discountProcent'.
     * 
     * @param discountProcent the value of field 'discountProcent'.
     */
    public void setDiscountProcent(double discountProcent)
    {
        this._discountProcent = discountProcent;
        this._has_discountProcent = true;
    } //-- void setDiscountProcent(double) 

    /**
     * Sets the value of field 'exchangeRate'.
     * 
     * @param exchangeRate the value of field 'exchangeRate'.
     */
    public void setExchangeRate(double exchangeRate)
    {
        this._exchangeRate = exchangeRate;
        this._has_exchangeRate = true;
    } //-- void setExchangeRate(double) 

    /**
     * Sets the value of field 'id'.
     * 
     * @param id the value of field 'id'.
     */
    public void setId(long id)
    {
        this._id = id;
        this._has_id = true;
    } //-- void setId(long) 

    /**
     * Sets the value of field 'itemCode'.
     * 
     * @param itemCode the value of field 'itemCode'.
     */
    public void setItemCode(java.lang.String itemCode)
    {
        this._itemCode = itemCode;
    } //-- void setItemCode(java.lang.String) 

    /**
     * Sets the value of field 'itemUnitSymbol'.
     * 
     * @param itemUnitSymbol the value of field 'itemUnitSymbol'.
     */
    public void setItemUnitSymbol(java.lang.String itemUnitSymbol)
    {
        this._itemUnitSymbol = itemUnitSymbol;
    } //-- void setItemUnitSymbol(java.lang.String) 

    /**
     * Sets the value of field 'price'.
     * 
     * @param price the value of field 'price'.
     */
    public void setPrice(double price)
    {
        this._price = price;
        this._has_price = true;
    } //-- void setPrice(double) 

    /**
     * Sets the value of field 'quantity'.
     * 
     * @param quantity the value of field 'quantity'.
     */
    public void setQuantity(int quantity)
    {
        this._quantity = quantity;
        this._has_quantity = true;
    } //-- void setQuantity(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static com.mpe.pos.xml.PosOrderDetailx unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (com.mpe.pos.xml.PosOrderDetailx) Unmarshaller.unmarshal(com.mpe.pos.xml.PosOrderDetailx.class, reader);
    } //-- com.mpe.pos.xml.PosOrderDetailx unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
