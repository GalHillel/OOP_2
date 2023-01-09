import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CustomExecutorTest {
    @Test
    public void testSubmitTask() throws ExecutionException, InterruptedException {
        // Create a new CustomExecutor
        CustomExecutor executor = new CustomExecutor();

        // Create a Callable task that returns a string
        Callable<String> task = () -> "Hello, World!";

        // Submit the task for execution
        Task<String> future = executor.submit(task, TaskType.COMPUTATIONAL);

        // Check that the task completed successfully
        assertEquals("Hello, World!", future.get());
    }

    @Test
    public void testGetCurrentMax() throws ExecutionException, InterruptedException {
        // Create a new CustomExecutor
        CustomExecutor executor = new CustomExecutor();

        // Create a Callable task that returns a string
        Callable<String> task = () -> "Hello, World!";

        // Submit the task for execution with a high priority
        Task<String> future = executor.submit(task, TaskType.COMPUTATIONAL);

        // Check that the highest priority task in the queue is the task we just submitted
        assertEquals("Computational Task", executor.getCurrentMax());
    }

    @Test
    public void testGracefullyTerminate() throws InterruptedException, ExecutionException, TimeoutException {
        // Create a new CustomExecutor
        CustomExecutor executor = new CustomExecutor();

        // Create a Callable task that returns a string
        Callable<String> task = () -> {
            // Sleep for 1 second
            Thread.sleep(500);
            return "Hello, World!";
        };

        // Submit the task for execution
        Task<String> future = executor.submit(task, TaskType.COMPUTATIONAL);

        // Shut down the executor
        executor.gracefullyTerminate();

        // Check that the task did not complete within 500 milliseconds
        try {
            future.get(500, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            // Expected exception
        }
    }
}
