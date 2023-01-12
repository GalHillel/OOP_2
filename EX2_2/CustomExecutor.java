package EX2_2;

import java.util.ArrayList;
import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {
    // The ExecutorService that will execute the tasks
    private final ArrayList<Object> arr = new ArrayList<>();
    private static final int processors = Runtime.getRuntime().availableProcessors();

    /**
     * The constructor:
     * Get the number of available processors.
     * Initialize the Executor with a thread pool of size [processors / 2, processors - 1].
     * The queue size is set to 300, and the threads will wait for 300 milliseconds before timing out.
     */
    public CustomExecutor() {
        super(processors / 2, processors - 1,
                300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
    }

    /**
     * Submit a task WITHOUT priority TaskType (assign it to OTHER).
     *
     * @param task A Task to submit.
     * @param <V>  The type of the task's result.
     * @return A Future representing pending completion of the task.
     */
    public <V> Future submit(Task<V> task) {
        arr.add(TaskType.OTHER);
        MyFutureTask<V> future = new MyFutureTask<>(task);
        task.setType(TaskType.OTHER);
        this.execute(future);
        return future;
    }

    /**
     * Submit a task WITHOUT priority TaskType (assign it to OTHER).
     *
     * @param call the task to submit
     * @param <V>  The type of the task's result
     * @return A Future representing pending completion of the task.
     */
    @Override
    public <V> Future submit(Callable<V> call) {
        Task<V> task = new Task<>(call);
        return submit(task);
    }

    /**
     * Create a new Task object with the given Callable and TaskType, and then submit it
     */
    public <V> Future submit(Callable<V> call, TaskType type) {
        final Task<V> task = Task.createTask(call, type);
        arr.add(type);
        return submit(task);
    }

//    /**
//     * Submit the task to the Executor by wrapping it in a Future object
//     */
//    private void submitTask(Task<?> task) {
//        final Future future = Executor.submit((Callable) task);
//        task.setFuture(future);
//    }

    /**
     * Get the maximum priority TaskType of the tasks currently in the queue
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
        this.shutdown();
        arr.clear();
        try {
            if (!this.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                this.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
