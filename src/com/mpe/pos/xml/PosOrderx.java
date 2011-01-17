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
import java.util.ArrayList;
import java.util.Enumeration;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class PosOrderx.
 * 
 * @version $Revision$ $Date$
 */
public class PosOrderx implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _posOrderNumber
     */
    private java.lang.String _posOrderNumber;

    /**
     * Field _posOrderDate
     */
    private java.lang.String _posOrderDate;

    /**
     * Field _startTime
     */
    private java.lang.String _startTime;

    /**
     * Field _endTime
     */
    private java.lang.String _endTime;

    /**
     * Field _cashAmount
     */
    private double _cashAmount;

    /**
     * keeps track of state for field: _cashAmount
     */
    private boolean _has_cashAmount;

    /**
     * Field _changesAmount
     */
    private double _changesAmount;

    /**
     * keeps track of state for field: _changesAmount
     */
    private boolean _has_changesAmount;

    /**
     * Field _creditCardAdm
     */
    private double _creditCardAdm;

    /**
     * keeps track of state for field: _creditCardAdm
     */
    private boolean _has_creditCardAdm;

    /**
     * Field _creditCardNumber
     */
    private java.lang.String _creditCardNumber;

    /**
     * Field _status
     */
    private java.lang.String _status;

    /**
     * Field _paymentMethod
     */
    private java.lang.String _paymentMethod;

    /**
     * Field _discountProcent
     */
    private double _discountProcent;

    /**
     * keeps track of state for field: _discountProcent
     */
    private boolean _has_discountProcent;

    /**
     * Field _taxProcent
     */
    private double _taxProcent;

    /**
     * keeps track of state for field: _taxProcent
     */
    private boolean _has_taxProcent;

    /**
     * Field _note
     */
    private java.lang.String _note;

    /**
     * Field _posted
     */
    private java.lang.String _posted;

    /**
     * Field _currencySymbol
     */
    private java.lang.String _currencySymbol;

    /**
     * Field _memberNumber
     */
    private java.lang.String _memberNumber;

    /**
     * Field _bankName
     */
    private java.lang.String _bankName;

    /**
     * Field _salemanCode
     */
    private java.lang.String _salemanCode;

    /**
     * Field _posOrderDetailxList
     */
    private java.util.ArrayList _posOrderDetailxList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PosOrderx() {
        super();
        _posOrderDetailxList = new ArrayList();
    } //-- com.mpe.pos.xml.PosOrderx()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addPosOrderDetailx
     * 
     * @param vPosOrderDetailx
     */
    public void addPosOrderDetailx(com.mpe.pos.xml.PosOrderDetailx vPosOrderDetailx)
        throws java.lang.IndexOutOfBoundsException
    {
        _posOrderDetailxList.add(vPosOrderDetailx);
    } //-- void addPosOrderDetailx(com.mpe.pos.xml.PosOrderDetailx) 

    /**
     * Method addPosOrderDetailx
     * 
     * @param index
     * @param vPosOrderDetailx
     */
    public void addPosOrderDetailx(int index, com.mpe.pos.xml.PosOrderDetailx vPosOrderDetailx)
        throws java.lang.IndexOutOfBoundsException
    {
        _posOrderDetailxList.add(index, vPosOrderDetailx);
    } //-- void addPosOrderDetailx(int, com.mpe.pos.xml.PosOrderDetailx) 

    /**
     * Method clearPosOrderDetailx
     */
    public void clearPosOrderDetailx()
    {
        _posOrderDetailxList.clear();
    } //-- void clearPosOrderDetailx() 

    /**
     * Method enumeratePosOrderDetailx
     */
    public java.util.Enumeration enumeratePosOrderDetailx()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_posOrderDetailxList.iterator());
    } //-- java.util.Enumeration enumeratePosOrderDetailx() 

    /**
     * Returns the value of field 'bankName'.
     * 
     * @return the value of field 'bankName'.
     */
    public java.lang.String getBankName()
    {
        return this._bankName;
    } //-- java.lang.String getBankName() 

    /**
     * Returns the value of field 'cashAmount'.
     * 
     * @return the value of field 'cashAmount'.
     */
    public double getCashAmount()
    {
        return this._cashAmount;
    } //-- double getCashAmount() 

    /**
     * Returns the value of field 'changesAmount'.
     * 
     * @return the value of field 'changesAmount'.
     */
    public double getChangesAmount()
    {
        return this._changesAmount;
    } //-- double getChangesAmount() 

    /**
     * Returns the value of field 'creditCardAdm'.
     * 
     * @return the value of field 'creditCardAdm'.
     */
    public double getCreditCardAdm()
    {
        return this._creditCardAdm;
    } //-- double getCreditCardAdm() 

    /**
     * Returns the value of field 'creditCardNumber'.
     * 
     * @return the value of field 'creditCardNumber'.
     */
    public java.lang.String getCreditCardNumber()
    {
        return this._creditCardNumber;
    } //-- java.lang.String getCreditCardNumber() 

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
     * Returns the value of field 'endTime'.
     * 
     * @return the value of field 'endTime'.
     */
    public java.lang.String getEndTime()
    {
        return this._endTime;
    } //-- java.lang.String getEndTime() 

    /**
     * Returns the value of field 'memberNumber'.
     * 
     * @return the value of field 'memberNumber'.
     */
    public java.lang.String getMemberNumber()
    {
        return this._memberNumber;
    } //-- java.lang.String getMemberNumber() 

    /**
     * Returns the value of field 'note'.
     * 
     * @return the value of field 'note'.
     */
    public java.lang.String getNote()
    {
        return this._note;
    } //-- java.lang.String getNote() 

    /**
     * Returns the value of field 'paymentMethod'.
     * 
     * @return the value of field 'paymentMethod'.
     */
    public java.lang.String getPaymentMethod()
    {
        return this._paymentMethod;
    } //-- java.lang.String getPaymentMethod() 

    /**
     * Returns the value of field 'posOrderDate'.
     * 
     * @return the value of field 'posOrderDate'.
     */
    public java.lang.String getPosOrderDate()
    {
        return this._posOrderDate;
    } //-- java.lang.String getPosOrderDate() 

    /**
     * Method getPosOrderDetailx
     * 
     * @param index
     */
    public com.mpe.pos.xml.PosOrderDetailx getPosOrderDetailx(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _posOrderDetailxList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (com.mpe.pos.xml.PosOrderDetailx) _posOrderDetailxList.get(index);
    } //-- com.mpe.pos.xml.PosOrderDetailx getPosOrderDetailx(int) 

    /**
     * Method getPosOrderDetailx
     */
    public com.mpe.pos.xml.PosOrderDetailx[] getPosOrderDetailx()
    {
        int size = _posOrderDetailxList.size();
        com.mpe.pos.xml.PosOrderDetailx[] mArray = new com.mpe.pos.xml.PosOrderDetailx[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (com.mpe.pos.xml.PosOrderDetailx) _posOrderDetailxList.get(index);
        }
        return mArray;
    } //-- com.mpe.pos.xml.PosOrderDetailx[] getPosOrderDetailx() 

    /**
     * Method getPosOrderDetailxCount
     */
    public int getPosOrderDetailxCount()
    {
        return _posOrderDetailxList.size();
    } //-- int getPosOrderDetailxCount() 

    /**
     * Returns the value of field 'posOrderNumber'.
     * 
     * @return the value of field 'posOrderNumber'.
     */
    public java.lang.String getPosOrderNumber()
    {
        return this._posOrderNumber;
    } //-- java.lang.String getPosOrderNumber() 

    /**
     * Returns the value of field 'posted'.
     * 
     * @return the value of field 'posted'.
     */
    public java.lang.String getPosted()
    {
        return this._posted;
    } //-- java.lang.String getPosted() 

    /**
     * Returns the value of field 'salemanCode'.
     * 
     * @return the value of field 'salemanCode'.
     */
    public java.lang.String getSalemanCode()
    {
        return this._salemanCode;
    } //-- java.lang.String getSalemanCode() 

    /**
     * Returns the value of field 'startTime'.
     * 
     * @return the value of field 'startTime'.
     */
    public java.lang.String getStartTime()
    {
        return this._startTime;
    } //-- java.lang.String getStartTime() 

    /**
     * Returns the value of field 'status'.
     * 
     * @return the value of field 'status'.
     */
    public java.lang.String getStatus()
    {
        return this._status;
    } //-- java.lang.String getStatus() 

    /**
     * Returns the value of field 'taxProcent'.
     * 
     * @return the value of field 'taxProcent'.
     */
    public double getTaxProcent()
    {
        return this._taxProcent;
    } //-- double getTaxProcent() 

    /**
     * Method hasCashAmount
     */
    public boolean hasCashAmount()
    {
        return this._has_cashAmount;
    } //-- boolean hasCashAmount() 

    /**
     * Method hasChangesAmount
     */
    public boolean hasChangesAmount()
    {
        return this._has_changesAmount;
    } //-- boolean hasChangesAmount() 

    /**
     * Method hasCreditCardAdm
     */
    public boolean hasCreditCardAdm()
    {
        return this._has_creditCardAdm;
    } //-- boolean hasCreditCardAdm() 

    /**
     * Method hasDiscountProcent
     */
    public boolean hasDiscountProcent()
    {
        return this._has_discountProcent;
    } //-- boolean hasDiscountProcent() 

    /**
     * Method hasTaxProcent
     */
    public boolean hasTaxProcent()
    {
        return this._has_taxProcent;
    } //-- boolean hasTaxProcent() 

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
     * Method removePosOrderDetailx
     * 
     * @param vPosOrderDetailx
     */
    public boolean removePosOrderDetailx(com.mpe.pos.xml.PosOrderDetailx vPosOrderDetailx)
    {
        boolean removed = _posOrderDetailxList.remove(vPosOrderDetailx);
        return removed;
    } //-- boolean removePosOrderDetailx(com.mpe.pos.xml.PosOrderDetailx) 

    /**
     * Sets the value of field 'bankName'.
     * 
     * @param bankName the value of field 'bankName'.
     */
    public void setBankName(java.lang.String bankName)
    {
        this._bankName = bankName;
    } //-- void setBankName(java.lang.String) 

    /**
     * Sets the value of field 'cashAmount'.
     * 
     * @param cashAmount the value of field 'cashAmount'.
     */
    public void setCashAmount(double cashAmount)
    {
        this._cashAmount = cashAmount;
        this._has_cashAmount = true;
    } //-- void setCashAmount(double) 

    /**
     * Sets the value of field 'changesAmount'.
     * 
     * @param changesAmount the value of field 'changesAmount'.
     */
    public void setChangesAmount(double changesAmount)
    {
        this._changesAmount = changesAmount;
        this._has_changesAmount = true;
    } //-- void setChangesAmount(double) 

    /**
     * Sets the value of field 'creditCardAdm'.
     * 
     * @param creditCardAdm the value of field 'creditCardAdm'.
     */
    public void setCreditCardAdm(double creditCardAdm)
    {
        this._creditCardAdm = creditCardAdm;
        this._has_creditCardAdm = true;
    } //-- void setCreditCardAdm(double) 

    /**
     * Sets the value of field 'creditCardNumber'.
     * 
     * @param creditCardNumber the value of field 'creditCardNumber'
     */
    public void setCreditCardNumber(java.lang.String creditCardNumber)
    {
        this._creditCardNumber = creditCardNumber;
    } //-- void setCreditCardNumber(java.lang.String) 

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
     * Sets the value of field 'endTime'.
     * 
     * @param endTime the value of field 'endTime'.
     */
    public void setEndTime(java.lang.String endTime)
    {
        this._endTime = endTime;
    } //-- void setEndTime(java.lang.String) 

    /**
     * Sets the value of field 'memberNumber'.
     * 
     * @param memberNumber the value of field 'memberNumber'.
     */
    public void setMemberNumber(java.lang.String memberNumber)
    {
        this._memberNumber = memberNumber;
    } //-- void setMemberNumber(java.lang.String) 

    /**
     * Sets the value of field 'note'.
     * 
     * @param note the value of field 'note'.
     */
    public void setNote(java.lang.String note)
    {
        this._note = note;
    } //-- void setNote(java.lang.String) 

    /**
     * Sets the value of field 'paymentMethod'.
     * 
     * @param paymentMethod the value of field 'paymentMethod'.
     */
    public void setPaymentMethod(java.lang.String paymentMethod)
    {
        this._paymentMethod = paymentMethod;
    } //-- void setPaymentMethod(java.lang.String) 

    /**
     * Sets the value of field 'posOrderDate'.
     * 
     * @param posOrderDate the value of field 'posOrderDate'.
     */
    public void setPosOrderDate(java.lang.String posOrderDate)
    {
        this._posOrderDate = posOrderDate;
    } //-- void setPosOrderDate(java.lang.String) 

    /**
     * Method setPosOrderDetailx
     * 
     * @param index
     * @param vPosOrderDetailx
     */
    public void setPosOrderDetailx(int index, com.mpe.pos.xml.PosOrderDetailx vPosOrderDetailx)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _posOrderDetailxList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _posOrderDetailxList.set(index, vPosOrderDetailx);
    } //-- void setPosOrderDetailx(int, com.mpe.pos.xml.PosOrderDetailx) 

    /**
     * Method setPosOrderDetailx
     * 
     * @param posOrderDetailxArray
     */
    public void setPosOrderDetailx(com.mpe.pos.xml.PosOrderDetailx[] posOrderDetailxArray)
    {
        //-- copy array
        _posOrderDetailxList.clear();
        for (int i = 0; i < posOrderDetailxArray.length; i++) {
            _posOrderDetailxList.add(posOrderDetailxArray[i]);
        }
    } //-- void setPosOrderDetailx(com.mpe.pos.xml.PosOrderDetailx) 

    /**
     * Sets the value of field 'posOrderNumber'.
     * 
     * @param posOrderNumber the value of field 'posOrderNumber'.
     */
    public void setPosOrderNumber(java.lang.String posOrderNumber)
    {
        this._posOrderNumber = posOrderNumber;
    } //-- void setPosOrderNumber(java.lang.String) 

    /**
     * Sets the value of field 'posted'.
     * 
     * @param posted the value of field 'posted'.
     */
    public void setPosted(java.lang.String posted)
    {
        this._posted = posted;
    } //-- void setPosted(java.lang.String) 

    /**
     * Sets the value of field 'salemanCode'.
     * 
     * @param salemanCode the value of field 'salemanCode'.
     */
    public void setSalemanCode(java.lang.String salemanCode)
    {
        this._salemanCode = salemanCode;
    } //-- void setSalemanCode(java.lang.String) 

    /**
     * Sets the value of field 'startTime'.
     * 
     * @param startTime the value of field 'startTime'.
     */
    public void setStartTime(java.lang.String startTime)
    {
        this._startTime = startTime;
    } //-- void setStartTime(java.lang.String) 

    /**
     * Sets the value of field 'status'.
     * 
     * @param status the value of field 'status'.
     */
    public void setStatus(java.lang.String status)
    {
        this._status = status;
    } //-- void setStatus(java.lang.String) 

    /**
     * Sets the value of field 'taxProcent'.
     * 
     * @param taxProcent the value of field 'taxProcent'.
     */
    public void setTaxProcent(double taxProcent)
    {
        this._taxProcent = taxProcent;
        this._has_taxProcent = true;
    } //-- void setTaxProcent(double) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static com.mpe.pos.xml.PosOrderx unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (com.mpe.pos.xml.PosOrderx) Unmarshaller.unmarshal(com.mpe.pos.xml.PosOrderx.class, reader);
    } //-- com.mpe.pos.xml.PosOrderx unmarshal(java.io.Reader) 

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
