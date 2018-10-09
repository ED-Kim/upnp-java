package com.tjapp.upnp;

import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class UPnPScpd {

	private int majorVersion;
	private int minorVersion;
	private Map<String, UPnPAction> actions = new LinkedHashMap<>();
	private Map<String, UPnPStateVariable> stateVariables = new LinkedHashMap<>();
	private static Logger logger = Logger.getLogger("UPnPScpd");
	static {
		logger.setWriter(Logger.NULL_WRITER);
	}
	
	public String getSpecVersion() {
		return String.format("%d.%d", majorVersion, minorVersion);
	}

	public void setSpecVersion(int majorVersion, int minorVersion) {
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
	}

	public void setSpecVersionMajor(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public void setSpecVersionMinor(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public UPnPAction getAction(String name) {
		return actions.get(name);
	}

	public void setAction(UPnPAction action) {
		actions.put(action.getName(), action);
	}

	public List<UPnPAction> getActions() {
	    return new ArrayList<UPnPAction>(actions.values());
	}

	public UPnPStateVariable getStateVariable(String name) {
		return stateVariables.get(name);
	}

	public void setStateVariable(UPnPStateVariable stateVariable) {
		stateVariables.put(stateVariable.getName(), stateVariable);
	}

	public Map<String, UPnPStateVariable> getStateVariables() {
		return stateVariables;
	}

	public static UPnPScpd fromXml(String xml) throws Exception {
		UPnPScpd scpd = new UPnPScpd();
		Document doc = XmlParser.parse(xml);
		Element root = doc.getDocumentElement();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getNodeName();
			if (name.equals("#text")) {
				continue;
			}
			logger.debug(name);
			if (name.equals("specVersion")) {
				NodeList versionList = node.getChildNodes();
				for (int j = 0; j < versionList.getLength(); j++) {
					Node versionNode = versionList.item(j);
					if (versionNode.getNodeName().equals("major")) {
						int major = Integer.parseInt(versionNode.getFirstChild().getNodeValue());
						scpd.setSpecVersionMajor(major);
					} else if (versionNode.getNodeName().equals("minor")) {
						int minor = Integer.parseInt(versionNode.getFirstChild().getNodeValue());
						scpd.setSpecVersionMinor(minor);
					}
				}
			} else if (name.equals("actionList")) {
				NodeList actionNodeList = node.getChildNodes();
				for (int j = 0; j < actionNodeList.getLength(); j++) {
					Node actionNode = actionNodeList.item(j);
					if (actionNode.getNodeName().equals("action")) {
						UPnPAction action = UPnPAction.fromNodeList(actionNode.getChildNodes());
						scpd.setAction(action);
					}
				}
			} else if (name.equals("serviceStateTable")) {
				NodeList stateVariableNodeList = node.getChildNodes();
				for (int j = 0; j < stateVariableNodeList.getLength(); j++) {
					Node stateVariableNode = stateVariableNodeList.item(j);
					if (stateVariableNode.getNodeName().equals("stateVariable")) {
						UPnPStateVariable stateVariable = UPnPStateVariable.fromNode(stateVariableNode);
						scpd.setStateVariable(stateVariable);
					}
				}
			} else {
				logger.debug(" - {skip}");
			}
		}
		return scpd;
	}

	public String getActionListXml() {
		StringBuffer sb = new StringBuffer();
		Iterator<String> keys = actions.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			sb.append(actions.get(key).toXml());
		}
		return sb.toString();
	}

	public String getServiceStateTableXml() {
		StringBuffer sb = new StringBuffer();
		Iterator<String> keys = stateVariables.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			sb.append(stateVariables.get(key).toXml());
		}
		return sb.toString();
	}

	public String toXml() {

		XmlTag scpd = new XmlTag("scpd");
		XmlTag specVersion = new XmlTag("specVersion");
		XmlTag major = new XmlTag("major");
		XmlTag minor = new XmlTag("minor");
		XmlTag actionList = new XmlTag("actionList");
		XmlTag serviceStateTable = new XmlTag("serviceStateTable");
		
		
		return XmlTag.docType(
			scpd.wrap(
				specVersion.wrap(XmlTag.wrap("major", "" + majorVersion) +
								 XmlTag.wrap("minor", "" + minorVersion)) +
				actionList.wrap(getActionListXml()) +
				serviceStateTable.wrap(getServiceStateTableXml())));
	}
}
