import java.util.concurrent.*;

public class Task<V> implements Comparable<Task<V>>, Callable<V> {
    private Future<V> future;
    private final Callable<V> operation;
    private final TaskType Type;

    public Task(Callable<V> task, TaskType type) {
        operation = task;
        Type = type;
    }

    public Task(Callable<V> task) {
        operation = task;
        Type = TaskType.OTHER;
    }

    public static <T> Task<T> createTask(Callable<T> task, TaskType taskType) {
        return new Task<T>(task, taskType);
    }

    @Override
    public int compareTo(Task t1) {
        return t1.Type.compareTo(Type);
    }

    public V get(long num, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return (V) future.get(num, timeUnit);
    }

    public V get() throws InterruptedException, ExecutionException {
        return (V) future.get();
    }

    public V call() throws Exception {
        return this.operation.call();
    }

    public void setFuture(Future<V> future) {
        this.future = future;
    }

    public TaskType getType() {
        return Type;
    }
}