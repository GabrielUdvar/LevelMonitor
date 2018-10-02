package ro.siit.java11.LevelMonitor.service;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Lists;
import ro.siit.java11.LevelMonitor.dao.ReadingDAO;
import ro.siit.java11.LevelMonitor.domain.Reading;
import ro.siit.java11.LevelMonitor.domain.SensorResponse;
import ro.siit.java11.LevelMonitor.dto.CreateReadingRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@Service
public class ReadingServiceImplementation implements ReadingService {
    @Autowired
    private ReadingDAO readingDAO;

    @Autowired
    private SensorService sensorService;

    List<Reading> readingList = new ArrayList<>();
    //List[][] listForGraph = new String[][];

    @Override
    public List<Reading> getAll() {
        List<Reading> readingsList = new ArrayList<>(readingDAO.getAll());

        List<Reading> reverseReadingsList = reverseList(readingsList); //returning a reversed list in order to display results from newer to older.

        return reverseReadingsList;

    }

//    public static ArrayList<Reading> sortAscending(){
//        List<Reading> ascendingSortedList = new ArrayList<Reading>();
//        Collections.sort(ascendingSortedList);
//    }

    public static <T> List<T> reverseList(List<T> list){
        List<T> reverse = new ArrayList<>(list);
        Collections.reverse(reverse);
        return reverse;
    }

    @Override
    public void createReading(Reading reading) {
        readingDAO.createManualReading(reading);
    }

    @Override
    public void removeReading(long id) {
        readingDAO.removeReading(id);
    }

    @Override
    public void updateReading(Reading reading, long id) {
        readingDAO.updateReading(reading,id);

    }
    @Override
    public Reading getReading(CreateReadingRequest readingRequest){
        Reading reading = new Reading();
        reading.setTankNumber(readingRequest.getTankNumber());
        reading.setWaterLevel(readingRequest.getWaterLevel());
        reading.setFillLevel(readingRequest.getFillLevel());

        return reading;
    }

    @Override
    public Reading getSensorReading(String comPort){
        SerialPort communicationPort = SerialPort.getCommPort(comPort);
        communicationPort.openPort();
        communicationPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING,100,0);

        List<SensorResponse> sensorResponsesList = new ArrayList<>();
        sensorResponsesList.add(sensorService.getSensorResponse(communicationPort));

        if(sensorResponsesList!=null){
            for (SensorResponse responses :sensorResponsesList) {
                String str = responses.getResponseString();
                String parts[] = str.split("@");

                String fillLevel = parts[0].replace("#"," ").trim();
                String waterLevel = parts[1];
                String temperature = parts[2];
                String probeOffset = parts[3];
                String softwareVersion = parts[4];
                String probeError = parts [5];
                String checksum = parts [6];
                //SensorServiceImplementation.parseSensorResponse(responses);
                Reading sensorReading = new Reading();
                sensorReading.setFillLevel(parseFloat(fillLevel)/10);
                sensorReading.setWaterLevel(parseFloat(waterLevel)/10);
                sensorReading.setTemperature(parseFloat(temperature)/10);
                sensorReading.setProbeOffset(parseFloat(probeOffset));
                sensorReading.setProbeError(probeError);
                sensorReading.setSoftwareVersion(parseInt(softwareVersion));
                sensorReading.setChecksum(checksum);

                return sensorReading;
            }
        }
        return null;}

    @Override
    public void createAutomatedReading(Reading reading) {
        readingDAO.createAutomatedReading(reading);
    }

    @Override
    public Reading getById(long id) {
        return readingDAO.getReadingById(id);
    }


//    public List getListForGraph(){
//        for (Reading reading readingList) {
//            listForGraph.add(reading.getFillLevel())
//            listForGraph.add(reading.getDate());
//        }
//
//        return
//    }

}
