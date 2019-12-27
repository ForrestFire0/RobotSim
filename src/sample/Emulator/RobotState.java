package sample.Emulator;

import java.util.ArrayList;

public class RobotState {
    double requestedAngle;
    double x;
    double y;
    ArrayList<MyTalonSRX> talonSRXES = new ArrayList<>();

    RobotState(double requestedAngle) {
        this.requestedAngle = requestedAngle;
    }

    public RobotState() {
        this(0);
    }

    public void setTalonSRXES(ArrayList<MyTalonSRX> talonSRXES) {
        this.talonSRXES = talonSRXES;
    }

    public ArrayList<MyTalonSRX> getTalonSRXES() {
        return talonSRXES;
    }

    public double getRequestedAngle() {
        return requestedAngle;
    }

    public void setRequestedAngle(double requestedAngle) {
        this.requestedAngle = requestedAngle;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
