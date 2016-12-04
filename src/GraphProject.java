
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * Implementation of a song and artist storage system using a memory manager The
 * class containing the main method.
 *
 * @author Cheick Berthe
 * @author Broulaye Doumbia
 * @version 11/18/2016
 */
public class GraphProject {
    /**
     * @param args
     *            Command line parameters
     */
    public static void main(String[] args) {
        String memFile;
        int numBuffs;
        int blockSize;
        int hashSize;
        String inputfileName;
        String statFile;

        if (args == null || args.length != 6) {
            System.out.println("Usage:java GraphProject {memFile} "
                    + "{numBuffs} {buffSize} {initHashSize} "
                    + "{commandFile} {statFile}");
            return;
        }
        memFile = args[0];
        numBuffs = Integer.parseInt(args[1]);
        blockSize = Integer.parseInt(args[2]);
        hashSize = Integer.parseInt(args[3]);
        inputfileName = args[4];
        statFile = args[5];

        try {
            // initialize processor
            Processor processor = new Processor(hashSize, numBuffs, blockSize,
                    inputfileName, memFile);
            // process commands
            long start = System.currentTimeMillis();
            processor.process();
            long end = System.currentTimeMillis();
            PrintWriter writer = new PrintWriter(
                    new FileOutputStream(new File(statFile), true));
            writer.append((end - start) + "\n");
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}