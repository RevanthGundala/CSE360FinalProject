// This class is responsible for all database operations related to efforts

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskSystem {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    // Constructor
    public TaskSystem(){
        dbUrl = "jdbc:mysql://localhost:42069/loginuser"; // 'loginuser' is the name of the database on my local computer
        dbUsername = "root";
        dbPassword = "password";
    }

    // this method creates a new task
    public boolean createTask(String taskName, String taskDescription, int estimatedHours){
        boolean createTaskSuccess = false;
        try{
            String sql = "INSERT INTO tasks (task_name, task_description, estimated_hours) VALUES (?, ?, ?)";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, taskName);
            stmt.setString(2, taskDescription);
            stmt.setInt(3, estimatedHours);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                createTaskSuccess = true;
                System.out.println("Task inserted successfully!");
            } else {
                System.out.println("Failed to insert task.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return createTaskSuccess;
    }

    // this method gets a specific task from the database
    public Task getSpecificTask(int taskId){
        Task task = null;
        try{
            String sql = "SELECT * FROM tasks WHERE task_id = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int task_id = rs.getInt("task_id");
                String task_name = rs.getString("task_name");
                String task_description = rs.getString("task_description");
                int estimatedHours = rs.getInt("estimated_hours");

                task = new Task(task_id, task_name, task_description, estimatedHours);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return task;
    }

    // this method gets a task from user input - "task search feature"
    public Task getTaskFromUserInput(int taskId, String taskName, String taskDescription, int estimatedHours){
        Task task = null;
        try{
            String sql = "SELECT * FROM tasks WHERE task_id = ? OR task_name = ? OR task_description = ? OR estimated_hours = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);
            stmt.setString(2, taskName);
            stmt.setString(3, taskDescription);
            stmt.setInt(4, estimatedHours);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int task_id = rs.getInt("task_id");
                String task_name = rs.getString("task_name");
                String task_description = rs.getString("task_description");
                int estimated_hours = rs.getInt("estimated_hours");

                task = new Task(task_id, task_name, task_description, estimated_hours);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return task;
    }
    
    // this method gets all tasks in the database
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            // Execute SELECT statement
            String sql = "SELECT * FROM tasks";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Iterate through the result set and create Task objects
            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                String taskName = rs.getString("task_name");
                String taskDescription = rs.getString("task_description");
                int estimatedHours = rs.getInt("estimated_hours");

                Task task = new Task(taskId, taskName, taskDescription, estimatedHours);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // this method updates a task with new information
    public boolean updateTask(int taskId, String newTaskName, String newTaskDescription, int newEstimatedHours){
        boolean updateTaskSuccess = false;
        try{
            String sql = "UPDATE tasks SET task_name = ?, task_description = ?, estimated_hours = ? WHERE task_id = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            // todo: need to reorder these
            stmt.setString(1, newTaskName);
            stmt.setString(2, newTaskDescription);
            stmt.setInt(3, newEstimatedHours);
            stmt.setInt(4, taskId);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                updateTaskSuccess = true;
                System.out.println("Task updated successfully!");
            } else {
                System.out.println("Failed to update task.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return updateTaskSuccess;
    }

    // this method deletes a task from the database
    public boolean deleteTask(int taskId){
        boolean deleteTaskSuccess = false;
        try{
            String sql = "DELETE FROM tasks WHERE task_id = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                deleteTaskSuccess = true;
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("Failed to delete task.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return deleteTaskSuccess;
    }
}
