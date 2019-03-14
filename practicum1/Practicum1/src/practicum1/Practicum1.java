/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicum1;

import Objects.ProcessSRT;
import Objects.Process;
import Strategies.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class Practicum1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            FirstComeFirstServe fcfs = new FirstComeFirstServe();
            ShortestJobFirst sjf = new ShortestJobFirst();
            RoundRobinQ8 rr8 = new RoundRobinQ8();
            RoundRobinQ2 rr2 = new RoundRobinQ2();
            ShortestRemaingTime srt = new ShortestRemaingTime();
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                
                File fXmlFile = new File(selectedFile.getAbsolutePath());
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                doc.getDocumentElement().normalize();

                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                NodeList nList = doc.getElementsByTagName("process");

                System.out.println("----------------------------");
                
                List<Process> processes1 = new ArrayList<>();
                List<Process> processes2 = new ArrayList<>();
                List<Process> processes3 = new ArrayList<>();
                List<Process> processes4 = new ArrayList<>();
                List<ProcessSRT> processes5 = new ArrayList<>();
                
                for (int temp = 0; temp < nList.getLength(); temp++) {

                        Node nNode = nList.item(temp);

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                                Element eElement = (Element) nNode;

                                int id = Integer.parseInt(eElement.getElementsByTagName("pid").item(0).getTextContent());
                                int arrivaltime = Integer.parseInt(eElement.getElementsByTagName("arrivaltime").item(0).getTextContent());
                                int servicetime = Integer.parseInt(eElement.getElementsByTagName("servicetime").item(0).getTextContent()); 
                                processes1.add(new Process(id, arrivaltime, servicetime, 0, 0, 0, 0, 0, servicetime));
                                processes2.add(new Process(id, arrivaltime, servicetime, 0, 0, 0, 0, 0, servicetime));
                                processes3.add(new Process(id, arrivaltime, servicetime, 0, 0, 0, 0, 0, servicetime));
                                processes4.add(new Process(id, arrivaltime, servicetime, 0, 0, 0, 0, 0, servicetime));
                                processes5.add(new ProcessSRT(id, arrivaltime, servicetime, 0, 0, 0, 0, 0, servicetime));
                        }
                      
                }
                System.out.println("finished reading data");
                 // evt create threads here
                fcfs.startFirstComeFirstServe(processes1);
                sjf.startShortestJob(processes2);
                rr8.startRoundRobin(processes3);
                rr2.startRoundRobin(processes4);
                srt.startShortestRemaining(processes5);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
