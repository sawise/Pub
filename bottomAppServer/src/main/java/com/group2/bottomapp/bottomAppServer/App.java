package com.group2.bottomapp.bottomAppServer;

//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;


public class App 
{
    public static void main( String[] args )
    {
    	/*
    	String password = "";
    	 
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        md.update(password.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
 
        System.out.println("Hex format : " + sb.toString());
 
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	System.out.println("Hex format : " + hexString.toString());
    	*/
    }
}




/* TODO  */

//TODO implement Hibernate instead.

// Use @Autowire instead?

// Search in new thread?

// API security

//TODO add ingredient with JSON and jQuery instead

//TODO validate drink input

//TODO Be able to add and remove ingredients (and measurements for them) in the edit view,  Drink Ingredients should not be able to be empty
//and measurements should not be able to be empty   using JSON??

//TODO add messages for all errors.

//TODO Secure db-connection? http://docs.spring.io/spring-security/site/docs/3.0.x/reference/core-services.html

//TODO Make header, footer

//TODO Nice feedback for errors (correct error messages), custom 404 page. and feedback on remove and adding item

//TODO Set Headers, methods etc... in JsonController 

//TODO JSON API Handle Exceptions, and null pointers

//TODO JSON Methods, update rating method. secure login from app.  

//TODO Handle Exceptions in web controllers aswell
