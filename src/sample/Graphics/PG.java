package sample.Graphics;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class PG {

    static GridPane gridPane = new GridPane();
    static ArrayList<TextField> textFields = new ArrayList<>();
    static ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    static int currentRow = 0;

    public static GridPane getGridPane() {
        return gridPane;
    }

    public static void add(String name, double defaultVal, boolean isChecked) {

        TextField textField = new TextField(String.valueOf(defaultVal));
        textFields.add(textField); //adds the text field to the array list.
        CheckBox checkBox = new CheckBox(name);
        checkBoxes.add(checkBox);

        if(!isChecked) {
            textField.setEditable(false);
            textField.setStyle("-fx-text-inner-color: grey;");
        } else {
            checkBox.setSelected(true);
        }

        checkBox.setOnAction(actionEvent -> {
            if(checkBox.isSelected()) {
                textField.setEditable(true);
                textField.setStyle("-fx-text-inner-color: black;");
            } else {
                textField.setText(String.valueOf(defaultVal));
                textField.setEditable(false);
                textField.setStyle("-fx-text-inner-color: grey;");
            }
        });
        gridPane.add(checkBox, 0, currentRow);
        gridPane.add(textField, 1, currentRow);
        currentRow++;
    }

    public static TextField get(String name) {
        //returns the text field with the given name
        for (int i = 0; i < checkBoxes.size(); i++) {
            CheckBox x = checkBoxes.get(i);
            if (x.getText().equals(name)) return textFields.get(i);
        }
        System.out.println("You fucked up. Tryna get " + name + " and we couldn't find it.");
        return new TextField("Null");
    }
}
