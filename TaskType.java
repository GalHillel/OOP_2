/**
 * The TaskType enum represents the type of a task,
 * and is used to infer the priority of the task.
 * The TaskType enum has three values: COMPUTATIONAL, IO, and OTHER.
 * Each value has an associated integer priority, ranging from 1 to 3.
 * The TaskType enum also has methods for getting and setting the priority of a task,
 * and for getting the TaskType itself.
 * The TaskType enum has a private method for validating the priority of a task,
 * which checks that the priority is an integer value between 1 and 10.
 */
public enum TaskType {
    COMPUTATIONAL(1) {
        @Override
        public String toString() {
            return "Computational Task";
        }
    },
    IO(2) {
        @Override
        public String toString() {
            return "IO-Bound Task";
        }
    },
    OTHER(3) {
        @Override
        public String toString() {
            return "Unknown Task";
        }
    };

    private int typePriority;

    private TaskType(int priority) {
        if (validatePriority(priority)) {
            typePriority = priority;
        } else {
            throw new IllegalArgumentException("Priority is not an integer");
        }
    }

    public void setPriority(int priority) {
        if (validatePriority(priority)) {
            this.typePriority = priority;
        } else {
            throw new IllegalArgumentException("Priority is not an integer");
        }
    }

    public int getPriorityValue() {
        return typePriority;
    }

    public TaskType getType() {
        return this;
    }

    /**
     * priority is represented by an integer value, ranging from 1 to 10
     *
     * @param priority
     * @return whether the priority is valid or not
     */
    private static boolean validatePriority(int priority) {
        if (priority < 1 || priority > 10) {
            return false;
        }
        return true;
    }
}
