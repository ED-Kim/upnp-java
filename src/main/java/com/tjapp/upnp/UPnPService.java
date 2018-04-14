package com.tjapp.upnp;

import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;


public class UPnPService {

	private UPnPScpd scpd;
	private Map<String, UPnPProperty> properties = new LinkedHashMap<>();
	private static Logger logger = Logger.getLogger("UPnPService");

    public String getServiceType() {
		return properties.get("serviceType").getValue();
	}

	public String getServiceId() {
		return properties.get("serviceId").getValue();
	}

	public String getScpdUrl() {
		return properties.get("SCPDURL").getValue();
	}

	public void setScpdUrl(String scpdUrl) {
		logger.debug("set scpd url: " + scpdUrl);
		setProperty("SCPDURL", scpdUrl);
	}

	public String getControlUrl() {
		return properties.get("controlURL").getValue();
	}
	
	public void setControlUrl(String controlUrl) {
		logger.debug("set control url: " + controlUrl);
		setProperty("controlURL", controlUrl);
	}

	public String getEventSubUrl() {
		return properties.get("eventSubURL").getValue();
	}
	
	public void setEventSubUrl(String eventSubUrl) {
		logger.debug("set event sub url: " + eventSubUrl);
		setProperty("eventSubURL", eventSubUrl);
	}

	public void setProperty(String name, String value) {
		properties.put(name, new UPnPProperty(name, value));
	}

	public void setProperty(UPnPProperty property) {
		properties.put(property.getName(), property);
	}

	public UPnPAction getAction(String name) {
		return scpd.getAction(name);
	}

	public UPnPScpd getScpd() {
		return scpd;
	}

	public void setScpd(UPnPScpd scpd) {
		this.scpd = scpd;
	}

	public static UPnPService fromNodeList(NodeList nodeList) {
		UPnPService service = new UPnPService();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			Node first = node.getFirstChild();
			if (node.getChildNodes().getLength() == 1 && first.getNodeName().equals("#text")) {
				UPnPProperty property = new UPnPProperty(node.getNodeName(), first.getNodeValue());
				service.setProperty(property);
			}
		}
		return service;
	}

	public String toXml() {
		XmlTag service = new XmlTag("service");
		StringBuffer sb = new StringBuffer();
		Iterator<String> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			sb.append(properties.get(key).toXml());
		}
		return service.wrap(sb.toString());
	}
}
