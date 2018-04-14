package com.tjapp.upnp;

import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;


public class UPnPAction {
	
	private String name;
	private Map<String, UPnPActionArgument> arguments = new LinkedHashMap<>();

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setArgument(UPnPActionArgument argument) {
		arguments.put(argument.getName(), argument);
	}

	public UPnPActionArgument getArgument(String name) {
		return arguments.get(name);
	}

	public List<UPnPActionArgument> getArgumentList() {
		List<UPnPActionArgument> list = new ArrayList<>();
		Iterator<String> keys = arguments.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			list.add(arguments.get(key));
		}
		return list;
	}

	public static UPnPAction fromNodeList(NodeList list) {
		UPnPAction action = new UPnPAction();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getNodeName();
			if (name.equals("name")) {
				action.setName(node.getFirstChild().getNodeValue());
			} else if (name.equals("argumentList")) {
				NodeList argumentNodeList = node.getChildNodes();
				for (int j = 0; j < argumentNodeList.getLength(); j++) {
					Node argumentNode = argumentNodeList.item(j);
					if (argumentNode.getNodeName().equals("argument")) {
						UPnPActionArgument argument = UPnPActionArgument.fromNode(argumentNode);
						action.setArgument(argument);
					}
				}
			}
		}
		return action;
	}

	public String getArgumentListXml() {
		StringBuffer sb = new StringBuffer();
		Iterator<String> keys = arguments.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			sb.append(arguments.get(key).toXml());
		}
		return sb.toString();
	}

	public String toXml() {
		XmlTag action = new XmlTag("action");
		XmlTag argumentList = new XmlTag("argumentList");

		return action.wrap(XmlTag.wrap("name", name) +
						   argumentList.wrap(getArgumentListXml()));
	}
}
