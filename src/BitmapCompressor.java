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
        int current = BinaryStdIn.readInt(1);
        int next = current;


        byte temp = 0;
        System.out.println("banana");
        while (!BinaryStdIn.isEmpty()) {
            while ((next == current) && count < 127) {
                count++;
                next = BinaryStdIn.readInt(1);
            }

            temp = (byte) (current << 7);
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
        System.out.println("hello");

        for (int i = 0; i < 2; i++) {
            System.out.println("hi ");
        }

        if (args[0].equals("-")) {
            compress();
            System.out.println("kevin");
        } else if (args[0].equals("+")) {
            expand();
            System.out.println("bob");
        } else {
            System.out.println("stuart");
            throw new IllegalArgumentException("Illegal command line argument");

        }
    }
}