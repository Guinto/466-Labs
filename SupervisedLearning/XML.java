import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.w3c.dom.*;
import org.xml.sax.*;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import javax.xml.parsers.*;

public class XML {
	 
	 private ArrayList<Element> variableList;
	 private Element category;
	 
	 private DecisionTreeNode decisionTree;
	 
	 public static void main(String args[]) {
		new XML("tree.xml", new CSV("data/tree03-20-numbers.csv"));
	 }
	 
	 public XML(String fileName) {
		 variableList = new ArrayList<Element>();
		 try {
			 parseDomain(fileName);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } 
	 }
	 
	 public XML(String fileName, CSV csv) {
		 try {
			 this.setDecisionTree(parseTree(fileName, csv));
		 } catch (Exception e) {
			 e.printStackTrace();
		 } 
	 }

	public XML() {
		 variableList = new ArrayList<Element>();
		 try {
			 //parseDomain("data/domain.xml");
			 //parseTree("data/treeHouse01.xml");
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 //printDomain();
	 }
	 
	 public ArrayList<Element> getListOfVariables() {
		 return variableList;
	 }
	 
	 public int getEnd(Element e) {
		 return Integer.parseInt(e.getAttribute("end"));
	 }
	 
	 public String getVar(Element e) {
		 return e.getAttribute("var");
	 }
	 
	 public String getName(Element e) {
		 return e.getAttribute("name");
	 }
	 
	 public String getProbability(Element e) {
		 return e.getAttribute("p");
	 }
	 
	 public String getType(Element e) {
		 System.out.println(e.getAttributes().item(0));
		 return e.getAttribute("type");
	 }
	 
	 public String getAttributes(Element e) {
		 String attributes = "";
		 for (int i = 0; i < e.getAttributes().getLength(); i++) {
			 attributes += e.getAttributes().item(i) + " ";
		 }
		 return attributes.substring(0, attributes.length() - 1);
	 }
	 
	 public void printDomain() {
		 for (Element variable : getListOfVariables()) {
			 System.out.println(getName(variable));
			 for (Element group : getChildElementsFromType(variable, "group")) {
				 System.out.println(getAttributes(group));
			 }
			 System.out.println();
		 }
		 System.out.println(getName(getCategory()));
		 for (Element choice : getChildElementsFromType(getCategory(), "choice")) {
			 System.out.println(getAttributes(choice));
		 }
	 }
	 
	 public DecisionTreeNode parseTree(String fileName, CSV csv) throws ParserConfigurationException, SAXException, IOException {
         File fXmlFile = new File(fileName);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document document = dBuilder.parse(fXmlFile);
         document.getDocumentElement().normalize();
         
         DecisionTreeNode tree = null;
         
		 NodeList nodes = document.getDocumentElement().getChildNodes();

		 for (int i = 0; i < nodes.getLength(); i++) {
			 Node nodeNode = nodes.item(i);
			 if (nodeNode.getNodeType() == Node.ELEMENT_NODE) {
				 Element node = (Element) nodeNode;
				 tree = new DecisionTreeNode(csv.data.get(0).indexOf(getVar(node)));
				 tree.setChildren(fillTree(node, csv));
			 }
		 }
		 
		 return tree;
	 }
	 
	 private ArrayList<DecisionTreeNode> fillTree(Element node, CSV csv) {
		 ArrayList<Element> edges = getChildElementsFromType(node, "edge");
		 ArrayList<DecisionTreeNode> nodes = new ArrayList<DecisionTreeNode>();
		 
		 for (int i = 0; i < edges.size(); i++) {
			 ArrayList<Element> nodesOrDecision;
			 
			 if (getChildElementsFromType(edges.get(i), "decision").size() != 0) {
				 nodesOrDecision = getChildElementsFromType(edges.get(i), "decision");
				 
				 DecisionTreeNode decision = new DecisionTreeNode(getEnd(nodesOrDecision.get(0)));
				 nodes.add(decision);
				 
			 } else {
				 nodesOrDecision = getChildElementsFromType(edges.get(i), "node");
				 
				 for (Element e : nodesOrDecision) {
					 DecisionTreeNode xmlNode = new DecisionTreeNode(csv.data.get(0).indexOf(getVar(e)));
					 xmlNode.setChildren(fillTree(e, csv));
					 nodes.add(xmlNode);
				 }
			 }
		 }
		 return nodes;
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
				setCategory((Element) attributeNode);
			 }
		 }
		 return;
	 }
	 
	 public ArrayList<Element> getChildElementsFromType(Element e, String type) {
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

	public Element getCategory() {
		return category;
	}

	public void setCategory(Element category) {
		this.category = category;
	}

	public DecisionTreeNode getDecisionTree() {
		return decisionTree;
	}
	 
	private void setDecisionTree(DecisionTreeNode parseTree) {
		this.decisionTree = parseTree;
	}

}