/*
 * Created on Apr 18, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.mpe.common.form;

/**
 * @author Agung Hadiwaluyo
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import org.apache.struts.validator.DynaValidatorForm;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.struts.upload.FormFile;

/** 
 * BaseDynaForm.java created Agung Hadiwaluyo.
 * created on 05-11-2003
 * 
 * Handle fields form jsp form
 */
public class BaseDynaForm extends DynaValidatorForm {
	private static final long serialVersionUID = 1L;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat sdtf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static SimpleDateFormat sdt = new SimpleDateFormat("HH:mm");

	/** 
	 * Returns the value.
	 * @return FormFile
	 */		
	public FormFile getFile(String name) {
		return (FormFile)(this.get(name));
	}

	/** 
	 * Returns the value.
	 * @return FormFile
	 */		
	public FormFile getFile(String name,int index) {
		return (FormFile)(this.get(name,index));
	}
	
	/** 
	 * Returns the value.
	 * @return String[]
	 */	
	public String[] getCollectionSelect(String name){
		return (String[])(this.get(name));
	}

	/** 
	 * Set the value.
	 * @param value The value to set
	 */	
	public void setCollectionSelect(String name, String collectionSelect[]) {
		this.set(name,collectionSelect);
	}
	
	/** 
	 * Returns the value.
	 * @return String
	 */
	public String getString(String name) {
		return (String) this.get(name);
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setString(String name,String value) {
		this.set(name,value);
	}

	/** 
	 * Returns the value.
	 * @return String
	 */	
	public String getString(String name, int index) {
		return (String) this.get(name, index);
	}

	/** 
	 * Returns the value.
	 * @return float
	 */		
	public float getFloat(String name) {
		try {
			return (Float.parseFloat(this.getString(name)));
		}catch(Exception ex){
			return 0;
		}
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setString(String name,float value) {
		this.set(name,Float.toString(value));
	}

	/** 
	 * Returns the value.
	 * @return double
	 */		
	public double getDouble(String name) {
		try {
			return (Double.parseDouble(this.getString(name)));
		}catch(Exception ex){
			return 0;
		}
	}
	
	/** 
	 * Returns the value.
	 * @return long
	 */		
	public double getDouble(String name,int index) {
		try {
			return (Double.parseDouble(this.getString(name,index)));
		}catch(Exception ex){
			return 0;
		}
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setString(String name,double value) {
		this.set(name,Double.toString(value));
	}

	/** 
	 * Returns the value.
	 * @return long
	 */		
	public long getLong(String name) {
		try {
			return (Long.parseLong(this.getString(name)));
		}catch(Exception ex){
			return 0;
		}
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setString(String name,long value) {
		this.set(name,Long.toString(value));
	}
	
	/** 
	 * Returns the value.
	 * @return long
	 */		
	public long getLong(String name,int index) {
		try {
			return (Long.parseLong(this.getString(name,index)));
		}catch(Exception ex){
			return 0;
		}
	}
	
	/** 
	 * Returns the value.
	 * @return int
	 */		
	public int getInt(String name) {
		try {
			return (Integer.parseInt(this.getString(name)));
		}catch(Exception ex){
			return 0;
		}
	}

	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setString(String name,int value) {
		this.set(name,Integer.toString(value));
	}
	
	/** 
	 * Returns the value.
	 * @return int
	 */		
	public int getInt(String name,int index) {
		try {
			return (Integer.parseInt(this.getString(name,index)));
		}catch(Exception ex){
			return 0;
		}
	}

	/** 
	 * Returns the value.
	 * @return Calendar
	 */			
	public Calendar getCalendar(String name) {
		Calendar cal = new GregorianCalendar();
		try {
			cal.setTime(sdf.parse(this.getString(name)));
			return cal;
		} catch(Exception ex) {
			return null;
		}
	}
	
	/** 
	 * Returns the value.
	 * @return Timestamp
	 */			
	public java.sql.Timestamp getTimestamp(String name) {
		Calendar cal = new GregorianCalendar();
		java.sql.Timestamp ts = new java.sql.Timestamp(cal.getTime().getTime());
		try {
			cal.setTime(sdtf.parse(this.getString(name)));
			ts.setTime(cal.getTime().getTime());
			return ts;
		} catch(Exception ex) {
			return null;
		}
	}
	
	/** 
	 * Returns the value.
	 * @return Calendar
	 */			
	public java.sql.Time getTime(String name) {
		Calendar cal = new GregorianCalendar();
		java.sql.Time time = new java.sql.Time(cal.getTime().getTime());
		try {
			cal.setTime(sdt.parse(this.getString(name)));
			time.setTime(cal.getTime().getTime());
			return time;
		} catch(Exception ex) {
			return null;
		}
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setCurentCalendar(String name) {
		Calendar cal = new GregorianCalendar();
		this.set(name,sdf.format(cal.getTime()));
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setCurentTimestamp(String name) {
		Calendar cal = new GregorianCalendar();
		this.set(name,sdtf.format(cal.getTime()));
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setCurentTime(String name) {
		Calendar cal = new GregorianCalendar();
		this.set(name,sdt.format(cal.getTime()));
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setCalendar(String name,Calendar cal) {
		if (cal!=null) {
			this.set(name,sdf.format(cal.getTime()));
		} else {
			this.set(name,"");
		}
	}	

	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setTimestamp(String name,java.sql.Timestamp ts) {
		if (ts!=null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(ts);
			this.set(name,sdtf.format(cal.getTime()));
		} else {
			this.set(name,"");
		}
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setTimestamp(String name, Date date) {
		if (date!=null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			this.set(name,sdtf.format(cal.getTime()));
		} else {
			this.set(name,"");
		}
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setCalendar(String name, Date date) {
		if (date!=null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			this.set(name,sdf.format(cal.getTime()));
		} else {
			this.set(name,"");
		}
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setTime(String name,java.sql.Time t) {
		if (t!=null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(t);
			this.set(name,sdt.format(cal.getTime()));
		} else {
			this.set(name,"");
		}
	}
	
	/** 
	 * Set the value.
	 * @param value The value to set
	 */
	public void setTime(String name, Date t) {
		if (t!=null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(t);
			this.set(name,sdt.format(cal.getTime()));
		} else {
			this.set(name,"");
		}
	}	
		

}
