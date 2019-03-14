/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import Objects.Process;

/**
 * Round robin technique
 * an infinite loop in which a loop of "quantum-amount" is placed
 * each program is added to the queue and gets "quantum-amount" of time to process, 
 * the process gets moved to the back after this time
 * 
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class RoundRobinQ8 {
    int CurrentAddedProcess = 0;
    int CurrentRunProcess = 0;
    String name = "RR8";
    
    int quantum = 8;
        
    double avgTAT=0;
    double avgNTAT=0;
    double avgWT=0;
    
    public List<Process> startRoundRobin(List<Process> processes) {
        Queue<Process> ProcessQueue = new LinkedList<Process>();
        
        int pid =0;
        //infinite loop where each cycle is one jiffy
        for (int i = 1 ;;) {
            // check if not all processes have been run
            if(CurrentAddedProcess != processes.size()) {         
                // add the process to the queue when the arrival time is reached
                if(i >= processes.get(CurrentAddedProcess).getArrivaltime()) {      
                    ProcessQueue.add (processes.get(CurrentAddedProcess));
                    CurrentAddedProcess ++;
                    
                }
            }
            if(ProcessQueue.size() != 0){
                // prevent that the same process is run again if new processes are added
                if(ProcessQueue.peek().getPid() == pid) {
                    Process backToEnd = ProcessQueue.element();
                    backToEnd.setRemainingtime(backToEnd.getRemainingtime()-1);
                    // added to the back and deleted from the front
                    ProcessQueue.add(backToEnd);
                    ProcessQueue.remove();
                }
                for (int j = 0; j < quantum; j++) {
                    // process is finished
                    if(ProcessQueue.element().getRemainingtime() <= 0 ) {
                        Process finishedProcess = ProcessQueue.element();
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
                        i+=quantum;
                        break;
                    }
                    // turn of process is over
                    else if ( j == quantum-1 ){
                        Process backToEnd = ProcessQueue.element();
                        backToEnd.setRemainingtime(backToEnd.getRemainingtime()-1);
                        // added to the back and deleted from the front
                        ProcessQueue.add(backToEnd);
                        ProcessQueue.remove();
                        i+=quantum;
                        pid = backToEnd.getPid();

                    }
                    // nothing special, just execute the program
                    else {
                        ProcessQueue.element().setRemainingtime(ProcessQueue.element().getRemainingtime()-1);
                        // if the starttime is not added yet, it means its the first run
                        if(ProcessQueue.element().getStarttime()==0) {                            
                            ProcessQueue.element().setStarttime(i);
                        }
                    }

                }
            }
            else {
                i++;
            }
            
            // break the infinite loop if all the processes ran and the queue is empty again
            if(CurrentAddedProcess == processes.size() && ProcessQueue.size() == 0) {
                System.out.println("Round Robin");
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
