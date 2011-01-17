/*
 * Created on Apr 3, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mpe.common;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author Agung Hadiwaluyo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PhysicalNetworkAddress {

    public static void main(String[] args) {
        try {
            String computer = "ballack";
            InetAddress a = InetAddress.getByName(computer);
            System.out.println("Computer name & IP : " +a);
            NetworkInterface ni = NetworkInterface.getByInetAddress(a);
            byte[] hwAddrByte = ni.getHardwareAddress();
            
            for(int i=0; i<hwAddrByte.length; i++) {
                if ( i == 0) System.out.print ("Hardware Address : ");
                System.out.print(Integer.toHexString(hwAddrByte[i]));
                if (i != (hwAddrByte.length - 1)) System.out.print (" - ");
            }
        } catch (Throwable e) {
            System.out.println("Error : " + e); 
        }
    }
}
