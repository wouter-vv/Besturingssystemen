/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.util.Comparator;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class ProcessHRRN implements Comparable{
    int pid;
    int arrivaltime;
    int servicetime;
    int starttime;
    int endtime;
    int turnaroundtime;
    double normalizedTurnaroundtime;
    int waitingtime;
    int remainingtime;
    double responseRatio;
    boolean read = false;
    
    
    public ProcessHRRN () {}
    public ProcessHRRN (int pid, int arrivaltime, int servicetime, int starttime, int endtime, int turnaroundtime, double normalizedTurnaroundtime, int waitingtime, int remainingtime, double responseRatio) {
        this.pid = pid;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.starttime = starttime;
        this.endtime = endtime;
        this.turnaroundtime = turnaroundtime;
        this.normalizedTurnaroundtime = normalizedTurnaroundtime;
        this.waitingtime = waitingtime;
        this.remainingtime = remainingtime;
        this.responseRatio = ((double)servicetime + (double)waitingtime) / (double)servicetime;
    }
    
    /************
     * Getters and Setters
     ************/
    
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(int arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public int getServicetime() {
        return servicetime;
    }

    public void setServicetime(int servicetime) {
        this.servicetime = servicetime;
        this.responseRatio = ((double)servicetime + (double)waitingtime) / (double)servicetime;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public int getTurnaroundtime() {
        return turnaroundtime;
    }

    public void setTurnaroundtime(int turnaroundtime) {
        this.turnaroundtime = turnaroundtime;
    }

    public double getNormalizedTurnaroundtime() {
        return normalizedTurnaroundtime;
    }

    public void setNormalizedTurnaroundtime(double normalizedTurnaroundtime) {
        this.normalizedTurnaroundtime = normalizedTurnaroundtime;
    }

    public int getWaitingtime() {
        return waitingtime;
    }

    public void setWaitingtime(int waitingtime) {
        this.waitingtime = waitingtime;
        this.responseRatio = ((double)servicetime + (double)waitingtime) / (double)servicetime;
    }

    public int getRemainingtime() {
        return remainingtime;
    }

    public void setRemainingtime(int remainingtime) {
        this.remainingtime = remainingtime;
    }
    
    public double getResponseRatio() {
        return responseRatio;
    }

    public void setResponseRatio(double responseRatio) {
        this.responseRatio = responseRatio;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    
    @Override
    public String toString() {
        return  pid + ", " + arrivaltime + ", " + servicetime + ", " + starttime + ", " + endtime + ", " + turnaroundtime + ", " + normalizedTurnaroundtime + ", " + waitingtime + ';';
    }

    
    

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof ProcessHRRN))
            throw new ClassCastException("A ProcessHRRN object expected.");
        return Comparator.comparing(ProcessHRRN::getResponseRatio).reversed()
              .compare(this, (ProcessHRRN)o);
              
    }
    
//    static Comparator<ProcessHRRN> getAttribute2Comparator() {
//        return new Comparator<ProcessHRRN>() {
//            return one.;
//        };
//    }
}
