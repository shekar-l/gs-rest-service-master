package com.ashwinpathi.garage;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Created by slakshm on 5/20/16
 */
public class GarageDoorManager {
    private GpioController gpio;
    private GpioPinDigitalOutput outputPin;
    private GpioPinDigitalInput inputPin;
    private boolean isDoorOpen;

    public static void main(String[] commandLineArgs ) {
        GarageDoorManager manager = new GarageDoorManager();
        manager.pulseToOutputPin();
    }

    public GarageDoorManager() {
        if(gpio == null || outputPin == null) {
            System.out.println("--> Initializing Pins ...");
            gpio = GpioFactory.getInstance();
            outputPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "MyLED", PinState.LOW);
            inputPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_27, "MyLED2", PinPullResistance.PULL_DOWN);
            initializeDoorStatus();
            // register a listener
            try {
                setupListeners();
                System.out.println("Listener setup");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("e = " + e);
            }

            System.out.println("--> Initialized Pin ...");
        } else {
            System.out.println("-->  Pin already Initialized ...");
        }
        System.out.println("--> Initialized Garage Manager bean ...");
    }

    private void initializeDoorStatus(){
        isDoorOpen = !(inputPin.getState() + "").equals("LOW");
    }

    private void setupListeners() throws InterruptedException{
        // create GPIO listener
        //listener is on pin 27
        GpioPinListenerDigital listener  = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                System.out.println("1. inputPin.getState() = " + inputPin.getState());
                initializeDoorStatus();
            }
        };
        inputPin.addListener(listener);
    }

    public boolean isDoorOpen(){
        return isDoorOpen;
    }

    public void pulseToOutputPin()
    {
        // send an output pulse, but don't wait
        // asynchronous call
        outputPin.pulse(2000, false);
    }
}
