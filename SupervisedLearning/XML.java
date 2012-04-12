import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;

public class XML {

	 private DocumentBuilder builder;
	 
	 private ArrayList<Element> variableList;
	 private Element category;
	 
	 public static void main(String args[]) {
		 new XML();
	 }
	 
	 public XML() {
		 variableList = new ArrayList<Element>();
		 try {
			parseDomain("data/domain.xml");
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 printDomain();
	 }
	 
	 public ArrayList<Element> getListOfVariables() {
		 return variableList;
	 }
	 
	 public String getName(Element e) {
		 return e.getAttribute("name");
	 }
	 
	 public String getProbability(Element e) {
		 return e.getAttribute("p");
	 }
	 
	 public String getType(Element e) {
		 return e.getAttribute("type");
	 }
	 
	 public void printDomain() {
		 for (Element variable : getListOfVariables()) {
			 System.out.println(getName(variable));
			 for (Element group : getChildElementsFromType(variable, "group")) {
				 System.out.println(getName(group) + " : " + getProbability(group));
			 }
			 System.out.println();
		 }
		 System.out.println(getName(category));
		 for (Element choice : getChildElementsFromType(category, "choice")) {
			 System.out.println(getName(choice) + " : " + getType(choice));
		 }
	 }

	 public void parseDomain(String fileName) throws ParserConfigurationException, SAXException, IOException {
         File fXmlFile = new File(fileName);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document document = dBuilder.parse(fXmlFile);
         document.getDocumentElement().normalize();
         
		 NodeList attributes = document.getDocumentElement().getChildNodes();

		 for (int i = 0; i < attributes.getLength(); i++) {
			 Node attributeNode = attributes.item(i);
			 if (attributeNode.getNodeType() == Node.ELEMENT_NODE && ((Element) attributeNode).getTagName().equals("variable")) {
				 Element variable = (Element) attributeNode;
				 variableList.add(variable);
			 } else if (attributeNode.getNodeType() == Node.ELEMENT_NODE && ((Element) attributeNode).getTagName().equals("Category")) {
				category = (Element) attributeNode;
			 }
		 }
		 return;
	 }
	 
	 private ArrayList<Element> getChildElementsFromType(Element e, String type) {
		 ArrayList<Element> elementList = new ArrayList<Element>();
		 
		 NodeList childElements = e.getChildNodes();
		 for (int j = 0; j < childElements.getLength(); j++) {
			 Node childNode = childElements.item(j);
			 if (childNode.getNodeType() == Node.ELEMENT_NODE && ((Element) childNode).getTagName().equals(type)) {
				 Element child = (Element) childNode;
				 elementList.add(child);
			 }
		 }
		 
		 return elementList;
	 }
}