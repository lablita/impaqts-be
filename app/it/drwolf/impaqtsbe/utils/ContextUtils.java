package it.drwolf.impaqtsbe.utils;

import com.sketchengine.manatee.StrVector;

public class ContextUtils {
	public static String strip_tags(StrVector tokens) {
		String ret = "";
		for (int i = 0; i < tokens.size(); i += 2) {
			ret += tokens.get(i);
			ret += ' ';
		}
		return ret;
	}
}
