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
 * Class PosOrderxs.
 * 
 * @version $Revision$ $Date$
 */
public class PosOrderxs implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _organizationName
     */
    private java.lang.String _organizationName;

    /**
     * Field _locationName
     */
    private java.lang.String _locationName;

    /**
     * Field _memberxList
     */
    private java.util.ArrayList _memberxList;

    /**
     * Field _posOrderxList
     */
    private java.util.ArrayList _posOrderxList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PosOrderxs() {
        super();
        _memberxList = new ArrayList();
        _posOrderxList = new ArrayList();
    } //-- com.mpe.pos.xml.PosOrderxs()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addMemberx
     * 
     * @param vMemberx
     */
    public void addMemberx(com.mpe.pos.xml.Memberx vMemberx)
        throws java.lang.IndexOutOfBoundsException
    {
        _memberxList.add(vMemberx);
    } //-- void addMemberx(com.mpe.pos.xml.Memberx) 

    /**
     * Method addMemberx
     * 
     * @param index
     * @param vMemberx
     */
    public void addMemberx(int index, com.mpe.pos.xml.Memberx vMemberx)
        throws java.lang.IndexOutOfBoundsException
    {
        _memberxList.add(index, vMemberx);
    } //-- void addMemberx(int, com.mpe.pos.xml.Memberx) 

    /**
     * Method addPosOrderx
     * 
     * @param vPosOrderx
     */
    public void addPosOrderx(com.mpe.pos.xml.PosOrderx vPosOrderx)
        throws java.lang.IndexOutOfBoundsException
    {
        _posOrderxList.add(vPosOrderx);
    } //-- void addPosOrderx(com.mpe.pos.xml.PosOrderx) 

    /**
     * Method addPosOrderx
     * 
     * @param index
     * @param vPosOrderx
     */
    public void addPosOrderx(int index, com.mpe.pos.xml.PosOrderx vPosOrderx)
        throws java.lang.IndexOutOfBoundsException
    {
        _posOrderxList.add(index, vPosOrderx);
    } //-- void addPosOrderx(int, com.mpe.pos.xml.PosOrderx) 

    /**
     * Method clearMemberx
     */
    public void clearMemberx()
    {
        _memberxList.clear();
    } //-- void clearMemberx() 

    /**
     * Method clearPosOrderx
     */
    public void clearPosOrderx()
    {
        _posOrderxList.clear();
    } //-- void clearPosOrderx() 

    /**
     * Method enumerateMemberx
     */
    public java.util.Enumeration enumerateMemberx()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_memberxList.iterator());
    } //-- java.util.Enumeration enumerateMemberx() 

    /**
     * Method enumeratePosOrderx
     */
    public java.util.Enumeration enumeratePosOrderx()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_posOrderxList.iterator());
    } //-- java.util.Enumeration enumeratePosOrderx() 

    /**
     * Returns the value of field 'locationName'.
     * 
     * @return the value of field 'locationName'.
     */
    public java.lang.String getLocationName()
    {
        return this._locationName;
    } //-- java.lang.String getLocationName() 

    /**
     * Method getMemberx
     * 
     * @param index
     */
    public com.mpe.pos.xml.Memberx getMemberx(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _memberxList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (com.mpe.pos.xml.Memberx) _memberxList.get(index);
    } //-- com.mpe.pos.xml.Memberx getMemberx(int) 

    /**
     * Method getMemberx
     */
    public com.mpe.pos.xml.Memberx[] getMemberx()
    {
        int size = _memberxList.size();
        com.mpe.pos.xml.Memberx[] mArray = new com.mpe.pos.xml.Memberx[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (com.mpe.pos.xml.Memberx) _memberxList.get(index);
        }
        return mArray;
    } //-- com.mpe.pos.xml.Memberx[] getMemberx() 

    /**
     * Method getMemberxCount
     */
    public int getMemberxCount()
    {
        return _memberxList.size();
    } //-- int getMemberxCount() 

    /**
     * Returns the value of field 'organizationName'.
     * 
     * @return the value of field 'organizationName'.
     */
    public java.lang.String getOrganizationName()
    {
        return this._organizationName;
    } //-- java.lang.String getOrganizationName() 

    /**
     * Method getPosOrderx
     * 
     * @param index
     */
    public com.mpe.pos.xml.PosOrderx getPosOrderx(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _posOrderxList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (com.mpe.pos.xml.PosOrderx) _posOrderxList.get(index);
    } //-- com.mpe.pos.xml.PosOrderx getPosOrderx(int) 

    /**
     * Method getPosOrderx
     */
    public com.mpe.pos.xml.PosOrderx[] getPosOrderx()
    {
        int size = _posOrderxList.size();
        com.mpe.pos.xml.PosOrderx[] mArray = new com.mpe.pos.xml.PosOrderx[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (com.mpe.pos.xml.PosOrderx) _posOrderxList.get(index);
        }
        return mArray;
    } //-- com.mpe.pos.xml.PosOrderx[] getPosOrderx() 

    /**
     * Method getPosOrderxCount
     */
    public int getPosOrderxCount()
    {
        return _posOrderxList.size();
    } //-- int getPosOrderxCount() 

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
     * Method removeMemberx
     * 
     * @param vMemberx
     */
    public boolean removeMemberx(com.mpe.pos.xml.Memberx vMemberx)
    {
        boolean removed = _memberxList.remove(vMemberx);
        return removed;
    } //-- boolean removeMemberx(com.mpe.pos.xml.Memberx) 

    /**
     * Method removePosOrderx
     * 
     * @param vPosOrderx
     */
    public boolean removePosOrderx(com.mpe.pos.xml.PosOrderx vPosOrderx)
    {
        boolean removed = _posOrderxList.remove(vPosOrderx);
        return removed;
    } //-- boolean removePosOrderx(com.mpe.pos.xml.PosOrderx) 

    /**
     * Sets the value of field 'locationName'.
     * 
     * @param locationName the value of field 'locationName'.
     */
    public void setLocationName(java.lang.String locationName)
    {
        this._locationName = locationName;
    } //-- void setLocationName(java.lang.String) 

    /**
     * Method setMemberx
     * 
     * @param index
     * @param vMemberx
     */
    public void setMemberx(int index, com.mpe.pos.xml.Memberx vMemberx)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _memberxList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _memberxList.set(index, vMemberx);
    } //-- void setMemberx(int, com.mpe.pos.xml.Memberx) 

    /**
     * Method setMemberx
     * 
     * @param memberxArray
     */
    public void setMemberx(com.mpe.pos.xml.Memberx[] memberxArray)
    {
        //-- copy array
        _memberxList.clear();
        for (int i = 0; i < memberxArray.length; i++) {
            _memberxList.add(memberxArray[i]);
        }
    } //-- void setMemberx(com.mpe.pos.xml.Memberx) 

    /**
     * Sets the value of field 'organizationName'.
     * 
     * @param organizationName the value of field 'organizationName'
     */
    public void setOrganizationName(java.lang.String organizationName)
    {
        this._organizationName = organizationName;
    } //-- void setOrganizationName(java.lang.String) 

    /**
     * Method setPosOrderx
     * 
     * @param index
     * @param vPosOrderx
     */
    public void setPosOrderx(int index, com.mpe.pos.xml.PosOrderx vPosOrderx)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _posOrderxList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _posOrderxList.set(index, vPosOrderx);
    } //-- void setPosOrderx(int, com.mpe.pos.xml.PosOrderx) 

    /**
     * Method setPosOrderx
     * 
     * @param posOrderxArray
     */
    public void setPosOrderx(com.mpe.pos.xml.PosOrderx[] posOrderxArray)
    {
        //-- copy array
        _posOrderxList.clear();
        for (int i = 0; i < posOrderxArray.length; i++) {
            _posOrderxList.add(posOrderxArray[i]);
        }
    } //-- void setPosOrderx(com.mpe.pos.xml.PosOrderx) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static com.mpe.pos.xml.PosOrderxs unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (com.mpe.pos.xml.PosOrderxs) Unmarshaller.unmarshal(com.mpe.pos.xml.PosOrderxs.class, reader);
    } //-- com.mpe.pos.xml.PosOrderxs unmarshal(java.io.Reader) 

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
