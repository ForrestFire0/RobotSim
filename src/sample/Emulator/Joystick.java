package sample.Emulator;

import java.util.ArrayList;

public class Joystick {
    int port;
    private ArrayList<Boolean> buttons;
    private ArrayList<Double> axis;

    public Joystick(int port) {
        this.port = port;
        buttons = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            buttons.add(false);
        }

        axis = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            axis.add((double) 0);
        }
    }

    public boolean getRawButton(int i) {
        return buttons.get(i);
    }

    public double getRawAxis(int i) {
        return axis.get(i);
    }

    public void setButtons(ArrayList<Boolean> buttons) {
        this.buttons = buttons;
    }

    public void setAxis(ArrayList<Double> axis) {
        this.axis = axis;
    }

    public int getPort() {
        return port;
    }

    public ArrayList<Boolean> getButtons() {
        return buttons;
    }

    public ArrayList<Double> getAxis() {
        return axis;
    }
}
