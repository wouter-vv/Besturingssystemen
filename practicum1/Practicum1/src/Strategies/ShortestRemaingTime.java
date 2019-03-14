/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import Objects.ProcessSRT;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class ShortestRemaingTime {
    
    int CurrentAddedProcess = 0;
    String name = "SRT";
    
    public List<ProcessSRT> startShortestRemaining(List<ProcessSRT> processes) {
        PriorityQueue<Objects.ProcessSRT> ProcessQueue = new PriorityQueue<Objects.ProcessSRT>();
        boolean processorInUse = false;
        double avgTAT=0;
        double avgNTAT=0;
        double avgWT=0;
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
            
            
            if(ProcessQueue.peek() != null) {
                if(ProcessQueue.peek().getRemainingtime() == 0 ) {
                    ProcessSRT finishedProcess = ProcessQueue.element();
                    finishedProcess.setEndtime(i);
                    finishedProcess.setWaitingtime(finishedProcess.getEndtime()-finishedProcess.getArrivaltime()-finishedProcess.getServicetime());
                    avgWT += finishedProcess.getWaitingtime();
                    int turnaroundtime = finishedProcess.getEndtime()-finishedProcess.getArrivaltime();
                    finishedProcess.setTurnaroundtime(turnaroundtime);
                    avgTAT += turnaroundtime;
                    double normalizedTurnaroundtime = (double)Math.round((double)turnaroundtime / (double)finishedProcess.getServicetime()*100)/100;
                    finishedProcess.setNormalizedTurnaroundtime(normalizedTurnaroundtime);
                    avgNTAT += normalizedTurnaroundtime;
                    processes.set(finishedProcess.getPid()-1, finishedProcess);
                    ProcessQueue.remove();
                    i+=10;
                }
                else {
                    ProcessQueue.element().setRemainingtime(ProcessQueue.element().getRemainingtime()-1);
                    // if the starttime is not added yet, it means its the first run
                    if(ProcessQueue.element().getStarttime()==0) {

                        ProcessQueue.element().setStarttime(i);

                        
                    }
                }
            }
            
            // break the infinite loop if all the processes ran and the queue is empty again
            if(CurrentAddedProcess == processes.size() && ProcessQueue.size() == 0) {
                System.out.println("Shortest Remaining time: ");
                System.out.println("average TAT= " + (avgTAT /= processes.size()));
                System.out.println("average NTAT= " + (avgNTAT /= processes.size()));
                System.out.println("average WT= " + (avgWT /= processes.size()));
                System.out.println("\n");
                try {
                FileWriter writer = new FileWriter(name + processes.size()+".txt"); 
                writer.write("PID, Arrivaltime, servicetime, starttime, endtime, turnaroundtime, normalisedTurnaroundtime, waitingtime;");
                writer.write("\n");

                for(ProcessSRT str: processes) {
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
