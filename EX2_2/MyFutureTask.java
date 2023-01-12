package EX2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyFutureTask<V> extends FutureTask<V> implements Comparable<MyFutureTask<V>> {
    private Task<V> task;


    public MyFutureTask(Callable<V> callable) {
        super(callable);
    }

    public MyFutureTask(Runnable runnable, V result) {
        super(runnable, result);
    }

    @Override
    public int compareTo(MyFutureTask<V> t1) {
        return t1.task.getType().compareTo(task.getType());
    }
}
