import java.util.concurrent.Callable;

/**
 * The Task class is a combination of three interfaces: Callable, Comparable, and Runnable.
 * It has a field for a TaskType and a Callable, and it has methods for getting the TaskType,
 * comparing two Task objects based on their TaskType priority values, and running the Task.
 * It also has two static factory methods for creating new Task objects.
 *
 * @param <T>
 */
public class Task<T> implements Callable<T>, Comparable<Task<T>>, Runnable {
    private final TaskType type;
    private final Callable<T> callable;

    public Task(TaskType type, Callable<T> callable) {
        this.type = type;
        this.callable = callable;
    }

    public TaskType getType() {
        return type;
    }

    @Override
    public T call() throws Exception {
        return callable.call();
    }

    @Override
    public int compareTo(Task<T> other) {
        return Integer.compare(type.getPriorityValue(), other.getType().getPriorityValue());
    }

    public static <T> Task<T> create(TaskType type, Callable<T> callable) {
        return new Task<>(type, callable);
    }

    public static <T> Task<T> create(Callable<T> callable, TaskType type) {
        return new Task<>(type, callable);
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {

    }
}
