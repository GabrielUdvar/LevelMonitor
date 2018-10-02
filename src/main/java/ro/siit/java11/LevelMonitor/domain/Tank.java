package ro.siit.java11.LevelMonitor.domain;

public class Tank {

    public int tankID;
    public String tankName;
    public String shape;
    public int installedSensorID;
    public float volume;


    public int getTankID() {
        return tankID;
    }

    public void setTankID(int tankID) {
        this.tankID = tankID;
    }

    public String getTankName() {
        return tankName;
    }

    public void setTankName(String tankName) {
        this.tankName = tankName;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(TankShape tankShape) {
        this.shape = shape;
    }

    public int getInstalledSensorID() {
        return installedSensorID;
    }

    public void setInstalledSensorID(int installedSensorID) {
        this.installedSensorID = installedSensorID;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
