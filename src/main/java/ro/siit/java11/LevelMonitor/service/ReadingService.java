package ro.siit.java11.LevelMonitor.service;

import com.fazecast.jSerialComm.SerialPort;
import ro.siit.java11.LevelMonitor.domain.Reading;
import ro.siit.java11.LevelMonitor.dto.CreateReadingRequest;

import java.util.List;

public interface ReadingService {

    List<Reading> getAll();

    void createReading(Reading reading);

    public void removeReading (long id);

    void updateReading (Reading reading, long id);

    public Reading getReading(CreateReadingRequest readingRequest);

    public Reading getSensorReading(String comPort);

    void createAutomatedReading(Reading reading);

    public Reading getById(long id);

}
