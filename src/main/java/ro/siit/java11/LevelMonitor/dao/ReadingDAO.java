package ro.siit.java11.LevelMonitor.dao;

import ch.qos.logback.core.net.server.Client;
import ro.siit.java11.LevelMonitor.domain.Reading;
import ro.siit.java11.LevelMonitor.domain.SensorResponse;

import java.util.List;

public interface ReadingDAO {


    List<Reading> getAll();

    void createManualReading(Reading reading);
    void createAutomatedReading(Reading reading);
    void removeReading(long id);
    void updateReading(Reading reading, long id);
    Reading getReadingById(long id);


}
