/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import java.io.FileWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class HighestResponseRatioNext {
    int CurrentAddedProcess = 0;
    int CurrentRunProcess = 0;
    String name = "SJF";
    
    double avgTAT=0;
    double avgNTAT=0;
    double avgWT=0;
    
    int test;
    
    public List<Objects.ProcessHRRN> startHighestResponseRatioNext(List<Objects.ProcessHRRN> processes){
        PriorityQueue<Objects.ProcessHRRN> ProcessQueue = new PriorityQueue<Objects.ProcessHRRN>();
        Queue<Objects.ProcessHRRN> ReadyQueue = new LinkedList<Objects.ProcessHRRN>();
        
        boolean processorInUse = false;
        //infinite loop where each cycle is one jiffy
        for (int i = 1 ;;i++) {
            // check if not all processes have been run
            if(CurrentAddedProcess != processes.size()) {   
                // add a new arrived process to the queue when the arrival time is reached
                if(i >= processes.get(CurrentAddedProcess).getArrivaltime() && !processes.get(CurrentAddedProcess).isRead()) {   
                    processes.get(CurrentAddedProcess).setRead(true);
                    // if processor is not in use, the processes are added to the 
                    // ProcessQueue and the ReadyQueue is transferred to this queue also
                    if(!processorInUse) {
                        ProcessQueue.add (processes.get(CurrentAddedProcess));
                        CurrentAddedProcess ++;
                    }
                    else {
                        if(processes.get(CurrentAddedProcess).getServicetime() > 500) {
                            test = 0;
                        }
                        ReadyQueue.add(processes.get(CurrentAddedProcess));
                        CurrentAddedProcess ++;
                        
                    }
                }
            }
            int lll;
            if(ReadyQueue.size() >=10 ){
                lll = 1;
            }
            
            // readyqueue to list to update waiting times
            List<Objects.ProcessHRRN> lijst = (List<Objects.ProcessHRRN>)ReadyQueue;  
            for (Iterator<Objects.ProcessHRRN> x = lijst.iterator(); x.hasNext();) {
                Objects.ProcessHRRN item = x.next();
                item.setWaitingtime(item.getWaitingtime()+1);
            }
            

            // readylist to back to queue
            Collections.sort(lijst);
            ReadyQueue = (Queue)lijst;
            if(ReadyQueue.size() >=10 ){
                lll = 1;
            }
            // add a new process if there is none being run
            if(!processorInUse) {
                if(ReadyQueue.peek() != null) {
                    ProcessQueue.add(ReadyQueue.poll());
                }
            }
            // there is a process being ran
            if(ProcessQueue.size() != 0){
                // process is finished
                if(ProcessQueue.element().getRemainingtime() == 0) {
                    Objects.ProcessHRRN finishedProcess = ProcessQueue.element();
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
                // process still in action
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
                System.out.println("Highest response ratio next: ");
                System.out.println("average TAT= " + (avgTAT /= processes.size()));
                System.out.println("average NTAT= " + (avgNTAT /= processes.size()));
                System.out.println("average WT= " + (avgWT /= processes.size()));
                System.out.println("\n");
                try {
                FileWriter writer = new FileWriter(name + processes.size()+".txt"); 
                writer.write("PID, Arrivaltime, servicetime, starttime, endtime, turnaroundtime, normalisedTurnaroundtime, waitingtime;");
                writer.write("\n");
                for(Objects.ProcessHRRN str: processes) {
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
