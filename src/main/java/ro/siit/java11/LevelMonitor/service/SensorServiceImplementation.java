package ro.siit.java11.LevelMonitor.service;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;
import ro.siit.java11.LevelMonitor.dao.SensorDAOImplementation;
import ro.siit.java11.LevelMonitor.domain.SensorResponse;

/**
 * Business logic that handles the HLS6010 sensor interrogation
 */

@Service
public class SensorServiceImplementation implements SensorService {


    /**
     * Method used to listen for the sensor response.
     * After the $A01 command sent to the sensor, it replies with a series of bytes containing information regarding the tank it is installed in.
     * This method listens for the response and verifies the integrity of the response.
     * @param comPort
     * @return
     */
    @Override
    public SensorResponse getSensorResponse(SerialPort comPort) {
        try {
            SensorDAOImplementation.getSensorState(comPort);
            byte[] readBuffer = new byte[comPort.bytesAvailable()]; //a byte array read buffer used to store the raw bytes response of the sensor
            int numRead = comPort.readBytes(readBuffer, readBuffer.length);
            String stringResponse = new String(readBuffer); //conversion of the bytes array into a string array
            System.out.println("Sensor response" + stringResponse);
            //generation of the sensorResponse object but only after its integrity has been validated
            SensorResponse sensorResponse = verifySensorAnswerIntegrity(comPort, numRead, stringResponse);
            if (sensorResponse != null)
                return sensorResponse;
        } catch (Exception e) { e.printStackTrace(); }
        return null ;
    }


    /**
     * Method is used to check for the integrity and validity of a sensor response.
     * Due to communication break-ups some sensor responses might be corrupted.
     * Conditions: response should have more than 24 bytes and string response should start with #
     * //TODO also implement CRC verification (see sensor documentation)
     * If an invalid response is sent, the sensor is polled again
     * @param comPort
     * @param numRead
     * @param stringResponse
     * @return
     * @throws InterruptedException
     */
    private static SensorResponse verifySensorAnswerIntegrity(SerialPort comPort, int numRead, String stringResponse) throws InterruptedException {
        if (numRead>=24){
            if( stringResponse.startsWith("#")){
                SensorResponse sensorResponse = new SensorResponse();
                sensorResponse.setNumOfBytes(numRead);
                sensorResponse.setResponseString(stringResponse);
                sensorResponse.setCorrectReading(true);
                System.out.println(sensorResponse.getNumOfBytes() +"and" +sensorResponse.getResponseString());
                comPort.closePort();
                return sensorResponse;
            }
        }
        else{
            Thread.sleep(1000);
            SensorDAOImplementation.getSensorState(comPort);
        }
        return null;
    }
}
