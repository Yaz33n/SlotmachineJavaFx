package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * This class is created for avoid code duplication -
 * while creating labels, buttons, imageViews.
 *
 * Some methods are overloaded.
 *
 * Access to all the method are default.
 */
class NodeCreator {

    /**
     * @param img    ImageView need to set on the label. (Mostly needed for the Reels)
     * @param params are the GridPane constraints.
     *               (Column Index, Row Index, Column Span, Row Span) As follows.
     * @return label that newly created
     */
    Label labelCreator(ImageView img, int... params) {

        Label label = new Label(); // Creates a new label
        label.setId("reelLabels"); // Sets the id for the label.
        label.setGraphic(img); // Sets the graphic of that image.
        /* Aligns the element in the gridPane layout. */
        GridPane.setConstraints(label, params[0], params[1], params[2], params[3]);

        return label;
    }

    /**
     * @param text   Is the name of the label (Label Text)
     * @param id     is the CSS ID. (For styling purposes)
     * @param params are the GridPane constraints.
     *               (Column Index, Row Index, Column Span, Row Span) As follows.
     * @return label that newly created
     */
    Label labelCreator(String text, String id, int... params) {

        Label label = new Label();
        if (text != null) label.setText(text);
        label.setId(id);
        GridPane.setConstraints(label, params[0], params[1], params[2], params[3]);

        return label;
    }

    /**
     * @param id     is the CSS ID. (For styling purposes)
     * @param params are the GridPane constraints.
     *               (Column Index, Row Index, Column Span, Row Span) As follows.
     * @return label that newly created
     */
    Label labelCreator(String id, int... params) {

        Label label = new Label();
        label.setId(id);
        GridPane.setConstraints(label, params[0], params[1], params[2], params[3]);

        return label;
    }

    /**
     * @param gridPane add the rows and column constraints to the passed gridPane.
     */
    void addConstraints(GridPane gridPane) {

        for (int i = 0; i < 21; i++) {
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.SOMETIMES);
            constraints.setMinWidth(10.00);
            constraints.setPrefWidth(100.00);
            gridPane.getColumnConstraints().add(constraints);
        }

        for (int i = 0; i < 20; i++) {
            RowConstraints constraints = new RowConstraints();
            constraints.setVgrow(Priority.SOMETIMES);
            constraints.setMinHeight(10.00);
            constraints.setPrefHeight(30.00);
            gridPane.getRowConstraints().add(constraints);
        }

        gridPane.setPadding(new Insets(5));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(2);
        gridPane.setHgap(2);
    }

    /**
     * @param text   Is the name of the Button (Button Text)
     * @param insets Sets the margin in the gridPane layout.
     * @param params are the GridPane constraints.
     *               (Column Index, Row Index, Column Span, Row Span) As follows.
     * @return the newly created Button
     */
    Button buttonCreator(String text, Insets insets, int... params) {

        Button button = new Button(text);
        GridPane.setMargin(button, insets);
        GridPane.setConstraints(button, params[0], params[1], params[2], params[3]);

        return button;
    }

    /**
     * @param text   Is the name of the Button (Button Text)
     * @param params are the GridPane constraints.
     *               (Column Index[0], Row Index[1], Column Span[2], Row Span[3]) As follows.
     * @return the newly created Button
     */
    Button buttonCreator(String text, int... params) {

        Button button = new Button(text);
        GridPane.setConstraints(button, params[0], params[1], params[2], params[3]);

        return button;
    }

    /**
     * @param url of the image
     * @return newly created imageView Object.
     */
    ImageView iconCreator(String url) {

        ImageView imageView = new ImageView(url);
        imageView.setFitHeight(150);
        imageView.setFitWidth(130);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        return imageView;
    }

    /**
     * Shows a alert box to the user if invoked.
     *
     * @param header  title of the message
     * @param content info of the message
     */
    void showAlert(String header, String content) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SlotMachine - Information");
        ((Stage) alert.getDialogPane().getScene().getWindow())
                .getIcons().add(new Image("images/legend.png"));
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
