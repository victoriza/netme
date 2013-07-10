package es.moodbox.humantower.handlers.util;

import java.util.HashMap;
import java.util.StringTokenizer;

public class RequestUtil {

	public static HashMap<String, String> getParams(String paramaters) {
		StringTokenizer paramGroup = new StringTokenizer(paramaters, "&");

		HashMap<String, String> params = new HashMap<String, String>();
		while(paramGroup.hasMoreTokens()){
			StringTokenizer value = new StringTokenizer(paramGroup.nextToken(), "=");
			params.put(value.nextToken(), value.nextToken());
		}
		return params;
	}
}
