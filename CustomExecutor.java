import java.util.concurrent.*;

public class CustomExecutor {
    // The ExecutorService that will execute the tasks
    private final ExecutorService Executor;
    // A priority queue to store the tasks based on their priority (TaskType)
    private final PriorityBlockingQueue<Runnable> Priorities;

    /**
     * The constructor:
     * * Get the number of available processors
     * * Initialize the Executor with a thread pool of size [processors / 2, processors - 1]
     * * The queue size is set to 300, and the threads will wait for 300 milliseconds before timing out
     */
    public CustomExecutor() {
        int processors = Runtime.getRuntime().availableProcessors();
        Priorities = new PriorityBlockingQueue<>();
        Executor = new ThreadPoolExecutor(processors / 2, processors - 1,
                300, TimeUnit.MILLISECONDS, Priorities);
    }

    /**
     * Submit a task with a given priority (TaskType)
     *
     * @param task
     * @param <V>
     * @return
     */
    public <V> Task<V> submit(Task<V> task) {
        submitTask(task);
        return task;
    }

    /**
     * Create a new Task object with the given Callable and TaskType, and then submit it
     *
     * @param operation
     * @param type
     * @param <T>
     * @return
     */
    public <T> Task<T> submit(Callable<T> operation, TaskType type) {
        final Task<T> task = Task.createTask(operation, type);
        submitTask(task);
        return task;
    }

    /**
     * Submit the task to the Executor by wrapping it in a Future object
     *
     * @param task
     */
    private void submitTask(Task<?> task) {
        final Future future = Executor.submit(task);
        task.setFuture(future);
    }

    /**
     * Get the maximum priority (TaskType) of the tasks currently in the queue
     *
     * @return
     */
    public String getCurrentMax() {
        Task task = (Task) Priorities.peek();
        if (task != null) {
            return task.getType().toString();
        }
        return "There is no task";
    }

    /**
     * Gracefully terminate the Executor
     */
    public void gracefullyTerminate() {
        Executor.shutdown();
        try {
            if (!Executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                Executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
