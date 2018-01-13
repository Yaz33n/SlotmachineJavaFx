package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created By Yazeen Thariq on 2017/10/26
 *
 * PROJECT LANGUAGE LEVEL JAVA-8 (1.8.0.131)_JDK64
 */
public class Main extends Application {

    /**
     * @param args commandline arguments.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * @param primaryStage Main window of the application.
     * @throws java.lang.Exception if any error occurred.
     */
    @Override
    public void start(Stage primaryStage) throws java.lang.Exception {
        setScene(primaryStage);
    }

    /**
     * Setups the welcome screen and sets the stage.
     *
     * @param primaryStage {@link #start(Stage)}
     */
    private void setScene(Stage primaryStage) {

        /* Creates the layout */
        GridPane gridLayout = new GridPane();
        // gridLayout.setGridLinesVisible(true);
        new NodeCreator().addConstraints(gridLayout);

        /* Create the label and sets the image */
        Label lbl_Img = new Label();
        ImageView imageView = new ImageView("images/start1.png");
        lbl_Img.setGraphic(imageView);
        GridPane.setConstraints(lbl_Img, 6, 3, 9, 11);

        TextField playerName = new TextField();
        playerName.setId("textBox");
        playerName.setPromptText("Your Name...");
        playerName.setFocusTraversable(false);

        GridPane.setConstraints(playerName, 8, 15, 5, 2);

        /* Adds the play button */
        Button playButton = new Button("PLAY");
        playButton.setId("playButton");

        /* Positioning the button on the gridLayout */
        GridPane.setConstraints(playButton, 8, 17, 5, 2);

         /* Event handler using lambda */
        playButton.setOnAction((e) -> new SlotMachineGUI(primaryStage, new Player(playerName.getText())));

        /* Adds all the child elements to the root layout. */
        gridLayout.getChildren().addAll(lbl_Img, playButton, playerName);

        primaryStage.setResizable(false);
        primaryStage.setMinHeight(650); // Restrict the minimum resizable size.
        primaryStage.setMinWidth(950);
        primaryStage.setTitle("Slot Machine Game");
        Scene scene = new Scene(gridLayout, 950, 650);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("images/legend.png"));
        scene.getStylesheets().add("CSS/buttons.css"); // Adding the stylesheet.
        primaryStage.show();
    }

}
