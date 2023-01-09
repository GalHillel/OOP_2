import java.util.ArrayList;
import java.util.concurrent.*;

public class CustomExecutor {
    // The ExecutorService that will execute the tasks
    private final ExecutorService Executor;
    private final ArrayList<Object> arr = new ArrayList<>();

    /**
     * The constructor:
     * * Get the number of available processors
     * * Initialize the Executor with a thread pool of size [processors / 2, processors - 1]
     * * The queue size is set to 300, and the threads will wait for 300 milliseconds before timing out
     */
    public CustomExecutor() {
        int processors = Runtime.getRuntime().availableProcessors();
        // A priority queue to store the tasks based on their priority (TaskType)
        PriorityBlockingQueue<Runnable> priorities = new PriorityBlockingQueue<>();
        Executor = new ThreadPoolExecutor(processors / 2, processors - 1,
                300, TimeUnit.MILLISECONDS, priorities);
    }

    /**
     * Submit a task with a given priority (TaskType)*
     */
    public <V> Task<V> submit(Task<V> task) {
        submitTask(task);
        arr.add(TaskType.OTHER);
        return task;
    }

    /**
     * Create a new Task object with the given Callable and TaskType, and then submit it
     */
    public <T> Task<T> submit(Callable<T> operation, TaskType type) {
        final Task<T> task = Task.createTask(operation, type);
        submitTask(task);
        arr.add(type);
        return task;
    }

    /**
     * Submit the task to the Executor by wrapping it in a Future object
     */
    private void submitTask(Task<?> task) {
        final Future future = Executor.submit(task);
        task.setFuture(future);
    }

    /**
     * Get the maximum priority (TaskType) of the tasks currently in the queue
     */
    public String getCurrentMax() {
        if (arr.contains(TaskType.COMPUTATIONAL))
            return TaskType.COMPUTATIONAL.toString();
        else if (arr.contains(TaskType.IO))
            return TaskType.IO.toString();
        else if (arr.contains(TaskType.OTHER))
            return TaskType.OTHER.toString();
        return "There is no task";
    }

    /**
     * Gracefully terminate the Executor
     */
    public void gracefullyTerminate() {
        Executor.shutdown();
        arr.clear();
        try {
            if (!Executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                Executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
