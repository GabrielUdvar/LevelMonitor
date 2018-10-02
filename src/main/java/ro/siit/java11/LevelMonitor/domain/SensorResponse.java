package ro.siit.java11.LevelMonitor.domain;

public class SensorResponse {
    public int numOfBytes;
    public String responseString;
    public boolean correctReading=false;


    public int getNumOfBytes() {
        return numOfBytes;
    }

    public void setNumOfBytes(int numOfBytes) {
        this.numOfBytes = numOfBytes;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public boolean isCorrectReading() {
        return correctReading;
    }

    public void setCorrectReading(boolean correctReading) {
        this.correctReading = correctReading;
    }


}
