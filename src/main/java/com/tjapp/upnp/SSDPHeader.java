package com.tjapp.upnp;

class SSDPHeader extends HttpHeader {
	public boolean isNotify() {
		return getFirstParts()[0] == "NOTIFY";
	}
	public boolean isMsearch() {
		return getFirstParts()[0] == "M-SEARCH";
	}
	public boolean isResponse() {
		return getFirstParts()[0].startsWith("HTTP");
	}
}
