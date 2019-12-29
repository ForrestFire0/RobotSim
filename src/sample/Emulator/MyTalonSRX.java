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
    static ArrayList<Integer> encoders;

    public MyTalonSRX(int port) {
        if (encoders == null) {
            encoders = new ArrayList<>();
            encoders.add(1);
            encoders.add(1);
            encoders.add(1);
            encoders.add(1);
        }
        this.port = port;
        hasEncoder = false;
        talonSRXES.add(this);
    }

    public static void printEncoders() {
        for (Integer x :
                encoders) {
            System.out.println(x);
        }
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
        if(isFollower) return leader.getThrottle();
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
        hasEncoder = true;
    }

    public int getSelectedSensorVelocity() {
        return encoders.get(port-1);
    }
}
