package app;

/**
 * Created By Yazeen Thariq on 2017 October 27th
 *
 * This Interface Represents the common functionality Which Must be implemented in the Symbol class.
 */
interface ISymbol {

    /**
     * @param url sets the image associated with one of the symbols in a reel,
     *            represented by the Symbol Class implementing this Interface.
     * @see Symbol
     */
    void setImage(String url);

    /**
     * @return the source location of the symbol(Image).
     */
    String getImage();

    /**
     * @param value which sets the value of the symbol as defined in the specification.
     * @see Symbol
     */
    void setValue(int value);

    /**
     * @return the value of the Symbol.
     * @see Symbol
     */
    int getValue();


}
