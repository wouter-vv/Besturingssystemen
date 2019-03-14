/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class PageTableEntry {
    int virtualAddress;
    int presentBit;
    int modifyBit;
    int lastAccessTime;
    int frameNumber;
    int physicalAddress;

    public PageTableEntry(int virtualAddress, int presentBit, int modifyBit, int lastAccessTime, int frameNumber, int physicalAddress) {
        this.virtualAddress = virtualAddress;
        this.presentBit = presentBit;
        this.modifyBit = modifyBit;
        this.lastAccessTime = lastAccessTime;
        this.frameNumber = frameNumber;
        this.physicalAddress = physicalAddress;
    }

    
    public int getPresentBit() {
        return presentBit;
    }

    public void setPresentBit(int presentBit) {
        this.presentBit = presentBit;
    }

    public int getModifyBit() {
        return modifyBit;
    }

    public void setModifyBit(int modifyBit) {
        this.modifyBit = modifyBit;
    }

    public int getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(int lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public int getVirtualAddress() {
        return virtualAddress;
    }

    public void setVirtualAddress(int virtualAddress) {
        this.virtualAddress = virtualAddress;
    }

    public int getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(int physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    @Override
    public String toString() {
        return "PageTableEntry{" + "presentBit=" + presentBit + ", modifyBit=" + modifyBit + ", lastAccessTime=" + lastAccessTime + ", frameNumber=" + frameNumber + '}';
    }
    
    
}
