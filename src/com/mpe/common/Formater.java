/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.mpe.common;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.sql.Time;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Formater {
  static Log log = LogFactory.getFactory().getInstance("Formater");
	
  // default value
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat sdtf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
	private static SimpleDateFormat sdt = new SimpleDateFormat("HH:mm");
	
	public static String getFormatedPayment(double x) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("0");
		java.text.DecimalFormat df2 = new java.text.DecimalFormat("000");
		String a = df.format(x);
		String out = "";
		if (a.length() > 3) {
			// 1234567
			String a1 = a.substring(0, a.length()-3);
			String a2 = a.substring(a.length() - 3);
			double w = Double.parseDouble(a2);
			if (w > 0 && w < 250 ) {
				w = 0;
			} else if (250 <= w && w < 500) {
				w = 500;
			} else if (500 <= w && w < 750) {
				w = 500;
			} else if (750 <= w && w <= 1000) {
				w = 1000;
			}
			// join a1 + w
			if (w == 1000) {
				double w1 = Double.parseDouble(a1);
				w1 = w1 + 1;
				out = df.format(w1)+"000";
			} else {
				out = a1 + df2.format(w);
			}
		} else {
			if (a.length() == 3) {
				double w = Double.parseDouble(a);
				if (w > 0 && w < 250 ) {
					w = 0;
				} else if (250 <= w && w < 500) {
					w = 500;
				} else if (500 <= w && w < 750) {
					w = 500;
				} else if (750 <= w && w <= 1000) {
					w = 1000;
				}
				out = df.format(w);
			} else if (a.length() > 0 && a.length() <3) {
				double w = Double.parseDouble(a);
				if (w > 0) {out = "500";} else out = "0"; 
			} else {
				out = "0";
			}
		}
		return out;
	}
	
	public static String getFormatedOutput(int numberOfDigit, double x) {
		java.text.DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
		if (numberOfDigit == 5) {
			df = new DecimalFormat("###,###,###,###,###,##0.00000");
			df.setGroupingSize(3);
			return df.format(x);
		} else if (numberOfDigit == 4) {
			df = new DecimalFormat("###,###,###,###,###,##0.0000");
			df.setGroupingSize(3);
			return df.format(x);
		} else if (numberOfDigit == 3) {
			df = new DecimalFormat("###,###,###,###,###,##0.000");
			df.setGroupingSize(3);
			return df.format(x);
		} else if (numberOfDigit == 2) {
			df = new DecimalFormat("###,###,###,###,###,##0.00");
			df.setGroupingSize(3);
			return df.format(x);	
		} else if (numberOfDigit == 1) {
			df = new DecimalFormat("###,###,###,###,###,##0.0");
			df.setGroupingSize(3);
			return df.format(x);
		} else if (numberOfDigit == 0) {
			df = new DecimalFormat("###,###,###,###,###,##0");
			df.setGroupingSize(3);
			return df.format(x);
		} else {
			df.setGroupingSize(3);
			return df.format(x);
		}
	}
	
	public static String getFormatedOutputForm(int numberOfDigit, double x) {
		java.text.DecimalFormat df = new DecimalFormat("0.##");
		if (numberOfDigit == 5) {
			df = new DecimalFormat("0.00000");
			df.setGroupingSize(3);
			return df.format(x);
		} else if (numberOfDigit == 4) {
			df = new DecimalFormat("0.0000");
			df.setGroupingSize(3);
			return df.format(x);
		} else if (numberOfDigit == 3) {
			df = new DecimalFormat("0.000");
			df.setGroupingSize(3);
			return df.format(x);
		} else if (numberOfDigit == 2) {
			df = new DecimalFormat("0.00");
			df.setGroupingSize(3);
			return df.format(x);	
		} else if (numberOfDigit == 1) {
			df = new DecimalFormat("0.0");
			df.setGroupingSize(3);
			return df.format(x);
		} else if (numberOfDigit == 0) {
			df = new DecimalFormat("0");
			df.setGroupingSize(3);
			return df.format(x);
		} else {
			df.setGroupingSize(3);
			return df.format(x);
		}
	}
	
	public static double getFormatedOutputResult(int numberOfDigit, double x) {
		java.text.DecimalFormat df = new DecimalFormat("0.##");
		double y2 = 0;
		if (numberOfDigit == 5) {
			df = new DecimalFormat("######################0.00000");
			df.setGroupingSize(3);
			y2 = Double.parseDouble(df.format(x));
		} else if (numberOfDigit == 4) {
			df = new DecimalFormat("######################0.0000");
			df.setGroupingSize(3);
			y2 = Double.parseDouble(df.format(x));
		} else if (numberOfDigit == 3) {
			df = new DecimalFormat("######################0.000");
			df.setGroupingSize(3);
			y2 = Double.parseDouble(df.format(x));
		} else if (numberOfDigit == 2) {
			df = new DecimalFormat("######################0.00");
			df.setGroupingSize(3);
			y2 = Double.parseDouble(df.format(x));
		} else if (numberOfDigit == 1) {
			df = new DecimalFormat("######################0.0");
			df.setGroupingSize(3);
			y2 = Double.parseDouble(df.format(x));
		} else if (numberOfDigit == 0) {
			df = new DecimalFormat("######################0");
			df.setGroupingSize(3);
			y2 = Double.parseDouble(df.format(x));
		} else {
			df.setGroupingSize(3);
			y2 = Double.parseDouble(df.format(x));
		}
		//log.info("[ CONVERT DOUBLE : "+x+" --> "+y2+" ]");
		return y2;
	}
	
	public static String getFormatedOutputNoDecimal(double x) {
		java.text.DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0");
		df.setGroupingSize(3);
		return df.format(x);
	}
	
	public static String getFormatedOutput(double x) {
		java.text.DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00000");
		df.setGroupingSize(3);
		return df.format(x);
	}
	
	public static String getFormatedOutputForm(double x) {
		java.text.DecimalFormat df = new DecimalFormat("0");
		df.setGroupingSize(3);
		return df.format(x);
	}
	
	public static String getFormatedDate(Date date) {
		try {
			ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
			sdf = new SimpleDateFormat((String)(prop.getString("date.format")));
		}catch(Exception exx) {
		}
		return date!=null?sdf.format(date):"";
	}
	
	public static String getFormatedDateTime(Date date) {
		try {
			ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
			sdtf = new SimpleDateFormat((String)(prop.getString("date.time.format")));
		}catch(Exception exx) {
		}
		return date!=null?sdtf.format(date):"";
	}
	
	public static String getFormatedTime(Time time) {
		try {
			ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
			sdt = new SimpleDateFormat((String)(prop.getString("time.format")));
		}catch(Exception exx) {
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(time);
		return time!=null?sdt.format(calendar.getTime()):"";
	}
	
	public static String getFormatedTime(Date date) {
		try {
			ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
			sdt = new SimpleDateFormat((String)(prop.getString("time.format")));
		}catch(Exception exx) {
		}
		Calendar calendar = new GregorianCalendar();
		if (date!=null) calendar.setTime(date);
		return date!=null?sdt.format(calendar.getTime()):"";
	}

}
