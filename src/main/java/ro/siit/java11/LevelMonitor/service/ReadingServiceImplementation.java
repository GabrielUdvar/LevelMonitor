package ro.siit.java11.LevelMonitor.service;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.java11.LevelMonitor.dao.ReadingDAO;
import ro.siit.java11.LevelMonitor.domain.Reading;
import ro.siit.java11.LevelMonitor.domain.SensorResponse;
import ro.siit.java11.LevelMonitor.dto.CreateReadingRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

/**
 * ReadingServiceImplementation class is the business logic layer that handles requests for creating/deleting/editing, etc of Readings.
 */

@Service
public class ReadingServiceImplementation implements ReadingService {
    @Autowired
    private ReadingDAO readingDAO;

    @Autowired
    private SensorService sensorService;

    List<Reading> readingList = new ArrayList<>();


    /**
     * Method called by controller that is used to return a list for the Model to pass to the View.
     * @return
     */
    @Override
    public List<Reading> getAll() {
        List<Reading> readingsList = new ArrayList<>(readingDAO.getAll());
        //TODO sort list in ascending or descending order
        List<Reading> reverseReadingsList = reverseList(readingsList); //returning a reversed list in order to display results from newer to older.

        return reverseReadingsList;
    }


    /**
     * Method used to reverse a list of Readings
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> reverseList(List<T> list){
        List<T> reverse = new ArrayList<>(list);
        Collections.reverse(reverse);
        return reverse;
    }


    /**
     * Method used to trigger a manual reading insert into the db.
     * @param reading
     */
    @Override
    public void createReading(Reading reading) {
        readingDAO.createManualReading(reading);
    }


    /**
     * Method used to trigger the removal of a Reading based on the Reading_ID.
     * @param id
     */
    @Override
    public void removeReading(long id) {
        readingDAO.removeReading(id);
    }


    /**
     * Method used to trigger the update of a reading, based on the Reading_ID and user input data.
     * @param reading
     * @param id
     */
    @Override
    public void updateReading(Reading reading, long id) {
        readingDAO.updateReading(reading,id);

    }


    /**
     * Method is used to pull a Reading from the db which is then used for the update process.
     * @param readingRequest
     * @return
     */
    @Override
    public Reading getReading(CreateReadingRequest readingRequest){
        Reading reading = new Reading();
        reading.setTankNumber(readingRequest.getTankNumber());
        reading.setWaterLevel(readingRequest.getWaterLevel());
        reading.setFillLevel(readingRequest.getFillLevel());

        return reading;
    }


    /**
     * Method used to get a Reading object based on the Sensor response.
     * @param comPort
     * @return
     */
    @Override
    public Reading getSensorReading(String comPort){
        SerialPort communicationPort = SerialPort.getCommPort(comPort);
        communicationPort.openPort(); //opens the comPort
        communicationPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING,100,0); //sets comPort Timeouts

        List<SensorResponse> sensorResponsesList = new ArrayList<>();
        sensorResponsesList.add(sensorService.getSensorResponse(communicationPort)); //triggers a sensor interrogation and a validated sensor response is added to the list.
        //parse the sensor response String in order to extract relevant data for the construction of a Response object
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
                //create the sensorReading object based on the parsed info.
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


    /**
     * Calls the DAO method used to add a sensor reading into the db.
     * @param reading
     */
    @Override
    public void createAutomatedReading(Reading reading) {
        readingDAO.createAutomatedReading(reading);
    }


    /**
     * Calls the DAO method used to retrieve a Reading from the db, based on the Reading_ID
     * @param id
     * @return
     */
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

    //    public static ArrayList<Reading> sortAscending(){
//        List<Reading> ascendingSortedList = new ArrayList<Reading>();
//        Collections.sort(ascendingSortedList);
//    }

}
