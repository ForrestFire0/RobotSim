package sample.Emulator;

public class NetworkTableEntry {
    double val;

    public double getDouble(double defaultVal) {
        if(val != 0) {
            return val;
        }
        else return defaultVal;
    }

    public void setNumber(double lav) {
        val = lav;
    }
}
