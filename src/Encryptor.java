import java.util.Arrays;

public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    private int rowsShifted;

    private int columnsShifted;

    /** Constructor*/
    public Encryptor(int r, int c,int rowsShifted, int columnsShifted)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
        this.rowsShifted = rowsShifted % numCols;
        this.columnsShifted = columnsShifted % numRows;
    }
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
        this.rowsShifted = 0;
        this.columnsShifted = 0;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        int index = 0;
        for (int r = 0; r < letterBlock.length; r++){
            for (int c = 0; c < letterBlock[0].length; c++){
                if (index < str.length()){
                    letterBlock[r][c] = str.substring(index, index + 1);
                    index++;
                } else {
                    letterBlock[r][c] = "A";
                }
            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String str = "";
        for (int c = 0; c < letterBlock[0].length; c++){
            for (int r = 0; r < letterBlock.length; r++){
                str += letterBlock[r][c];
            }
        }
        return str;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        shiftRow(rowsShifted);
        shiftColumn(columnsShifted);
        int length = message.length() / (numRows * numCols) + 1;
        if (message.length() % (numRows * numCols) == 0){
            length--;
        }
        String[] splitMessage = new String[length];
        int prevIndex = 0;
        for (int i = 0; i < splitMessage.length - 1; i++){
            splitMessage[i] = message.substring(prevIndex, numCols * numRows + prevIndex);
            prevIndex = numCols * numRows + prevIndex;
        }
        splitMessage[splitMessage.length - 1] = message.substring(prevIndex);
        String str = "";
        for (int i = 0; i < splitMessage.length; i++){
            fillBlock(splitMessage[i]);
            str += encryptBlock();
        }

        return str;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        shiftRow(rowsShifted * -1);
        shiftColumn(columnsShifted * -1);
        String decryptedMessage = "";
        int num = encryptedMessage.length() / (numRows * numCols) + 1;
        if (encryptedMessage.length() % (numRows * numCols) == 0){
            num--;
        }
        while (num > 0){
            String[][] temp = new String[numRows][numCols];
            int strIndex = 0;
            for (int c = 0; c < temp[0].length; c++){
                for (int r = 0; r < temp.length; r++){
                    temp[r][c] = encryptedMessage.substring(strIndex, strIndex + 1);
                    strIndex++;
                }
            }
            encryptedMessage = encryptedMessage.substring(strIndex);

            for (int r = 0; r < temp.length; r++){
                for (int c = 0; c < temp[0].length; c++){
                    decryptedMessage += temp[r][c];
                }
            }
            num--;
        }
        while (decryptedMessage.substring(decryptedMessage.length() - 1).equals("A")){
            decryptedMessage = decryptedMessage.substring(0, decryptedMessage.length() - 1);
        }
        return decryptedMessage;
    }
    public void shiftRow(int numShifts){
        String[][] result = new String[numRows][numCols];
        if (numShifts >= 0){
            for (int r = 0; r < letterBlock.length; r++){
                for (int c = 0; c < letterBlock[0].length; c++){
                    if (c < letterBlock[0].length - numShifts){
                        result[r][c + numShifts] = letterBlock[r][c];
                    } else {
                        result[r][c - numShifts + 1] = letterBlock[r][c];
                    }
                }
            }
        } else {
            for (int r = 0; r < letterBlock.length; r++){
                for (int c = 0; c < letterBlock[0].length; c++){
                    if (c >= numShifts * -1){
                        result[r][c - (numShifts * -1)] = letterBlock[r][c];
                    } else {
                        result[r][c + (numShifts * -1) - 1] = letterBlock[r][c];
                    }
                }
            }
        }

        for (int r = 0; r < result.length;r ++){
            for (int c = 0; c < result[0].length; c++){
                letterBlock[r][c] = result[r][c];
            }
        }
    }
    public void shiftColumn(int numShifts){
        String[][] result = new String[numRows][numCols];
        if (numShifts >= 0){
            for (int c = 0; c < letterBlock[0].length; c++){
                for (int r = 0; r < letterBlock.length; r++){
                    if (r < letterBlock.length - numShifts){
                        result[r + numShifts][c] = letterBlock[r][c];
                    } else {
                        result[r - numShifts + 1][c] = letterBlock[r][c];
                    }
                }
            }
        } else {
            for (int c = 0; c < letterBlock[0].length; c++){
                for (int r = 0; r < letterBlock.length; r++){
                    if (r >= numShifts * -1){
                        result[r - (numShifts * -1)][c] = letterBlock[r][c];
                    } else {
                        result[r + (numShifts * -1) - 1][c] = letterBlock[r][c];
                    }
                }
            }
        }

        for (int r = 0; r < result.length;r ++){
            for (int c = 0; c < result[0].length; c++){
                letterBlock[r][c] = result[r][c];
            }
        }
    }
    public String toString(){
        for (String[] rows : letterBlock){
            System.out.println(Arrays.toString(rows));
        }
        return "";
    }
}