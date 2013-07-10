package es.moodbox.humantower.handlers.util;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Utilities to manage the request
 * 
 * @author victor
 *
 */
public class RequestUtil {

	private static final String EQUAL = "=";
	private static final String DELIM = "&";
	
	
	public static HashMap<String, String> getParams(String paramaters) {
		StringTokenizer paramGroup = new StringTokenizer(paramaters, DELIM);

		HashMap<String, String> params = new HashMap<String, String>();
		while(paramGroup.hasMoreTokens()){
			StringTokenizer value = new StringTokenizer(paramGroup.nextToken(), EQUAL);
			params.put(value.nextToken(), value.nextToken());
		}
		return params;
	}
}
