package app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URISyntaxException;

/**
 * Created by Yazeen Thariq on 2017 October 28th
 */
class SlotMachineGUI extends NodeCreator {

    /*** TotalCredit is initially 10, TotalBetAmount for keep track of User_Bet amounts. */
    private static int currentTotalCredit = 10, currentTotalBetAmount = 0;

    /*** Keep track of the Reel label click status. */
    private static boolean reelLabelClicked, resultsEvaluated, betMaxClicked;

    /*** A Player Entity to save player data. */
    private Player player;

    /*** Three reels for the labels. */
    private Reel reel_1, reel_2, reel_3;

    /* -------------------------- UI COMPONENTS BELOW HERE --------------------------------- */

    /*** A separator to divide between Buttons and the Three Reels */
    private Separator separator;

    /*** Six buttons as per coursework specification */
    private Button buttonBetOne, buttonBetMax, buttonAddCoin, buttonSpin, buttonReset, buttonStats;

    /**
     * Three ImageView objects to set Symbol Icons on top of the Label.
     * This will only used in the Runtime environment.
     * Sits on top of Graphic property of the label.
     */
    private ImageView reel_1_Img, reel_2_Img, reel_3_Img;

    /*** 3 Labels for the reels, 2 for show credit-balance & lbl_User_Bet-amount. */
    private Label label_Reel_1, label_Reel_2, label_Reel_3, lbl_User_Credits_Tag, lbl_User_Credits, lbl_User_Bet,
            lbl_User_Bet_Tag, lbl_Result_Img, lbl_Message_Area, lbl_CopyRight;

    /*DEFAULT GETTER*/
    static boolean isReelLabelClicked() {
        return reelLabelClicked;
    }

    @Deprecated
    SlotMachineGUI() {/*DEFAULT CONSTRUCTOR*/}

    /*CONSTRUCTOR*/
    SlotMachineGUI(Stage primaryStage, Player player) {
        super();

        this.player = player; /*Current Player Object*/
        setScene(primaryStage); /*Setup the scene*/
    }

    /**
     * @param primaryStage is the stage object that is passed by the Main.class
     * @see Main
     * <p>
     * This method sets the layout and its children and sets the scene into the primary stage.
     */
    private void setScene(Stage primaryStage) {

        /* Root layout of the gridPane in GUI class */
        GridPane rootLayout = new GridPane();
        /* Sets the Max Width and Max Height */
        rootLayout.setMaxSize(950, 650);
         /* Adds the column constraints and Row constraints */
        addConstraints(rootLayout);
//        rootLayout.setGridLinesVisible(true);

        buttonInIt(); // initializes the buttons and its styles.
        labelInIt(); // initializes the labels and its styles.

        /* Adds all the children to the scene(root_layout) */
        rootLayout.getChildren().addAll(buttonBetOne, buttonSpin, buttonBetMax, buttonReset, buttonAddCoin, buttonStats,
                label_Reel_1, label_Reel_2, label_Reel_3, lbl_Result_Img, lbl_Message_Area, separator,
                lbl_User_Credits, lbl_User_Bet, lbl_CopyRight, lbl_User_Credits_Tag, lbl_User_Bet_Tag);

        primaryStage.setTitle("Slot Machine Game"); // Title of the stage
        Scene scene = new Scene(rootLayout, 950, 650); // Size of the stage.
        primaryStage.setResizable(true);
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(950);
        primaryStage.setScene(scene);
        setResultOnIcon("slotMachine");
        primaryStage.getIcons().add(new Image("images/legend.png"));
        scene.getStylesheets().addAll("CSS/buttons.css", "CSS/labels.css");
        primaryStage.show();
    }

    /**
     * This method is responsible to instantiate all the declared buttons-
     * -and align the buttons in the right position in the gridPane.
     * <p>
     * This method is also responsible to set Event Handlers to the necessary buttons.
     */
    private void buttonInIt() {

        buttonBetOne = buttonCreator("BET ONE", new Insets(15, 0, -15, 0), 2, 10, 4, 2);
        buttonBetOne.setOnAction(e -> betOne()); // Action Listener

        buttonSpin = buttonCreator("SPIN", new Insets(15, -35, -15, -35), 7, 10, 7, 2);
        buttonSpin.setOnAction(e -> spin()); // Action Listener

        buttonBetMax = buttonCreator("BET MAX", new Insets(15, 0, -15, 0), 15, 10, 4, 2);
        buttonBetMax.setOnAction(e -> betMax()); // Action Listener

        buttonReset = buttonCreator("RESET", 2, 13, 4, 2);
        buttonReset.setOnAction(e -> reset()); // Action Listener

        buttonAddCoin = buttonCreator("ADD COINS", new Insets(0, -35, 0, -35), 7, 13, 7, 2);
        buttonAddCoin.setOnAction(e -> addCoin()); // Action Listener

        buttonStats = buttonCreator("STATISTICS", 15, 13, 4, 2);
        buttonStats.setOnAction(e -> stats()); // Action Listener

    }

    /**
     * This method is responsible to instantiate all the labels and align them
     * in the correct positions in the gridPane.
     */
    private void labelInIt() {

        // Sets a random image on the startup for the three reels.
        reel_1_Img = iconCreator((reel_1 = new Reel()).spin().get(0).getImage());
        reel_2_Img = iconCreator((reel_2 = new Reel()).spin().get(0).getImage());
        reel_3_Img = iconCreator((reel_3 = new Reel()).spin().get(0).getImage());

        /* Returns a label with given attributes. */
        label_Reel_1 = labelCreator(reel_1_Img, 2, 3, 5, 6);
        label_Reel_2 = labelCreator(reel_2_Img, 8, 3, 5, 6);
        label_Reel_3 = labelCreator(reel_3_Img, 14, 3, 5, 6);

        lbl_Result_Img = labelCreator("lblResult", 5, 0, 11, 3);

        lbl_User_Credits_Tag = labelCreator("CREDITS", "lblCreditsLabel", 2, 16, 4, 1);
        lbl_User_Bet_Tag = labelCreator("CURRENT BET", "lblBetLabel", 15, 16, 4, 1);

        lbl_User_Credits = labelCreator(("$" + currentTotalCredit), "lblCredits", 2, 17, 4, 1);
        GridPane.setMargin(lbl_User_Credits, new Insets(-2, 0, 0, 0));

        lbl_User_Bet = labelCreator(("$" + currentTotalBetAmount), "lblBet", 15, 17, 4, 1);
        GridPane.setMargin(lbl_User_Bet, new Insets(-2, 0, 0, 0));

        lbl_Message_Area = labelCreator("lblMessage", 6, 16, 9, 2);
        lbl_CopyRight = labelCreator(Messages.COPYRIGHT.toString(), "lblCopyright", 0, 19, 21, 1);

        // A separator between the three reels and the buttons.
        separator = new Separator();
        separator.setPrefSize(770, 5);
        GridPane.setConstraints(separator, 2, 9, 17, 1);

        label_Reel_1.setOnMouseClicked(e -> onReelLabelClick());
        label_Reel_2.setOnMouseClicked(e -> onReelLabelClick());
        label_Reel_3.setOnMouseClicked(e -> onReelLabelClick());

        setResultOnLabel("Bet one and press SPIN!!");
    }

    /**
     * This method is responsible to enable or disable UI elements.
     *
     * @param condition boolean that contain whether not to disable elements or not.
     */
    private void disableButtons(boolean condition) {
         /* Inverts the value of val variable. */
        reelLabelClicked = !condition;
        // Sets the button text back to 'SPIN'.
        buttonSpin.setText("SPIN");

        // Enables/ Disables all the button according to the condition.
        buttonSpin.setDisable(condition);
        buttonBetOne.setDisable(condition);
        buttonBetMax.setDisable(condition);
        buttonReset.setDisable(condition);
        buttonAddCoin.setDisable(condition);
        buttonStats.setDisable(condition);
    }

    /**
     * This decides what to do when the one of the label is clicked.
     */
    private void onReelLabelClick() {
        disableButtons(false); // Disables the Buttons in the UI
        if (!resultsEvaluated)
            evaluateResults();
    }

    /**
     * This method is responsible to play a sound clip without
     * blocking the UI thread.
     * <p>
     * {@link #evaluateResults()}
     *
     * @param sound name of the sound clip. (To concat with the url)
     */
    private void playSound(String sound) {

        synchronized (this) {
            new Thread(() -> {

                MediaPlayer mediaPlayer = null;

                try {
                    Media clip = new Media(getClass().getResource("/audio/" + sound + ".mp3").toURI().toString());
                    mediaPlayer = new MediaPlayer(clip);
                    mediaPlayer.setVolume(0.5);
                    mediaPlayer.play();
                    Thread.sleep(5000);
                    /**/
                } catch (InterruptedException | URISyntaxException e1) {
                    e1.printStackTrace();

                } finally {
                    assert mediaPlayer != null;
                    mediaPlayer.dispose();
                    //System.out.println("< Clip Played Successfully. >");
                }

            }).start();
        }
    }

    /**
     * This method is responsible to set the result icon according to the win status.
     *
     * @param iconName as a string to concat with the url
     *                 {@link #evaluateResults()}
     */
    private void setResultOnIcon(String iconName) {
        ImageView resultIcon = new ImageView("images/" + iconName + ".png");
        resultIcon.setPreserveRatio(true);
        resultIcon.setSmooth(true);
        lbl_Result_Img.setGraphic(resultIcon);
    }

    /**
     * Sets the text on the message area. This indicates the user what's going on
     *
     * @param resultMessage message to be displayed.
     */
    private void setResultOnLabel(String resultMessage) {
        lbl_Message_Area.setText(resultMessage);
    }

    /**
     * Notify the BET_LABEL and the CREDIT_LABEL to update values.
     */
    private void notifyLabels() {
        lbl_User_Bet.setText("$ " + currentTotalBetAmount);
        lbl_User_Credits.setText("$ " + currentTotalCredit);
    }

    /**
     * Add a coin to to the credit amount.
     */
    private void addCoin() {
        ++SlotMachineGUI.currentTotalCredit;
        notifyLabels();
        //System.out.println("1 Coin Added.");
    }

    /**
     * Reduces one coin from the credit amount -
     * - and adds one to the bet amount.
     */
    private void betOne() {

        if (currentTotalCredit > 0) {
            --SlotMachineGUI.currentTotalCredit;
            ++SlotMachineGUI.currentTotalBetAmount;
            notifyLabels();
            // System.out.println("User clicked bet one button. Coin transaction successful.");
        } else {
            showAlert("You are out of Coins!", Messages.NO_CREDITS.toString());
        }
    }

    /**
     * Reduces three Coins from the credit -
     * amount and adds three to the bet amount.
     * <p>
     * Max click allowed clicks per round is 1.
     */
    private void betMax() {

        if (!betMaxClicked) {
            if (currentTotalCredit > 0 && currentTotalCredit > 2) {
                betMaxClicked = true;
                currentTotalBetAmount += 3;
                currentTotalCredit -= 3;
                notifyLabels();
                //System.out.println("User clicked bet max button. Coin transaction successful.");
            } else {
                if (currentTotalCredit == 0) {
                    showAlert("You are out of Coins!", Messages.NO_CREDITS.toString());
                } else {
                    showAlert("Low On Coins", Messages.BET_MAX_RULE_1.toString());
                }
            }
        } else {
            showAlert("One time only!", Messages.BET_MAX_RULE_2.toString());
        }
    }

    /**
     * Refunds the total current bet amount and -
     * - adds back to the credit amount.
     */
    private void reset() {

        if (SlotMachineGUI.currentTotalBetAmount > 0) {

            betMaxClicked = false;
            currentTotalCredit += currentTotalBetAmount;
            currentTotalBetAmount = 0;
            notifyLabels();
            showAlert("Coins Refunded!", Messages.REFUND.toString());
        }
    }

    /**
     * Starts spinning the three reels.
     */
    private void spin() {

        if (currentTotalBetAmount > 0) {

            resultsEvaluated = false;
            betMaxClicked = false;
            player.setTotalSpins(player.getTotalSpins() + 1);

            // All threads are daemon. Works as background tasks.
            // Before starting the thread must specify what image_view need to be change.
            // For more details see Reel.java class.

            reel_1.setImageView(reel_1_Img);
            Thread r1 = new Thread(reel_1);
            r1.setDaemon(true); // Background Task

            reel_2.setImageView(reel_2_Img);
            Thread r2 = new Thread(reel_2);
            r2.setDaemon(true); // Background Task

            reel_3.setImageView(reel_3_Img);
            Thread r3 = new Thread(reel_3);
            r3.setDaemon(true); // Background Task

            r1.start();
            r2.start();
            r3.start();

            playSound("spin"); // Play the spinning sound effect
            disableButtons(true);
            buttonSpin.setText("CLICK A REEL TO STOP"); // Changes the text of the spin button
            setResultOnIcon("SlotMachine");
            setResultOnLabel("Good Luck!");

        } else {
            showAlert("Bet Credits to play!", Messages.BET_ONE_RULE.toString());
        }
    }

    /**
     * Shows the statistics in a new window.
     */
    private void stats() {
        if (player.getTotalSpins() > 0)
            new Statistics(player);
        else
            showAlert("No Game Data Found!", Messages.NO_DATA.toString());
    }

    /**
     * Calculating the results after the label click.
     */
    private void evaluateResults() {

        if (player.getTotalSpins() > 0)

            if (SlotMachineGUI.reelLabelClicked) {

                resultsEvaluated = true;
                Symbol s1 = reel_1.getLastSymbol();
                Symbol s2 = reel_2.getLastSymbol();
                Symbol s3 = reel_3.getLastSymbol();

                if (s1.equals(s2) && s2.equals(s3)) {
                    logToConsole(s1.getValue() + " " + s2.getValue() + " " + s3.getValue());

                    int winAmount = (s1.getValue() * SlotMachineGUI.currentTotalBetAmount);
                    currentTotalCredit += winAmount;
                    player.setTotalWins(player.getTotalWins() + 1);
                    player.setCreditsWon(player.getCreditsWon() + winAmount);

                    playSound("jackpot");
                    setResultOnIcon("jackpot");

                    setResultOnLabel("You Win! "
                            + winAmount + "$" + " Added ("
                            + s1.getValue() + " x $" + currentTotalBetAmount + ")");
                    player.setTotalBetted(player.getTotalBetted() +  currentTotalBetAmount);
                    currentTotalBetAmount = 0;
                    notifyLabels();
                    showAlert("Jackpot win!", "Congratulations You are amazing! " +
                            "You matched all three reels!!");

                } else if (s1.equals(s2) || s2.equals(s3) || s1.equals(s3)) {

                    logToConsole(s1.getValue() + " " + s2.getValue() + " " + s3.getValue());

                    int multiplier;

                    if (s1.equals(s2) || s1.equals(s3)) {
                        multiplier = s1.getValue();
                    } else {
                        multiplier = s3.getValue();
                    }

                    int winAmount = (multiplier * SlotMachineGUI.currentTotalBetAmount);
                    currentTotalCredit += winAmount;
                    player.setCreditsWon(player.getCreditsWon() + winAmount);
                    player.setTotalPartialWins(player.getTotalPartialWins() + 1);

                    playSound("partialWin");
                    setResultOnIcon("youwin");

                    setResultOnLabel("You Win! "
                            + winAmount + "$" + " Added ("
                            + multiplier + " x $" + currentTotalBetAmount + ")");
                    player.setTotalBetted(player.getTotalBetted() +  currentTotalBetAmount);
                    currentTotalBetAmount = 0;
                    notifyLabels();
                    showAlert("You won with two matched reels!", Messages.MATCHED_TWO_ALERT.toString());

                } else {

                    logToConsole(s1.getValue() + " " + s2.getValue() + " " + s3.getValue());

                    player.setCreditsLost(player.getCreditsLost() + currentTotalBetAmount);
                    player.setTotalLoses(player.getTotalLoses() + 1);
                    player.setTotalBetted(player.getTotalBetted() +  currentTotalBetAmount);
                    currentTotalBetAmount = 0;

                    notifyLabels();
                    playSound("lose");
                    setResultOnIcon("youLose");
                    setResultOnLabel(Messages.MATCHED_NONE.toString());
                }
            }
    }

    /**
     * Logs messages to the console to debug purposes.
     *
     * @param message content.
     */
    private void logToConsole(String message) {
        System.out.println(message);
    }

    private enum Messages {

        MATCHED_TWO_ALERT("You Won With Two Matched Reels. Coins have been added!"),
        MATCHED_NONE("You Lose! Try Again"),
        NO_CREDITS("Add Coins to play. it's this simple, Press Add Coins button as much as you want!"),
        BET_MAX_RULE_1("You Need At Least 3 Coins To Bet Max!"),
        BET_MAX_RULE_2("You can only use BET MAX feature once in a round! "),
        BET_ONE_RULE("You Need At Least 1 Coin To Bet Minimum."),
        REFUND("Your Coins Have Been Refunded From The Total Bet Amount."),
        COPYRIGHT("\u00a9 2017 All Rights Reserved. Developed by Yazeen Thariq"),
        NO_DATA("You Didn't spin the reel. At least 1 Spin required to view statistics.");

        private final String text;

        Messages(String s) {
            this.text = s;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}



