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

        // Initialize counter and assume the first letter is a zero
        int numRepeated = 0;
        int character = 0;

        // While there is still stuff to read
        while (!BinaryStdIn.isEmpty()) {
            // Read the next letter
            int next = BinaryStdIn.readInt(1);
            // If the streak continues
            if (next == character) {
                // If you have gone over the bit limit
                if (numRepeated == 255) {
                    // Write  255 and then 0 and then reset count
                    BinaryStdOut.write(numRepeated, 8);
                    BinaryStdOut.write(0, 8);
                    numRepeated = 0;
                }
                numRepeated++;
            } else {
                // If the streak is over write the streak reset the count to one and change the character
                BinaryStdOut.write(numRepeated, 8);
                numRepeated = 1;
                character = next;
            }

        }
        BinaryStdOut.write(numRepeated, 8);
        BinaryStdOut.close();

    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {

        int[] types = {0, 1};
        int count = 0;
        // While their stuff to read
        while (!BinaryStdIn.isEmpty()) {
            // Read the length
            int length = BinaryStdIn.readInt(8);
            // For the length write the current type
            for (int i = 0; i < length; i++) {
                BinaryStdOut.write(types[count % 2], 1);
            }
            // Change the type
            count++;
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


        if (args[0].equals("-")) {
            compress();

        } else if (args[0].equals("+")) {
            expand();

        } else {

            throw new IllegalArgumentException("Illegal command line argument");

        }
    }
}