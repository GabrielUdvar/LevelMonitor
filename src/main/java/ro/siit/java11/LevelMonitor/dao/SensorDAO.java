package ro.siit.java11.LevelMonitor.dao;

import com.fazecast.jSerialComm.SerialPort;
import ro.siit.java11.LevelMonitor.domain.SensorResponse;

public interface SensorDAO {

    public static SensorResponse getSensorState(SerialPort comPort){
        return null;
    }
}
