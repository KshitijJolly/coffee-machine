package org.jollyk.coffee.machine.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Semaphore;

@Getter
@Setter
public class Outlets {

    private Semaphore numOutletsSemaphore;

    public Outlets(int numOutlets) {
        numOutletsSemaphore = new Semaphore(numOutlets);
    }

    public void dispense(Beverage beverage) throws InterruptedException{
        numOutletsSemaphore.acquire();
        //Dispense beverage here
        numOutletsSemaphore.release();
    }

    public boolean isOutletAvailable() {
        return this.numOutletsSemaphore.availablePermits() > 0;
    }
}
