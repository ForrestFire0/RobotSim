package sample.Graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Emulator.RobotState;

public class Drawer {
    public static final int windowX = 1298;
    public static final int windowY = 638;
    static final int robotDim = 80;

    private static void drawField(GraphicsContext gc) {
        Image image = new Image("/frcField.jpeg");
        gc.drawImage(image, 205, 34, 1110, 547, 20, 20, windowX, windowY);
    }

    public static void drawRobot(GraphicsContext gc, RobotState robotState) {
        double rotation = robotState.getRequestedAngle();
        double x = robotState.getX();
        double y = robotState.getY();
        double temp = y;

        y = x*2 + 20;
        x = temp*2 + 20;

        drawField(gc);
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
    }
}
