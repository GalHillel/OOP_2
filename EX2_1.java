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
