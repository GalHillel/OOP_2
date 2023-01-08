import java.util.concurrent.*;

public class CustomExecutor {
    private final ExecutorService Executor;
    private final PriorityBlockingQueue<Runnable> Priorities;

    public CustomExecutor() {
        int processors = Runtime.getRuntime().availableProcessors();
        Priorities = new PriorityBlockingQueue<>();
        Executor = new ThreadPoolExecutor(processors / 2, processors - 1,
                300, TimeUnit.MILLISECONDS, Priorities);
    }

    public <V> Task<V> submit(Task<V> task) {
        submitTask(task);
        return task;
    }


    public <T> Task<T> submit(Callable<T> operation, TaskType type) {
        final Task<T> task = Task.createTask(operation, type);
        submitTask(task);
        return task;
    }

    private void submitTask(Task<?> task) {
        final Future future = Executor.submit(task);
        task.setFuture(future);
    }


    public String getCurrentMax() {
        Task task = (Task) Priorities.peek();
        if (task != null) {
            return task.getType().toString();
        }
        return "There is no task";
    }

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