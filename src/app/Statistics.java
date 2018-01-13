package app;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

class Statistics extends NodeCreator {

    /*Current Player Object*/
    private Player player;
    /*A new Stage*/
    private Stage stage;
    /*GridLayout for child nodes*/
    private GridPane layout;
    /*Labels to display statistics summary*/
    private Label title, wins, loses, partialWins, creditsLost, creditsWon, totSpins, result, nettedCoins, average;
    /*A separator between the chart and the labels*/
    private Separator separator;
    /*Two buttons for save and exit the current stage*/
    private Button saveButton, closeButton, payoutBtn;

    /**
     * This is the only constructor that is IMPLEMENTED in the statistics class.
     *
     * @param player is the object that is needed to be summarize.
     */
    Statistics(Player player) {
        super();
        this.player = player;
//        System.out.println(player.getTotalBetted());
        this.stage = new Stage();
        initializeStage();
    }

    /**
     * Creates a new stage(Window) to display the stats
     */
    private void initializeStage() {
        setLayout();
        Scene scene = new Scene(layout, 950, 650);
        scene.getStylesheets().addAll("CSS/buttons.css", "CSS/labels.css");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Game Statistics.");
        stage.getIcons().add(new Image("images/legend.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Sets the layout(scene) for the statistics stage.
     */
    private void setLayout() {

        layout = new GridPane();
        layout.setPrefSize(950, 650);
//        layout.setGridLinesVisible(true);

        addConstraints(layout);
        createLabels();
        createButtons();
        layout.getChildren().addAll(title, wins, loses, partialWins, saveButton, closeButton,
                createChart(), result, creditsWon, creditsLost, totSpins, nettedCoins, average, separator, payoutBtn);
    }

    /**
     * Creates the pie chart with data parameters.
     *
     * @return created chart.
     */
    private PieChart createChart() {

        /*
            Pie chart was selected because for the user it is easy to understand and to -
            see the results clearly with different colors. Since the representational data only
            shows about wins, partial wins, loses it is easy to create as well. Moreover, other
            data is displayed using labels to the user.
         */

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Wins", this.player.getTotalWins()),
                new PieChart.Data("Loses", this.player.getTotalLoses()),
                new PieChart.Data("Partial Wins", this.player.getTotalPartialWins()));

        // Construct a new PieChart with the given data.
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("GAME STATISTICS");
        chart.setId("chart");
        chart.setLabelLineLength(10);
        GridPane.setConstraints(chart, 9, 3, 11, 11);

        return chart;
    }

    /**
     * Initializes the labels
     */
    private void createLabels() {

        title = labelCreator("SUMMARY OF THE GAMEPLAY", "statsTitle", 5, 0, 11, 2);
        wins = labelCreator("Total Wins: " + player.getTotalWins(), "lblStats", 1, 3, 6, 1);
        loses = labelCreator("Total Loses: " + player.getTotalLoses(), "lblStats", 1, 5, 6, 1);
        partialWins = labelCreator("Partial Wins: " + player.getTotalPartialWins(), "lblStats", 1, 7, 6, 1);
        creditsLost = labelCreator("Credits Lost: $" + player.getCreditsLost(), "lblStats", 1, 9, 6, 1);
        creditsWon = labelCreator("Credits Won: $" + player.getCreditsWon(), "lblStats", 1, 11, 6, 1);
        totSpins = labelCreator("Total Spins: " + player.getTotalSpins(), "lblStats", 1, 13, 6, 1);
        nettedCoins = labelCreator("Coins Netted: $" + Player.coinsNettedPerGame(player), "lblStats", 1, 15, 6, 1);
        average = labelCreator("Average: " + Player.calculateAverage(player) + "%", "lblSAvg", 2, 17, 4, 2);
        result = labelCreator(Player.getFeedback(Player.calculateAverage(player)), "lblSResult", 9, 14, 11, 2);

        separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setPrefSize(2, 400);
        GridPane.setMargin(separator, new Insets(20, 0, 0, 20));
        GridPane.setConstraints(separator, 7, 2, 1, 16);
    }

    /**
     * Initializes the buttons and adding event handlers.
     */
    private void createButtons() {

        saveButton = buttonCreator("Save Data", 9, 17, 3, 1);
        closeButton = buttonCreator("Close This", 13, 17, 3, 1);
        payoutBtn = buttonCreator("Payout", 17, 17, 3, 1);
        closeButton.setOnAction(e -> stage.close());

        saveButton.setOnAction(e -> new WriteToFile(
                "Statistics",
                toString() + player.toString()
        ));

        payoutBtn.setOnAction(e -> new WriteToFile(
                "Payout Info",
                payoutDetails()
        ));
    }

    private String payoutDetails() {

        return "DESCRIPTION OF THE MODEL\n\n" +
                "The average return of this Slot Machine is the dot product " +
                "of the above probabilities and their respective Payoffs.\nSo, " +
                "for every $1 bet amount the expected amount of payout is " +
                "$0.8883 Which means ~ 88% payout on $1 nomination.\n\n" +

                String.format("%-15s %-15s %-15s %-15s%n%s%n", "Symbol", "Value", "Probability of 2", "Probability of 3",
                        "------------------------------------------------------------------------------------") +
                String.format("%-15s %-15s %-15s %-15s%n", "Cherry", "2", calcProb(2, 2), calcProb(3, 2)) +
                String.format("%-15s %-15s %-15s %-15s%n", "Lemon", "3", calcProb(2, 3), calcProb(3, 3)) +
                String.format("%-15s %-15s %-15s %-15s%n", "Plum", "4", calcProb(2, 4), calcProb(3, 4)) +
                String.format("%-15s %-15s %-15s %-15s%n", "Watermelon", "5", calcProb(2, 5), calcProb(3, 5)) +
                String.format("%-15s %-15s %-15s %-15s%n", "Bell", "6", calcProb(2, 6), calcProb(3, 6)) +
                String.format("%-15s %-15s %-15s %-15s%n", "RedSeven", "7", calcProb(2, 7), calcProb(3, 7)) +
                String.format("%n%-15s %-15s %-15s %-15s%n%n%n", "--------", "Total", "0.756", "0.131") +

                "The average return of this Slot Machine is the dot product of the above\n" +
                "probabilities and their respective Payoffs. So, for every $1 bet amount the\n" +
                "expected amount of payout is $0.8883 Which means ~ 88% payout on $1 nomination.\n\n" +
                "--------- PAYOUT \uF0E0 0.756 + 0.131 = 0.887 * 100 ~ 88.70% ---------\n\n" +

                "Your average bet amount per each round: " + "$" + player.getTotalBetted() / player.getTotalSpins() + "\n" +
                "Your expected payout is: $" + String.format("%.3f", (player.getTotalBetted() / player.getTotalSpins()) * 0.887);

    }

    private String calcProb(int r, double s) {
        return String.format("%.3f", ((Math.pow(1d / 6d, r) * s) + 0.001));
    }

    @Override
    public String toString() {
        return " ------------------- SLOTMACHINE GAME - PLAYER STATISTICS ------------------- \n\n" +
                "Player Average ----->  " + Player.calculateAverage(player) + "% \n" +
                "Feedback       ----->  " + Player.getFeedback(Player.calculateAverage(player)) + "\n";
    }

    private class WriteToFile {

        /**
         * @param data which comes from the statistics class as a String object.
         */
        private WriteToFile(String dirName, String data) {
            writeChunk(dirName, data); // Pass the data to the method.
        }

        /**
         * @param data String representation of the data.
         */
        private void writeChunk(String dirName, String data) {
            Platform.runLater(() -> {
                //Creates the directory if not exist.
                File dir = new File(dirName);

                if (!dir.exists()) dir.mkdir() /*Result Ignored*/;

                // Creates a new text file with the current date and time.
                String path = dir.getAbsolutePath() + "\\" + getTimeStamp() + ".txt";

                try {
                    // Writes data to the file.
                    Files.write(Paths.get(path), data.getBytes());
                    // Displays a Alert message to the user if I/O Operation was successful.
                    showAlert("INFORMATION", "Data Saved to a text file " +
                            "in the directory named today's date and time.");
                    System.out.println("Saved In the directory.\n\n" + data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        /**
         * This method will generate the current date and time.
         *
         * @return current date and time.
         */
        @Resource
        private String getTimeStamp() {
            return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        }

    }

}
