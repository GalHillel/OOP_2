import java.util.concurrent.*;

public class Task<V> implements Comparable<Task<V>>, Callable<V> {
    // The Future object representing the result of the task
    private Future<V> future;
    // The operation that the task will execute
    private final Callable<V> operation;
    // The type of the task
    private final TaskType Type;

    // Constructor that takes a Callable and a TaskType
    public Task(Callable<V> task, TaskType type) {
        operation = task;
        Type = type;
    }

    /**
     * Constructor that takes only a Callable
     *
     * @param task
     */
    public Task(Callable<V> task) {
        operation = task;
        Type = TaskType.OTHER;
    }

    /**
     * Factory method that creates and returns a new Task object
     *
     * @param task
     * @param taskType
     * @param <T>
     * @return
     */
    public static <T> Task<T> createTask(Callable<T> task, TaskType taskType) {
        return new Task<T>(task, taskType);
    }

    /**
     * Compares the types of two tasks
     *
     * @param t1 the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Task t1) {
        return t1.Type.compareTo(Type);
    }

    /**
     * Returns the result of the task when it is executed with a timeout
     *
     * @param num
     * @param timeUnit
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public V get(long num, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return (V) future.get(num, timeUnit);
    }

    /**
     * Returns the result of the task when it is executed without a timeout
     *
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public V get() throws InterruptedException, ExecutionException {
        return (V) future.get();
    }

    /**
     * Executes the operation that was passed to the Task constructor
     *
     * @return
     * @throws Exception
     */
    public V call() throws Exception {
        return this.operation.call();
    }

    /**
     * Sets the Future object representing the result of the task
     *
     * @param future
     */
    public void setFuture(Future<V> future) {
        this.future = future;
    }

    /**
     * Returns the type of the task
     *
     * @return
     */
    public TaskType getType() {
        return Type;
    }
}
