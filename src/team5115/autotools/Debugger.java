package team5115.autotools;

import team5115.subsystems.NavX;

import java.util.Objects;

public class Debugger {
    NavX navX;
    DriveBase dt;

    public Debugger(NavX navX, DriveBase dt) {
        this.navX = navX;
        this.dt = dt;
    }

    public void runDebugger() {
        System.out.println("Starting the debugger");

        if (navX == null || dt == null) {
            System.out.println("NavX or DriveBase is Null.");
            return;
        } else System.out.println("DriveBase and NavX are not Null.");

        delay(1);
        System.out.println("Moving forward");
        delay(0.25);
        dt.drive(1,0,0.2);
        delay(0.5);
        double encSpeed = dt.getAvgSpd();
        if(encSpeed < 0) {
            System.out.println("Error, encoders should be positive. Read Negative.(" + encSpeed + ")");
            return;
        } else System.out.println("Encoders for forward correct.");
        dt.stop();
        System.out.println("Done.");
        delay(0.5);

        System.out.println("Moving backward");
        delay(0.25);
        dt.drive(-1,0,0.2);
        delay(0.5);
        encSpeed = dt.getAvgSpd();
        if(encSpeed > 0) {
            System.out.println("Error, encoders should be negative. Read positive. (" + encSpeed + ")");
            return;
        } else System.out.println("Encoders for backward correct.");
        dt.stop();
        System.out.println("Done.");
        delay(0.5);
        navX.runTick();
    }

    public void delay(double seconds) {
        try {
            this.wait((long) seconds*1000);
        } catch (InterruptedException e) {
            System.out.println("Delay Interrupted");
        }
    }
}
