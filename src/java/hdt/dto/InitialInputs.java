/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dto;

import hdt.domain.Bundle;
import hdt.domain.Network;
import hdt.domain.HdtSystem;
import java.io.Serializable;

/**
 *
 * @author escralp
 */
public class InitialInputs implements Serializable {
    private HdtSystem system;
    private Network network;
    private Bundle bundle;

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public HdtSystem getSystem() {
        return system;
    }

    public void setSystem(HdtSystem system) {
        this.system = system;
    }
}
