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
 * Class Memberx.
 * 
 * @version $Revision$ $Date$
 */
public class Memberx implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _memberNumber
     */
    private java.lang.String _memberNumber;

    /**
     * Field _memberDate
     */
    private java.lang.String _memberDate;

    /**
     * Field _fullName
     */
    private java.lang.String _fullName;

    /**
     * Field _nickName
     */
    private java.lang.String _nickName;

    /**
     * Field _isActive
     */
    private java.lang.String _isActive;

    /**
     * Field _mobile
     */
    private java.lang.String _mobile;

    /**
     * Field _telephone
     */
    private java.lang.String _telephone;

    /**
     * Field _memberDiscountAmount
     */
    private double _memberDiscountAmount;

    /**
     * keeps track of state for field: _memberDiscountAmount
     */
    private boolean _has_memberDiscountAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public Memberx() {
        super();
    } //-- com.mpe.pos.xml.Memberx()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'fullName'.
     * 
     * @return the value of field 'fullName'.
     */
    public java.lang.String getFullName()
    {
        return this._fullName;
    } //-- java.lang.String getFullName() 

    /**
     * Returns the value of field 'isActive'.
     * 
     * @return the value of field 'isActive'.
     */
    public java.lang.String getIsActive()
    {
        return this._isActive;
    } //-- java.lang.String getIsActive() 

    /**
     * Returns the value of field 'memberDate'.
     * 
     * @return the value of field 'memberDate'.
     */
    public java.lang.String getMemberDate()
    {
        return this._memberDate;
    } //-- java.lang.String getMemberDate() 

    /**
     * Returns the value of field 'memberDiscountAmount'.
     * 
     * @return the value of field 'memberDiscountAmount'.
     */
    public double getMemberDiscountAmount()
    {
        return this._memberDiscountAmount;
    } //-- double getMemberDiscountAmount() 

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
     * Returns the value of field 'mobile'.
     * 
     * @return the value of field 'mobile'.
     */
    public java.lang.String getMobile()
    {
        return this._mobile;
    } //-- java.lang.String getMobile() 

    /**
     * Returns the value of field 'nickName'.
     * 
     * @return the value of field 'nickName'.
     */
    public java.lang.String getNickName()
    {
        return this._nickName;
    } //-- java.lang.String getNickName() 

    /**
     * Returns the value of field 'telephone'.
     * 
     * @return the value of field 'telephone'.
     */
    public java.lang.String getTelephone()
    {
        return this._telephone;
    } //-- java.lang.String getTelephone() 

    /**
     * Method hasMemberDiscountAmount
     */
    public boolean hasMemberDiscountAmount()
    {
        return this._has_memberDiscountAmount;
    } //-- boolean hasMemberDiscountAmount() 

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
     * Sets the value of field 'fullName'.
     * 
     * @param fullName the value of field 'fullName'.
     */
    public void setFullName(java.lang.String fullName)
    {
        this._fullName = fullName;
    } //-- void setFullName(java.lang.String) 

    /**
     * Sets the value of field 'isActive'.
     * 
     * @param isActive the value of field 'isActive'.
     */
    public void setIsActive(java.lang.String isActive)
    {
        this._isActive = isActive;
    } //-- void setIsActive(java.lang.String) 

    /**
     * Sets the value of field 'memberDate'.
     * 
     * @param memberDate the value of field 'memberDate'.
     */
    public void setMemberDate(java.lang.String memberDate)
    {
        this._memberDate = memberDate;
    } //-- void setMemberDate(java.lang.String) 

    /**
     * Sets the value of field 'memberDiscountAmount'.
     * 
     * @param memberDiscountAmount the value of field
     * 'memberDiscountAmount'.
     */
    public void setMemberDiscountAmount(double memberDiscountAmount)
    {
        this._memberDiscountAmount = memberDiscountAmount;
        this._has_memberDiscountAmount = true;
    } //-- void setMemberDiscountAmount(double) 

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
     * Sets the value of field 'mobile'.
     * 
     * @param mobile the value of field 'mobile'.
     */
    public void setMobile(java.lang.String mobile)
    {
        this._mobile = mobile;
    } //-- void setMobile(java.lang.String) 

    /**
     * Sets the value of field 'nickName'.
     * 
     * @param nickName the value of field 'nickName'.
     */
    public void setNickName(java.lang.String nickName)
    {
        this._nickName = nickName;
    } //-- void setNickName(java.lang.String) 

    /**
     * Sets the value of field 'telephone'.
     * 
     * @param telephone the value of field 'telephone'.
     */
    public void setTelephone(java.lang.String telephone)
    {
        this._telephone = telephone;
    } //-- void setTelephone(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static com.mpe.pos.xml.Memberx unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (com.mpe.pos.xml.Memberx) Unmarshaller.unmarshal(com.mpe.pos.xml.Memberx.class, reader);
    } //-- com.mpe.pos.xml.Memberx unmarshal(java.io.Reader) 

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
