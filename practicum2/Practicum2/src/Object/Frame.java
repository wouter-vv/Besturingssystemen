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
public class Frame {
    AddressFrame[] addressesFrame = new AddressFrame[4096];

    public Frame(AddressFrame[] frame) {
        this.addressesFrame = frame;
    }

    public AddressFrame[] getFrame() {
        return addressesFrame;
    }

    public void setFrame(AddressFrame[] frame) {
        this.addressesFrame = frame;
    }    
    
    
}
