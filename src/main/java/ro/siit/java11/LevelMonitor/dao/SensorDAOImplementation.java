package ro.siit.java11.LevelMonitor.dao;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import ro.siit.java11.LevelMonitor.domain.SensorResponse;

public class SensorDAOImplementation implements SensorDAO {

    @Autowired
    private SerialPort comPort;

    //interrogation of the sensor with the $A01 command
    public static SensorResponse getSensorState(SerialPort comPort) {
        try {
            for (int j=0; j<1; ++j){
                comPort.writeBytes("$A01".getBytes(),"$A01".getBytes().length); //check sensor status - see sensor doc for list of commands
                comPort.writeBytes("\r\n".getBytes(),"\r\n".getBytes().length); //carriage return line for above command
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }
        return null;
    }
}
