/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Objects.Process;

/**
 * This class runs the First Come First Serve technique
 * also known as FIFO
 * 
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class FirstComeFirstServe {
    int CurrentAddedProcess = 0;
    int CurrentRunProcess = 0;
    String name = "FCFS";
    
    double avgTAT=0;
    double avgNTAT=0;
    double avgWT=0;
        
    public List<Process> startFirstComeFirstServe(List<Process> processes) {
        Queue<Process> ProcessQueue = new LinkedList<Process>();
        
        boolean processorInUse = false;
        
        //infinite loop where each cycle is one jiffy
        for (int i = 0 ;; i++) {
            //stop when all the processes are run
            if(CurrentAddedProcess != processes.size()) {         
                // add the process to the queue when the arrival time is reached
                if(i >= processes.get(CurrentAddedProcess).getArrivaltime()) {      
                    ProcessQueue.add (processes.get(CurrentAddedProcess));
                    CurrentAddedProcess ++;
                }
            }
            
            // check if another process isn't being run, if not, run the next one
            if(!processorInUse && ProcessQueue.size() != 0) {                       
                processes.get(CurrentRunProcess).setStarttime(i); 
                processorInUse = true;
                
                int endtime = processes.get(CurrentRunProcess).getStarttime()+ processes.get(CurrentRunProcess).getServicetime();
                processes.get(CurrentRunProcess).setEndtime(endtime);
                
                int turnaroundtime = processes.get(CurrentRunProcess).getEndtime() - processes.get(CurrentRunProcess).getArrivaltime();
                processes.get(CurrentRunProcess).setTurnaroundtime(turnaroundtime);
                avgTAT += turnaroundtime;
                
                double normalizedTurnaroundtime = (double)Math.round((double)turnaroundtime / (double)processes.get(CurrentRunProcess).getServicetime()*100)/100;
                processes.get(CurrentRunProcess).setNormalizedTurnaroundtime(normalizedTurnaroundtime);
                avgNTAT += normalizedTurnaroundtime;
                
                int waitingtime = processes.get(CurrentRunProcess).getStarttime() - processes.get(CurrentRunProcess).getArrivaltime();
                processes.get(CurrentRunProcess).setWaitingtime(waitingtime);
                avgWT += waitingtime;
                
                CurrentRunProcess++;
            }
            
            if( ProcessQueue.size() != 0 ){
                // remove the process if the process is finished
                if(i >= ProcessQueue.element().getEndtime()&& ProcessQueue.element().getEndtime() != 0) {
                    
                    ProcessQueue.remove();                                          
                    processorInUse = false;
                }
            }
            
            // break the infinite loop if all the processes ran and the queue is empty again
            if(CurrentRunProcess == processes.size() && ProcessQueue.size() == 0) {
                System.out.println("First Come First Serve: ");
                System.out.println("average TAT= " + (avgTAT /= processes.size()));
                System.out.println("average NTAT= " + (avgNTAT /= processes.size()));
                System.out.println("average WT= " + (avgWT /= processes.size()));
                System.out.println("\n");
                try {
                FileWriter writer = new FileWriter(name + processes.size()+".txt"); 
                writer.write("PID, Arrivaltime, servicetime, starttime, endtime, turnaroundtime, normalisedTurnaroundtime, waitingtime;");
                writer.write("\n");
                for(Process str: processes) {
                  writer.write(str.toString());
                  writer.write("\n");
                }
                writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return processes;
    }
}
