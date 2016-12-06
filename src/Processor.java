import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * This class process the commands and write the outputs to a file
 *
 * @author Broulaye Doumbia
 * @author Cheick Berthe
 * @version 09/05/2016
 */
public class Processor {

    private Commands commands;
    private Hash songHashTable;
    private Hash artistHashTable;
    private MemManager memoryManager;
    private Graph graph;

    /**
     * Constructor that set the fields to provided values
     *
     * @param hashSize
     *            represent the size of the hash table
     * @param numBuff
     *            represent number of buffers
     * @param blockSize
     *            represent the size for a block in the memory pool
     * @param fileName
     *            represent the file to be parsed
     * @param memFile
     *            represent the name of the file that will store the memory pool
     * @throws Exception
     *             exception is thrown if the parsing failed
     */
    public Processor(int hashSize, int numBuff, int blockSize, String fileName,
            String memFile) throws Exception {
        this.commands = Parser.parse(fileName);
        memoryManager = new MemManager(numBuff, blockSize, memFile);
        this.songHashTable = new Hash(hashSize, memoryManager, "song");
        this.artistHashTable = new Hash(hashSize, memoryManager, "artist");
        graph = new Graph(blockSize);

    }

    /**
     * @throws Exception
     *             various exception from nested calls
     */
    public void process() throws Exception {
        try {
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            LinkedList<Command> list = commands.getCommandList();
            for (Command command : list) {
                switch (command.getOp()) {
                    case insert:
                        insert(command.getValues()[0], command.getValues()[1],
                                writer);
                        break;
                    case remove:
                        remove(command.getTyp(), command.getValues()[0],
                                writer);
                        break;
                    case print:
                        printContent(command.getTyp(), writer);
                        break;
                    default:
                        break;

                }
            }
            writer.close();
            memoryManager.closePool();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * print content of given type of database
     *
     * @param type
     *            requested type of database
     * @param writer
     *            used for output
     */
    private void printContent(Type type, PrintWriter writer) {
        switch (type) {
            case Song:
                // writer.print(songHashTable.printTable());
                System.out.print(songHashTable.printTable());
                break;
            case Artist:
                // writer.print(artistHashTable.printTable());
                System.out.print(artistHashTable.printTable());
                break;
            case Block:
                // writer.println(memoryManager.dump());
                System.out.println(memoryManager.dump());
                break;
            case Graph:
                // writer.println(graph.printGraph());
                System.out.println(graph.printGraph());
                break;
            default:
                break;
        }
    }

    /**
     * @param what
     *            type to be removed
     * @param str
     *            string to be removed
     * @param writer
     *            for status report
     */
    private void remove(Type what, String str, PrintWriter writer) {
        str = str.trim();
        Handle removed;
        if (what == Type.Song) {
            removed = songHashTable.removeString(str);
            if (removed != null) {
                graph.removeNode(removed.getVertex());
                System.out.println(
                        "|" + str + "| is removed from the song database.");
            }
            else {
                System.out.println(
                        "|" + str + "| does not exist in the song database.");
            }
        }
        else if (what == Type.Artist) {
            removed = artistHashTable.removeString(str);
            if (removed != null) {
                graph.removeNode(removed.getVertex());
                System.out.println(
                        "|" + str + "| is removed from the artist database.");
            }
            else {
                System.out.println("|" + str
                        + "| does not exist in the artist database.");
            }

        }
    }

    /**
     * Insert
     *
     * @param artist
     *            artist name
     * @param song
     *            song title
     * @param writer
     *            used for status output
     * @throws Exception
     *             exception from inner calls
     */
    private void insert(String artist, String song, PrintWriter writer)
            throws Exception {
        artist = artist.trim();
        song = song.trim();
        //String temp = song.trim();
        Handle artistHandle = artistHashTable.insertString(artist);
        Handle songHandle = songHashTable.insertString(song);
        artistHandle.setVertex(graph.addNode(artistHandle.getVertex()));
        songHandle.setVertex(graph.addNode(songHandle.getVertex()));
        graph.addEdge(artistHandle.getVertex(), songHandle.getVertex());

    }

}