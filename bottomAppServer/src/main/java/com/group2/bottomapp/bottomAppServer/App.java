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

	// Exceptions!!  http://www.baeldung.com/2013/01/31/exception-handling-for-rest-with-spring-3-2/
	// https://github.com/nebhale/spring-one-2012/tree/master/src/main/java/com

/*
  
<property name="url" value="jdbc:mysql://localhost:3306/team-2" />
<property name="username" value="team-2" />
<property name="password" value="team-2" /> 

<property name="url" value="jdbc:mysql://localhost:3306/BottomApp" />
<property name="username" value="root" />
<property name="password" value="" />
 
 */

// also push, check login errors 
//Error with login, email get's null? no need to replace token each time?

// what happens on server timeout???  503 Service Unavailable : (funkar med did not work??)

// OBS validate new user input here as well max chars


// http://java.dzone.com/articles/java-security-tutorial-%E2%80%93-step
// http://blog.antoine.li/2010/10/22/android-trusting-ssl-certificates/

//TODO JSON API Handle Exceptions, and null pointers

//TODO Handle Exceptions in Controllers

//TODO implement Hibernate instead.

// Use @Autowire instead?

//TODO pagination will be needed for drinks?

//TODO add logout on all pages

//TODO add ingredient with JSON and jQuery instead

//TODO validate drink input

//TODO Be able to add and remove ingredients (and measurements for them) in the edit view,  Drink Ingredients should not be able to be empty
//and measurements should not be able to be empty   using JSON??

//TODO add messages for all errors.

//TODO Secure db-connection? http://docs.spring.io/spring-security/site/docs/3.0.x/reference/core-services.html

//TODO Make header, footer

//TODO Nice feedback for errors (correct error messages), custom 404 page. and feedback on remove and adding item

