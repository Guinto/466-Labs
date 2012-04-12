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
	public int value;
	public ArrayList<DecisionTreeNode> children;
   
	public DecisionTreeNode(int value) {
	   this.value = value;
	   children = new ArrayList<DecisionTreeNode>();
	}
	
	public ArrayList<DecisionTreeNode> getChildren() {
		return children;
	}
	
	public void setChildren(ArrayList<DecisionTreeNode> children) {
		this.children = children;
	}
	
	public void print() {
		System.out.println(value);
		for (int i = 0; i < children.size(); i++) {
			System.out.println(i);
			children.get(i).print();
		}
	}
	
	public void printXML(XML xml) {
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element root = doc.createElement("Tree");
            doc.appendChild(root);
           
            print(xml, root, doc);

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

            //print xml
            System.out.println(xmlString);

		} catch (Exception e) {
       		e.printStackTrace();
        }
	}

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
	        
			children.get(index).print(xml, edge, doc);
		}
	}
	
	private boolean isLeaf() {
		return children.size() == 0;
	}
	
	private String getNodeName(int index, XML xml) {
		if (index == xml.getListOfVariables().size()) {
			return xml.getName(xml.getCategory());
		}
		return xml.getName(xml.getListOfVariables().get(index));
	}
	
	private String getEdgeName(int parentValue, int index, XML xml) {
		if (parentValue == xml.getListOfVariables().size()) {
			return xml.getName(xml.getChildElementsFromType(xml.getCategory(), "choice").get(index));
		}
		return xml.getName(xml.getChildElementsFromType(xml.getListOfVariables().get(parentValue), "group").get(index));
	}
}
