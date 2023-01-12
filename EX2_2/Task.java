package EX2_2;

import java.util.concurrent.*;

public class Task<V> implements Callable<V> {

    // The operation that the task will execute
    private final Callable<V> call;

    // The type of the task
    private TaskType type;

    /**
     * Constructor that takes only a Callable
     *
     * @param task Callable EX2_2.Task.
     */
    public Task(Callable<V> task) {
        this.call = task;
        this.type = TaskType.OTHER;
    }

    /**
     * Constructor that takes a Callable and a EX2_2.TaskType.
     *
     * @param task Callable EX2_2.Task.
     * @param type EX2_2.TaskType representing the priority of the task.
     */
    public Task(Callable<V> task, TaskType type) {
        this.call = task;
        this.type = type;
    }


    /**
     * Factory method that creates and returns a new EX2_2.Task object.
     *
     * @param task     Callable EX2_2.Task.
     * @param taskType EX2_2.TaskType representing the priority of the task.
     * @param <V>      Generic type.
     * @return Result value of type <V>.
     */
    public static <V> Task<V> createTask(Callable<V> task, TaskType taskType) {
        return new Task<>(task, taskType);
    }

//    /**
//     * Compares the types of two tasks.
//     *
//     * @param t1 the object to be compared.
//     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater
//     * than the specified object.
//     */
//    @Override
//    public int compareTo(Task t1) {
//        return t1.type.compareTo(type);
//    }

    /**
     * Executes the operation that was passed to the EX2_2.Task constructor
     *
     * @return
     * @throws Exception
     */
    public V call() throws Exception {
        return this.call.call();
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public TaskType getType() {
        return type;
    }


    //
//    /**
//     * Returns the result of the task when it is executed with a timeout.
//     *
//     * @param num
//     * @param timeUnit
//     * @return
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws TimeoutException
//     */
//    public V get(long num, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
//        return (V) future.get(num, timeUnit);
//    }
//
//    /**
//     * Returns the result of the task when it is executed without a timeout
//     *
//     * @return
//     * @throws InterruptedException
//     * @throws ExecutionException
//     */
//    public V get() throws InterruptedException, ExecutionException {
//        return future.get();
//    }
//
//
//    /**
//     * Sets the Future object representing the result of the task
//     *
//     * @param future
//     */
//    public void setFuture(Future<V> future) {
//        this.future = future;
//    }
//
//    /**
//     * Returns the type of the task
//     *
//     * @return
//     */
//    public TaskType getType() {
//        return type;
//    }
//
//    /**
//     * Setter
//     *
//     * @param type
//     */
//    public void setType(TaskType type) {
//        this.type = type;
//    }
//
//    /**
//     * Getter
//     *
//     * @return
//     */
//    public Future<V> getFuture() {
//        return future;
//    }
}
