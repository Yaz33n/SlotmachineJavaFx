package app;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created By Yazeen Thariq on 2017 October 29th
 *
 * Actual Reel which holds all the images and values.
 * Implemented the Runnable Interface and run method is implemented.
 */
class Reel implements Runnable {

    private List<Symbol> symbolList = new ArrayList<>(6);

    private volatile ImageView imageView; // ImageView of the Reel
    private volatile Symbol lastSymbol; // latest symbol that is assigned for the reel.

    /*DEFAULT ACCESS*/
    Reel() {
        super();
        Collections.addAll(this.symbolList,
                createSymbol("images/redseven.png", 7),
                createSymbol("images/bell.png", 6),
                createSymbol("images/watermelon.png", 5),
                createSymbol("images/plum.png", 4),
                createSymbol("images/lemon.png", 3),
                createSymbol("images/cherry.png", 2)
        );

        this.spin(); /*SPINS THE LIST AFTER CREATION*/
    }

    private static Symbol createSymbol(String url, Integer val) {
        return new Symbol(url, val);
    }

    /**
     * @return shuffled symbol list.
     */
    /*DEFAULT*/ List<Symbol> spin() { /*Shuffles the list and return the list*/
        Collections.shuffle(this.symbolList);
        return this.symbolList;
    }

    @Override
    public void run() {
        /*Random number generator*/
        final Random random = new Random();

        while (!SlotMachineGUI.isReelLabelClicked()) {
            Platform.runLater(() -> {
                if (!SlotMachineGUI.isReelLabelClicked() /* Really check if clicked */) {
                    try {
                        /*Random Number Generating*/
                        final int randomNumber = random.nextInt(6);
                        /*Gets The Randomly Picked Image*/
                        this.lastSymbol = symbolList.get(randomNumber);
                        /*Sets the symbol to the same image_view*/
                        this.imageView.setImage(new Image(symbolList.get(randomNumber).getImage()));

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            /*This code block is outside because, Platform.runLater() will execute the Runnable
             block on the _Event Dispatching_ Thread, which means it will block the UI if I add the
             Thread.sleep in it.*/
            try {
                Thread.sleep(70); // Thread sleeps for 70 nano seconds
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /* ----------------------------- GETTERS & SETTERS ----------------------------- */

    /*DEFAULT*/ Symbol getLastSymbol() {
        return lastSymbol;
    }

    /*DEFAULT*/ void setImageView(ImageView imageView) {
        if (imageView != null)
            this.imageView = imageView;
        else
            System.err.println("Null ImageView Object Detected!");
    }
}
