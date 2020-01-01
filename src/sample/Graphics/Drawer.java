package sample.Graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Emulator.RobotState;
import team5115.autotools.Instruction;


public class Drawer {
    public static final int windowX = 1298;
    public static final int windowY = 638;
    static final int robotDim = 80;
    final static Image image = new Image("/frcField.jpeg");
    static GraphicsContext gc;

    public static void init(GraphicsContext gc) {
        Drawer.gc = gc;
    }

    private static void drawField() {
        gc.clearRect(0,0,windowX,windowY);
        gc.drawImage(image, 205, 34, 1110, 547, 20, 20, windowX, windowY);
    }

    public static void drawRobot(RobotState robotState) {
        gc.setFill(Color.PAPAYAWHIP);
        double rotation = robotState.getRequestedAngle();
        double x = robotState.getX();
        double y = robotState.getY();

        double temp = y;
        y = x*2 + 20;
        x = temp*2 + 20;

        drawField();

        rotation = Math.toRadians(rotation);

        double[] xPoints = new double[]{
                x + robotDim * Math.sin(rotation + Math.PI / 4),
                x + robotDim * Math.sin(rotation + 3 * Math.PI / 4),
                x + robotDim * Math.sin(rotation + 5 * Math.PI / 4),
                x + robotDim * Math.sin(rotation + 7 * Math.PI / 4)};

        double[] yPoints = new double[]{
                y + robotDim * Math.cos(rotation + Math.PI / 4),
                y + robotDim * Math.cos(rotation + 3 * Math.PI / 4),
                y + robotDim * Math.cos(rotation + 5 * Math.PI / 4),
                y + robotDim * Math.cos(rotation + 7 * Math.PI / 4)};

        gc.fillPolygon(xPoints, yPoints, 4);
        drawStep(robotState.getCurrentStep());
    }

    public static void drawStep(Instruction instruction) {
        if (instruction == null) {
            return;
        }
        double x = instruction.getX();
        double y = instruction.getY();
        double temp = y;
        y = x*2 + 20;
        x = temp*2 + 20;

        switch (instruction.getType()) {
            case "Location":
                gc.setFill(Color.BLUE);
                gc.fillRect(x-20, y-20, 40, 40);

                break;
            case "Portal":
                break;
            case "Cube":
                gc.setFill(Color.YELLOW);
                gc.fillRect(x, y, 10, 10);
                break;
            default:
                try {
                    throw new Exception("Unknown Instruction Type for Drawing Instruction.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
