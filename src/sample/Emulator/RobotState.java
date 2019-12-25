package sample.Emulator;

import sample.Emulator.MyTalonSRX;

import java.util.ArrayList;

public class RobotState {
    double RequestedAngle;
    double x;
    double y;
    ArrayList<MyTalonSRX> talonSRXES = new ArrayList<>();

    RobotState(double requestedAngle) {
        RequestedAngle = requestedAngle;
    }

    RobotState() {
        this(127);
    }

    public void setTalonSRXES(ArrayList<MyTalonSRX> talonSRXES) {
        this.talonSRXES = talonSRXES;
    }

    public ArrayList<MyTalonSRX> getTalonSRXES() {
        return talonSRXES;
    }
}
