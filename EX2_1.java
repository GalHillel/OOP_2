import java.io.*;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EX2_1 {

    /**
     * The createTextFiles method creates n text files, each containing the string "HELLO WORLD".
     * The method takes in three parameters:
     * *n is the number of text files to create
     * *seed is a seed for a random number generator used to create the text files
     * The method returns an array of strings, each element of which is the contents of one of the text files.
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] fileContents = new String[n];
        Random rng = new Random(seed);

        for (int i = 0; i < n; i++) {
            // Create a new text file and write "HELLO WORLD" to it
            File file = new File("file_" + i + ".txt");
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("HELLO WORLD");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Store the contents of the file in the fileContents array
            fileContents[i] = "HELLO WORLD";
        }

        return fileContents;
    }

    /**
     * The getNumOfLines method takes in an array of file names and counts the number of lines in each file,
     * by reading the file line by line until the end of the file is reached.
     * The method returns the total number of lines in all the files.
     */
    public static int getNumOfLines(String[] fileNames) {
        int numLines = 0;
        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while (reader.readLine() != null) {
                    numLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numLines;
    }

    /**
     * The getNumOfLinesThreads method works similarly to getNumOfLines,
     * but it uses multiple threads to count the lines in the files.
     * The method creates a LineCounter thread for each file, starts the threads, and then waits for them to finish.
     * The LineCounter class extends the Thread class and overrides the run method to count the lines in a single file.
     * The method returns the total number of lines in all the files.
     */
    public int getNumOfLinesThreads(String[] fileNames) throws InterruptedException {
        LineCounter[] counters = new LineCounter[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            counters[i] = new LineCounter(fileNames[i]);
            counters[i].start();
        }
        int numLines = 0;
        for (LineCounter counter : counters) {
            counter.join();
            numLines += counter.getNumLines();
        }
        return numLines;
    }

    static class LineCounter extends Thread {
        private String fileName;
        private int numLines;

        public LineCounter(String fileName) {
            this.fileName = fileName;
        }

        public int getNumLines() {
            return numLines;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while (reader.readLine() != null) {
                    numLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The getNumOfLinesThreadPool method also uses multiple threads to count the lines in the files,
     * but it uses a thread pool and the Callable interface to do so.
     * The method creates a LineCounterPool object for each file and submits it to an executor service (a thread pool).
     * The LineCounterPool class implements the Callable interface and has a call method that counts the lines in a single file.
     * The method then waits for the results of the Callable tasks and returns the total number of lines in all the files.
     */
    public static int getNumOfLinesThreadPool(String[] fileNames)
            throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(fileNames.length);
        List<Future<Integer>> futures = new ArrayList<>();
        for (String fileName : fileNames) {
            futures.add(executor.submit(new LineCounterPool(fileName)));
        }
        int numLines = 0;
        for (Future<Integer> future : futures) {
            numLines += future.get();
        }
        executor.shutdown();
        return numLines;
    }

    static class LineCounterPool implements Callable<Integer> {
        private String fileName;

        public LineCounterPool(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public Integer call() {
            int numLines = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while (reader.readLine() != null) {
                    numLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return numLines;
        }
    }
}
