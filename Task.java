// This class defins what a Task object is

public class Task {
    private int taskId;
    private String taskName;
    private String taskDescription;

    public Task(int taskId, String taskName, String taskDescription, int estimatedHours) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
