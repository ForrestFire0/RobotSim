package sample.Emulator;

import java.util.ArrayList;

public class StartRobotState {
    private double currentAngle;
    private double tv;
    private double ty;
    private double tx;
    private ArrayList<Integer> encoders = new ArrayList<>();
    private Joystick joystick;

    public StartRobotState(double currentAngle, double tv, double ty, double tx, double rightspd, double leftspd, Joystick joystick) {
        this.currentAngle = currentAngle;
        this.tv = tv;
        this.ty = ty;
        this.tx = tx;
        this.joystick = joystick;
        encoders.add((int) rightspd);
        encoders.add((int) rightspd);
        encoders.add((int) leftspd);
        encoders.add((int) leftspd);
        System.out.println(rightspd);
    }

    public void setEncoders(ArrayList<Integer> encoders) {
        this.encoders = encoders;
    }

    public ArrayList<Integer> getEncoders() {
        return encoders;
    }

    public double getCurrentAngle() {
        return currentAngle;
    }

    public double getTv() {
        return tv;
    }

    public double getTy() {
        return ty;
    }

    public double getTx() {
        return tx;
    }
}
