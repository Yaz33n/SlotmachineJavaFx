package app;

import javax.annotation.Resource;

/**
 * ---- Created By Yazeen Thariq on 2017 October 27th
 *
 * FUNCTIONAL REQUIREMENT No: 2
 *
 * Expected Symbols As Follows:
 * ---------------------------
 * Symbol Name   Symbol Value
 * ---------------------------
 * Seven      ---     7
 * Bell       ---     6
 * Watermelon ---     5
 * Plum       ---     4
 * Lemon      ---     3
 * Cherry     ---     2
 *
 * Final Implementation of the ISymbol Interface.
 * Restricts Overriding & Inheritance.
 */
@Resource
final class Symbol implements ISymbol {

    private String imageUrl; /* Holds the source URL of the Symbol */
    private int imageValue; /* Holds corresponding value of the Symbol */

    @Deprecated
    /*DEFAULT ACCESS*/ Symbol() { }

    /*DEFAULT ACCESS*/ Symbol(String imageUrl, int imageValue) {

        /* Since anyone cannot extend or override methods in this class
           I have used the same validation code in the setter method to
           instantiate the Symbol Objects. */

        setImage(imageUrl);
        setValue(imageValue);
    }

    /**
     * @param url sets the image associated with one of the symbols in a reel.
     *            This expects a valid URL (Cannot be NULL). if not an error message is
     *            logged to the console.
     */
    @Override
    public void setImage(String url) {
        if (url == null)
            System.err.println("Image URL Must not be \'NULL\'");
        else
            this.imageUrl = url; /* Sets the image path to the instance variable. */

    }

    /**
     * @return the source location of the Initialized symbol object.
     */
    @Override
    public String getImage() {
        return this.imageUrl; /* Return the symbol path from the instance variable. */
    }

    /**
     * @param value which sets the value of the symbol as defined above.
     *              This expects valid arguments. if not an error message is
     *              logged to the console.
     */
    @Override
    public void setValue(int value) {
        if (!(value >= 2 && value <= 7)/* Low is 2, Max is 7 */)
            System.err.println("Invalid Arguments Detected!");
        else
            /* Sets the value to the instance variable. */
            this.imageValue = value;

    }

    /**
     * @return the initialized value of the Symbol object.
     */
    @Override
    public int getValue() {
        return this.imageValue; /* Return the symbol value from the instance variable. */
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return imageValue == symbol.imageValue && (imageUrl != null ?
                imageUrl.equals(symbol.imageUrl) : symbol.imageUrl == null);
    }

    @Override
    public int hashCode() {
        int result = imageUrl != null ? imageUrl.hashCode() : 0;
        result = 31 * result + imageValue;
        return result;
    }

    @Override
    public String toString() {
        return "Symbol : " + this.imageUrl /* Actual Symbol Name */
                + " Value : " + this.imageValue /* Symbol Value */;
    }

}
