package com.example;
// This class defins what an Effort object is

public class Effort {
    private int effortId;
    private int taskId;
    private String effortName;
    private String effortDescription;
    private int effortHours;

    public Effort(int effortId, int taskId, String effortName, String effortDescription, int effortHours) {
        this.effortId = effortId;
        this.taskId = taskId;
        this.effortName = effortName;
        this.effortHours = effortHours;
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

    public String getName(){
        return effortName;
    }

    public void setName(String effortName){
        this.effortName = effortName;
    }

    public String getDescription(){
        return effortDescription;
    }

    public void setDescription(String effortDescription){
        this.effortDescription = effortDescription;
    }

    public int geteffortHours() {
        return effortHours;
    }

    public void seteffortHours(int effortHours) {
        this.effortHours = effortHours;
    }

    
}
