package team5115.robot;
import sample.Emulator.Joystick;
import team5115.autotools.DriveBase;
import team5115.subsystems.*;

//todome make a calibration method to move it to a certain distance and know the angle.
public class Robot {
    private Joystick joy;
    private DriveBase dt;
    private NavX navX;
    private Auto auto;

    public void robotInit() {
        joy = new Joystick(0);
        navX = new NavX();
        dt = new Drivetrain(navX);
        auto = new Auto(dt, navX);
        dt.resetTargetAngle(); //set the target angle to where we are looking.
    }

    public void teleopPeriodic() {
        navX.runTick();

        if(joy.getRawButton(8)) {
            auto.runAuto();
        }

        else {
            dt.driveByWire(joy.getRawAxis(0), joy.getRawAxis(1)); //Drive by wire based on the angle.
            auto.IMUCalc();
        }

        if(joy.getRawButton(9)) { //press this button to calibrate.
            navX.navxAngleReset(); //if the button is pressed reset the navx angle. Do this when relative to the wall.
            dt.resetTargetAngle();
            auto.IMURESET();
        }
    }

    public void disabledInit() {
        System.out.println("Disabled.");
        dt.drive(0,0,0);
    }

    public void teleopInit() {
        System.out.println("Starting! Reset dt Target Angle");
        dt.resetTargetAngle();
    }

    public void autonomousPeriodic() {
        auto.runAuto();
    }
}

/**
 * When the button is pressed,
 * Look for the target. If found,
 * Generate path to target, based on data currently found / averaged.
 * We have a path found. This path describes the angle we need to hold for this current angle. We use this curve, find the angle we want when in the position we are, then go to that angle. Forget the path.
 *
 */


