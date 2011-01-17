/*
 * Created on May 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mpe.common;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Agung Hadiwaluyo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AmountSay {
    static Log log = LogFactory.getFactory().getInstance("AmountSay");
    // angka
    static String angka[] = {"nol", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan"};  
    static String puluhan[] = {"", "puluh", "ratus"};
    static String pengganti[] = {"sepuluh", "seratus", "seribu"};
    static String perkalian[] = {"", "ribu", "juta", "milyar", "trilyun", "biliun"};
    
    public static String getSaying(String string) {
        // hilangkan tanda "(" & ")"
        if (string.indexOf("(")>0) string.substring(1, string.length()-1);
        String r = "";
        String depanKoma = "";
        String belakangKoma = "";
        try {
	        StringTokenizer tokenizer = new StringTokenizer(string, ".");
	        // bedakan belakang koma
	        int i = 0;
	        while (tokenizer.hasMoreTokens()) {
	            if (i==0) depanKoma = tokenizer.nextToken();
	            else belakangKoma = tokenizer.nextToken();
	            i++;
	        }
	        if (depanKoma!=null && depanKoma.length()>0) {
	            //log.info("D : "+depanKoma+"//"+belakangKoma);
	            int d = 0;
	            StringTokenizer tokenizer2 = new StringTokenizer(depanKoma, ",");
	            // bedakan ribuan, jutaan, milyar, trilyun
	            while (tokenizer2.hasMoreTokens()) {
	                tokenizer2.nextToken();
	  	            d++;
	  	        }
	            tokenizer2 = new StringTokenizer(depanKoma, ",");
	            // bedakan ribuan, jutaan, milyar, trilyun
	            String c = "";
	            while (tokenizer2.hasMoreTokens()) {
		            c = tokenizer2.nextToken();
		            int y = c.length();
		            //y--;
		            boolean semuaNol = true;
		            // cek belasan!
		            for (int x=0; x<c.length(); x++) {
		                //log.info("C : " + c.substring(c.length()-2, c.length()-1));
		                if (y==1 && d==2 && c.length()==1 && c.equalsIgnoreCase("1")) {
		                    r = r + (r.length()>0?" ":"") +"seribu";
		                    semuaNol = false;
		                    y--;
		                } else if (y==3 && c.substring(c.length()-3, c.length()-2).equalsIgnoreCase("1")) {
		                    r = r + (r.length()>0?" ":"") +"se" + "ratus";
		                    semuaNol = false;
		                    y--;
		                } else if (y==2 && c.substring(c.length()-2, c.length()-1).equalsIgnoreCase("1")) {
		                    String z = angka[Integer.parseInt(c.substring(c.length()-1, c.length()))];
		                    if (!z.equalsIgnoreCase("nol")) {
		                        if (z.equalsIgnoreCase("satu")) r = r + (r.length()>0?" ":"") +"se" + "belas";
		                        else r = r + (r.length()>0?" ":"") +z + " belas";
		                        y--;
		                        x++;
		                        semuaNol = false;
		                    } else {
		                        r = r + (r.length()>0?" ":"") +"se" + "puluh";
		                        y--;
		                        x++;
		                        semuaNol = false;
		                    }
		                } else {
		                	//log.info("[ B "+y+" "+x+" "+d+" ]");
		                    // cek jika nol => abaikan
		                    String z = angka[Integer.parseInt(c.substring(x, x+1))];
		                    if (!z.equalsIgnoreCase("nol")) {
		                        r = r + ((r.length()>0?" ":"") + z);
		                        semuaNol = false;
		                    }
		                    if (y>0) {
		                      if (!z.equalsIgnoreCase("nol")) {
			                      r = r + (puluhan[y-1].length()>0?(" "+puluhan[y-1]):"");
			                      semuaNol = false;
		                      }
		                      y--;
		                    }
		                }
		            }
		            if (!semuaNol) r = r + (r.length()>0?" ":"") + perkalian[d-1];
		            d--;
	            }
	        }
	        if (belakangKoma!=null && belakangKoma.length()>0) {
	            // belakang koma
	            int d = 1;
	            // cek apakah nol semua OR tidak
	            boolean nolSemua = true;
	            for (int k=0; k<belakangKoma.length(); k++) {
	                if (!belakangKoma.substring(k, k+1).equalsIgnoreCase("0")) nolSemua = false;
	            }
	            if (nolSemua);
	            else r = r.trim() + " koma";
	            
	            int y = belakangKoma.length();
	            //y--;
	            boolean semuaNol = true;
	            // cek belasan!
	            for (int x=0; x<belakangKoma.length(); x++) {
	                //log.info("C : " + c.substring(c.length()-2, c.length()-1));
	                if (y==1 && d==2 && belakangKoma.length()==1) {
	                    r = r + (r.length()>0?" ":"") +"seribu";
	                    y--;
	                } else if (y==3 && belakangKoma.substring(belakangKoma.length()-3, belakangKoma.length()-2).equalsIgnoreCase("1")) {
	                    r = r + (r.length()>0?" ":"") +"se" + "ratus";
	                    y--;
	                } else if (y==2 && belakangKoma.substring(belakangKoma.length()-2, belakangKoma.length()-1).equalsIgnoreCase("1")) {
	                    String z = angka[Integer.parseInt(belakangKoma.substring(belakangKoma.length()-1, belakangKoma.length()))];
	                    if (!z.equalsIgnoreCase("nol")) {
	                        if (z.equalsIgnoreCase("satu")) r = r + (r.length()>0?" ":"") +"se" + "belas";
	                        else r = r + (r.length()>0?" ":"") +z + " belas";
	                        y--;
	                        x++;
	                        semuaNol = false;
	                    } else {
	                        r = r + (r.length()>0?" ":"") +"se" + "puluh";
	                        y--;
	                        x++;
	                        semuaNol = false;
	                    }
	                } else {
	                    // cek jika nol => abaikan
	                    String z = angka[Integer.parseInt(belakangKoma.substring(x, x+1))];
	                    if (!z.equalsIgnoreCase("nol")) {
	                        r = r + ((r.length()>0?" ":"") + z);
	                        semuaNol = false;
	                    }
	                    if (y>0) {
	                      if (!z.equalsIgnoreCase("nol")) {
		                      r = r + (puluhan[y-1].length()>0?(" "+puluhan[y-1]):"");
		                      semuaNol = false;
	                      }
	                      y--;
	                    }
	                }
	            }
	            if (!semuaNol) r = r + (r.length()>0?" ":"") + perkalian[d-1];
	            d--;
            }
	        
	        
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return r;
    }
    
    public static void main(String[] args) {
        String x = getSaying("100,000,000.00");
        //System.out.println("Say : "+x);
    }
    

}
