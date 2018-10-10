package ro.siit.java11.LevelMonitor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ro.siit.java11.LevelMonitor.domain.Reading;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReadingDAOImplementation implements ReadingDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * DAO method that selects all entries in the Readings table and generates a list of all existing readings
     * @return
     */
    @Override
    public List<Reading> getAll() {
        return jdbcTemplate.query("SELECT * FROM readings order by Reading_id asc", new RowMapper<Reading>() {
            @Override
            public Reading mapRow(ResultSet resultSet, int i) throws SQLException {
                Reading reading = new Reading();
                reading.setId(resultSet.getLong("reading_id"));
                reading.setTankNumber(resultSet.getInt("tanknumber"));
                reading.setFillLevel(resultSet.getFloat("filllevel"));
                reading.setWaterLevel(resultSet.getFloat("waterlevel"));
                reading.setTemperature(resultSet.getFloat("temperature"));
                reading.setProbeOffset(resultSet.getFloat("probeoffset"));
                reading.setDate(resultSet.getString("date_created"));
                //TODO change the date format
                reading.setTime(resultSet.getString("time_created"));
                //TODO Change time format
                return reading;
            }
        });
    }


    /**
     * DAO method that inserts a manually created reading into the Readings table
     * @param reading
     */
    @Override
    public void createManualReading(Reading reading) {
        jdbcTemplate.update("INSERT INTO readings(tanknumber, filllevel, waterlevel) VALUES (?, ?, ?)",reading.getTankNumber(),
                reading.getFillLevel(), reading.getWaterLevel());
        //no date and time meed to be added. Current date and time from the DB are set into the "Readings" table. Other values are "Default" in DB.
    }


    /**
     * DAO method that inserts an automatically created reading (from the sensors) into the Readings table
     * @param reading
     */
    @Override
    public void createAutomatedReading(Reading reading) {
        jdbcTemplate.update("INSERT INTO readings(tanknumber, filllevel, waterlevel, temperature, probeoffset, " +
                        "softwarevers, probeerror, checksum) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",reading.getTankNumber(),
                reading.getFillLevel(), reading.getWaterLevel(), reading.getTemperature(), reading.getProbeOffset(), reading.getSoftwareVersion(), reading.getProbeError(), reading.getChecksum());
    }


    /**
     * /** DAO method used to delete a db entry based on the Reading_ID
     * @param id
     */
    @Override
    public void removeReading(long id) {
        jdbcTemplate.update("DELETE FROM readings WHERE reading_id = ? ",id);
    }


    /**
     * DAO method used to update a reading in the Readings table
     * @param reading
     * @param id
     */
    @Override
    public void updateReading(Reading reading, long id) {
        jdbcTemplate.update("UPDATE readings SET tanknumber = ?, filllevel = ?, waterlevel = ? WHERE reading_id = ?", reading.getTankNumber(), reading.getFillLevel(), reading.getWaterLevel(), id);
    }


    /**
     * AO method used to return a reading object from the db, based on the Reading_ID
     * @param id
     * @return
     */
    public Reading getReadingById(long id){
        List<Reading> readings=jdbcTemplate.query("SELECT * FROM readings where reading_id=?", new RowMapper<Reading>() {
            @Override
            public Reading mapRow(ResultSet resultSet, int i) throws SQLException {
                Reading reading = new Reading();
                reading.setId(resultSet.getLong("reading_id"));
                reading.setTankNumber(resultSet.getInt("tanknumber"));
                reading.setFillLevel(resultSet.getFloat("filllevel"));
                reading.setWaterLevel(resultSet.getFloat("waterlevel"));
                reading.setTemperature(resultSet.getFloat("temperature"));
                reading.setProbeOffset(resultSet.getFloat("probeoffset"));
                reading.setDate(resultSet.getString("date_created"));
                reading.setTime(resultSet.getString("time_created"));
                return reading;
            }
        },id);
        return readings.get(0); //return the only element of the 'readings' list
    }

}

