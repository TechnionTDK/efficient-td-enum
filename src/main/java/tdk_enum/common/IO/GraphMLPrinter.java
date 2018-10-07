package tdk_enum.common.IO;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

public class GraphMLPrinter {

    public static String treeDecompositionToGraphMLFormat(ITreeDecomposition treeDecomposition)
    {

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(treeDecompositionToGraphML(treeDecomposition), new StreamResult(writer));
            return writer.getBuffer().toString();


        }
         catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void treeDecompositionToGraphMLFile(ITreeDecomposition treeDecomposition, String fileName)
    {
        try
        {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            File file = new File(fileName);

            file.getParentFile().mkdirs();
            file.createNewFile();
            transformer.transform(treeDecompositionToGraphML(treeDecomposition), new StreamResult(file));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static DOMSource treeDecompositionToGraphML(ITreeDecomposition treeDecomposition)
    {
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("graphml");
            rootElement.setAttribute("xmlns","http://graphml.graphdrawing.org/xmlns" );
            rootElement.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance" );
            rootElement.setAttribute("xsi:schemaLocation", "http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd");
            doc.appendChild(rootElement);

            Element keyElement = doc.createElement("key");
            keyElement.setAttribute("id", "bag");
            keyElement.setAttribute("for", "node");

            rootElement.appendChild(keyElement);
            Element graphElement = doc.createElement("graph");
            graphElement.setAttribute("edgedefault", "undirected");
            postOrderTreeWalk(treeDecomposition, treeDecomposition.getBag(treeDecomposition.getRoot()),doc, graphElement);
//            for (DecompositionNode bag : treeDecomposition.getBags())
//            {
//                Element nodeElement = doc.createElement("node");
//                nodeElement.setAttribute("id", "n"+(bag.getBagId().intValue()+1));
//                Element dataElement = doc.createElement("data");
//                dataElement.setAttribute("key", "bag");
//                StringBuilder bagContentBuilder = new StringBuilder();
//                for (int i = 0; i < bag.size(); i++)
//                {
//                    bagContentBuilder.append(bag.get(i).intValue());
//                    if (i!=bag.size()-1)
//                    {
//                        bagContentBuilder.append(", ");
//                    }
//                }
//
//                dataElement.setTextContent(bagContentBuilder.toString());
//                nodeElement.appendChild(dataElement);
//                graphElement.appendChild(nodeElement);
//
//            }
//            for (DecompositionNode bag : treeDecomposition.getBags())
//            {
//                if (bag.getChildren().size() != 0)
//                {
//                    for (Node child : bag.getChildren())
//                    {
//                        Element edgeElement = doc.createElement("edge");
//                        edgeElement.setAttribute("source", "n"+(bag.getBagId().intValue()+1));
//                        edgeElement.setAttribute("target", "n"+(child.intValue()+1));
//                        graphElement.appendChild(edgeElement);
//                    }
//                }
//            }
            rootElement.appendChild(graphElement);


            return new DOMSource(doc);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return new DOMSource();

    }

    static void postOrderTreeWalk (ITreeDecomposition treeDecomposition, DecompositionNode bag, Document doc , Element graphElement)
    {
        Element nodeElement = doc.createElement("node");
        nodeElement.setAttribute("id", "n"+(bag.getBagId().intValue()+1));
        Element dataElement = doc.createElement("data");
        dataElement.setAttribute("key", "bag");
        StringBuilder bagContentBuilder = new StringBuilder();
        for (int i = 0; i < bag.size(); i++)
        {
            bagContentBuilder.append(bag.get(i).intValue());
            if (i!=bag.size()-1)
            {
                bagContentBuilder.append(", ");
            }
        }

        dataElement.setTextContent(bagContentBuilder.toString());
        nodeElement.appendChild(dataElement);
        graphElement.appendChild(nodeElement);
        if (bag.getChildren().size() != 0)
        {
            for (Node child : bag.getChildren())
            {
                postOrderTreeWalk(treeDecomposition, treeDecomposition.getBag(child), doc, graphElement);
                Element edgeElement = doc.createElement("edge");
                edgeElement.setAttribute("source", "n"+(bag.getBagId().intValue()+1));
                edgeElement.setAttribute("target", "n"+(child.intValue()+1));
                graphElement.appendChild(edgeElement);
            }
        }
    }


}
