package sample;

import sample.Emulator.AHRS;
import sample.Emulator.MyTalonSRX;
import sample.Emulator.RobotState;
import sample.Emulator.StartRobotState;
import team5115.autotools.SimpleAutoSeries;
import team5115.robot.Robot;
import team5115.subsystems.NavX;

public class RobotWrapper {

    static team5115.robot.Robot robot;
    public static StartRobotState srs;
    public static RobotState ers;

    static RobotState runTick(StartRobotState startRobotState) {

        srs = startRobotState;
        //set things like location and encoder values from the Start Robot State.
        MyTalonSRX.setEncoders(srs.getEncoders());
        ers = new RobotState();
        //run the tick
        AHRS.angle = srs.getCurrentAngle();
        AHRS.yaw = srs.getCurrentAngle();
        robot.autonomousPeriodic();

        //push everything into the End Robot State (Robot state)
        double currentAngle = srs.getCurrentAngle();
        double forwardSpeed = robot.dt.getAvgSpd();
        double deltaY = Math.sin(Math.toRadians(currentAngle)) * forwardSpeed; //converts from M/s to inches/sec then * 0.02 seconds to get deltaInches.
        double deltaX = Math.cos(Math.toRadians(currentAngle)) * forwardSpeed;
        System.out.println(deltaX);
        System.out.println(deltaY);
        ers.setX(deltaX);
        ers.setY(deltaY);
        ers.setTalonSRXES(MyTalonSRX.getTalonSRXES());
        ers.setRequestedAngle(MyTalonSRX.getRequestedAngle());
        return ers;
    }

    public static void instanciate() {
        robot = new team5115.robot.Robot();
        robot.robotInit();
    }

    public static void resetAutoSeries() {
        SimpleAutoSeries.reset();
    }
}
