package com.tjapp.upnp;

import java.io.*;
import java.net.*;

public class HttpResponse {
	
	public HttpHeader header;
	public byte[] data;

	public HttpResponse () {
		
	}
	public HttpResponse (int code) {
		this.header = new HttpHeader();
		this.header.setFirstLine("HTTP/1.1 " + code + " " + HttpStatusCode.getMessage(code));
	}

	public HttpResponse (HttpHeader header) {
		this.header = header;
	}
		
	public HttpResponse (HttpHeader header, byte[] data) {
		this.header = header;
		this.data = data;
	}

	public void clear() {
		header.clear();
		data = null;
	}

	public void setHttpHeader(HttpHeader header) {
		this.header = header;
	}

	public HttpHeader getHttpHeader() {
		return header;
	}

	public void setFirstLine(String firstLine) {
		header.setFirstLine(firstLine);
	}

	public String getFirstLine() {
		return header.getFirstLine();
	}
	
	public int getContentLength() {
		return header.getContentLength();
	}

	public String getContentType() {
		return header.getContentType();
	}

	public void setHeader(String name, String value) {
		header.setHeader(name, value);
	}
	
	public String getHeader(String name) {
		return header.getHeader(name);
	}

	public void setData(String text) {
		setData(text.getBytes());
	}

	public void setData(byte[] data) {
		this.data = data;
		header.setHeader("Content-Length", Integer.toString(data.length));
	}

	public byte[] getData() {
		return data;
	}

	public String text() {
		return new String(data);
	}

	public static HttpResponse fromConnection(HttpURLConnection conn) throws IOException {
		HttpHeader header = new HttpHeader();
		header.setFirstLine(conn.getHeaderFields().get(null).get(0));
		header.setHeaderFields(conn.getHeaderFields());
		byte[] data = IOUtil.dump(conn.getInputStream());
		return new HttpResponse(header, data);
	}
}
