package ro.siit.java11.LevelMonitor.service;

import com.fazecast.jSerialComm.SerialPort;
import ro.siit.java11.LevelMonitor.domain.Reading;
import ro.siit.java11.LevelMonitor.domain.SensorResponse;

public interface SensorService {

    public SensorResponse getSensorResponse(SerialPort serialPort);



}
