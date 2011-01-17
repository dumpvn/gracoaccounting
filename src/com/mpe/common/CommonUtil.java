/*
 * Created on Jun 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mpe.common;

import java.security.MessageDigest;
import java.util.Random;
import com.mpe.financial.model.View;


/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonUtil {
	public static final String SAMA_DENGAN = "=";
	
	public static boolean isHasRoleAccess(java.util.Set lst,String viewAccessPath) {
		if(lst !=null && viewAccessPath!=null) {
			java.util.Iterator itr = lst.iterator();
			while(itr.hasNext()) {
				View view = (View)itr.next();
				if (view.getLink()!=null) {
					try {
						if (view.getLink().equals(viewAccessPath)) return true;
					} catch(Exception exx) {
					}
				}
			}
		}
		return false;		
	}
	
	/** 
	 * Method digest.
	 * @return String
	 */
	public static String digest(String plain) throws Exception {
		if (plain == null) return null;
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			sha.update(plain.getBytes("UTF-16"));
			byte bs[] = sha.digest();
			StringBuffer res = new StringBuffer();
			for (int ix=0; ix<bs.length; ix++) {
				int i;
				byte b = bs[ix];
				if (b > 0) i = b; else i = 256 + b;
				int d = i / 16;
				if (d > 9) res.append((char) ('A' + d - 10)); else res.append((char) ('0' + d));
				d = i % 16;
				if (d > 9) res.append((char) ('A' + d - 10)); else res.append((char) ('0' + d));
			}
			return res.toString();
		} catch (Exception ex) {
			throw new Exception("Digest Exception",ex);
		}
	}
	
	public static String getMedia(byte[] b) throws Exception {
		StringBuffer out = new StringBuffer();
		out.append(new String(b, "ISO-8859-1"));
		return out.toString();
	}
	
	public static String randomin(int minLeft, int lenLeft, int minRight, int lenRight) {
		String[] data = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

		Random generator = new Random();
		StringBuffer result = new StringBuffer();

		int len = minLeft + generator.nextInt(lenLeft);
		for (int i=0; i<len; i++) {
			int idx = generator.nextInt(data.length);
			result.append(data[idx]);
			idx = generator.nextInt(data.length);
		}

		result.append(SAMA_DENGAN);

		len = minRight + generator.nextInt(lenRight);
		for (int i=0; i<len; i++) {
			int idx = generator.nextInt(data.length);
			result.append(data[idx]);
			idx = generator.nextInt(data.length);
		}

		return result.toString();
	}
	
	public static String htmlEntities(String value){
		String result = value;
		try{
			
			String[][] tagArray = new String[][]{{"<","&lt;"}, {">","&gt;"}, {"\\[b\\]","<b>"}, {"\\[li\\]","<li>"} ,{"\\[/b\\]","</b>"}, {"\\[i\\]","<i>"}, {"\\[/i\\]","</i>"}, {"\\[ul\\]","<ul>"}, {"\\[ul type=\"1\"\\]","<ul type=\"1\">"} ,  {"\\[ul type=\"a\"\\]","<ul type=\"a\">"} ,  {"\\[ul type=\"A\"\\]","<ul type=\"A\">"} ,{"\\[/ul\\]","</ul>"}, {"\\[center\\]","<center>"}, {"\\[/center\\]","</center>"}, {"\\[br\\]","<br>"}, {"\\[/br\\]","</br>"}, {"\\[u\\]","<u>"}, {"\\[/u\\]","</u>"}};
  			for (int i = 0; i < tagArray.length; i++) {
              result=result.replaceAll(tagArray[i][0]  , tagArray[i][1]);
            }

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}


	public static String htmlUser(String value){
		String result = value;
		try{
			
			String[][] tagArray = new String[][]{{"<","\\["}, {">","\\]"} ,{"</","\\[/"}};
  			for (int i = 0; i < tagArray.length; i++) {
              result=result.replaceAll(tagArray[i][0]  , tagArray[i][1]);
            }

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
}
