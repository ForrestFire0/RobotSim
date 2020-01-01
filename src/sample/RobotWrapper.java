package sample;

import sample.Emulator.*;
import team5115.autotools.ErrList;
import team5115.autotools.SimpleAutoSeries;

public class RobotWrapper {

    static team5115.robot.Robot robot;
    public static StartRobotState srs;
    public static RobotState ers;

    static RobotState runTick(StartRobotState startRobotState, boolean locationAllowed) {

        srs = startRobotState;
        //set things like location and encoder values from the Start Robot State.
        MyTalonSRX.setEncoders(srs.getEncoders());
        ers = new RobotState();
        //run the tick
        AHRS.angle = srs.getCurrentAngle();
        AHRS.yaw = srs.getCurrentAngle();
        srs.addList(NetworkTableEntry.getList());

        if (locationAllowed) {
            robot.auto.yLoc = srs.getY();
            robot.auto.xLoc = srs.getX();
        }

        robot.autonomousPeriodic();

        //push everything into the End Robot State (Robot state)
        double currentAngle = srs.getCurrentAngle();
        double forwardSpeed = robot.dt.getAvgSpd();
        double deltaY = Math.sin(Math.toRadians(currentAngle)) * forwardSpeed; //converts from M/s to inches/sec then * 0.02 seconds to get deltaInches.
        double deltaX = Math.cos(Math.toRadians(currentAngle)) * forwardSpeed;
        ers.setX(deltaX);
        ers.setY(deltaY);
        ers.setTalonSRXES(MyTalonSRX.getTalonSRXES());
        ers.setRequestedAngle(MyTalonSRX.getRequestedAngle());
        ers.setCurrentStep(SimpleAutoSeries.getCurrentStep());
        return ers;
    }

    public static void instanciate() {
        robot = new team5115.robot.Robot();
        robot.robotInit();
    }

    public static void resetAutoSeries() {
        SimpleAutoSeries.reset();
    }

    public static void printErrList() {
        ErrList.printErrorList();
    }
}
