package org.wso2.projecttracker.app.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;




public class AppUtil {
	
	public static String getServicePath(String property){
		return getProperty(property);
	}
	
	private static String getProperty(String value) {
        String servicePath = null;
        try {
            Properties properties = new Properties();
            properties.load(AppUtil.class.getResourceAsStream("/endpoint.properties"));
            servicePath = properties.getProperty(value);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return servicePath;
    }
	
	 public synchronized static String encrypt(String plaintext)
	            throws NoSuchAlgorithmException, UnsupportedEncodingException {
	                MessageDigest md = MessageDigest.getInstance("SHA1");
	                md.reset();
	                byte[] buffer = plaintext.getBytes();
	                md.update(buffer);
	                byte[] digest = md.digest();

	                String hexStr = "";
	                for (int i = 0; i < digest.length; i++) {
	                    hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
	                }
	                return hexStr;
	  }
	

}
