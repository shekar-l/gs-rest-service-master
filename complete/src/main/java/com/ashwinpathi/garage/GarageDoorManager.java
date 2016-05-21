package com.ashwinpathi.garage;


import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Created by slakshm on 5/20/16
 */
public class GarageDoorManager {
    GpioController gpio;
    GpioPinDigitalOutput outputPin;
    GpioPinDigitalInput inputPin;
    private boolean isDoorOpen;

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

//    private void init() {
//        if(gpio == null || outputPin == null) {
//            System.out.println("--> Initializing Pin ...");
//            gpio = GpioFactory.getInstance();
//            outputPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "MyLED", PinState.HIGH);
//            inputPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_27, "MyLED2", PinPullResistance.PULL_DOWN);
//            System.out.println("--> Initialized Pin ...");
//        } else {
//            System.out.println("-->  Pin already Initialized ...");
//        }
//    }
//
//    public boolean turnOnPin() {
//        init();
//        // set shutdown state for this pin
//        outputPin.setShutdownOptions(true, PinState.LOW);
//
//        System.out.println("--> GPIO state should be: ON");
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    public boolean turnOffPin() {
//        init();
//        // turn off gpio pin #01
//        outputPin.low();
//        System.out.println("--> GPIO state should be: OFF");
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }


    public void initializeDoorStatus(){
        if((inputPin.getState()+"").equals("LOW"))
            isDoorOpen = false;
        else
            isDoorOpen = true;

    }

    public void pressSwitch() {
        // set shutdown state for this pin
        outputPin.setShutdownOptions(true, PinState.LOW);

        // turn on gpio pin #01 for 1 second and then off
        System.out.println("--> GPIO state should be: ON for only 1 second");
        outputPin.pulse(2000, true); // set second argument to 'true' use a blocking call

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
//        gpio.shutdown();
//        gpio.unprovisionPin(outputPin);
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

    public static void main(String[] commandLineArgs ) {
        GarageDoorManager manager = new GarageDoorManager();
//        String option = "0";
//        if (commandLineArgs.length > 0) {
//            option = commandLineArgs[0];
//        }
//        if(option.equals("0")) {
//            manager.turnOnPin();
//        }
//
//        if(option.equals("1")) {
//            manager.turnOffPin();
//        }
    }
    public void pulseToOutputPin()
    {
        outputPin.pulse(2000, false);
    }
}

