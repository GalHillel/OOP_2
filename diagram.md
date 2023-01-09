classDiagram
direction BT
class Callable~V~ {
<<Interface>>
  + call() V
}
class Comparable~T~ {
<<Interface>>
  + compareTo(T) int
}
class CustomExecutor {
  + CustomExecutor() 
  + submit(Task~V~) Task~V~
  - submitTask(Task~?~) void
  + gracefullyTerminate() void
  + submit(Callable~T~, TaskType) Task~T~
   String currentMax
}
class Task~V~ {
  + Task(Callable~V~, TaskType) 
  + Task(Callable~V~) 
  + get() V
  + compareTo(Task) int
  + call() V
  + createTask(Callable~V~, TaskType) Task~V~
  + get(long, TimeUnit) V
   TaskType Type
   Future~V~ future
}
class TaskType {
<<enumeration>>
  - TaskType(int) 
  + values() TaskType[]
  + valueOf(String) TaskType
  - validatePriority(int) boolean
   int priority
   TaskType type
   int priorityValue
}

Task~V~  ..>  Callable~V~ 
Task~V~ "1" *--> "operation 1" Callable~V~ 
Task~V~  ..>  Comparable~T~ 
Task~V~ "1" *--> "Type 1" TaskType 
TaskType  ..>  Comparable~T~ 
