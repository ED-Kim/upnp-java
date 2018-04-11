package com.tjapp.upnp;

class StringUtil {

	public static String yesNo(boolean b) {
		return b ? "yes" : "no";
	}

	public static String yesNo(int num) {
		return num == 0 ? "no" : "yes";
	}

	public static String quote(String text) {
		return "\"" + text.replaceAll("\"", "\\\"") + "\"";
	}

	public static String escape(String str) {
		return str.replaceAll("\"", "\\\"");
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static String unwrap(String text, String start, String end) {
		if (text.startsWith(start)) {
			text = text.substring(start.length());
		}
		if (text.endsWith(end)) {
			text = text.substring(0, text.length() - end.length());
		}
		return text;
	}
}
