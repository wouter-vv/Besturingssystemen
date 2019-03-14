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
public class Page {
    int dedicatedProcessID;
    AddressPage[] addressesPage = new AddressPage[4096];

    public Page(int dedicatedProcessID, AddressPage[] RAM) {
        this.addressesPage = RAM;
        this.dedicatedProcessID = dedicatedProcessID;
    }

    public int getDedicatedProcessID() {
        return dedicatedProcessID;
    }

    public void setDedicatedProcessID(int dedicatedProcessID) {
        this.dedicatedProcessID = dedicatedProcessID;
    }

    public AddressPage[] getAddressesPage() {
        return addressesPage;
    }

    public void setAddressesPage(AddressPage[] addressesPage) {
        this.addressesPage = addressesPage;
    }
    
    
    
}
