package com.ashwinpathi.garage;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GarageDoorController {
    /*
        cd "C:\Users\Shekar\Downloads\gs-rest-service-master\complete"
        mvn tomcat7:undeploy
        mvn -e clean install tomcat7:deploy

        https://garageraspi.local:8443/GarageDoorController/isDoorOpen
        https://garageraspi.local:8443/GarageDoorController/pressButton
        https://garageraspi.local:8443/GarageDoorController/test
        https://garageraspi.local:8443/manager
    */
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private GarageDoorManager garageDoorManager;

    public GarageDoorController() {
        System.out.println("Initialized Greeting Controller Bean");
        System.out.println("Garage Manager Bean: "+garageDoorManager);
    }

    @RequestMapping("/test")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(value="/isDoorOpen",method=RequestMethod.GET)
    public @ResponseBody
    boolean isDoorOpen()  throws InterruptedException
    {
        return garageDoorManager.isDoorOpen();
    }

    @RequestMapping(value="/pressButton",method=RequestMethod.POST)
    public @ResponseBody
    void pressSwitch(@RequestParam(value="secret") String secretCode) throws InterruptedException
    {
        if(secretCode.equals("abc"))
            garageDoorManager.pulseToOutputPin();
    }
}
