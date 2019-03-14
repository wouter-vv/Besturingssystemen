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

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class MultilevelFeedback {
    
    String name = "MLFB";
    
    Queue<Objects.Process> Queue1 = new LinkedList<Objects.Process>();
    Queue<Objects.Process> Queue2 = new LinkedList<Objects.Process>();
    Queue<Objects.Process> Queue3 = new LinkedList<Objects.Process>();
    
    int quantum1 = 16;
    int quantum2 = 32;
    int quantum3 = 64;
    
            
    double avgTAT=0;
    double avgNTAT=0;
    double avgWT=0;
    
    int CurrentAddedProcess = 0;
    
    int test;
    
    public List<Objects.Process> startMultiLevelFeedback(List<Objects.Process> processes) {
        
        for (int i = 1 ;;) {
            // check if not all processes have been run
            if(CurrentAddedProcess != processes.size()) {         
                // add the process to the queue when the arrival time is reached
                if(i >= processes.get(CurrentAddedProcess).getArrivaltime()) {      
                    Queue1.add (processes.get(CurrentAddedProcess));
                    CurrentAddedProcess ++;
                    
                }
            }
            
            if(Queue1.size() != 0){
                for (int q1 = 0; q1 < quantum1; q1++) {
                    // process is finished
                    if(Queue1.element().getRemainingtime() <= 0 ) {
                        Objects.Process finishedProcess = Queue1.element();
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
                        Queue1.remove();
                        // +1 because starts at 0
                        i+=q1+1;
                        break;
                    }
                    // turn of process is over
                    else if ( q1 == quantum1-1 ){
                        Objects.Process moveToTwo = Queue1.element();
                        moveToTwo.setRemainingtime(moveToTwo.getRemainingtime()-1);
                        // added to the back and deleted from the front
                        Queue2.add(moveToTwo);
                        Queue1.remove();
                        i+=quantum1;

                    }
                    // nothing special, just execute the program
                    else {
                        Queue1.element().setRemainingtime(Queue1.element().getRemainingtime()-1);
                        // if the starttime is not added yet, it means its the first run
                        if(Queue1.element().getStarttime()==0) {
                            Queue1.element().setStarttime(i);
                        }
                    }
                }
            }
            else if(Queue2.size() != 0){
                for (int q2 = 0; q2 < quantum2; q2++) {
                    // process is finished
                    if(Queue2.element().getRemainingtime() <= 0 ) {
                        Objects.Process finishedProcess = Queue2.element();
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
                        Queue2.remove();
                        // +1 because starts at 0
                        i+=q2+1;
                        break;
                    }
                    // turn of process is over
                    else if ( q2 == quantum2-1 ){
                        Objects.Process moveToThree = Queue2.element();
                        moveToThree.setRemainingtime(moveToThree.getRemainingtime()-1);
                        // added to the back and deleted from the front
                        Queue3.add(moveToThree);
                        Queue2.remove();
                        i+=quantum2;

                    }
                    // nothing special, just execute the program
                    else {
                        Queue2.element().setRemainingtime(Queue2.element().getRemainingtime()-1);
                    }
                }
            } else if(Queue3.size() != 0){
                
                for (int q3 = 0; q3 < quantum3; q3++) {
                    // process is finished
                    if(Queue3.element().getRemainingtime() <= 0 ) {
                        Objects.Process finishedProcess = Queue3.element();
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
                        Queue3.remove();
                        i+=q3+1;
                        break;
                    }
                    // turn of process is over
                    else if ( q3 == quantum3-1 ){
                        Objects.Process backToEnd = Queue3.element();
                        backToEnd.setRemainingtime(backToEnd.getRemainingtime()-1);
                        // added to the back and deleted from the front
                        Queue3.add(backToEnd);
                        Queue3.remove();
                        i+=quantum3;

                    }
                    // nothing special, just execute the program
                    else {
                        Queue3.element().setRemainingtime(Queue3.element().getRemainingtime()-1);
                    }

                }
            }
            else {
                i++;
            }
            if(i >= 10000) {
                test = 10;
            }
            
            // break the infinite loop if all the processes ran and the queue is empty again
            if(CurrentAddedProcess == processes.size() && Queue1.size() == 0 && Queue2.size() == 0 && Queue3.size() == 0) {
                System.out.println("MultiLevelFeedback");
                System.out.println("average TAT= " + (avgTAT /= processes.size()));
                System.out.println("average NTAT= " + (avgNTAT /= processes.size()));
                System.out.println("average WT= " + (avgWT /= processes.size()));
                System.out.println("\n");
                try {
                FileWriter writer = new FileWriter(name + processes.size()+".txt"); 
                writer.write("PID, Arrivaltime, servicetime, starttime, endtime, turnaroundtime, normalisedTurnaroundtime, waitingtime;");
                writer.write("\n");
                for(Objects.Process str: processes) {
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
