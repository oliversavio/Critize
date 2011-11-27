package com.critize.parser;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParseTest {

	public static void readConfig(ServletConfig config, HashMap actions, HashMap views) {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			Document myDocument;
			DocumentBuilder db = dbf.newDocumentBuilder();
			String xmlPath = config.getServletContext().getRealPath("WEB-INF/appConfig.xml");
			myDocument = db.parse(xmlPath);

			Element rootElement = myDocument.getDocumentElement();
			NodeList actionNode = rootElement.getElementsByTagName("action");
			NodeList viewNode = rootElement.getElementsByTagName("view");
			Element actionElement, viewElement = null;
			String name, viewName = null;
			String myClass, viewFile = null;
			Class myActionClass = null ;
			Constructor myActionConstructor = null ;
			//Initialize Actions Map
			if (actionNode != null && actionNode.getLength() > 0) {
				for (int i = 0; i < actionNode.getLength(); i++) {

					actionElement = (Element) actionNode.item(i);
					name = getTextValue(actionElement, "action-name");
					myClass = getTextValue(actionElement, "action-class");
					
					myActionClass = Class.forName(myClass);
					myActionConstructor = myActionClass.getConstructor(null);
					actions.put(name, myActionConstructor.newInstance(null));

				}
			}
			//Initialize Views Map
			if (viewNode != null && viewNode.getLength() > 0) {
				for (int i = 0; i < viewNode.getLength(); i++) {

					viewElement = (Element) viewNode.item(i);
					viewName = getTextValue(viewElement, "view-name");
					viewFile = getTextValue(viewElement, "view-file");
					
					
					views.put(viewName, viewFile);

				}
			}
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		Element el = null;
		if (nl != null && nl.getLength() > 0) {
			el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	public static void main(String[] args) {

		final HashMap actions = new HashMap<String, String>();
		final HashMap views = new HashMap<String, String>();

		DomParseTest dp = new DomParseTest();
		// dp.parseXmlFile();
		// dp.parseDocument(actions,views);
		System.out.println(actions);

	}

}
