// This class defins what an Effort object is

public class Effort {
    private int effortId;
    private int taskId;
    private String effortName;
    private String effortDescription;
    private int effortHours;


    // Constructor
    public Effort(int effortId, int taskId, String effortName, String effortDescription, int effortHours) {
        this.effortId = effortId;
        this.taskId = taskId;
        this.effortName = effortName;
        this.effortHours = effortHours;
        this.effortDescription = effortDescription;
    }

    // Getters and Setters
    public int getEffortId() {
        return effortId;
    }
    
    // Sets ID of effort object
    public void setEffortId(int effortId) {
        this.effortId = effortId;
    }

    // Returns task ID
    public int getTaskId() {
        return taskId;
    }

    // Set ID of task object
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    // Get name of effort object
    public String getName(){
        return effortName;
    }

    // Set name of effort object
    public void setName(String effortName){
        this.effortName = effortName;
    }

    // Get description of effort object
    public String getDescription(){
        return effortDescription;
    }

    // Set description of effort object
    public void setDescription(String effortDescription){
        this.effortDescription = effortDescription;
    }

    // Get hours attributed to effort object
    public int geteffortHours() {
        return effortHours;
    }

    // Sets hours attributed to effort object
    public void seteffortHours(int effortHours) {
        this.effortHours = effortHours;
    }
}
