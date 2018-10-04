package ro.siit.java11.LevelMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.siit.java11.LevelMonitor.domain.Reading;
import ro.siit.java11.LevelMonitor.dto.CreateReadingRequest;
import ro.siit.java11.LevelMonitor.service.ReadingService;
import ro.siit.java11.LevelMonitor.service.SensorService;

@Controller
public class SensorController {

    @Autowired
    private ReadingService readingService;

    @Autowired
    private SensorService sensorService;


    /**
     * Controller method to call for the generation of an automated sensor reading
     * @param model
     * @param readingRequest
     * @return
     */
    @RequestMapping(value ="/listReadings/getSensorReading/", method = RequestMethod.POST)
    public String getSensorReading(Model model, CreateReadingRequest readingRequest){
        Reading sensorReading =  readingService.getSensorReading("COM3");
        readingService.createAutomatedReading(sensorReading);

        return "redirect:/listReadings";
    }
}
