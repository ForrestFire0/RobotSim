package sample.Graphics;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class HelpMenu {
    static public void KABOOM() {

        Label title = new Label("Help");
        title.setFont(new Font(30));

        Label text = new Label("Click on the game field to set X and Y of the Robot\nClick ESCAPE to reset the X and Y values to the way they were before.\nHover over options to see more.");
        Label me = new Label("Made by Forrest Milner for himself, Team 5115, and anyone else!");
        text.setFont(new Font(20));
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(title,text,me);

        Scene scene = new Scene(layout, 600,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Help");
        stage.showAndWait();
    }
}
