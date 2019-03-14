/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import Objects.Process;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class ShortestJobFirst {
    int CurrentAddedProcess = 0;
    int CurrentRunProcess = 0;
    String name = "SJF";
    
    double avgTAT=0;
    double avgNTAT=0;
    double avgWT=0;
    
    public List<Process> startShortestJob(List<Process> processes){
        PriorityQueue<Process> ProcessQueue = new PriorityQueue<Process>();
        Queue<Process> ReadyQueue = new LinkedList<Process>();
        
        boolean processorInUse = false;
        //infinite loop where each cycle is one jiffy
        for (int i = 1 ;;i++) {
            // check if not all processes have been run
            if(CurrentAddedProcess != processes.size()) {   
                // add the process to the queue when the arrival time is reached
                if(i >= processes.get(CurrentAddedProcess).getArrivaltime()) {    
                    // if processor is not in use, the processes are added to the 
                    // ProcessQueue and the ReadyQueue is transferred to this queue also
                    if(!processorInUse) {
                        ProcessQueue.add (processes.get(CurrentAddedProcess));
                    }
                    else {
                        ReadyQueue.add(processes.get(CurrentAddedProcess));
                        CurrentAddedProcess++;
                    }
                }
            }
            if(!processorInUse) {
                if(ReadyQueue.peek() != null) {
                    for(int j = 0; j< ReadyQueue.size(); j++) {
                        ProcessQueue.add(ReadyQueue.poll());
                    }
                }
            }
            if(ProcessQueue.size() != 0){
                if(ProcessQueue.element().getRemainingtime() == 0) {
                    Process finishedProcess = ProcessQueue.element();
                    finishedProcess.setEndtime(i);

                    int turnaroundtime = finishedProcess.getEndtime()-finishedProcess.getArrivaltime();
                    finishedProcess.setTurnaroundtime(turnaroundtime);
                    avgTAT += turnaroundtime;

                    double normalizedTurnaroundtime = (double)Math.round((double)turnaroundtime / (double)finishedProcess.getServicetime()*100)/100;
                    finishedProcess.setNormalizedTurnaroundtime(normalizedTurnaroundtime);
                    avgNTAT += normalizedTurnaroundtime;  
                                                         
                    processes.set(finishedProcess.getPid()-1, finishedProcess);
                    ProcessQueue.remove();
                    processorInUse = false;
                    
                }
                else {
                    processorInUse = true;
                    ProcessQueue.element().setRemainingtime(ProcessQueue.element().getRemainingtime()-1);
                    // if the starttime is not added yet, it means its the first run
                    if(ProcessQueue.element().getStarttime()==0) {
                        ProcessQueue.element().setStarttime(i);
                        int waitingtime = ProcessQueue.element().getStarttime() - ProcessQueue.element().getArrivaltime();
                        ProcessQueue.element().setWaitingtime(waitingtime);
                        avgWT += waitingtime;
                    }
                }
            }
            
            // break the infinite loop if all the processes ran and the queue is empty again
            if(CurrentAddedProcess == processes.size() && ProcessQueue.size() == 0) {
                System.out.println("Shortest Job First: ");
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
