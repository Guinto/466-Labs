import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
public class XML 
{
   public static void main( String[] args )
   {
      try {

         File fXmlFile = new File("domain.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(fXmlFile);
         doc.getDocumentElement().normalize();

         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("Category");
         System.out.println(nList.getLength());
         System.out.println("-----------------------");

         for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               NodeList cList = nNode.getChildNodes();
               for(int j = 0; j < cList.getLength(); j++) {
                  System.out.println(cList.getLength());
                  System.out.println("stuff " + cList.item(j).getNodeValue());

           //    Element eElement = (Element) cNode;
             //  System.out.println(cNode.getTagName());
               
        //       System.out.println("First Name : " + cNode.getNodeValue());
            //   System.out.println("Last Name : " + getTagValue("group", eElement));
              // System.out.println("Nick Name : " + getTagValue("nickname", eElement));
               //System.out.println("Salary : " + getTagValue("salary", eElement));
               }
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   
   private static String getTagValue(String sTag, Element eElement) {
    NodeList nlList = eElement.getElementsByTagName(sTag);
  
         Node nValue = (Node) nlList.item(0);
  
    return nValue.getNodeValue();
   }
  
}
