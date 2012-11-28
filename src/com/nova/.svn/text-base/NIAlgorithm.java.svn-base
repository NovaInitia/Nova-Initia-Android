package com.nova;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;

public class NIAlgorithm {
	public static String sha256(String in) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(in.getBytes("UTF-8"));
	        byte[] byteData = md.digest();

	        StringBuilder hexString = new StringBuilder();
	        for (int i = 0; i < byteData.length; i++) {
	            String hex = Integer.toHexString(0xff & byteData[i]);
	            if (hex.length() == 1)
	                hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
		} catch (Exception e) {
			System.out.println(e.toString());
			return "";
		}
	}
	
	private static String MD5(String in) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(in.getBytes("UTF-8"));
	        byte[] byteData = md.digest();

	        StringBuilder hexString = new StringBuilder();
	        for (int i = 0; i < byteData.length; i++) {
	            String hex = Integer.toHexString(0xff & byteData[i]);
	            if (hex.length() == 1)
	                hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
		} catch (Exception e) {
			System.out.println(e.toString());
			return "";
		}
	}
	
	public static String base32md5(String in) {
		String hex = MD5(in);
		
		String b32 = "";
		for(int i = 0; i < 7; i++)
		{
			int length = (i==6?2:5);
			String b32tmp = Integer.toString(Integer.parseInt(hex.substring(0,length),16),32);
			while(b32tmp.length() < (i==6?2:4))
				b32tmp = "0"+b32tmp;
			b32 += b32tmp; 
			if (i < 6)
				hex = hex.substring(5);
		}
		
		return b32;
	}

}
