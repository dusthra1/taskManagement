package com.mindtree.task.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class TaskUtil {
	
	private static final Logger log = Logger.getLogger(TaskUtil.class);
	
	@Autowired
	private ReloadableResourceBundleMessageSource applicationProperties;
	
	public static Date parseDate(String sDate){	
		Date date = null;
			try {
				 date = new SimpleDateFormat("dd-MM-yyyy").parse(sDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    return date;
	}

	/*public static Gson getGsonInstance(){
		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();
		return gson;
	}*/
	
	public static boolean isEmptyString(String string) {
		boolean isEmpty = true;
		string = null != string ? string.trim() : string;
		if (null != string && !"".equalsIgnoreCase(string) && !"null".equalsIgnoreCase(string)) {
			isEmpty = false;
		}
		return isEmpty;
	}
	
	public static List<String> parseString(String dataStr, String key) {		
		List<String> parsedList = new ArrayList<String>();
		if (null != dataStr) {
			String[] tempArray = dataStr.split(key);
			if (null != tempArray) {
				parsedList.addAll(Arrays.asList(tempArray));
			}
		}	
		return parsedList;
	}
	
	/**
	 * Method encrypts the given string using Base64
	 * 
	 * @param plainText
	 * @return String
	 */
	public static String getEncryptedString(String plainText) {		
		String encryptedText = "";
		try {
			encryptedText = new String(Base64.encodeBase64(plainText.getBytes()));
		} catch (Exception e) {
			log.error("Exception while encrypting text: " + e.getMessage());
			encryptedText = "";
		}
		return encryptedText;
	}
	
	/**
	 * Method descrypts the given string using Base64
	 * 
	 * @param encryptedText
	 * @return String
	 */
	public static String getDecryptedString(String encryptedText) {		
		String plainText = "";
		try {
			byte[] decoded = Base64.decodeBase64(encryptedText);
			plainText = new String(decoded);

		} catch (Exception e) {
			log.error("Exception while decrypting text: " + e.getMessage());
			plainText = "";
		}		
		return plainText;
	}
	
	/**
	 * Method returns the url that needs to be invoked on session time out
	 * 
	 * @return String
	 */
	public String timeOutUrl() {			
		String appname = applicationProperties.getMessage("application.name", null, null);
		String timeOutUrl = "/" + appname + "/logoff.do?st=1";		
		return timeOutUrl;
	}
	
	/**
	 * Null-safe check if the specified collection is empty.
	 * <p>
	 * Null returns true.
	 * 
	 * @param coll  the collection to check, may be null
	 * @return true if empty or null
	 * @since Commons Collections 3.2
	 */
	public static boolean isEmpty(Collection coll) {
	    return (coll == null || coll.isEmpty());
	}
	
	/*public static void main(String args[]){
		System.out.println(getEncryptedString("abcd1234"));
		
		System.out.println(getDecryptedString("YWJjZDEyMzQ="));
		
	}*/
	
	
	
}
