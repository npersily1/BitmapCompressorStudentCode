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


        int numRepeated = 0;
        int character = BinaryStdIn.readInt(1);


        while (!BinaryStdIn.isEmpty()) {
            int next = BinaryStdIn.readInt(1);
            if (next == character) {
                if (numRepeated == 127) {
                    BinaryStdOut.write(character, 1);
                    BinaryStdOut.write(numRepeated,7);
                    numRepeated = 0;
                }
                numRepeated++;
            } else {
                BinaryStdOut.write(character, 1);
                BinaryStdOut.write(numRepeated,7);
                numRepeated = 1;
                character = next;
            }

        }

        BinaryStdOut.close();

    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {

        while (!BinaryStdIn.isEmpty()) {
            int type = BinaryStdIn.readInt(1);
            int length = BinaryStdIn.readInt(7);

            for (int i = 0; i < length; i++) {
                BinaryStdOut.write(type, 1);
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


        if (args[0].equals("-")) {
            compress();

        } else if (args[0].equals("+")) {
            expand();

        } else {

            throw new IllegalArgumentException("Illegal command line argument");

        }
    }
}