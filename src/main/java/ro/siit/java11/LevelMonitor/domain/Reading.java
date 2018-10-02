package ro.siit.java11.LevelMonitor.domain;

/**
 * Class for the Reading object
 */

public class Reading {
    public long id;
    public int tankNumber;
    public float fillLevel;
    public float waterLevel;
    public float temperature;
    public float probeOffset;
    public int softwareVersion;
    public String probeError;
    public String checksum;
    public String date;
    public String time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTankNumber() {
        return tankNumber;
    }

    public void setTankNumber(int tankNumber) {
        this.tankNumber = tankNumber;
    }

    public float getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(float fillLevel) {
        this.fillLevel = fillLevel;
    }

    public float getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(float waterLevel) {
        this.waterLevel = waterLevel;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getProbeOffset() {
        return probeOffset;
    }

    public void setProbeOffset(float probeOffset) {
        this.probeOffset = probeOffset;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(int softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getProbeError() {
        return probeError;
    }

    public void setProbeError(String probeError) {
        this.probeError = probeError;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

}
