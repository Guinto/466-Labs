import java.io.File;
import java.io.IOException;

import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;

public class XML {

	 private DocumentBuilder builder;
	 
	 public static void main(String args[]) {
		 new XML();
	 }
	 
	 public XML() {
		 try {
			print("data/domain.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }

	 public void print(String fileName) throws ParserConfigurationException, SAXException, IOException {
         File fXmlFile = new File(fileName);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document document = dBuilder.parse(fXmlFile);
         document.getDocumentElement().normalize();
         
		 NodeList nodes_i = document.getDocumentElement().getChildNodes();

		 for (int i = 0; i < nodes_i.getLength(); i++) {
			 Node variableNode = nodes_i.item(i);
			 if (variableNode.getNodeType() == Node.ELEMENT_NODE && ((Element) variableNode).getTagName().equals("variable")) {
				 Element variable = (Element) variableNode;
				 System.out.println(variable.getAttribute("name"));
				 NodeList groups = variable.getChildNodes();
				 for (int j = 0; j < groups.getLength(); j++) {
					 Node groupNode = groups.item(j);
					 if (groupNode.getNodeType() == Node.ELEMENT_NODE && ((Element) groupNode).getTagName().equals("group")) {
						 Element group = (Element) groupNode;
						 System.out.println(group.getAttribute("name") + " : " + group.getAttribute("p"));
					 }
				 }
				 System.out.println();
			 }
		 }
		 return;
	 }
}