package com.tjapp.upnp;

import java.util.*;

class HttpHeader {

	public class HeaderFields extends LinkedHashMap<String, List<String>> {

		Map<String, String> keymap = new HashMap<>();
		
		@Override
		public List<String> put(String key, List<String> value) {
			keymap.put(key.toLowerCase(), key);
            return super.put(key, value);
		}
		public List<String> get(String key) {
            return super.get(keymap.get(key.toLowerCase()));
		}
	}

	private String firstLine;
	private HeaderFields headers = new HeaderFields();

	public String getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}
	
	public String getHeader(String name) {
		List<String> values = headers.get(name);
		if (values != null && values.size() > 0) {
			return values.get(0);
		}
		return null;
	}
	public String[] getHeaders(String name) {
		List<String> values = headers.get(name);
		if (values != null) {
			return values.toArray(new String[values.size()]);
		}
		return null;
	}
	public void setHeader(String name, String value) {
		List<String> values = headers.get(name);
		if (values == null) {
			values = new ArrayList();
			values.add(value);
			headers.put(name, values);
		} else {
			values.clear();
			values.add(value);
		}
	}
	public void setHeaders(String name, String[] values) {
		headers.put(name, new ArrayList<>(Arrays.asList(values)));
	}
	public void appendHeader(String name, String value) {
		List<String> values = headers.get(name);
		if (values == null) {
			values = new ArrayList();
			values.add(value);
			headers.put(name, values);
		} else {
			values.add(value);
		}
	}
	public void removeHeader(String name) {
		headers.remove(name);
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(firstLine + "\r\n");
		Iterator<String> iter = headers.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			List<String> values = headers.get(key);
			for (String value : values) {
				sb.append(key + ": " + value + "\r\n");
			}
		}
		sb.append("\r\n");
		return sb.toString();
	}
}
