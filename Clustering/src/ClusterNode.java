import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ClusterNode {
   public double value;
   public Vector max;
   public Vector min;
   public Vector points;
   public ClusterNode left;
   public ClusterNode right;
   
   public String toString() {
	   String str = "";
	   
	   str += "[value: " + value + ", ";
	   str += "max: " + max + ", ";
	   str += "min: " + min + ", ";
	   str += "points: " + points + "\n\t";
	   str += "left: " + left + "\n\t";
	   str += "right: " + right + "]";
	   return str;
   }

   public ClusterNode(double value, Vector min, Vector max, ClusterNode left, ClusterNode right) {
      this.value = value;
      this.left = left;
      this.right = right;
      this.min = min;
      this.max = max;
   }

   public ClusterNode(Vector points, Vector min, Vector max) {
      this.value = 0;
      this.points = points;
      this.min = min;
      this.max = max;
   }

   public ClusterNode merge(ClusterNode other, DecisionTreeNode tree) {
      ClusterNode answer = new ClusterNode(this.findDist(other), this.findMin(other), this.findMax(other), this, other);
      tree.remove(this);
      tree.remove(other);
      return answer;
   }

   public double findDist(ClusterNode other) {      
      double first = this.max.getEucledianDistance(other.min);
      double second = other.max.getEucledianDistance(this.min);
      double third = this.max.getEucledianDistance(this.min);
      double fourth = other.max.getEucledianDistance(other.min);

      return Math.max(first, Math.max(second, Math.max(third, fourth)));
   }
   
   public Vector findMin(ClusterNode other) {
      double first = this.max.getEucledianDistance(other.min);
      double fourth = other.max.getEucledianDistance(other.min);
      double answer = this.findDist(other);
      
      if(first == answer || fourth == answer)
         return other.min;
      else
         return this.min;
   }
   
   public Vector findMax(ClusterNode other) {
      double first = this.max.getEucledianDistance(other.min);
      double third = this.max.getEucledianDistance(this.min);
      double answer = this.findDist(other);
      
      if(first == answer || third == answer)
         return this.max;
      else
         return other.max;
   }
	
	public String toXMLString(double threshold) {
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
           DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
           Document doc = docBuilder.newDocument();

           Element root = doc.createElement("tree");
           doc.appendChild(root);
          
           print(this, root, doc, threshold);

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

	private void print(ClusterNode cNode, Element node, Document doc, double threshold) {
        Element nodeOrDecision;
		if (cNode.isLeaf()) {
			if (cNode.value < threshold || threshold == -1) {
				nodeOrDecision = doc.createElement("leaf");
				nodeOrDecision.setAttribute("height", Double.toString(cNode.value));
				nodeOrDecision.setAttribute("data", cNode.points.toString());
				node.appendChild(nodeOrDecision);
			}
		} else {
			if (cNode.value < threshold || threshold == -1) {
				nodeOrDecision = doc.createElement("node");
				nodeOrDecision.setAttribute("height", Double.toString(cNode.value));
				if (cNode.left != null) {
					print(cNode.left, nodeOrDecision, doc, threshold);
				}
				if (cNode.right != null) {
					print(cNode.right, nodeOrDecision, doc, threshold);
				}
				node.appendChild(nodeOrDecision);
			} else {
				if (cNode.left != null) {
					print(cNode.left, node, doc, threshold);
				}
				if (cNode.right != null) {
					print(cNode.right, node, doc, threshold);
				}
			}
		}
	}
	
	private boolean isLeaf() {
		return (right == null && left == null);
	}
}
