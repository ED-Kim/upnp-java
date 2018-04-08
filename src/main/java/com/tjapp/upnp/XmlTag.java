package com.tjapp.upnp;

import java.util.*;


public class XmlTag {

	private String ns;
	private String name;
	private List<Pair<String, String>> attrs = new ArrayList<>();

	public XmlTag(String name) {
		this.name = name;
	}

	public XmlTag(String ns, String name) {
		this.ns = ns;
		this.name = name;
	}

	public XmlTag(String name, List<Pair<String, String>> attrs) {
		this.name = name;
		this.attrs = attrs;
	}

	public XmlTag(String ns, String name, List<Pair<String, String>> attrs) {
		this.ns = ns;
		this.name = name;
		this.attrs = attrs;
	}

	public static void main(String[] args) {
		// main
	}
	
	public String start() {
		return "<" + append(nsName(), strAttributes(), " ") + ">";
	}

	public String end() {
		return "</" + nsName() + ">";
	}

	public String wrap(String text) {
		return start() + text + end();
	}

	public String nsName() {
		return (isEmpty(ns) ? name : (ns + ":" + name));
	}

	public String strAttributes() {
		StringBuffer sb = new StringBuffer();
		for (Pair<String, String> attr : attrs) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			sb.append(attr.getFirst() + "=\"" + attr.getSecond() + "\"");
		}
		return sb.toString();
	}

	private String append(String a, String b, String sep) {
		if (isEmpty(a)) {
			return b;
		}
		if (isEmpty(b)) {
			return a;
		}
		return a + sep + b;
	}

	public String getNamespace() {
		return ns;
	}

	public void setNamespace(String ns) {
		this.ns = ns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addAttribute(String name, String value) {
		addAttribute(new Pair<String, String>(name, value));
	}

	public void addAttribute(Pair<String, String> pair) {
		attrs.add(pair);
	}

	public List<Pair<String, String>> getAttributes() {
		return attrs;
	}

	public void setAttributes(List<Pair<String, String>> attrs) {
		this.attrs = attrs;
	}

	private boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	private String escape(String str) {
		return str.replaceAll("\"", "\\\"");
	}
}
