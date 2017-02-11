package org.comtel2000.fritzhome;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtils {

  private static DocumentBuilderFactory builderFac = DocumentBuilderFactory.newInstance();

  public static Element getElement(Document doc, String tagName, int index) {
    // given an XML document and a tag
    // return an Element at a given index
    NodeList rows = doc.getDocumentElement().getElementsByTagName(tagName);
    return (Element) rows.item(index);
  }

  public static int getSize(Document doc, String tagName) {
    // given an XML document and a tag name
    // return the number of ocurances
    NodeList rows = doc.getDocumentElement().getElementsByTagName(tagName);
    return rows.getLength();
  }

  public static String getValue(Element e, String tagName) {
    try {
      // get node lists of a tag name from a Element
      NodeList elements = e.getElementsByTagName(tagName);

      if (elements.getLength() == 0) {
        return null;
      }
      Node node = elements.item(0);
      return node.getTextContent();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static boolean isChildNodeExist(Document doc, String parentTag, String childnode) {
    return getChildNode(doc, parentTag, childnode) != null;
  }

  public static Node getChildNode(Document doc, String parentTag, String childnode) {
    try {
      XPath xpath = XPathFactory.newInstance().newXPath();
      XPathExpression expr = xpath.compile("//" + parentTag + "/" + childnode);
      Object obj = expr.evaluate(doc, XPathConstants.NODE);
      return obj != null ? (Node) obj : null;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static NodeList getChildNodes(Document doc, String parentTag) {
    return getChildNodesByExpression(doc, "//" + parentTag + "/*");
  }

  public static NodeList getChildNodesByExpression(Document doc, String expression) {
    try {
      XPath xpath = XPathFactory.newInstance().newXPath();
      XPathExpression expr = xpath.compile(expression);

      Object result = expr.evaluate(doc, XPathConstants.NODESET);
      return (NodeList) result;

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static String docToString(Document doc) {
    return docToString(doc, false);
  }

  public static String docToString(Document doc, boolean formated) {

    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = null;
    try {
      transformer = tf.newTransformer();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
      return null;
    }

    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    if (formated) {
      // linefeed formatting
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    } else {
      // remove xml header
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    }
    StringWriter sw = new StringWriter();
    StreamResult sr = new StreamResult(sw);

    DOMSource domSource = new DOMSource(doc);
    try {
      transformer.transform(domSource, sr);
    } catch (TransformerException e) {
      e.printStackTrace();
      return null;
    }
    return sw.toString();
  }

  public static byte[] doc2bytes(Document doc) {
    return doc2bytes(doc, false);
  }

  public static byte[] doc2bytes(Document doc, boolean formated) {
    try {
      Source source = new DOMSource(doc);
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      Result result = new StreamResult(out);
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.METHOD, "xml");
      if (formated) {
        // linefeed formatting
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      } else {
        // remove xml header
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      }
      transformer.transform(source, result);
      return out.toByteArray();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    } catch (TransformerException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Document bytes2doc(byte[] xml) throws SAXException, ParserConfigurationException, IOException {
    DocumentBuilder builder = builderFac.newDocumentBuilder();
    return builder.parse(new ByteArrayInputStream(xml));
  }

  public static Document string2doc(String xml) throws SAXException, ParserConfigurationException, IOException {
    DocumentBuilder builder = builderFac.newDocumentBuilder();
    return builder.parse(new InputSource(new StringReader(xml)));
  }

  public static Document file2doc(File file) throws SAXException, ParserConfigurationException, IOException {
    DocumentBuilder dBuilder = builderFac.newDocumentBuilder();
    Document doc = dBuilder.parse(file);
    doc.getDocumentElement().normalize();
    return doc;
  }

}
