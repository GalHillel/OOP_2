package EX2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyFutureTask<V> extends FutureTask<V> implements Comparable<MyFutureTask<V>> {
    private Task<V> task;


    public MyFutureTask(Task<V> task) {
        super(task);
        this.task=task;
    }


    @Override
    public int compareTo(MyFutureTask<V> t1) {
        return t1.task.getType().compareTo(task.getType());
    }
}
