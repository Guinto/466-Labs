import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
	
public class DecisionTreeNode {
	public ArrayList<Node> children;
   
	public DecisionTreeNode() {
	   children = new ArrayList<Node>();
	}
	
	public ArrayList<Node> getChildren() {
		return children;
	}
	
	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}
	
	public void remove(Node child) {
	   children.remove(child);
	}
	
	public void outputTree(String fileName, XML xml) {
		try {
			FileWriter file = new FileWriter(fileName);
			file.write(toXMLString(xml));
			file.close();
		} catch (IOException e) {
			System.err.println("Error writing file: " + fileName);
			e.printStackTrace();
		}
		
	}
	
	public void print(XML xml) {
		System.out.println(toXMLString(xml));
	}
	/*
	public void print() {
		if(children.size() == 0) {
		System.out.println("decision " + value);
		}
		else {
			System.out.println("node " + value);
		
			for (int i = 0; i < children.size(); i++) {
				System.out.println("edge " + i);
				children.get(i).print();
			}
		}
	}*/
	
	public String toXMLString(XML xml) {
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element root = doc.createElement("Tree");
            doc.appendChild(root);
           
    //        print(xml, root, doc);

            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();

            //return xml
            return xmlString;

		} catch (Exception e) {
       		e.printStackTrace();
        }
		
		return "";
	}
/*
	private void print(XML xml, Element node, Document doc) {
        Element nodeOrDecision;
		if (isLeaf()) {
			nodeOrDecision = doc.createElement("decision");
			nodeOrDecision.setAttribute("end", Integer.toString(value));
		} else {
			nodeOrDecision = doc.createElement("node");
			nodeOrDecision.setAttribute("var", getNodeName(value, xml));
		}
		node.appendChild(nodeOrDecision);
		for (int index = 0; index < children.size(); index++) {
	        Element edge = doc.createElement("edge");
	        edge.setAttribute("var", getEdgeName(value, index, xml));
	        nodeOrDecision.appendChild(edge);
	        
		//	children.get(index).print(xml, edge, doc);
		}
		
	}
	*/
	private boolean isLeaf() {
		return children.size() == 0;
	}
	
	private String getNodeName(int index, XML xml) {
		if ((index - 1) == xml.getListOfVariables().size()) {
			return xml.getName(xml.getCategory());
		}
		return xml.getName(xml.getListOfVariables().get(index - 1));
	}
	
	private String getEdgeName(int parentValue, int index, XML xml) {
		if ((parentValue - 1) == xml.getListOfVariables().size()) {
			return xml.getName(xml.getChildElementsFromType(xml.getCategory(), "choice").get(index));
		}
		return xml.getName(xml.getChildElementsFromType(xml.getListOfVariables().get(parentValue - 1), "group").get(index));
	}
}
