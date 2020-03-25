package com.javarush.task.task33.task3309;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.regex.Pattern;


/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) throws Exception {

        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        // сериализовали объект в document
        marshaller.marshal(obj, document);
        // получаем список всех элементов из документа
        NodeList nodes = document.getElementsByTagName("*");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeName().equals(tagName)) {
                Comment com = document.createComment(comment);
                // добавление комментария перед требуемым элементом
                node.getParentNode().insertBefore(com, node);
            }
            replaceTextWithCDATA(node, document);
        }
        // Чтобы преобразовать DOM-дерево в XML документ, необходимо создать Transformer object, который будет выполнять преобразование
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();

        // сохраняем табуляцию
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        // задаем отступы величиной 4
        //transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

        // Используя корневой узел DOM-дерева, следующая строка создает DOMSource объект как источник преобразования.
        DOMSource source = new DOMSource(document);
        // создаем объект StreamResult для получения результатов преобразования и трансформации дерева в XML
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        transformer.transform(source, result);
        // для вывода на экран исходного XML документа
        // toString(document);
        return sw.toString();
    }

    private static void replaceTextWithCDATA(Node node, Document document) {
        //nodeType <-- https://www.w3schools.com/xml/dom_nodetype.asp (3 = текстовый элемент)
        if ((node.getNodeType() == 3) && (Pattern.compile("[<>&'\"]").matcher(node.getTextContent()).find())) {

            Node cdataSection = document.createCDATASection(node.getNodeValue());
            node.getParentNode().replaceChild(cdataSection, node);
        }

        NodeList list = node.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            replaceTextWithCDATA(list.item(i), document);
        }
    }

    public static void main(String[] args) throws Exception {
        First first = new First();
        System.out.println(toXmlWithComment(first, "second", "it's a comment"));
    }

    /*public static void toString(Document newDoc) throws Exception{
        //трансформирует XML документ и выводит его в консоль
        TransformerFactory trFactory = TransformerFactory.newInstance();
        Transformer tr = trFactory.newTransformer();
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.STANDALONE, "no");
        Source src = new DOMSource(newDoc);
        Result dest = new StreamResult(System.out);
        tr.transform(src, dest);
    }*/
}
