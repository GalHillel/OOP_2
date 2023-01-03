import java.util.concurrent.Callable;

public class Task<T> implements Callable<T>, Comparable<Task<T>> {
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
}
