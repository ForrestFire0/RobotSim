package sample;

import sample.Emulator.MyTalonSRX;
import sample.Emulator.RobotState;
import sample.Emulator.StartRobotState;

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
        robot.teleopPeriodic();

        //push everything into the End Robot Stat (Robot state)
        ers.setTalonSRXES(MyTalonSRX.getTalonSRXES());
        return ers;
    }

    public static void instanciate() {
        robot = new team5115.robot.Robot();
        robot.robotInit();
    }
}
