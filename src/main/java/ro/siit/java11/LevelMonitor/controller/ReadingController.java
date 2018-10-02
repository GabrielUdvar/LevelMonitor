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
/**
 * */
@Controller
public class ReadingController {

    @Autowired
    private ReadingService readingService;

    /**
     * Controller Method lists all readings existing in the database
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/listReadings", method = RequestMethod.GET)
    public String listReadings(Model model, HttpServletRequest request){
        List<Reading> readings = readingService.getAll(); //it is a reversed order list from the DB.
        model.addAttribute("listReadings", readings);

        return "listReadings";
    }


    /**
     * Controller Method creates a manual reading (Dip Stick Reading) using user input data
     * @param readingRequest
     * @param bindingResult
     * @param model
     * @return
     */
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


    /**
     * Controller Method removes an db entry based in the ReadingID
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/listReadings/removeReading/{id}", method = RequestMethod.POST)
    public String removeReading(@PathVariable long id, Model model){
        readingService.removeReading(id);

        return "redirect:/listReadings";
    }


    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "listReadings/update/{id}", method = RequestMethod.GET)
    public String getReading(@PathVariable long id, Model model){
        Reading reading = readingService.getById(id);
        model.addAttribute("updateReadingRequest", getReadingRequest(reading));
        model.addAttribute("reading_id", id);
        return "updateReadings";
    }


    /**
     * Controller Method updates a reading based on the ReadingID, using user input data
     * @param readingRequest
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "listReadings/update/listReadings/update/{id}/", method = RequestMethod.POST)
    public String updateReading(CreateReadingRequest readingRequest, @PathVariable long id, Model model){
        Reading reading = readingService.getReading(readingRequest);;
        readingService.updateReading(reading, id);

        return "redirect:/listReadings";
    }

    /**
     * Creates a ReadingRequest object for the manual reading and updates of entries in the db
     * @param reading
     * @return
     */
    private CreateReadingRequest getReadingRequest (Reading reading){
        CreateReadingRequest createReadingRequest = new CreateReadingRequest();
        createReadingRequest.setTankNumber(reading.getTankNumber());
        createReadingRequest.setFillLevel(reading.getFillLevel());
        createReadingRequest.setWaterLevel(reading.getWaterLevel());

        return createReadingRequest;
    }

}
