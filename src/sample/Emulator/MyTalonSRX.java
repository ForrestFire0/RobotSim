package sample.Emulator;

import java.util.ArrayList;

public class MyTalonSRX {
    int port;
    double throttle;
    MyTalonSRX leader;
    boolean hasEncoder;
    boolean isFollower;

    public static double getRequestedAngle() {
        return requestedAngle;
    }

    public static void setRequestedAngle(double requestedAngle) {
        MyTalonSRX.requestedAngle = requestedAngle;
    }

    static double requestedAngle;
    static ArrayList<MyTalonSRX> talonSRXES = new ArrayList<>();
    static ArrayList<Integer> encoders = new ArrayList<>();

    public MyTalonSRX(int port) {
        this.port = port;
        talonSRXES.add(this);
    }

    public void set(ControlMode controlMode, double x) {
        switch (controlMode) {
            case PercentOutput:
                throttle = x;
                break;
            case Follower:
                try {
                    throw new Exception("Change to either percent or smthin else.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    throw new Exception("Unrecognized control Mode.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void set(ControlMode controlMode, MyTalonSRX myTalonSRX) {
        if(controlMode == ControlMode.Follower) {
            leader = myTalonSRX;
            isFollower = true;
        } else try {
            throw new Exception("You have to set it to follower control mode.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getThrottle() {
        return throttle;
    }

    public int getPort() {
        return port;
    }

    public static ArrayList<MyTalonSRX> getTalonSRXES() {
        return talonSRXES;
    }

    public static void setEncoders(ArrayList<Integer> encoders) {
        MyTalonSRX.encoders = encoders;
    }

    public MyTalonSRX getDeviceID() {
        return this;
    }

    public void configSelectedFeedbackSensor(FeedbackDevice x) {
        hasEncoder = false;
    }

    public int getSelectedSensorVelocity() {
        if(hasEncoder) return encoders.get(port);
        else return 0;
    }
}
