import java.util.concurrent.*;

/**
 * The CustomExecutor class is an executor that asynchronously computes Task instances.
 * It has a thread pool and a priority queue for holding the tasks.
 * The CustomExecutor class has methods for submitting a Task instance,
 * or a Callable operation and a TaskType, which will be used to create a Task instance.
 * The CustomExecutor class also has a method for getting the maximum priority in the queue
 */
public class CustomExecutor {
    private final Executor executor;
    private final PriorityBlockingQueue<Task<?>> queue;
    private int maxPriority;

    public CustomExecutor() {
        int numProcessors = Runtime.getRuntime().availableProcessors();
        int numThreads = numProcessors / 2;

        executor = Executors.newFixedThreadPool(numThreads);
        queue = new PriorityBlockingQueue<>();
    }

    public void submitTask(Task<?> task) {
        maxPriority = Math.max(maxPriority, task.getType().getPriorityValue());
        queue.offer(task);
    }

    public <T> void submitTask(TaskType type, Callable<T> task) {
        Task<T> t = Task.create(type, task);
        maxPriority = Math.max(maxPriority, t.getType().getPriorityValue());
        queue.offer(t);
    }

    public <T> void submitTask(Callable<T> task, TaskType type) {
        Task<T> t = Task.create(type, task);
        maxPriority = Math.max(maxPriority, t.getType().getPriorityValue());
        queue.offer(t);
    }

    public int getMaxPriority() {
        return maxPriority;
    }

    public void shutdown() {
        while (!queue.isEmpty()) {
            Task<?> task = queue.poll();
            executor.execute(task);
        }
        ((ExecutorService) executor).shutdown();
    }
}
