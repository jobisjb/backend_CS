package com.wywm.capstone_backend.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class TroopersList {
    private List<Trooper> troopers = new LinkedList<>();

    public TroopersList() {
    }
    Path filePath = Paths.get("data.xml");
    //This method reads data from an XML file and then returns a linkedList list "List<Player>".
    public List<Trooper> getAllTroopers() throws ParserConfigurationException, IOException, SAXException {
        if (getXMLFile().exists()) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(String.valueOf(filePath.toAbsolutePath()));

            //Gets the elements "Name" and "Numbers" from the data.xml file and add them to a node list
            NodeList[] nodeList = {document.getElementsByTagName("name"), document.getElementsByTagName("number"), document.getElementsByTagName("percentage"), };

            for (int i = 0; i < nodeList[0].getLength(); i++) {
                //Iterate through the node list and collect the string values of both "name", "number" and "progress" then pass the as parameters to new Player
                String name = nodeList[0].item(i).getTextContent();
                int number = Integer.parseInt(nodeList[1].item(i).getTextContent());
                int percentage = Integer.parseInt(nodeList[2].item(i).getTextContent());
                //Then add the new nodeList to the troopers List
                this.troopers.add(new Trooper(name,number,percentage));
            }
        }else {
            System.out.println("File not found.");
        }

        //We then return the final collection of troopers
        return this.troopers;
    }

    private File getXMLFile(){
        File file = null;
        try{
            file = new File(String.valueOf(filePath.toAbsolutePath()));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return file;
    }
}
