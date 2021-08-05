package pers.phy.core;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * FreeMind File Parser
 * @author PHY
 * @date 2021/8/5
 */
public class FreeMindParser {

    public static void parse(String mmFile, String tarDir) throws ParserConfigurationException, IOException, SAXException {
        if (tarDir == null || "".equals(tarDir.trim())) {
            tarDir = System.getProperty("user.dir");
        }
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dom = builder.parse(mmFile);
        NodeList list = dom.getElementsByTagName("map");
        Node node = list.item(0);
        createDir(node.getChildNodes(), tarDir);
    }

    public static void createDir(NodeList list, String dir) {
        for (int i = 0; i < list.getLength(); i++) {
            Node tmp = list.item(i);
            Node text = tmp.getAttributes().getNamedItem("TEXT");
            String current = dir + "/" + text.getNodeValue();
            if (tmp.hasChildNodes()) {
                createDir(tmp.getChildNodes(), current);
            } else {
                new File(current).mkdirs();
            }
        }
    }
}