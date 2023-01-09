import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.*;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void testCreateTask() {
        Callable<Integer> task = () -> 1;
        TaskType taskType = TaskType.IO;
        Task<Integer> t = Task.createTask(task, taskType);
        assertEquals(taskType, t.getType());
    }

    @Test
    void testCompareTo() {
        Callable<Integer> task1 = () -> 1;
        Callable<Integer> task2 = () -> 2;
        TaskType taskType1 = TaskType.IO;
        TaskType taskType2 = TaskType.COMPUTATIONAL;
        Task<Integer> t1 = Task.createTask(task1, taskType1);
        Task<Integer> t2 = Task.createTask(task2, taskType2);
        assertEquals(-1, t1.compareTo(t2));
    }

    @Test
    void testGet() throws InterruptedException, ExecutionException {
        Callable<Integer> task = () -> 1;
        TaskType taskType = TaskType.IO;
        Task<Integer> t = Task.createTask(task, taskType);
        t.setFuture(new TestFuture<>(1));
        assertEquals(1, (int) t.get());
    }

    @Test
    void testGetWithTimeout() throws InterruptedException, ExecutionException, TimeoutException {
        Callable<Integer> task = () -> 1;
        TaskType taskType = TaskType.IO;
        Task<Integer> t = Task.createTask(task, taskType);
        t.setFuture(new TestFuture<>(1));
        assertEquals(1, (int) t.get(1, TimeUnit.SECONDS));
    }

    @Test
    void testCall() throws Exception {
        Callable<Integer> task = () -> 1;
        TaskType taskType = TaskType.IO;
        Task<Integer> t = Task.createTask(task, taskType);
        assertEquals(1, (int) t.call());
    }

    @Test
    void testSetFuture() {
        Callable<Integer> task = () -> 1;
        TaskType taskType = TaskType.IO;
        Task<Integer> t = Task.createTask(task, taskType);
        Future<Integer> f = new TestFuture<>(1);
        t.setFuture(f);
        assertEquals(f, t.getFuture());
    }

    @Test
    void testSetType() {
        Callable<Integer> task = () -> 1;
        TaskType taskType = TaskType.IO;
        Task<Integer> t = Task.createTask(task, taskType);
        TaskType newTaskType = TaskType.COMPUTATIONAL;
        t.setType(newTaskType);
        assertEquals(newTaskType, t.getType());
    }
}

class TestFuture<V> implements Future<V> {
    private final V value;

    TestFuture(V value) {
        this.value = value;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public V get() {
        return value;
    }

    @Override
    public V get(long timeout, TimeUnit unit) {
        return value;
    }
}

