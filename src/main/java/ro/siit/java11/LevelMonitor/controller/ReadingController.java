package ro.siit.java11.LevelMonitor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.expression.Lists;
import ro.siit.java11.LevelMonitor.domain.Reading;
import ro.siit.java11.LevelMonitor.dto.CreateReadingRequest;
import ro.siit.java11.LevelMonitor.service.ReadingService;
import ro.siit.java11.LevelMonitor.service.SensorService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class ReadingController {

    @Autowired
    private ReadingService readingService;

    @RequestMapping(value = "/listReadings", method = RequestMethod.GET)
    public String listReadings(Model model, HttpServletRequest request){
        List<Reading> readings = readingService.getAll(); //it is a reversed order list from the DB.
        model.addAttribute("listReadings", readings);

        return "listReadings";
    }

    @RequestMapping(value = "/listReadings", method = RequestMethod.POST)
    public String createReading(@Valid CreateReadingRequest readingRequest, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> errorsText = new LinkedList<>();
            errors.stream().forEach(error -> {
                errorsText.add(error.getField() + " -- " + error.getRejectedValue() + " -- " + error.getDefaultMessage());
            });
            model.addAttribute("errors", errorsText);
            model.addAttribute("createReadingRequest", readingRequest);

            return "redirect:/listReadings";
        } else {
            Reading reading = readingService.getReading(readingRequest);
            readingService.createReading(reading);

            return "redirect:/listReadings";
        }
    }

    @RequestMapping(value = "/listReadings/removeReading/{id}", method = RequestMethod.POST)
    public String removeReading(@PathVariable long id, Model model){
        readingService.removeReading(id);

        return "redirect:/listReadings";
    }

    @RequestMapping(value = "listReadings/update/{id}", method = RequestMethod.GET)
    public String getReading(@PathVariable long id, Model model){
        Reading reading = readingService.getById(id);
        model.addAttribute("updateReadingRequest", getReadingRequest(reading));
        model.addAttribute("reading_id", id);
        return "updateReadings";
    }

    @RequestMapping(value = "listReadings/update/listReadings/update/{id}/", method = RequestMethod.POST)
    public String updateReading(CreateReadingRequest readingRequest, @PathVariable long id, Model model){
        Reading reading = readingService.getReading(readingRequest);;
        readingService.updateReading(reading, id);

        return "redirect:/listReadings";
    }

//    private Reading getManualReading(CreateReadingRequest readingRequest) {
//        Reading reading = new Reading();
//        reading.setTankNumber(readingRequest.getTankNumber());
//        reading.setFillLevel(readingRequest.getFillLevel());
//        reading.setWaterLevel(readingRequest.getWaterLevel());
//
//        return reading;
//    }

private CreateReadingRequest getReadingRequest (Reading reading){
        CreateReadingRequest createReadingRequest = new CreateReadingRequest();
        createReadingRequest.setTankNumber(reading.getTankNumber());
        createReadingRequest.setFillLevel(reading.getFillLevel());
        createReadingRequest.setWaterLevel(reading.getWaterLevel());

        return createReadingRequest;
}

}
