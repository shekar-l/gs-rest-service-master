package com.ashwinpathi.garage;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Created by slakshm on 5/20/16
 */
public class GarageDoorManager {
/*
        cd "C:\Users\Shekar\Downloads\gs-rest-service-master\complete"
        mvn tomcat7:undeploy
        mvn -e clean install tomcat7:deploy
*/
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
            outputPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "SimulateSwitchPress", PinState.LOW);
            inputPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_27, "DoorLimitSwitch", PinPullResistance.PULL_DOWN);
            inputPin.setDebounce(1000);
            initializeDoorStatus();
            // register a listener
            setupListeners();
            System.out.println("Listener setup");

            System.out.println("--> Initialized Pin ...");
        } else {
            System.out.println("-->  Pin already Initialized ...");
        }
        System.out.println("--> Initialized Garage Manager bean ...");
    }

    private void initializeDoorStatus(){
        isDoorOpen = !(inputPin.getState() + "").equals("LOW");
    }

    private void setupListeners() {
        System.out.println("Creating a listener on input pin");
        GpioPinListenerDigital listener  = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO pin state change: " + event.getPin() + " new state is " + event.getState() + "    "+"inputPin.getState() = " + inputPin.getState());
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
        System.out.println("Sending a 2 second pulse to output pin");
        outputPin.pulse(2000, false);
    }
}
