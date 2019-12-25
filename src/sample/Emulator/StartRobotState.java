package sample.Emulator;

import java.util.ArrayList;

public class StartRobotState {
    private double currentAngle;
    private double tv;
    private double ty;
    private double tx;
    private double yVelocityFromNavX;
    private ArrayList<Integer> encoders = new ArrayList<>();

    public StartRobotState(double currentAngle, double tv, double ty, double tx, double yVelocityFromNavX) {
        this.currentAngle = currentAngle;
        this.tv = tv;
        this.ty = ty;
        this.tx = tx;
        this.yVelocityFromNavX = yVelocityFromNavX;
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

    public double getyVelocityFromNavX() {
        return yVelocityFromNavX;
    }
}
