package sample.Emulator;

public class AHRS {
    //todome set the yaw and the angle.
    public static double yaw;
    public static double angle;

    public AHRS(boolean... x) {
    }
    @SuppressWarnings("EmptyMethod")
    public void reset() {
    }

    public double getYaw() {
        return yaw;
    }

    public double getAngle() {
        return angle;
    }
}
