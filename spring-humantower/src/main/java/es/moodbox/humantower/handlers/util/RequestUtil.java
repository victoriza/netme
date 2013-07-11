package es.moodbox.humantower.handlers.util;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities to manage the request
 * 
 * @author victor
 *
 */
public class RequestUtil {

	private static final Logger LOG = LoggerFactory.getLogger(RequestUtil.class);

	private static final String EQUAL = "=";
	private static final String DELIM = "&";

	/**
	 * It returns a the params from a normal request
	 * Example:level=y&area=x
	 * 
	 * @param paramaters the actual params
	 * @return
	 */
	public static HashMap<String, String> getParams(String paramaters) {
		HashMap<String, String> params = new HashMap<String, String>();
		try{
			StringTokenizer paramGroup = new StringTokenizer(paramaters, DELIM);
			while(paramGroup.hasMoreTokens()){
				StringTokenizer value = new StringTokenizer(paramGroup.nextToken(), EQUAL);
				params.put(value.nextToken(), value.nextToken());
			}
		}
		catch (NoSuchElementException ex) {
			LOG.error(ex.getMessage());
		}
		return params;
	}

	/**
	 * Tries to builds an integer for a number, 
	 * if not posible it returns -1
	 * 
	 * @param number
	 * @return
	 */
	public static Integer parseString(String number) {
		
		Integer res = -1;
		
		try {
			res = Integer.parseInt(number);
		} catch (NumberFormatException ex) {
			LOG.error(ex.getMessage());
			
			//we are not able to parse
			res = -1;
		}
		return res;
	}
}
