package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.util.Duration;
import sample.Emulator.Joystick;
import sample.Emulator.MyTalonSRX;
import sample.Emulator.RobotState;
import sample.Emulator.StartRobotState;
import sample.Graphics.Drawer;
import sample.Graphics.GridPaneGenerator;
import sample.Graphics.HelpMenu;

import java.util.ArrayList;

public class Main extends javafx.application.Application {
    final Canvas canvas = new Canvas(Drawer.windowX, Drawer.windowY);
    final GraphicsContext gc = canvas.getGraphicsContext2D();
    boolean autoMove = false;
    public boolean locationAllowed = false;

    @Override
    public void start(Stage primaryStage) {
        Drawer.init(gc);
        //Set up inputs
        GridPaneGenerator pyspropgen = new GridPaneGenerator();
        pyspropgen.add("X", 40, true);
        pyspropgen.add("Y", 30, true);
        pyspropgen.add("orientation", 0, true);
        TitledPane physicalProperties = new TitledPane("Physical Properties", pyspropgen.generate());

        GridPaneGenerator limelightgen = new GridPaneGenerator();
        limelightgen.add("tx", 0, false);
        limelightgen.add("ty", 0, false);
        limelightgen.add("tv", 0, false);
        TitledPane limelight = new TitledPane("Limelight", limelightgen.generate());
        limelight.setExpanded(false);

        GridPaneGenerator joystickgen = new GridPaneGenerator();
        joystickgen.add("x axis", 0, false);
        joystickgen.add("y axis", 0, false);
        joystickgen.add("button 8", 1, false);
        TitledPane joystick = new TitledPane("Joystick", joystickgen.generate());
        joystick.setExpanded(false);

        GridPaneGenerator encodergen = new GridPaneGenerator();
        encodergen.add("left encoder spd", 0, true);
        encodergen.add("right encoder spd", 0, true);
        TitledPane encoder = new TitledPane("Encoders", encodergen.generate());

        VBox input = new VBox(physicalProperties, limelight, joystick, encoder);

        //Set up outputs.
        GridPaneGenerator wheeloutputsGen = new GridPaneGenerator();
        GridPaneGenerator requestedAngleGen = new GridPaneGenerator();
        requestedAngleGen.add("Requested Angle", 127, false);
        TitledPane requestedAngle = new TitledPane("Requested Angle", requestedAngleGen.generate());

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                t -> {
                    double x = t.getX();
                    double y = t.getY();
                    double temp = y;

                    y = (x - 20) / 2;
                    x = (temp - 20) / 2;

                    pyspropgen.get("X").setText(String.valueOf(x));
                    pyspropgen.get("Y").setText(String.valueOf(y));
                });
        //run the robot tick.
        RobotWrapper.instanciate();

        //fill in the motor values
        ArrayList<MyTalonSRX> talonSRXES = MyTalonSRX.getTalonSRXES();
        for (MyTalonSRX x : talonSRXES) {
            wheeloutputsGen.add("TalonSRX@prt" + x.getPort(), x.getThrottle(), false);
        }

        //calculate default outputs.
        TitledPane wheelOutputs = new TitledPane("Wheel outputs", wheeloutputsGen.generate());
//        wheelOutputs.setExpanded(true);
        VBox output = new VBox(wheelOutputs, requestedAngle);

        Button runTick = new Button("Run Tick");
        runTick.setTooltip(new Tooltip("Click to run the robots autonomous or teleop periodic (set in RobotWrapper)"));
        runTick.setOnAction(e -> {
            Joystick joy = new Joystick(0);
            ArrayList<Double> axis = new ArrayList<>();
            axis.add(getDFTF(joystickgen.get("x axis")));
            axis.add(getDFTF(joystickgen.get("y axis")));
            joy.setAxis(axis);
            ArrayList<Boolean> buttons = joy.getButtons();
            buttons.set(8, true);
            joy.setButtons(buttons);

            //set up the inputs (set start robot state)
            StartRobotState state = new StartRobotState(
                    getDFTF(pyspropgen.get("X")),
                    getDFTF(pyspropgen.get("Y")),
                    getDFTF(pyspropgen.get("orientation")),
                    getDFTF(limelightgen.get("tv")),
                    getDFTF(limelightgen.get("ty")),
                    getDFTF(limelightgen.get("tx")),
                    getDFTF(encodergen.get("left encoder spd")),
                    -getDFTF(encodergen.get("right encoder spd")),
                    joy
            );

            //get the result
            RobotState result = RobotWrapper.runTick(state, locationAllowed);

            requestedAngleGen.get("Requested Angle").setText(String.valueOf(Math.round(result.getRequestedAngle())));

            //setup the x and y for the drawer
            if (autoMove) { //set x and y
                result.setX(getDFTF(pyspropgen.get("X")) + result.getX());
                result.setY(getDFTF(pyspropgen.get("Y")) + result.getY());
                pyspropgen.get("X").setText(String.valueOf(result.getX()));
                pyspropgen.get("Y").setText(String.valueOf(result.getY()));
                pyspropgen.get("orientation").setText(String.valueOf(result.getRequestedAngle()));
            } else {
                result.setX(getDFTF(pyspropgen.get("X")));
                result.setY(getDFTF(pyspropgen.get("Y")));
                result.setRequestedAngle(getDFTF(pyspropgen.get("orientation")));
            }
            System.out.println("autoMove = " + autoMove);
            System.out.println("result.getRequestedAngle() = " + result.getRequestedAngle());
            //draw the robot.
            Drawer.drawRobot(result);
            //set the values in the outputs

            for (MyTalonSRX x : talonSRXES) {
                wheeloutputsGen.get("TalonSRX@prt" + x.getPort()).setText(String.valueOf(x.getThrottle()));
            }

        });

        Button resetSeries = new Button("Reset Auto Series");
        resetSeries.setOnAction(e -> RobotWrapper.resetAutoSeries());

        Button setEncoderVals = new Button("Autoencoders");
        setEncoderVals.setTooltip(new Tooltip("Check to automatically set encoder\nvalues based on motor outputs."));
        setEncoderVals.setOnAction(e -> {
            encodergen.get("left encoder spd").setText(
                    String.valueOf(
                            Math.abs(
                                    getDFTF(
                                            wheeloutputsGen.get("TalonSRX@prt1")) * 1000
                            )));
            encodergen.get("right encoder spd").setText(
                    String.valueOf(
                            Math.abs(
                                    getDFTF(
                                            wheeloutputsGen.get("TalonSRX@prt2")) * 1000
                            )));
        });

        CheckBox inputOrReported = new CheckBox("AutoMove");
        inputOrReported.setTooltip(new Tooltip("Select to automatically move and\nrotate the graphical representation\nof the robot."));
        inputOrReported.setOnAction(e -> autoMove = inputOrReported.isSelected());

        Button printErrors = new Button("Print Errors");
        printErrors.setTooltip(new Tooltip("Press to print out the errors from ErrList to the println"));
        printErrors.setOnAction(e -> RobotWrapper.printErrList()
        );

        CheckBox l = new CheckBox("Allow Location");
        l.setTooltip(new Tooltip("Check to allow robot to\nknow its location"));
        l.setOnAction(e -> locationAllowed = l.isSelected());

        HBox buttons = new HBox(runTick, resetSeries, setEncoderVals);
        HBox moreButtons = new HBox(inputOrReported, l, printErrors);

        Label welcomeLabel = new Label("Welcome to RobotSim!");
        welcomeLabel.setFont(new Font(30));

        VBox layout1 = new VBox(10);
        layout1.setPadding(new Insets(10));
        HBox finalLayout = new HBox(10);

        Label infoCard = new Label("Hover over options to see more");
        Button helpButton = new Button("Help");
        helpButton.setOnAction(e -> HelpMenu.KABOOM());
        layout1.getChildren().addAll(welcomeLabel, input, infoCard, buttons, moreButtons, output, helpButton);
        RobotState robotState = new RobotState();
        robotState.setX(40);
        robotState.setY(30);
        robotState.setRequestedAngle(0);

        Drawer.drawRobot(robotState);

        finalLayout.getChildren().addAll(layout1, canvas);
        Scene home = new Scene(finalLayout, 1700, Drawer.windowY + 100);
        finalLayout.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE) {
                pyspropgen.reset("X");
                pyspropgen.reset("Y");
            }
        });

        primaryStage.setScene(home);
        primaryStage.setTitle("RobotSim");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    double getDFTF(TextField t) {
        String txt = t.getText();
        return txt.equals("") ? 0 : Double.parseDouble(txt);
    }

    double getDFCB(CheckBox c) {
        return c.isSelected() ? 1 : 0;
    }
}
