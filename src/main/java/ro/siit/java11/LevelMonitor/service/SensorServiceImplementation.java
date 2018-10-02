package ro.siit.java11.LevelMonitor.service;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;
import ro.siit.java11.LevelMonitor.dao.SensorDAOImplementation;
import ro.siit.java11.LevelMonitor.domain.Reading;
import ro.siit.java11.LevelMonitor.domain.SensorResponse;

import static java.lang.Integer.parseInt;
@Service
public class SensorServiceImplementation implements SensorService {

    //Method to listen for sensor response
    @Override
    public SensorResponse getSensorResponse(SerialPort comPort) {

        try {
            SensorDAOImplementation.getSensorState(comPort);
            byte[] readBuffer = new byte[comPort.bytesAvailable()];
            int numRead = comPort.readBytes(readBuffer, readBuffer.length);
            String stringResponse = new String(readBuffer);
            System.out.println("Sensor response" + stringResponse);
            SensorResponse sensorResponse = verifySensorAnswerIntegrity(comPort, numRead, stringResponse);
            if (sensorResponse != null)
                return sensorResponse;
        } catch (Exception e) { e.printStackTrace(); }
        return null ;
    }

    //Check for a valid sensor response.
    // Conditions: response should have more than 24 bytes and string response should start with #
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
        //If an invalid response is sent, the sensor is polled again
        else{
            Thread.sleep(1000);
            SensorDAOImplementation.getSensorState(comPort);
        }
        return null;
    }


}
