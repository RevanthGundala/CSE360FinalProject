// This class defins what an Effort object is

public class Effort {
    private int effortId;
    private int taskId;
    private String effortDescription;
    private int hoursWorked;

    public Effort(int effortId, int taskId, String effortDescription, int hoursWorked) {
        this.effortId = effortId;
        this.taskId = taskId;
        this.hoursWorked = hoursWorked;
        this.effortDescription = effortDescription;
    }

    public int getEffortId() {
        return effortId;
    }

    public void setEffortId(int effortId) {
        this.effortId = effortId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDescription(){
        return effortDescription;
    }

    public void setDescription(String effortDescription){
        this.effortDescription = effortDescription;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    
}
