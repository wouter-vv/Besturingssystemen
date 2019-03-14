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
public class AddressFrame {
    int address;
    int offset;

    public AddressFrame(int offset, int Address) {
        this.address = Address;
        this.offset = offset;
    }
    
    public int getAddress() {
        return address;
    }

    public void setAddress(int Address) {
        this.address = Address;
    }
    
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


}
