/**
 * {Project Description Here}
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The class containing the main method.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */

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
     *     Command line parameters
     */
    public static void main(String[] args) {
        String memFile;
        int numBuffs;
        int blockSize;
        int hashSize;
        String inputfileName;
        String statFile;

        if (args == null || args.length != 6) {
            System.out.println("Usage:java GraphProject {memFile} " +
                    "{numBuffs} {buffSize} {initHashSize} " +
                    "{commandFile} {statFile}");
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
            Processor processor =
                    new Processor(hashSize, numBuffs, blockSize, inputfileName, memFile);
            // process commands
            processor.process();
            File file = new File("output.txt");

            // create a scanner object
            Scanner reader;

            try {
                reader = new Scanner(file);

                String token;

                // while the file got for line execute the following commands
                while (reader.hasNextLine()) {

                    token = reader.nextLine();
                    System.out.println(token);
                }
                reader.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}