package ro.siit.java11.LevelMonitor.config;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfiguration {
    private static final int PORT = 5432;
    private static final String DB = "filllevel";
    private static final String SERIALPORT = "COM1";

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        String url = new StringBuilder()
                .append("jdbc:")
                .append("postgresql")
                .append("://")
                .append("localhost")
                .append(":")
                .append(PORT)
                .append("/")
                .append(DB)
                .append("?user=")
                .append("postgres")
                .append("&password=")
                .append("parolatemporarapentrubazadedate").toString();

        return new SingleConnectionDataSource(url, false);
    }

}
