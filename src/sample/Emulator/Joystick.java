package sample.Emulator;

import javafx.scene.chart.Axis;

import java.util.ArrayList;

public class Joystick {
    int port;
    private ArrayList<Boolean> buttons = new ArrayList<>();
    private ArrayList<Double> axis = new ArrayList<>();

    public Joystick(int port) {
        this.port = port;
    }

    public boolean getRawButton(int i) {
        return buttons.get(i);
    }

    public double getRawAxis(int i) {
        return axis.get(i);
    }
}
