package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Emulator.RobotState;
import sample.Emulator.StartRobotState;
import sample.Graphics.Drawer;
import sample.Graphics.PG;


public class Main extends Application {
    double lastPrintedAngle = 0;

    @Override
    public void start(Stage primaryStage) {

        PG.add("Navx Angle", 0, true);
        PG.add("Accelerometer Y", 0, true);
        PG.add("tx", 0, false);
        PG.add("ty", 0, false);
        PG.add("tv", 0, false);

        Label lMotors = new Label("Click \"Run Tick\" to update.");
        Label rMotors = new Label();
        Label requestedAngle = new Label();

        RobotWrapper.instanciate();

        CheckBox checkBox = new CheckBox("Set Robot Angle to last requested Angle?");
        checkBox.setSelected(true);

        Button runTick = new Button("Run Tick");
        runTick.setOnAction(e -> {
            double targetAngle;
            if(checkBox.isSelected()) {
                targetAngle = lastPrintedAngle;
            } else {
                targetAngle = getDFTF(PG.get("Navx Angle"));
            }
            StartRobotState state = new StartRobotState(targetAngle, getDFTF(PG.get("tv")), getDFTF(PG.get("ty")), getDFTF(PG.get("tx")), getDFTF(PG.get("Accelerometer Y")));
            RobotState result = RobotWrapper.runTick(state);
//            lMotors.setText("Left Motor Output: " + -result.getlMotors());
//            rMotors.setText("Right Motor Output: " + result.getrMotors());
//            requestedAngle.setText("Current Angle: " + PG.get("Navx Angle").getText() + " | Requested Angle: " + result.getRequestedAngle());
//            lastPrintedAngle = result.getRequestedAngle();
            if(checkBox.isSelected()) PG.get("Navx Angle").setText("" + lastPrintedAngle);
        });

        Label welcomeLabel = new Label("Welcome to RobotSim!");
        Label enterParams = new Label("Enter information about the robot.");
        welcomeLabel.setFont(new Font(30));

        VBox layout1 = new VBox(20);
        layout1.setPadding(new Insets(10));
        HBox finalLayout = new HBox(10);

        GridPane grid = PG.getGridPane();
        layout1.getChildren().addAll(welcomeLabel, enterParams, grid, checkBox, runTick, requestedAngle, lMotors, rMotors);

        Canvas canvas = new Canvas(Drawer.windowX, Drawer.windowY);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        System.out.println("Drew the field.");
        Drawer.drawRobot(gc, 100, 100, 0);

        finalLayout.getChildren().addAll(layout1,canvas);

        Scene home = new Scene(finalLayout, 1700,Drawer.windowY+10);

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
        return c.isSelected()? 1 : 0;
    }
}
