package ro.siit.java11.LevelMonitor.dto;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotEmpty;

public class CreateReadingRequest {

    @Autowired
    private CreateReadingRequest createReadingRequest;

    private int tankNumber;
    private float fillLevel;
    private float waterLevel;
    private float temperature;
    private float probeOffset;
    private int softwareVersion;
    private String probeError;
    private String checksum;



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



//    public CreateReadingRequest getCreateReadingRequest() {
//        return createReadingRequest;
//    }
//
//    public void setCreateReadingRequest(CreateReadingRequest createReadingRequest) {
//        this.createReadingRequest = createReadingRequest;
//    }


}
