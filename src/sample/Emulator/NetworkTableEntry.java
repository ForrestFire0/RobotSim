package sample.Emulator;

import java.util.HashMap;

public class NetworkTableEntry {
    String name;

    static HashMap<String, Double> list = new HashMap<>();

    public NetworkTableEntry(String name) {
        this.name = name;
    }

    public static HashMap<String, Double> getList() {
        return list;
    }

    public static void setList(HashMap<String, Double> list) {
        NetworkTableEntry.list = list;
    }

    public double getDouble(double defaultVal) {
        final Double aDouble = list.get(name);
        if (aDouble == null) {
            return defaultVal;
        }
        return aDouble;
    }

    public String getName() {
        return name;
    }

    public void setNumber(int i) {
        list.put(name, (double) i);
    }
}
