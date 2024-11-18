/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

/**
 * The {@code BitmapCompressor} class provides static methods for compressing
 * and expanding a binary bitmap input.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @author Zach Blick
 * @author Noah Persily
 */
public class BitmapCompressor {

    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {

        int count = 1;
        boolean current = BinaryStdIn.readBoolean();
        boolean next = current;

        int i = 0;

        byte temp = 0;
        while (!BinaryStdIn.isEmpty()) {
            while ((next == current) && count < 127) {
                count++;
                next = BinaryStdIn.readBoolean();
            }
            if(current) {
                i = 1;
            } else {
                i = 0;
            }
            temp = (byte) (i << 7);
            temp += count;
            BinaryStdOut.write(temp);
            current = next;

        }


        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {

       while (!BinaryStdIn.isEmpty()) {
           byte b = BinaryStdIn.readByte();
           int length = ((int) b % 7);
           int type = (int) b / (1 << 7);
           for (int i = 0; i < length; i++) {
               BinaryStdOut.write(,1);
           }
       }

        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}